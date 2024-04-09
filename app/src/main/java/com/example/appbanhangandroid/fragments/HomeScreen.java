package com.example.appbanhangandroid.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appbanhangandroid.R;
import com.example.appbanhangandroid.activities.ProductDetailScreen;
import com.example.appbanhangandroid.activities.ProfileScreen;
import com.example.appbanhangandroid.adapter.Recycle_Item_Products;
import com.example.appbanhangandroid.interfaces.OnProductClickListener;
import com.example.appbanhangandroid.model.Product;
import com.example.appbanhangandroid.model.Response;
import com.example.appbanhangandroid.services.HttpRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class HomeScreen extends Fragment implements OnProductClickListener {
    private OnProductClickListener productClickListener;
    private HttpRequest httpRequest;
    private RecyclerView recycle_products, recycle_products1;
    private Recycle_Item_Products adapter;

    private ImageView btnProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_screen, container, false);
        httpRequest = new HttpRequest();
        btnProfile = view.findViewById(R.id.btnProfile);
        recycle_products = view.findViewById(R.id.RcvProduct);
        recycle_products1 = view.findViewById(R.id.RcvProduct1);

        // Initialize productClickListener and set it to this fragment
        productClickListener = this;

        httpRequest.callAPI()
                .getListProduct() //Phương thức API cần thực thi
                .enqueue(getProductAPI);

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ProfileScreen.class));
            }
        });

        return view;
    }

    private void getData(ArrayList<Product> ds) {
        adapter = new Recycle_Item_Products(getContext(), ds, responseProductAPI, productClickListener);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycle_products.setLayoutManager(layoutManager1);
        recycle_products.setAdapter(adapter);
        recycle_products1.setLayoutManager(layoutManager2);
        recycle_products1.setAdapter(adapter);
    }

    Callback<Response<ArrayList<Product>>> getProductAPI = new Callback<Response<ArrayList<Product>>>() {

        @Override
        public void onResponse(Call<Response<ArrayList<Product>>> call, retrofit2.Response<Response<ArrayList<Product>>> response) {
            //Khi call thành công sẽ chạy vào hàm này
            if (response.isSuccessful()) {
                //check status code
                if (response.body().getStatus() == 200) {
                    //Lấy data
                    ArrayList<Product> ds = response.body().getData();
                    //Set dữ liệu lên recycle
                    getData(ds);
                    //Toast ra thông tin từ Messenger
                    Toast.makeText(getContext(), response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<ArrayList<Product>>> call, Throwable t) {
            //Khi call thất bại sẽ chạy vào hàm này
            Log.d(">>> GetListSinhvien", "onFailure: " + t.getMessage());
        }
    };

    Callback<Response<Product>> responseProductAPI = new Callback<Response<Product>>() {
        @Override
        public void onResponse(Call<Response<Product>> call, retrofit2.Response<Response<Product>> response) {
            if (response.isSuccessful()) {
                //check status code
                if (response.body().getStatus() == 200) {
                    //Call lại API danh sách
                    httpRequest.callAPI()
                            .getListProduct() //Phương thức API cần thực thi
                            .enqueue(getProductAPI);
                    //Toast ra thông tin từ Messenger
                    Toast.makeText(getContext(), response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<Product>> call, Throwable t) {
            Log.d(">>> GetListDistributor", "onFailure: " + t.getMessage());
        }
    };

    @Override
    public void onProductClick(Product product) {
        String productId = product.getId();
        // Khởi tạo Intent để mở ProductDetailActivity
        Intent intent = new Intent(getContext(), ProductDetailScreen.class);
        // Truyền ID sản phẩm qua Intent
        intent.putExtra(ProductDetailScreen.EXTRA_PRODUCT_ID, productId);
        // Mở ProductDetailActivity
        startActivity(intent);
    }
}
