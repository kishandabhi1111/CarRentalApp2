package com.example.carrentalapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<CarModel> carList;
    CarAdapter carAdapter;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize views
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize list & adapter
        carList = new ArrayList<>();
        carAdapter = new CarAdapter(this, carList);
        recyclerView.setAdapter(carAdapter);

        // Firebase instance
        firestore = FirebaseFirestore.getInstance();

        // 🔥 REAL-TIME CAR LIST FROM FIREBASE
        firestore.collection("cars")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException error) {

                        if (error != null) {
                            Toast.makeText(HomeActivity.this,
                                    "Error loading cars",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        carList.clear();

                        if (value != null) {
                            for (DocumentSnapshot doc : value.getDocuments()) {
                                CarModel car = doc.toObject(CarModel.class);
                                if (car != null) {
                                    carList.add(car);
                                }
                            }
                        }

                        carAdapter.notifyDataSetChanged();
                    }
                });
    }
}
