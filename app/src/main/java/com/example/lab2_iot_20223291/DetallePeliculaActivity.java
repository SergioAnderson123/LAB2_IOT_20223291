package com.example.lab2_iot_20223291;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab2_iot_20223291.databinding.ActivityDetallePeliculaBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetallePeliculaActivity extends AppCompatActivity {

    private ActivityDetallePeliculaBinding binding;

    private static final String API_KEY = "bf81d461";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetallePeliculaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Recuperamos el ID que enviamos desde el MainActivity
        String idPelicula = getIntent().getStringExtra("ID_PELICULA");

        if (idPelicula != null && !idPelicula.isEmpty()) {
            buscarPeliculaEnApi(idPelicula);
        } else {
            Toast.makeText(this, "ID no válido", Toast.LENGTH_SHORT).show();
            binding.tvTitulo.setText("Error");
            binding.tvAnio.setText("Error");
        }

        // Configuración del botón Regresar con AlertDialog
        binding.btnRegresarDialog.setOnClickListener(v -> mostrarDialogoRegresar());
    }

    private void buscarPeliculaEnApi(String imdbId) {

        // Configuramos Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Creamos el servicio
        OmdbApiService service = retrofit.create(OmdbApiService.class);

        // Preparamos la llamada
        Call<PeliculaBean> call = service.obtenerPelicula(API_KEY, imdbId);

        // Ejecutamos la llamada asíncrona
        call.enqueue(new Callback<PeliculaBean>() {
            @Override
            public void onResponse(Call<PeliculaBean> call, Response<PeliculaBean> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PeliculaBean pelicula = response.body();

                    // OMDB devuelve el JSON aunque la película no exista (con un campo "Response": "False").
                    // Si el título es nulo, no se encuentra.
                    if (pelicula.getTitulo() != null) {
                        binding.tvTitulo.setText(pelicula.getTitulo());
                        binding.tvAnio.setText(pelicula.getAnio());
                    } else {
                        binding.tvTitulo.setText("Película no encontrada");
                        binding.tvAnio.setText("-");
                    }
                } else {
                    Toast.makeText(DetallePeliculaActivity.this, "Error en la respuesta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PeliculaBean> call, Throwable t) {
                Log.e("API_ERROR", "Fallo la conexión: " + t.getMessage());
                Toast.makeText(DetallePeliculaActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
                binding.tvTitulo.setText("Error de conexión");
                binding.tvAnio.setText("-");
            }
        });
    }

    private void mostrarDialogoRegresar() {

        new AlertDialog.Builder(this)

                .setTitle("Confirmación")
                .setMessage("¿Desea volver al menú principal?")
                .setPositiveButton("Sí", (dialog, which) -> {

                    // Cierro esta activity y vuelve al MainActivity
                    finish();
                })

                .setNegativeButton("No", (dialog, which) -> {

                    // Si se elige no, cerramos el diálogo y nos mantenemos en la pantalla
                    dialog.dismiss();
                })

                .setCancelable(false)
                .show();

    }
}