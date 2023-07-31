package com.example.thebloomroom.Activities.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thebloomroom.Classes.Cart;
import com.example.thebloomroom.Classes.RecViewAdapters.CartPageRecViewAdapter;
import com.example.thebloomroom.R;

public class CartPage extends AppCompatActivity {
    private Button btnPay;
    private ImageView backIcon;
    private RecyclerView cartList;
    private TextView total;
    private int id;
    private double cartTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);

        btnPay = findViewById(R.id.btn_pay_now);
        backIcon = findViewById(R.id.ic_back_arrow);
        cartList = findViewById(R.id.cart_list_rec_view);
        total = findViewById(R.id.total);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);

        cartTotal = Cart.getCartTotal();
        total.setText("$ " + cartTotal + "0");
        
        CartPageRecViewAdapter cartPageRecViewAdapter = new CartPageRecViewAdapter(this);
        cartList.setAdapter(cartPageRecViewAdapter);
        cartList.setLayoutManager(new LinearLayoutManager(this));

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cart = new Intent(CartPage.this, HomePage.class);
                startActivity(cart);
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartPage.this, OrderPage.class);
                Bundle extras = new Bundle();
                extras.putInt("id",id);
                extras.putDouble("total", cartTotal);

                String items = "";
                for (Cart c : Cart.cart) {
                    items += c.getFlower().getFlowerName() + ":" + c.getQuantity() + ", ";
                }

                extras.putString("items", items);

                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        
        
    }

}