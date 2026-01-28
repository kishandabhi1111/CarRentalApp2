package com.example.carrentalapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    Context context;
    ArrayList<CarModel> carList;

    public CarAdapter(Context context, ArrayList<CarModel> carList) {
        this.context = context;
        this.carList = carList;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {

        CarModel car = carList.get(position);

        holder.txtCarName.setText(car.name);
        holder.txtPrice.setText("₹ " + car.price);

        // 🔥 CLICK TO BOOK
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BookingActivity.class);
            intent.putExtra("carName", car.name);
            intent.putExtra("price", car.price);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    static class CarViewHolder extends RecyclerView.ViewHolder {

        TextView txtCarName, txtPrice;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCarName = itemView.findViewById(R.id.txtCarName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
        }
    }
}
