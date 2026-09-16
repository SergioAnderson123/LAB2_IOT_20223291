package com.example.lab2_iot_20223291; // ¡Ajusta esto si tu proyecto se llama distinto!

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab2_iot_20223291.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // View binding
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflamos la vista con View Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Contador
        binding.btnIrContador.setOnClickListener(v -> {
            // Descomentar cuando creemos ContadorActivity
            Intent intent = new Intent(MainActivity.this, ContadorActivity.class);
            startActivity(intent);
        });

        // Botón para comprobar conexión a Internet

        binding.btnComprobarConexion.setOnClickListener(v -> {
            if (tieneConexionInternet()) {
                Toast.makeText(this, "Success: Hay conexión a Internet", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error: No hay conexión a Internet", Toast.LENGTH_SHORT).show();
            }
        });

        // Botón para buscar película
        binding.btnBuscarPelicula.setOnClickListener(v -> {
            String idPelicula = binding.etIdPelicula.getText().toString().trim();

            if (idPelicula.isEmpty()) {
                Toast.makeText(this, "Por favor, ingrese un ID de IMDb", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(MainActivity.this, DetallePeliculaActivity.class);
            intent.putExtra("ID_PELICULA", idPelicula);
            startActivity(intent);
        });
    }

    //
    private boolean tieneConexionInternet() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkCapabilities capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (capabilities != null) {
                return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET);
            }
        }
        return false;
    }
}