package com.example.appbanhangandroid.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhangandroid.R;
import com.example.appbanhangandroid.interfaces.OnProductClickListener;
import com.example.appbanhangandroid.model.Cart;
import com.example.appbanhangandroid.model.Product;
import com.example.appbanhangandroid.model.Response;
import com.example.appbanhangandroid.services.HttpRequest;

import java.util.ArrayList;

import retrofit2.Callback;

public class Recycle_Item_Carts extends RecyclerView.Adapter<Recycle_Item_Carts.ViewHolder> {
    private Context context;
    private ArrayList<Cart> ds;
    private HttpRequest httpRequest;
    Callback<Response<Cart>> callback;

    public Recycle_Item_Carts(Context context, ArrayList<Cart> ds, Callback<Response<Cart>> callback) {
        this.context = context;
        this.ds = ds;
        this.callback = callback;
        httpRequest = new HttpRequest();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart cart = ds.get(holder.getAdapterPosition());
        holder.tvName.setText(cart.getName());
        holder.tvPrice.setText("Giá: " + cart.getPrice() +"$");


        Glide.with(context)
                .load(cart.getImage())
                .into(holder.imgCart);

    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName,tvPrice, tvQuantity;
        ImageView imgCart;
        Button btnUp, btnDown;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity); // Thêm TextView hiển thị số lượng
            imgCart = itemView.findViewById(R.id.imgCart);
            btnDown = itemView.findViewById(R.id.btnDown);
            btnUp = itemView.findViewById(R.id.btnUp);

            btnDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the current value displayed in tvQuantity
                    String currentQuantityString = tvQuantity.getText().toString();

                    // Parse the current value to an integer
                    int currentQuantity = Integer.parseInt(currentQuantityString);

                    // Decrement the quantity by 1
                    int newQuantity = currentQuantity - 1;

                    // Set the updated quantity back to tvQuantity
                    tvQuantity.setText(String.valueOf(newQuantity));
                }
            });

            btnUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the current value displayed in tvQuantity
                    String currentQuantityString = tvQuantity.getText().toString();

                    // Parse the current value to an integer
                    int currentQuantity = Integer.parseInt(currentQuantityString);

                    // Decrement the quantity by 1
                    int newQuantity = currentQuantity + 1;

                    // Set the updated quantity back to tvQuantity
                    tvQuantity.setText(String.valueOf(newQuantity));
                }
            });
        }
    }

}
