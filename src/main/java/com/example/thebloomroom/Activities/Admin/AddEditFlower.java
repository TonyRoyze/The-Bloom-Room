package com.example.thebloomroom.Activities.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.thebloomroom.Classes.Category;
import com.example.thebloomroom.Database.DatabaseHelper;
import com.example.thebloomroom.Classes.Flower;
import com.example.thebloomroom.R;

import java.util.ArrayList;
import java.util.List;

public class AddEditFlower extends AppCompatActivity {
    private Spinner categorySpinner;
    private ImageView backIcon;
    private Button btnAdd;
    private EditText flowerName, flowerDesc, price, imgName;
    private int id;
    private Flower flower;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_flower);

        categorySpinner = findViewById(R.id.cat_id);
        backIcon = findViewById(R.id.ic_back_arrow);
        btnAdd = findViewById(R.id.btnAddFlower);
        flowerName = findViewById(R.id.flower_name);
        flowerDesc = findViewById(R.id.flower_desc);
        price = findViewById(R.id.flower_price);
        imgName = findViewById(R.id.img_name);

        DatabaseHelper databaseHelper = new DatabaseHelper(AddEditFlower.this);
        List<Flower> flowers = databaseHelper.getAllFlowers();
        List<Category> categories = databaseHelper.getAllCategories();
        List<String> categoryNames = new ArrayList<>();

        for (Category category : categories) {
            categoryNames.add(category.getCatName());
        }

        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(this, R.layout.spinner_list, categoryNames);
        categorySpinner.setAdapter(catAdapter);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);

        if (id >= 0) {
            for (Flower f : flowers) {
                if (f.getId() == id) {
                    flower = f;
                }
            }
            flowerName.setText(flower.getFlowerName());
            flowerDesc.setText(flower.getFlowerDesc());
            price.setText(String.valueOf(flower.getPrice()));
            imgName.setText(flower.getImgName());
            btnAdd.setText("Update");
            categorySpinner.setSelection((int) flower.getCatId() - 1);
        }


        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToDash();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(AddEditFlower.this);
                if (id >= 0) {
                    Flower flower = null;
                    try {
                        flower = new Flower(id, flowerName.getText().toString(), flowerDesc.getText().toString(), imgName.getText().toString(), Double.parseDouble(price.getText().toString()), categorySpinner.getSelectedItemPosition() + 1);
                    } catch (Exception e) {
                        Toast.makeText(AddEditFlower.this, "Fields Cannot be Left Empty", Toast.LENGTH_SHORT).show();
                    }

                    boolean success = databaseHelper.updateFlower(flower);
                    if (success) {
                        Toast.makeText(AddEditFlower.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                        moveToDash();
                    } else {
                        Toast.makeText(AddEditFlower.this, "Error Updating Data", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Flower flower = null;
                    try {
                        flower = new Flower(-1, flowerName.getText().toString(), flowerDesc.getText().toString(), imgName.getText().toString(), Double.parseDouble(price.getText().toString()), categorySpinner.getSelectedItemPosition() + 1);
                    } catch (Exception e) {
                        Toast.makeText(AddEditFlower.this, "Fields Cannot be Left Empty", Toast.LENGTH_SHORT).show();
                    }


                    boolean success = databaseHelper.insertFlower(flower);
                    if (success) {
                        Toast.makeText(AddEditFlower.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddEditFlower.this, "Error Inserting Data", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

    }

    private void moveToDash() {
        Intent intent = new Intent(AddEditFlower.this, AdminDash.class);
        startActivity(intent);
    }
}