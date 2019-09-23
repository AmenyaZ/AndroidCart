package com.example.cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart,menu);
      //  getActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_empty:
                databaseHelper.clearData();
                break;
            case android.R.id.home:
                Intent intent = new Intent(CartActivity.this,MainActivity.class);
                finish();
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



}
