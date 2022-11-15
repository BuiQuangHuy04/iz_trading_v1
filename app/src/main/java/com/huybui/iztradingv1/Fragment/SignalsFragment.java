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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SignalsFragment extends Fragment {

    protected Context mContext;

    private EditText edtSearch;

    private ImageView imgvSearch;

    protected ArrayList<Order> signals = new ArrayList<>();

    private TextView  tvTotal, tvWinsRate, tvWinsTotal;

    protected SignalsAdapter adapter;

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

        adapter.setData((List<Order>) signals);

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

                notiNewOrder(signals);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    private void notiNewOrder(ArrayList<Order> signals){
        NotificationService notificationService = new NotificationService(mContext);
        if (signals.size() == 0) {
            return;
        }
        String title = signals.get(0).getType() + " " + signals.get(0).getPair() + " " + signals.get(0).getPrice();
        String body = "SL: " + signals.get(0).getSl() + "  TP: " + signals.get(0).getTp();
        notificationService.sendNoti(title, body);
    }
}