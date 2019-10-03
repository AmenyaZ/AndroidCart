package com.example.cart;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.example.cart.adapter.CartAdapter;
import com.example.cart.data.Cart;
import com.example.cart.helper.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
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
    private static final String PATH_TO_SERVER = "http://192.168.74.212/www.android.com/braintree/index.php";
    private String clientToken;
    private static final int BRAINTREE_REQUEST_CODE = 4949;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getClientTokenFromServer();
        recyclerView = findViewById(R.id.rv_cart);
        databaseHelper=new DatabaseHelper(this);
        cartAdapter = new CartAdapter(cartList,this);

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
            String id = String.valueOf(cursor.getInt(0));
            String name = cursor.getString(1);
            String price = cursor.getString(2);
            String quantity = cursor.getString(3);

            cart.setId(id);
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
                onBraintreeSubmit(v);
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

    private void sendPaymentNonceToServer(String paymentNonce){
        RequestParams params = new RequestParams("NONCE", paymentNonce);
        AsyncHttpClient androidClient = new AsyncHttpClient();
        androidClient.post(PATH_TO_SERVER+"?&&amount="+databaseHelper.getTotal(), params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(TAG, "Error: Failed to create a transaction");
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d(TAG, "Output " + responseString);
                databaseHelper.clearData();
                Intent intent = new Intent(CartActivity.this,CartActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BRAINTREE_REQUEST_CODE) {
            if (RESULT_OK == resultCode) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                String paymentNonce = result.getPaymentMethodNonce().getNonce();
                //send to your server
                Log.d(TAG, "Testing the app here");
                sendPaymentNonceToServer(paymentNonce);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.d(TAG, "User cancelled payment");
            } else {
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.d(TAG, " error exception: " + error);
            }
        }
    }

    public void onBraintreeSubmit(View view){
        DropInRequest dropInRequest = new DropInRequest().clientToken(clientToken);
        startActivityForResult(dropInRequest.getIntent(this), BRAINTREE_REQUEST_CODE);
    }
}
