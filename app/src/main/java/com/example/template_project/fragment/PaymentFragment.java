package com.example.template_project.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.template_project.R;
import com.example.template_project.response.PaymentResponse;
import com.example.template_project.retrofit.PaymentApi;
import com.example.template_project.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentFragment extends Fragment {

    private WebView vnpayWebView;
    private String amount;

    private static final String TAG = "PaymentFragment";

    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        if (getArguments() != null) {
            amount = getArguments().getString("AMOUNT");
            Log.d(TAG, "Received amount from arguments: " + amount);
        } else {
            Log.w(TAG, "No arguments received!");
        }

        vnpayWebView = view.findViewById(R.id.vnpayWebView);
        WebSettings webSettings = vnpayWebView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Bật JavaScript
        webSettings.setAllowFileAccess(true); // Cho phép truy cập file
        webSettings.setAllowContentAccess(true); // Cho phép truy cập nội dung
        webSettings.setDomStorageEnabled(true); // Kích hoạt hỗ trợ HTML5 Storage

        vnpayWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(TAG, "WebView finished loading URL: " + url);
                if (url.contains("vnpay_return")) {
                    Log.d(TAG, "Detected VNPAY return URL: " + url);
                    // TODO: Xử lý kết quả thanh toán
                }
            }
        });

        getPaymentUrl(amount);

        return view;
    }

    private void getPaymentUrl(String amount) {
        Log.d(TAG, "Calling payment API with amount: " + amount);

        RetrofitService retrofitService = new RetrofitService();
        PaymentApi paymentApi = retrofitService.getRetrofit().create(PaymentApi.class);

        paymentApi.createPayment(amount).enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                Log.d(TAG, "API response received");
                if (response.isSuccessful() && response.body() != null) {
                    String paymentUrl = response.body().getPaymentUrl();
                    Log.d(TAG, "Payment URL received: " + paymentUrl);

                    vnpayWebView.loadUrl(paymentUrl);
                } else {
                    Log.w(TAG, "API response failed or empty: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<PaymentResponse> call, Throwable t) {
                Log.e(TAG, "API call failed", t);
            }
        });
    }
}
