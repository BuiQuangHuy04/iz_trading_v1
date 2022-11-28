package com.huybui.iztradingv1.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.huybui.iztradingv1.Model.Order;
import com.huybui.iztradingv1.R;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SignalsAdapter extends RecyclerView.Adapter<SignalsAdapter.OrderViewholder> {

    private final Context mContext;
    private List<Order> mOrderList;

    public SignalsAdapter(Context context) {
        this.mContext = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Order> list) {
        this.mOrderList = list;
        notifyDataSetChanged(); //load n build data
    }

    @NonNull
    @Override
    public OrderViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewholder holder, int position) {
        Order order = mOrderList.get(position);

        if (order == null) return;

        int color = setColor(order);

        holder.cardvContainer.setCardBackgroundColor(color);
        holder.cardvContainer.setRadius(25);
        holder.txtvPair.setText(order.getPair());
        holder.txtvTime.setText(convertTimeStamp(order.getTime()));
        holder.txtvType.setText(order.getType());
        holder.txtvPrice.setText(order.getPrice());
        holder.txtvSl.setText(order.getSl());
        holder.txtvTp.setText(order.getTp());

        holder.cardvContainer.setOnClickListener(view -> {
//            String hexColor = "#"+Integer.toHexString(color).substring(2);
            System.out.println(getSignalResult(order));
            alert(getSignalResult(order),mContext);
        });
    }

    private String getSignalResult(Order order) {

        if (order.getComment().matches("") | order.getComment().matches("0")) {
            return "Lệnh đang chạy";
        }

        float openPrice = Float.parseFloat(order.getPrice());
        float closePrice = Float.parseFloat(order.getComment());
        float pips = 0;
        String type = order.getType().trim();
        String result = "";

        if (type.equalsIgnoreCase("SELL")) {
            pips = 10*(openPrice-closePrice);
        }

        if (type.equalsIgnoreCase("BUY")) {
            pips = 10*(closePrice-openPrice);
        }

        if (order.getPair().toUpperCase(Locale.ROOT).contains("CRUDE")) {
            pips = 10*pips;
        }

        if (order.getPair().toUpperCase(Locale.ROOT).contains("EUR") | order.getPair().toUpperCase(Locale.ROOT).contains("GBP")) {
            pips = 1000*pips;
        }

        if (pips > 0) {
            result = "Lệnh " + order.getTicket() + " lãi " + pips + " pip";
        }

        if (pips < 0) {
            result = "Lệnh " + order.getTicket() + " lỗ " + Math.abs(pips) + " pip";
        }

        if (pips == 0) {
            result = "Lệnh " + order.getTicket() + " hòa";
        }

        return result;
    }

    private int setColor(Order order) {
        if (order == null) {
            return 0;
        }

        int color = Color.parseColor("#ffd966");

        if (order.getComment().matches("0")) {
            return Color.parseColor("#28498C");
        }

        float openPrice = Float.parseFloat(order.getPrice());
        float closePrice = Float.parseFloat(order.getComment());
        float takeProfit;
        float stopLoss;
        String type = order.getType().trim();

        if (order.getTp().equalsIgnoreCase("open")) {
            takeProfit = Float.parseFloat(order.getComment());
        } else takeProfit= Float.parseFloat(order.getTp());
        if (order.getSl().equalsIgnoreCase("open")) {
            stopLoss = Float.parseFloat(order.getComment());
        } else stopLoss= Float.parseFloat(order.getSl());

        if (type.equalsIgnoreCase("SELL")) {
            if (openPrice > closePrice) {
                if (closePrice == takeProfit) {
                    color = Color.parseColor("#7e57c0");
                } else {
                    color = Color.parseColor("#1c8e40");
                }
            }
            if (openPrice < closePrice) {
                if (closePrice == stopLoss) {
                    color = Color.parseColor("#0c04aa");
                } else
                    color = Color.parseColor("#cc0000");
            }
        }

        if (type.equalsIgnoreCase("BUY")) {
            if (openPrice < closePrice) {
                if (closePrice == takeProfit) {
                    color = Color.parseColor("#7e57c0");
                } else {
                    color = Color.parseColor("#1c8e40");
                }
            }
            if (openPrice > closePrice) {
                if (closePrice == stopLoss) {
                    color = Color.parseColor("#0c04aa");
                } else
                    color = Color.parseColor("#cc0000");
            }
        }
        return color;
    }

    public void alert(String mesStr, Context context) {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);

        dlgAlert.setMessage(mesStr);
        dlgAlert.setTitle("Thông báo");
        dlgAlert.setPositiveButton("OK", null);

        dlgAlert.show();
    }

    private String convertTimeStamp(String time) {
        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yy HH:mm:ss", Locale.forLanguageTag("vi-VN"));
        Date date = new Date(Long.parseLong(time+"000"));
        return sf.format(date);
    }

    @Override
    public int getItemCount() {
        if (mOrderList != null) {
            return mOrderList.size();
        }
        return 0;
    }

    public static class OrderViewholder extends RecyclerView.ViewHolder {

        private final CardView cardvContainer;
        private final TextView txtvPair;
        private final TextView txtvTime;
        private final TextView txtvType;
        private final TextView txtvPrice;
        private final TextView txtvSl;
        private final TextView txtvTp;

        public OrderViewholder(@NonNull View itemView) {
            super(itemView);

            cardvContainer = itemView.findViewById(R.id.signal_container);
            txtvPair = itemView.findViewById(R.id.txtv_pair);
            txtvTime = itemView.findViewById(R.id.txtv_time);
            txtvType = itemView.findViewById(R.id.txtv_type);
            txtvPrice = itemView.findViewById(R.id.txtv_price);
            txtvSl = itemView.findViewById(R.id.txtv_sl);
            txtvTp = itemView.findViewById(R.id.txtv_tp);
        }
    }
}
