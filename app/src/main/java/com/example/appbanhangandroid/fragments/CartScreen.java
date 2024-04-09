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
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhangandroid.R;
import com.example.appbanhangandroid.activities.ProductDetailScreen;
import com.example.appbanhangandroid.activities.ProfileScreen;
import com.example.appbanhangandroid.adapter.Recycle_Item_Carts;
import com.example.appbanhangandroid.adapter.Recycle_Item_Products;
import com.example.appbanhangandroid.model.Cart;
import com.example.appbanhangandroid.model.Product;
import com.example.appbanhangandroid.model.Response;
import com.example.appbanhangandroid.services.HttpRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;


public class CartScreen extends Fragment {
    TextView txtTotal;
    private HttpRequest httpRequest;
    private RecyclerView recyclerCart;
    private Recycle_Item_Carts adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart_screen, container, false);
        httpRequest = new HttpRequest();
        recyclerCart = view.findViewById(R.id.RcvCart);
        txtTotal = view.findViewById(R.id.txtTotalAmount);

        httpRequest.callAPI()
                .getListCart() //Phương thức API cần thực thi
                .enqueue(getCartAPI);

        return view;
    }

    private void getData(ArrayList<Cart> ds) {
        adapter = new Recycle_Item_Carts(getContext(), ds, responseProductAPI);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerCart.setLayoutManager(layoutManager);
        recyclerCart.setAdapter(adapter);
        calculateTotalAmount(ds);
    }

    private void calculateTotalAmount(ArrayList<Cart> cartList) {
        int totalAmount = 0;
        for (Cart cart : cartList) {
            totalAmount += cart.getQuantity() * cart.getPrice(); // Số lượng * giá của mỗi mặt hàng
        }
        txtTotal.setText(String.valueOf(totalAmount) + " đ");
    }

    Callback<Response<ArrayList<Cart>>> getCartAPI = new Callback<Response<ArrayList<Cart>>>() {

        @Override
        public void onResponse(Call<Response<ArrayList<Cart>>> call, retrofit2.Response<Response<ArrayList<Cart>>> response) {
            // Khi call thành công sẽ chạy vào hàm này
            if (response.isSuccessful()) {
                Response<ArrayList<Cart>> responseBody = response.body();
                if (responseBody != null) {
                    // Kiểm tra status code
                    if (responseBody.getStatus() == 200) {
                        // Lấy data
                        ArrayList<Cart> ds = responseBody.getData();
                        // Kiểm tra danh sách không rỗng
                        if (ds != null && !ds.isEmpty()) {
                            // Set dữ liệu lên recycle
                            getData(ds);
                            // Toast ra thông tin từ Messenger
                            Toast.makeText(getContext(), responseBody.getMessenger(), Toast.LENGTH_SHORT).show();
                        } else {
                            // Xử lý trường hợp danh sách rỗng
                            // Ví dụ: Hiển thị một thông báo cho người dùng
                            Toast.makeText(getContext(), "Danh sách rỗng", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Xử lý trường hợp status code không thành công
                        // Ví dụ: Hiển thị một thông báo cho người dùng
                        Toast.makeText(getContext(), "Lỗi " + responseBody.getStatus(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Xử lý trường hợp response body null
                    // Ví dụ: Hiển thị một thông báo cho người dùng
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Xử lý trường hợp response không thành công
                // Ví dụ: Hiển thị một thông báo cho người dùng
                Toast.makeText(getContext(), "Lỗi " + response.code(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<Response<ArrayList<Cart>>> call, Throwable t) {
            // Xử lý trường hợp call không thành công
            // Ví dụ: Hiển thị một thông báo cho người dùng
            Toast.makeText(getContext(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
        }
    };


    Callback<Response<Cart>> responseProductAPI = new Callback<Response<Cart>>() {
        @Override
        public void onResponse(Call<Response<Cart>> call, retrofit2.Response<Response<Cart>> response) {
            if (response.isSuccessful()) {
                //check status code
                if (response.body().getStatus() == 200) {
                    //Call lại API danh sách
                    httpRequest.callAPI()
                            .getListCart() //Phương thức API cần thực thi
                            .enqueue(getCartAPI);
                    //Toast ra thông tin từ Messenger
                    Toast.makeText(getContext(), response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<Cart>> call, Throwable t) {
            Log.d(">>> GetListDistributor", "onFailure: " + t.getMessage());
        }
    };
}