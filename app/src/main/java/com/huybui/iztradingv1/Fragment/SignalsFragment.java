package com.huybui.iztradingv1.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.huybui.iztradingv1.Activity.SigninActivity;
import com.huybui.iztradingv1.Adapter.SignalsAdapter;
import com.huybui.iztradingv1.Model.Order;
import com.huybui.iztradingv1.R;
import com.huybui.iztradingv1.Service.NotificationService;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SignalsFragment extends Fragment {

    protected Context mContext;

    private EditText edtSearch;

    private ImageView imgvSearch;

    protected ArrayList<Order> signals = new ArrayList<>();
    protected ArrayList<Order> runningSignals = new ArrayList<>();

    private TextView  tvTotal, tvWinsRate, tvWinsTotal;

    protected SignalsAdapter adapter;

    protected NotificationService notificationService;

    public SignalsFragment(Context context){
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootview = inflater.inflate(R.layout.fragment_signals, container, false);

        tvTotal = rootview.findViewById(R.id.tv_total_signals);
        tvWinsRate = rootview.findViewById(R.id.tv_wins_rate);
        tvWinsTotal = rootview.findViewById(R.id.tv_wins_total);
        edtSearch = rootview.findViewById(R.id.search_view);
        imgvSearch = rootview.findViewById(R.id.imgv_search);

        RecyclerView rcvSignals = rootview.findViewById(R.id.item_signal);

        rcvSignals.setLayoutManager(new GridLayoutManager(rootview.getContext(),1));

        adapter = new SignalsAdapter(rootview.getContext());

        getSignalsList();

        imgvSearch.setOnClickListener(view-> searchPair());

        adapter.setData(signals);

        rcvSignals.setAdapter(adapter);

        edtSearch.setMaxWidth(8);

        return rootview;
    }

    private void searchPair() {
        getSignalsList();
    }

    private void getSignalsList() {
        // Read from the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("orders");
        Query query = myRef.orderByChild("time");
        query.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                signals.clear();
                String pair = edtSearch.getText().toString().trim();

                if (!pair.matches("")) {
                    SignalsFragment.this.signals.clear();
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        if (Objects.requireNonNull(dataSnapshot.getValue(Order.class)).getPair().toUpperCase(Locale.ROOT).contains(pair.toUpperCase(Locale.ROOT))) {
                            signals.add(dataSnapshot.getValue(Order.class));
                        }
                    }
                    if (signals.size()==0) {
                        new SigninActivity().alert("Không có kết quả!",mContext);
                    }
                } else {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        signals.add(dataSnapshot.getValue(Order.class));
                    }
                }

                //so pips win
                AtomicReference<Double> pips = new AtomicReference<>((double) 0);
                //so lenh win
                AtomicInteger winNo = new AtomicInteger();
                try {
                    signals.forEach(o->{
                        if(o.getComment() != null && !o.getComment().trim().isEmpty() && !o.getComment().trim().equals("cabinet") && !o.getComment().trim().isEmpty()) {
                            double prf;
                            double open = Double.parseDouble(o.getPrice().trim());
                            double close = Double.parseDouble(o.getComment().trim());
                            if (o.getType().trim().equals("SELL")) {
                                prf = open - close;
                            } else {
                                prf = close - open;
                            }
                            if (prf>0) winNo.getAndIncrement();
                            pips.set(prf + pips.get());
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

                float winRate = Float.parseFloat(String.valueOf(winNo)) / (float) signals.size();

                DecimalFormat df = new DecimalFormat("#.###");

                String totalSignalsStr = String.valueOf(signals.size());
                String winTotalsStr = String.valueOf(winNo);
                String winRateStr = "("+df.format(winRate*100)+"%)";
                tvTotal.setText(totalSignalsStr);
                tvWinsTotal.setText(winTotalsStr);
                tvWinsRate.setText(winRateStr);

                Collections.reverse(Objects.requireNonNull(signals));

                adapter.notifyDataSetChanged();

                runningSignals = runningOrder(signals);

                if (runningSignals.size() == 0) {
                    return;
                }

                Order lastestOrder = runningSignals.get(0);
                for (int i = 1; i < runningSignals.size(); i++) {
                    if (Integer.parseInt(lastestOrder.getTime()) < Integer.parseInt(runningSignals.get(i).getTime())) {
                        lastestOrder = runningSignals.get(i);
                    }
                }

                System.out.println("current: "+new Timestamp(System.currentTimeMillis()).getTime()/1000);
                System.out.println("lastest order: "+Integer.parseInt(lastestOrder.getTime()));

                if (Math.abs(new Timestamp(System.currentTimeMillis()).getTime()/1000 - Integer.parseInt(lastestOrder.getTime())) > 7195 & Math.abs(new Timestamp(System.currentTimeMillis()).getTime()/1000 - Integer.parseInt(lastestOrder.getTime())) < 7205) {
                    notiNewOrder(lastestOrder);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    protected ArrayList<Order> runningOrder(ArrayList<Order> signals){
        ArrayList<Order> listRunningOrders = new ArrayList<>();

        signals.forEach(o->{
            if (o.getComment().matches("0")) {
                listRunningOrders.add(o);
            }
        });
        return listRunningOrders;
    }

    private void notiNewOrder(Order order){
        notificationService = new NotificationService(mContext);

        String title = order.getType() + " " + order.getPair() + " " + order.getPrice();
        String body = "SL: " + order.getSl() + "  TP: " + order.getTp();
        notificationService.sendNoti(title, body);
    }
}