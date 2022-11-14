package com.huybui.iztradingv1.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.huybui.iztradingv1.Adapter.SignalsAdapter;
import com.huybui.iztradingv1.Model.Order;
import com.huybui.iztradingv1.R;
import com.huybui.iztradingv1.Service.NotificationService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SignalsFragment extends Fragment {

    protected Context mContext;

    protected int checkPosition = 0;

//    protected ArrayList<Order> signals = new ArrayList<>();

    private TextView  tvTotal, tvWinsRate, tvWinsTotal;

    public SignalsFragment(Context context){
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootview = inflater.inflate(R.layout.fragment_signals, container, false);

        tvTotal = rootview.findViewById(R.id.tv_total_signals);
        tvWinsRate = rootview.findViewById(R.id.tv_wins_rate);
        tvWinsTotal = rootview.findViewById(R.id.tv_wins_total);

        RecyclerView rcvSignals = rootview.findViewById(R.id.item_signal);

        rcvSignals.setLayoutManager(new GridLayoutManager(rootview.getContext(),1));

        SignalsAdapter adapter = new SignalsAdapter(rootview.getContext());

        ArrayList<Order> signals;

        checkPosition = 1;

        signals = getSignalsList(adapter);

        adapter.setData(signals);

        rcvSignals.setAdapter(adapter);

        return rootview;
    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        checkPosition = 1;
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        checkPosition = 0;
//    }

    private ArrayList<Order> getSignalsList(SignalsAdapter adapter) {
        ArrayList<Order> signalsList = new ArrayList<>();
        // Read from the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("orders");
        Query query = myRef.orderByChild("time");
        query.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                signalsList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    signalsList.add(dataSnapshot.getValue(Order.class));
                }

                //so pips win
                AtomicReference<Double> pips = new AtomicReference<>((double) 0);
                //so lenh win
                AtomicInteger winNo = new AtomicInteger();
                try {
                    signalsList.forEach(o->{
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

                float winRate = Float.parseFloat(String.valueOf(winNo)) / (float) signalsList.size();

                DecimalFormat df = new DecimalFormat("#.###");

                String totalSignalsStr = String.valueOf(signalsList.size());
                String winTotalsStr = String.valueOf(winNo);
                String winRateStr = "("+df.format(winRate*100)+"%)";
                tvTotal.setText(totalSignalsStr);
                tvWinsTotal.setText(winTotalsStr);
                tvWinsRate.setText(winRateStr);

                Collections.reverse(Objects.requireNonNull(signalsList));

                adapter.notifyDataSetChanged();

//                if (checkPosition == 1) {
                    notiNewOrder(signalsList);
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
        return signalsList;
    }

    private void notiNewOrder(ArrayList<Order> signals){
        NotificationService notificationService = new NotificationService(mContext);
        String title = signals.get(0).getType() + " " + signals.get(0).getPair() + " " + signals.get(0).getPrice();
        String body = "SL: " + signals.get(0).getSl() + "  TP: " + signals.get(0).getTp();
        notificationService.sendNoti(title, body);
    }
}