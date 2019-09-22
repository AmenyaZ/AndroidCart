package com.example.cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.cart.helper.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Utensils> utensilsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private UtensilsAdapter utensilsAdapter;
    public static DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        databaseHelper = new DatabaseHelper(this);

        utensilsAdapter = new UtensilsAdapter(utensilsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(utensilsAdapter);
        prepareUtensilsData();
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        MenuItem menuItem= menu.findItem(R.id.shopping_cart);
       // int count = databaseHelper.cartQty();
        Cursor cursor = databaseHelper.cartQty();
        System.out.println(cursor.getCount());
        menuItem.setIcon(buildCounterDrawable(cursor.getCount(),R.drawable.ic_shopping_cart));
        return true;
    }

    private Drawable buildCounterDrawable(int count, int image) {
        LayoutInflater layoutInflater= LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.cart_layout,null);
        view.setBackgroundResource(image);
        if (count==0){
            View counterTextPanel = view.findViewById(R.id.counterValuePanel);
            counterTextPanel.setVisibility(View.GONE);
        }else {
            TextView textView = view.findViewById(R.id.count);
            textView.setText(String.valueOf(count));
        }
        view.measure(
                View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED));
        view.layout(0,0,view.getMeasuredWidth(),view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return new BitmapDrawable(getResources(),bitmap);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.shopping_cart){
            //navigate to cart
        }
        return super.onOptionsItemSelected(item);
    }

    private void prepareUtensilsData() {
        Utensils utensils = new Utensils("1","Wooden Utensils", "2000", R.drawable.wooden_utensils);
        utensilsList.add(utensils);

        utensils = new Utensils("2","Tea Spoon", "65", R.drawable.tea_spoon);
        utensilsList.add(utensils);

        utensils = new Utensils("3","Spoons Pack", "1500", R.drawable.spoons_pack);
        utensilsList.add(utensils);

        utensils = new Utensils("4","Spoons", "200", R.drawable.spoons);
        utensilsList.add(utensils);

        utensils = new Utensils("5","Utensils Rack", "10,000", R.drawable.rack);
        utensilsList.add(utensils);

        utensils = new Utensils("6","Pressure Cooker", "1890", R.drawable.pressure_cooker);
        utensilsList.add(utensils);

        utensils = new Utensils("7","Pots", "500", R.drawable.pots);
        utensilsList.add(utensils);

        utensils = new Utensils("8","Plate", "280", R.drawable.plate);
        utensilsList.add(utensils);

        utensils = new Utensils("9","Pan", "700", R.drawable.pan);
        utensilsList.add(utensils);

        utensils = new Utensils("10","Knife", "150", R.drawable.knife);
        utensilsList.add(utensils);

        utensils = new Utensils("11","Iron Pan", "550", R.drawable.iron_pan);
        utensilsList.add(utensils);

        utensils = new Utensils("12","Hot Pot", "450", R.drawable.hot_pot);
        utensilsList.add(utensils);

        utensils = new Utensils("13","Grater", "350", R.drawable.grater);
        utensilsList.add(utensils);

        utensils = new Utensils("14","Fork, Spoon & Knife", "600", R.drawable.fork_spoon_knife);
        utensilsList.add(utensils);

        utensils = new Utensils("15","Dipper", "200", R.drawable.dipper);
        utensilsList.add(utensils);

        utensils = new Utensils("16","Cutting Board", "350", R.drawable.cutting_board);
        utensilsList.add(utensils);

        utensils = new Utensils("17","Cooking Stick", "350", R.drawable.cooking_stick);
        utensilsList.add(utensils);

        utensils = new Utensils("18","Cooking Pots", "350", R.drawable.cooking_pots);
        utensilsList.add(utensils);

        utensils = new Utensils("19","Container", "350", R.drawable.container);
        utensilsList.add(utensils);

        utensils = new Utensils("20","Barbecue", "5000", R.drawable.barbercue);
        utensilsList.add(utensils);

        utensilsAdapter.notifyDataSetChanged();
    }


}
