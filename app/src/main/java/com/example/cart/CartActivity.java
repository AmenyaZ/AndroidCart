package com.example.cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cart.adapter.CartAdapter;
import com.example.cart.data.Cart;
import com.example.cart.helper.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class CartActivity extends AppCompatActivity {

    private List<Cart> cartList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    public static DatabaseHelper databaseHelper;

    ConstraintLayout constraintLayout;
TextView textViewTotal;
Button buttonCheckout;

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String PATH_TO_SERVER = "http://192.168.43.17/www.android.com/braintree/index.php";
    private String clientToken;
    private static final int BRAINTREE_REQUEST_CODE = 4949;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getClientTokenFromServer();
        recyclerView = findViewById(R.id.rv_cart);
        databaseHelper=new DatabaseHelper(this);
        cartAdapter = new CartAdapter(cartList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cartAdapter);
        constraintLayout=findViewById(R.id.constraintLayout);
        textViewTotal = findViewById(R.id.textViewTotal);
        buttonCheckout = findViewById(R.id.btn_checkout);

        textViewTotal.setText(String.valueOf(databaseHelper.getTotal()));

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
            Snackbar snackbar = Snackbar.make(constraintLayout,"Cart Empty",Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(constraintLayout,"Feature coming soon. No worries :-)",Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

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
                Intent intentR = new Intent(CartActivity.this,CartActivity.class);
                finish();
                startActivity(intentR);
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

    private void getClientTokenFromServer(){
        AsyncHttpClient androidClient = new AsyncHttpClient();
        androidClient.get(PATH_TO_SERVER, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(TAG, getString(R.string.token_failed) + responseString);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseToken) {
                Log.d(TAG, "Client token: " + responseToken);
                clientToken = responseToken;
            }
        });
    }



}
