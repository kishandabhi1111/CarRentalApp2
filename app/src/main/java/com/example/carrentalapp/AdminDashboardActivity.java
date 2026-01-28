package com.example.carrentalapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class AdminDashboardActivity extends AppCompatActivity {

    TextView txtBookings;
    Button btnAddCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        // CONNECT XML
        txtBookings = findViewById(R.id.txtBookings);
        btnAddCar = findViewById(R.id.btnAddCar);

        // 🔥 OPEN ADD CAR SCREEN
        btnAddCar.setOnClickListener(v ->
                startActivity(new Intent(AdminDashboardActivity.this, AddCarActivity.class))
        );

        // 🔥 SHOW BOOKINGS
        FirebaseFirestore.getInstance()
                .collection("bookings")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException error) {

                        if (value == null || value.isEmpty()) {
                            txtBookings.setText("No bookings yet");
                            return;
                        }

                        StringBuilder sb = new StringBuilder();
                        for (DocumentSnapshot d : value.getDocuments()) {
                            sb.append(d.getString("user"))
                                    .append(" - ")
                                    .append(d.getString("date"))
                                    .append("\n\n");
                        }
                        txtBookings.setText(sb.toString());
                    }
                });
    }
}
