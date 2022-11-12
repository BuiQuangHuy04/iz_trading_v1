package com.huybui.iztradingv1.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.huybui.iztradingv1.Activity.SigninActivity;
import com.huybui.iztradingv1.Model.Order;
import com.huybui.iztradingv1.R;

import java.util.List;

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
        holder.txtvTime.setText(order.getTime());
        holder.txtvType.setText(order.getType());
        holder.txtvPrice.setText(order.getPrice());
        holder.txtvSl.setText(order.getSl());
        holder.txtvTp.setText(order.getTp());

        int finalColor = color;
        holder.cardvContainer.setOnClickListener(view -> {
            String hexColor = "#"+Integer.toHexString(finalColor).substring(2);
            new SigninActivity().alert(getSignalResult(hexColor),mContext);
        });
    }

    public String getSignalResult(String hexColor) {
        String message = "";
        switch (hexColor.toLowerCase()) {
            case "#7e57c0":
                message = "Màu tím nhạt là lệnh đã chạm điểm chốt lãi";
                break;
            case "#1c8e40":
                message = "Màu xanh lá là lệnh đã có lãi";
                break;
            case "#0c04aa":
                message = "Màu xanh dương là lệnh đã chạm điểm cắt lỗ";
                break;
            case "#cc0000":
                message = "Màu đỏ là lệnh bị lỗ";
                break;
            case "#ffd966":
                message = "Màu vàng là lệnh hòa";
                break;
            case "#28498c":
                message = "Màu xanh nước là lệnh đang chạy";
                break;
        }
        return message;
    }

    private int setColor(Order order) {
        if (order == null) {
            return 0;
        }

        int color = Color.parseColor("#ffd966");

        if (order.getComment().matches("")) {
            return color = Color.parseColor("#28498C");
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
