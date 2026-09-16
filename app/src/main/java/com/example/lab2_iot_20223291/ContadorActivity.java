package com.example.lab2_iot_20223291;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lab2_iot_20223291.databinding.ActivityContadorBinding;

public class ContadorActivity extends AppCompatActivity {

    private ActivityContadorBinding binding;
    private Thread workerThread;
    private int contador = 0;
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContadorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Botón Regresar
        binding.btnRegresar.setOnClickListener(v -> finish());

        // Botón Iniciar contador
        binding.btnIniciarContador.setOnClickListener(v -> {
            if (!isRunning) {
                iniciarContador();
            }
        });
    }

    private void iniciarContador() {

        isRunning = true;
        contador = 1;
        actualizarTexto();

        workerThread = new Thread(() -> {

            while (contador < 20 && isRunning) {
                try {
                    Thread.sleep(1000); // Pausa de 1 segundo
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                contador++;

                // Se actualiza la UI en el Hilo Principal
                runOnUiThread(this::actualizarTexto);
            }
            // Cuando llega a 20, sale del bucle
            isRunning = false;

        });

        workerThread.start();
    }

    private void actualizarTexto() {
        binding.tvNumeroContador.setText(String.valueOf(contador));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Detenemos el hilo si el usuario sale de la pantalla antes de llegar a 20s
        isRunning = false;
    }

}