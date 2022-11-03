package com.huybui.iztradingv1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huybui.iztradingv1.Model.Order;
import com.huybui.iztradingv1.R;

import java.util.List;

public class SignalsAdapter extends RecyclerView.Adapter<SignalsAdapter.OrderViewholder> {

    private Context mContext;
    private List<Order> mOrderList;

    public SignalsAdapter(Context context) {
        this.mContext = context;
    }

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

        holder.txtvPair.setText(order.getPair());
        holder.txtvTime.setText(order.getTime());
        holder.txtvType.setText(order.getType());
        holder.txtvPrice.setText(order.getPrice());
        holder.txtvSl.setText(order.getSl());
        holder.txtvTp.setText(order.getTp());
    }

    @Override
    public int getItemCount() {
        if (mOrderList != null) {
            return mOrderList.size();
        }
        return 0;
    }

    public class OrderViewholder extends RecyclerView.ViewHolder {

        private TextView txtvPair, txtvTime, txtvType, txtvPrice, txtvSl, txtvTp;

        public OrderViewholder(@NonNull View itemView) {
            super(itemView);

            txtvPair = itemView.findViewById(R.id.txtv_pair);
            txtvTime = itemView.findViewById(R.id.txtv_time);
            txtvType = itemView.findViewById(R.id.txtv_type);
            txtvPrice = itemView.findViewById(R.id.txtv_price);
            txtvSl = itemView.findViewById(R.id.txtv_sl);
            txtvTp = itemView.findViewById(R.id.txtv_tp);

        }
    }
}
