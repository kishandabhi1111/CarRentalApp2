package com.example.carrentalapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddCarActivity extends AppCompatActivity {

    EditText etCarName, etPrice;
    Button btnSaveCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        etCarName = findViewById(R.id.etCarName);
        etPrice = findViewById(R.id.etPrice);
        btnSaveCar = findViewById(R.id.btnSaveCar);

        btnSaveCar.setOnClickListener(v -> saveCar());
    }

    private void saveCar() {

        String name = etCarName.getText().toString().trim();
        String price = etPrice.getText().toString().trim();

        if (name.isEmpty() || price.isEmpty()) {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> car = new HashMap<>();
        car.put("name", name);
        car.put("price", price);

        FirebaseFirestore.getInstance()
                .collection("cars")
                .add(car)
                .addOnSuccessListener(doc -> {
                    Toast.makeText(this, "Car Added Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }
}
