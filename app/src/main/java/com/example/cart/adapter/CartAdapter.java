package com.example.cart.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cart.R;
import com.example.cart.data.Cart;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<Cart> cartList;

    public CartAdapter(List<Cart> cartList) {
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_row, parent, false);
        return new CartAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        final Cart cart = cartList.get(position);
        holder.textViewName.setText(cart.getName());
        holder.textViewQuantity.setText(cart.getQuantity());
        holder.textViewPrice.setText(cart.getPrice());
        holder.textViewTotal.setText(String.valueOf(cart.getTotal()));
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewName,textViewQuantity,textViewPrice,textViewTotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName=itemView.findViewById(R.id.textViewName);
            textViewQuantity=itemView.findViewById(R.id.textViewQty);
            textViewPrice=itemView.findViewById(R.id.textViewPrice);
            textViewTotal=itemView.findViewById(R.id.textViewTotal);
        }
    }
}
