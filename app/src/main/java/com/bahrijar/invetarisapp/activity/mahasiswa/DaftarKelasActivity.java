package com.bahrijar.invetarisapp.activity.mahasiswa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bahrijar.invetarisapp.R;
import com.bahrijar.invetarisapp.activity.EmptyRecyclerView;
import com.bahrijar.invetarisapp.adapter.KelasAdapter;
import com.bahrijar.invetarisapp.model.Kelas;
import com.bahrijar.invetarisapp.network.ServiceGenerator;
import com.bahrijar.invetarisapp.network.response.KelasResponse;
import com.bahrijar.invetarisapp.network.service.ApiInterface;
import com.bahrijar.invetarisapp.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarKelasActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "DaftarKelasActivity";
    SharedPrefManager sharedPrefManager;

    ImageButton btn_back;

    ProgressDialog dialog;

    private KelasAdapter adapter;
    ApiInterface apiInterface;
    SwipeRefreshLayout swipeRefreshLayout;
    EmptyRecyclerView rvKelas;

    List<Kelas> kelasList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_kelas);
        sharedPrefManager = new SharedPrefManager(this);

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);

        initViews();
        loadData();

    }

    private void initViews() {
        rvKelas = findViewById(R.id.rv_kelas);
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        rvKelas.setEmptyView(findViewById(R.id.empty_view));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvKelas.setLayoutManager(layoutManager);
        adapter = new KelasAdapter(getApplicationContext(), kelasList);
        rvKelas.setAdapter(adapter);


        apiInterface = ServiceGenerator.createBaseService(this, ApiInterface.class);


        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    private void loadData() {
        dialog.setMessage("Memuat Data..");
        dialog.show();

        apiInterface.getClasses(sharedPrefManager.getSPToken()).enqueue(new Callback<KelasResponse>() {
            @Override
            public void onResponse(Call<KelasResponse> call, Response<KelasResponse> response) {
                try {
                    if (response.code() == 200) {
                        assert response.body() != null;
                        kelasList = response.body().getKelas();
                        adapter.setListKelas(kelasList);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<KelasResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                Intent backtoHome = new Intent(DaftarKelasActivity.this, MainActivity.class);
                backtoHome.addCategory(Intent.CATEGORY_LAUNCHER);
                backtoHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(backtoHome);
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
