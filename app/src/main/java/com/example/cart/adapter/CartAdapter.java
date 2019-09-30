package com.example.cart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cart.CartActivity;
import com.example.cart.R;
import com.example.cart.data.Cart;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<Cart> cartList;
    private Context context;

//    public CartAdapter(List<Cart> cartList) {
//        this.cartList = cartList;
//    }


    public CartAdapter(List<Cart> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_row, parent, false);
        return new CartAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartAdapter.ViewHolder holder, final int position) {
        final Cart cart = cartList.get(position);
        holder.textViewName.setText(cart.getName()+" X "+cart.getQuantity());
       // holder.textViewQuantity.setText(cart.getQuantity());
        holder.textViewPrice.setText(cart.getPrice());
        holder.textViewTotal.setText(String.valueOf(cart.getTotal()));
        holder.buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartActivity.databaseHelper.deleteData(cart.getId());
                cartList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,getItemCount());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewName,textViewPrice,textViewTotal;
        public Button buttonRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName=itemView.findViewById(R.id.textViewName);
           // textViewQuantity=itemView.findViewById(R.id.textViewQty);
            textViewPrice=itemView.findViewById(R.id.textViewPrice);
            textViewTotal=itemView.findViewById(R.id.textViewTotal);
            buttonRemove=itemView.findViewById(R.id.btn_remove);
        }
    }
}
