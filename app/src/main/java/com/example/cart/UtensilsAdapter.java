package com.example.cart;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UtensilsAdapter extends RecyclerView.Adapter<UtensilsAdapter.ViewHolder> {

    private List<Utensils> utensilsList;

    public UtensilsAdapter(List<Utensils> utensilsList) {
        this.utensilsList = utensilsList;
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
                }else {
                    MainActivity.databaseHelper.updateData(utensils.getId(),String.valueOf(MainActivity.databaseHelper.getProductQuantity(utensils.getId())+1));
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
