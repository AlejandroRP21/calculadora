package com.ugb.calculadora;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {
    private EditText editTextConsumo;
    private Button buttonCalcular;
    private TextView textViewResultado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextConsumo = findViewById(R.id.editTextConsumo);
        buttonCalcular = findViewById(R.id.buttonCalcular);
        textViewResultado = findViewById(R.id.textViewResultado);

        buttonCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularCostoAgua();
            }
        });
    }

    private void calcularCostoAgua() {
        int consumo = Integer.parseInt(editTextConsumo.getText().toString());
        double costoTotal;

        if (consumo <= 18) {
            costoTotal = 6.0;
        } else if (consumo <= 28) {
            costoTotal = 6.0 + 0.45 * (consumo - 18);
        } else {
            costoTotal = 6.0 + 0.45 * (28 - 18) + 0.65 * (consumo - 28);
        }

        textViewResultado.setText("El costo total es: $" + costoTotal);
    }
}
