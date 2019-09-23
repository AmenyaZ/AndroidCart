package com.example.cart.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cart.MainActivity;
import com.example.cart.R;
import com.example.cart.data.Utensils;

import java.util.List;

public class UtensilsAdapter extends RecyclerView.Adapter<UtensilsAdapter.ViewHolder> {

    private List<Utensils> utensilsList;
    private Context context;

    public UtensilsAdapter(List<Utensils> utensilsList, Context context) {
        this.utensilsList = utensilsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.utensil_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UtensilsAdapter.ViewHolder holder, int position) {
        final Utensils utensils = utensilsList.get(position);
        holder.textViewName.setText(utensils.getName());
        holder.textViewPrice.setText(utensils.getPrice());
        holder.imageView.setImageResource(utensils.getImage());
        holder.buttonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = MainActivity.databaseHelper.checkProduct(utensils.getId());
                if (cursor.getCount()==0){
                    MainActivity.databaseHelper.insertData(utensils.getId(),utensils.getName(),utensils.getPrice(),"1");
                    context.startActivity(new Intent(context, MainActivity.class));
                    ((Activity)context).finish();
                }else {
                    MainActivity.databaseHelper.updateData(utensils.getId(),String.valueOf(MainActivity.databaseHelper.getProductQuantity(utensils.getId())+1));
                    context.startActivity(new Intent(context, MainActivity.class));
                    ((Activity)context).finish();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return utensilsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
         public TextView textViewName,textViewPrice;
         public ImageView imageView;
         public Button buttonCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPrice=itemView.findViewById(R.id.textViewPrice);
            imageView=itemView.findViewById(R.id.imageView);
            buttonCart=itemView.findViewById(R.id.button);
        }
    }
}
