package com.example.retrofit;

import com.google.gson.annotations.SerializedName;

public class Pytanie {
    @SerializedName("tresc")
    private String trescPytania;
    @SerializedName("odp_A")
    private String odpA;

    @SerializedName("odp_B")
    private String odpB;

    @SerializedName("odp_C")
    private String odpC;
    private int poprawna;

    public Pytanie(String trescPytania, String odpA, String odpB, String odpC, int poprawna) {
        this.trescPytania = trescPytania;
        this.odpA = odpA;
        this.odpB = odpB;
        this.odpC = odpC;
        this.poprawna = poprawna;
    }

    public String getTrescPytania() {
        return trescPytania;
    }

    public String getOdpA() {
        return odpA;
    }

    public String getOdpB() {
        return odpB;
    }

    public String getOdpC() {
        return odpC;
    }

    public int getPoprawna() {
        return poprawna;
    }
}
