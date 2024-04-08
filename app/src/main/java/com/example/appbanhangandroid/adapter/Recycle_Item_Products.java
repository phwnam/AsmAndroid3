package com.example.appbanhangandroid.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhangandroid.R;
import com.example.appbanhangandroid.interfaces.OnProductClickListener;
import com.example.appbanhangandroid.model.Product;
import com.example.appbanhangandroid.model.Response;
import com.example.appbanhangandroid.services.HttpRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class Recycle_Item_Products extends RecyclerView.Adapter<Recycle_Item_Products.ViewHolder> {
    private Context context;
    private ArrayList<Product> ds;
    private HttpRequest httpRequest;
    private OnProductClickListener productClickListener;
    Callback<Response<Product>> callback;

    public Recycle_Item_Products(Context context, ArrayList<Product> ds, Callback<Response<Product>> callback, OnProductClickListener productClickListener) {
        this.context = context;
        this.ds = ds;
        this.callback = callback;
        httpRequest = new HttpRequest();
        this.productClickListener = productClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = ds.get(holder.getAdapterPosition());
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(product.getPrice() +"$");

        Glide.with(context)
                .load(product.getImage())
                .into(holder.imgProduct);
        holder.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productClickListener != null) {
                    productClickListener.onProductClick(product);
                } else {
                    Log.e("Adapter", "ProductClickListener is null");
                }

//                Toast.makeText(context, "Product Detail" + product.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productClickListener != null) {
                    productClickListener.onProductClick(product);
                } else {
                    Log.e("Adapter", "ProductClickListener is null");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private HttpRequest httpRequest;

        TextView tvName,tvPrice;
        ImageView imgProduct;
        Button btnAddCart;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            btnAddCart = itemView.findViewById(R.id.btnAddCart);



        }

    }
}
