package com.example.thebloomroom.Activities.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.thebloomroom.Database.DatabaseHelper;
import com.example.thebloomroom.Classes.Order;
import com.example.thebloomroom.R;

public class OrderPage extends AppCompatActivity {

    private ImageView backIcon;
    private Button btnAdd;
    private EditText orderUserId, orderAddress, orderItems, orderTotal;
    private int id;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);

        backIcon = findViewById(R.id.ic_back_arrow);
        btnAdd = findViewById(R.id.btnAddOrder);
        orderUserId =findViewById(R.id.order_user_id);
        orderAddress =findViewById(R.id.order_address);
        orderItems =findViewById(R.id.order_items);
        orderTotal =findViewById(R.id.order_total);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String items = extras.getString("items");
        double total = extras.getDouble("total");
        id = intent.getIntExtra("id", -1);

        if (id >= 0) {
            
            orderUserId.setText(String.valueOf(id));
            orderItems.setText(items);
            orderTotal.setText(String.valueOf(total));
        }

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToHome();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(OrderPage.this);
                if (id >= 0) {
                    Order order = null;
                    try {
                        order = new Order(id, Integer.parseInt(orderUserId.getText().toString()), orderAddress.getText().toString(), orderItems.getText().toString(),  Double.parseDouble(orderTotal.getText().toString()));
                    } catch (Exception e) {
                        Toast.makeText(OrderPage.this, "Fields Cannot be Left Empty", Toast.LENGTH_SHORT).show();
                    }

                    boolean success = databaseHelper.updateOrder(order);
                    if (success) {
                        Toast.makeText(OrderPage.this, "Your order has been placed", Toast.LENGTH_SHORT).show();
                        moveToHome();
                    } else {
                        Toast.makeText(OrderPage.this, "Error Placing Order", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Order order = null;
                    try {
                        order = new Order(-1, Integer.parseInt(orderUserId.getText().toString()), orderAddress.getText().toString(), orderItems.getText().toString(),  Double.parseDouble(orderTotal.getText().toString()));
                    } catch (Exception e) {
                        Toast.makeText(OrderPage.this, "Fields Cannot be Left Empty", Toast.LENGTH_SHORT).show();
                    }


                    boolean success = databaseHelper.insertOrder(order);
                    if (success) {
                        Toast.makeText(OrderPage.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(OrderPage.this, "Error Inserting Data", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    private void moveToHome() {
        Intent intent = new Intent(OrderPage.this, CartPage.class);
        startActivity(intent);
    }
}