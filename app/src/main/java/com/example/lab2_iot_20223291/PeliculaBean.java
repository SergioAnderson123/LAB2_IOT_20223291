package com.example.lab2_iot_20223291;

import com.google.gson.annotations.SerializedName;

public class PeliculaBean {

    // @SerializedName para decirle a Gson que "Title" en el JSON corresponde a esta variable como tipo API
    @SerializedName("Title")
    private String titulo;

    @SerializedName("Year")
    private String anio;

    // Getters y Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

}