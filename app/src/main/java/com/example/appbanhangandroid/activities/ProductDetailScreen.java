package com.example.appbanhangandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.appbanhangandroid.R;
import com.example.appbanhangandroid.model.Cart;
import com.example.appbanhangandroid.model.Product;
import com.example.appbanhangandroid.model.Response;
import com.example.appbanhangandroid.services.ApiServices;
import com.example.appbanhangandroid.services.HttpRequest;

import retrofit2.Call;
import retrofit2.Callback;

public class ProductDetailScreen extends AppCompatActivity {
    public static final String EXTRA_PRODUCT_ID = "product_id";
    private TextView tvProductName, tvProductPrice,tvProductDescription;
    Button btnAddToCart;
    ImageView imgProduct;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_screen);
        tvProductName = findViewById(R.id.tvProductName);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        tvProductDescription = findViewById(R.id.tvProductDescription);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        imgProduct = findViewById(R.id.imgProductDetail);

        btnBack = findViewById(R.id.btnBackDetail);

        // Nhận ID sản phẩm từ Intent và gửi yêu cầu để lấy thông tin sản phẩm từ API
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_PRODUCT_ID)) { // Use EXTRA_PRODUCT_ID constant
            String productId = intent.getStringExtra(EXTRA_PRODUCT_ID);
            getProductById(productId);
        }
    }

    private void getProductById(String productId) {
        HttpRequest httpRequest = new HttpRequest();
        ApiServices apiServices = httpRequest.callAPI();

        Call<Response<Product>> call = apiServices.getProductById(productId);
        call.enqueue(new Callback<Response<Product>>() {
            @Override
            public void onResponse(Call<Response<Product>> call, retrofit2.Response<Response<Product>> retrofitResponse) {
                if (retrofitResponse.isSuccessful() && retrofitResponse.body() != null) {
                    Product product = retrofitResponse.body().getData();
                    displayProductDetail(product);
                } else {
                    Toast.makeText(ProductDetailScreen.this, "Failed to get product detail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response<Product>> call, Throwable t) {
                Toast.makeText(ProductDetailScreen.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayProductDetail(Product product) {
        Glide.with(this)
                .load(product.getImage())
                .into(imgProduct);
        tvProductName.setText(product.getName());
        tvProductPrice.setText("$" + product.getPrice());
        tvProductDescription.setText(product.getDescription());

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo đối tượng Cart từ thông tin sản phẩm hiện tại
                String proID = product.getId();
                String name = product.getName();
                Integer quantity = 1;
                Integer price = product.getPrice();
                String image = product.getImage();
                Cart cart = new Cart(proID, name, quantity, price, image);


                // Gọi phương thức addToCart để thêm sản phẩm vào giỏ hàng
                addToCart(cart);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // Add more fields if needed
    };

    private void addToCart(Cart cart) {
        HttpRequest httpRequest = new HttpRequest();
        ApiServices apiServices = httpRequest.callAPI();

        Call<Response<Cart>> call = apiServices.addToCart(cart);
        call.enqueue(new Callback<Response<Cart>>() {
            @Override
            public void onResponse(Call<Response<Cart>> call, retrofit2.Response<Response<Cart>> retrofitResponse) {
                if (retrofitResponse.isSuccessful() && retrofitResponse.body() != null) {
                    // Thành công: Hiển thị thông báo và cập nhật giao diện
                    Toast.makeText(ProductDetailScreen.this, "Đã thêm vào giỏ", Toast.LENGTH_SHORT).show();
                    // Cập nhật giao diện hoặc thực hiện các thao tác khác sau khi thêm vào giỏ hàng thành công
                } else {
                    // Xử lý khi thêm vào giỏ hàng không thành công
                    Toast.makeText(ProductDetailScreen.this, "Lỗi thêm", Toast.LENGTH_SHORT).show();
                    // Hiển thị thông báo lỗi cho người dùng
                }
            }

            @Override
            public void onFailure(Call<Response<Cart>> call, Throwable t) {
                // Xử lý khi có lỗi kết nối hoặc lỗi từ máy chủ
                // Hiển thị thông báo lỗi cho người dùng
            }
        });
    }

}
