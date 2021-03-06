package com.bahrijar.invetarisapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Kelas implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("kode_ruangan")
    @Expose
    private String namaKelas;
    @SerializedName("lokasi")
    @Expose
    private String lokasi;
    @SerializedName("kapasitas")
    @Expose
    private String kapasitas;

    @SerializedName("barang_ruangan")
    @Expose
    private ArrayList<Barang> barang = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaKelas() {
        return namaKelas;
    }

    public void setNamaKelas(String namaKelas) {
        this.namaKelas = namaKelas;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(String kapasitas) {
        this.kapasitas = kapasitas;
    }

    public ArrayList<Barang> getBarang() {
        return barang;
    }

    public void setBarang(ArrayList<Barang> barang) {
        this.barang = barang;
    }

    public static Creator<Kelas> getCREATOR() {
        return CREATOR;
    }

    protected Kelas(Parcel in) {
        id = in.readString();
        namaKelas = in.readString();
        lokasi = in.readString();
        kapasitas = in.readString();
    }

    public static final Creator<Kelas> CREATOR = new Creator<Kelas>() {
        @Override
        public Kelas createFromParcel(Parcel in) {
            return new Kelas(in);
        }

        @Override
        public Kelas[] newArray(int size) {
            return new Kelas[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(namaKelas);
        parcel.writeString(lokasi);
        parcel.writeString(kapasitas);
    }
}


