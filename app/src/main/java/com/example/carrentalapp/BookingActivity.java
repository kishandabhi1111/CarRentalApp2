package com.example.carrentalapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class BookingActivity extends AppCompatActivity {

    TextView txtCarName, txtPrice;
    EditText etDate;
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        txtCarName = findViewById(R.id.txtCarName);
        txtPrice = findViewById(R.id.txtPrice);
        etDate = findViewById(R.id.etDate);
        btnConfirm = findViewById(R.id.btnConfirm);

        // GET DATA FROM CAR CLICK
        String carName = getIntent().getStringExtra("carName");
        String price = getIntent().getStringExtra("price");

        txtCarName.setText(carName);
        txtPrice.setText("₹ " + price);

        btnConfirm.setOnClickListener(v -> {

            String date = etDate.getText().toString().trim();

            if (date.isEmpty()) {
                Toast.makeText(this, "Enter booking date", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, Object> booking = new HashMap<>();
            booking.put("user", FirebaseAuth.getInstance().getCurrentUser().getEmail());
            booking.put("carName", carName);
            booking.put("price", price);
            booking.put("date", date);

            FirebaseFirestore.getInstance()
                    .collection("bookings")
                    .add(booking)
                    .addOnSuccessListener(doc -> {
                        Toast.makeText(this, "Booking Successful", Toast.LENGTH_LONG).show();
                        finish();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show()
                    );
        });
    }
}
