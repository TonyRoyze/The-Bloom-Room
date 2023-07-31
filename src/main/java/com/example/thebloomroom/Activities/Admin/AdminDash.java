package com.example.thebloomroom.Activities.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.thebloomroom.Activities.User.LoginPage;
import com.example.thebloomroom.Classes.RecViewAdapters.CatRecViewAdapter;
import com.example.thebloomroom.Classes.Category;
import com.example.thebloomroom.Database.DatabaseHelper;
import com.example.thebloomroom.Classes.Flower;
import com.example.thebloomroom.Classes.RecViewAdapters.FlowerRecViewAdapter;
import com.example.thebloomroom.Classes.Order;
import com.example.thebloomroom.Classes.RecViewAdapters.OrderRecViewAdapter;
import com.example.thebloomroom.R;
import com.example.thebloomroom.Classes.User;
import com.example.thebloomroom.Classes.RecViewAdapters.UserRecViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AdminDash extends AppCompatActivity {

    private FloatingActionButton btnAdd;
    private RecyclerView flowerList, catList, userList, orderList;
    private Button btnFlowers, btnCat, btnUsers, btnOrders;
    private ImageView backIcon;
    private int selectedType = 0, id;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash);

        backIcon = findViewById(R.id.ic_back_arrow);
        btnAdd = findViewById(R.id.ic_add);
        btnFlowers = findViewById(R.id.btnFlower);
        btnCat = findViewById(R.id.btnCat);
        btnUsers = findViewById(R.id.btnUser);
        btnOrders = findViewById(R.id.btnOrder);
        flowerList = findViewById(R.id.flower_list_rec_view);
        catList = findViewById(R.id.cat_list_rec_view);
        userList = findViewById(R.id.user_list_rec_view);
        orderList = findViewById(R.id.order_list_rec_view);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);


        List<Flower> flowers = defaultFlowerList();
        FlowerRecViewAdapter flowerRecViewAdapter = new FlowerRecViewAdapter(this);
        flowerRecViewAdapter.setFlowers(flowers);
        flowerList.setAdapter(flowerRecViewAdapter);
        flowerList.setLayoutManager(new LinearLayoutManager(this));

        List<Category> categories = defaultCatList();
        CatRecViewAdapter catRecViewAdapter = new CatRecViewAdapter(this);
        catRecViewAdapter.setCategories(categories);
        catList.setAdapter(catRecViewAdapter);
        catList.setLayoutManager(new LinearLayoutManager(this));

        List<User> users = defaultUserList();
        UserRecViewAdapter userRecViewAdapter = new UserRecViewAdapter(this);
        userRecViewAdapter.setUsers(users);
        userList.setAdapter(userRecViewAdapter);
        userList.setLayoutManager(new LinearLayoutManager(this));

        List<Order> orders = defaultOrderList();
        OrderRecViewAdapter orderRecViewAdapter = new OrderRecViewAdapter(this);
        orderRecViewAdapter.setOrders(orders);
        orderList.setAdapter(orderRecViewAdapter);
        orderList.setLayoutManager(new LinearLayoutManager(this));

        flowerList.setVisibility(View.VISIBLE);
        catList.setVisibility(View.GONE);
        userList.setVisibility(View.GONE);
        orderList.setVisibility(View.GONE);


        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(AdminDash.this, LoginPage.class);
                startActivity(login);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (selectedType) {
                    case 0:
                        Intent addFlower = new Intent(AdminDash.this, AddEditFlower.class);
                        startActivity(addFlower);
                        break;

                    case 1:
                        Intent addCat = new Intent(AdminDash.this, AddEditCat.class);
                        startActivity(addCat);
                        break;

                    case 2:
                        Intent addUser = new Intent(AdminDash.this, AddEditUser.class);
                        startActivity(addUser);
                        break;

                    case 3:
                        Intent addOrder = new Intent(AdminDash.this, AddEditOrder.class);
                        startActivity(addOrder);
                        break;

                    default:
                        Toast.makeText(AdminDash.this, "No such type", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });

        btnFlowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedType = 0;

                btnFlowers.setBackgroundColor(getResources().getColor(R.color.selected));
                btnCat.setBackgroundColor(getResources().getColor(R.color.not_selected));
                btnUsers.setBackgroundColor(getResources().getColor(R.color.not_selected));
                btnOrders.setBackgroundColor(getResources().getColor(R.color.not_selected));

                flowerList.setVisibility(View.VISIBLE);
                catList.setVisibility(View.GONE);
                userList.setVisibility(View.GONE);
                orderList.setVisibility(View.GONE);
            }
        });

        btnCat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                selectedType = 1;

                btnFlowers.setBackgroundColor(getResources().getColor(R.color.not_selected));
                btnCat.setBackgroundColor(getResources().getColor(R.color.selected));
                btnUsers.setBackgroundColor(getResources().getColor(R.color.not_selected));
                btnOrders.setBackgroundColor(getResources().getColor(R.color.not_selected));


                flowerList.setVisibility(View.GONE);
                catList.setVisibility(View.VISIBLE);
                userList.setVisibility(View.GONE);
                orderList.setVisibility(View.GONE);

            }
        });

        btnUsers.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                selectedType = 2;

                btnFlowers.setBackgroundColor(getResources().getColor(R.color.not_selected));
                btnCat.setBackgroundColor(getResources().getColor(R.color.not_selected));
                btnUsers.setBackgroundColor(getResources().getColor(R.color.selected));
                btnOrders.setBackgroundColor(getResources().getColor(R.color.not_selected));

                flowerList.setVisibility(View.GONE);
                catList.setVisibility(View.GONE);
                userList.setVisibility(View.VISIBLE);
                orderList.setVisibility(View.GONE);
            }
        });

        btnOrders.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                selectedType = 3;

                btnFlowers.setBackgroundColor(getResources().getColor(R.color.not_selected));
                btnCat.setBackgroundColor(getResources().getColor(R.color.not_selected));
                btnUsers.setBackgroundColor(getResources().getColor(R.color.not_selected));
                btnOrders.setBackgroundColor(getResources().getColor(R.color.selected));

                flowerList.setVisibility(View.GONE);
                catList.setVisibility(View.GONE);
                userList.setVisibility(View.GONE);
                orderList.setVisibility(View.VISIBLE);
            }
        });

    }

    public List<Flower> defaultFlowerList() {
        DatabaseHelper databaseHelper = new DatabaseHelper(AdminDash.this);
        return databaseHelper.getAllFlowers();
    }
    public List<Category> defaultCatList() {
        DatabaseHelper databaseHelper = new DatabaseHelper(AdminDash.this);
        return databaseHelper.getAllCategories();
    }
    public List<User> defaultUserList() {
        DatabaseHelper databaseHelper = new DatabaseHelper(AdminDash.this);
        return databaseHelper.getAllUsers();
    }
    public List<Order> defaultOrderList() {
        DatabaseHelper databaseHelper = new DatabaseHelper(AdminDash.this);
        return databaseHelper.getAllOrders();
    }

}