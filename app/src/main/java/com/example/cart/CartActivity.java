package com.example.cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cart.adapter.CartAdapter;
import com.example.cart.data.Cart;
import com.example.cart.helper.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private List<Cart> cartList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    public static DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView = findViewById(R.id.rv_cart);
        databaseHelper=new DatabaseHelper(this);
        cartAdapter = new CartAdapter(cartList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cartAdapter);

        Cursor cursor = databaseHelper.getCartData();
        cartList.clear();
        while (cursor.moveToNext()) {
            Cart cart = new Cart();
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String price = cursor.getString(2);
            String quantity = cursor.getString(3);

            cart.setName(name);
            cart.setPrice(price);
            cart.setQuantity(quantity);
            cartList.add(cart);
        }
        cartAdapter.notifyDataSetChanged();
        if (cartList.size() == 0) {
            Toast.makeText(this, "Cart is Empty", Toast.LENGTH_LONG).show();
        }

    }
}
