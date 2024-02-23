package com.ugb.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editTextConsumo;
    private Button buttonCalcular;
    private TextView textViewResultado;
    TabHost tbh;
    TextView tempVal;
    Spinner spn;
    Button btn;
    Area area = new Area();
    EditText editTextMetrosConsumidos;
    Button btnCalcular;
    TextView tvResultado;
    Button btnConvertirArea; // Cambio de nombre
    TextView tvResultadoArea; // Cambio de nombre

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configuración de la calculadora de agua
        editTextConsumo = findViewById(R.id.editTextConsumo);
        buttonCalcular = findViewById(R.id.buttonCalcular);
        textViewResultado = findViewById(R.id.textViewResultado);

        buttonCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularCostoAgua();
            }
        });

        // Configuración de la conversión de área
        tbh = findViewById(R.id.tabhost);
        tbh.setup();
        tbh.addTab(tbh.newTabSpec("AREA").setIndicator("AREAA", null).setContent(R.id.Area));
        tbh.addTab(tbh.newTabSpec("TARI").setIndicator("TARIFA",null).setContent(R.id.Tarifa));

        editTextMetrosConsumidos = findViewById(R.id.editTextMetrosConsumidos);
        btnCalcular = findViewById(R.id.btnCalcular);
        tvResultado = findViewById(R.id.tvResultado);
        btnConvertirArea = findViewById(R.id.btnConvertir1); // Cambio de nombre
        tvResultadoArea = findViewById(R.id.tvResultado); // Cambio de nombre

        btnConvertirArea.setOnClickListener(new View.OnClickListener() { // Actualización del OnClickListener
            @Override
            public void onClick(View view) {
                spn = findViewById(R.id.spnDeArea);
                int de = spn.getSelectedItemPosition();

                spn = findViewById(R.id.spnAArea);
                int a = spn.getSelectedItemPosition();

                tempVal = findViewById(R.id.txtCantidad1);
                try {
                    double cantidad = Double.parseDouble(tempVal.getText().toString());
                    double resp = area.convertir(0, de, a, cantidad);
                    mostrarResultadoArea(resp); // Cambio de método
                } catch (NumberFormatException e) {
                    mostrarErrorArea("Ingresa una cantidad válida."); // Cambio de método
                }
            }
        });

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String metrosConsumidosStr = editTextMetrosConsumidos.getText().toString();
                if (!metrosConsumidosStr.isEmpty()) {
                    int metrosConsumidos = Integer.parseInt(metrosConsumidosStr);
                    double costoTotal = calcularCostoTotal(metrosConsumidos);
                    tvResultado.setText("El costo total es: $" + costoTotal);
                } else {
                    tvResultado.setText("Por favor ingrese los metros consumidos");
                }
            }
        });
    }

    private double calcularCostoTotal(int metrosConsumidos) {
        double costoTotal = 0.0;
        if (metrosConsumidos >= 1 && metrosConsumidos <= 18) {
            costoTotal = 6.0;
        } else if (metrosConsumidos >= 19 && metrosConsumidos <= 28) {
            costoTotal = 6.0 + (metrosConsumidos - 18) * 0.45;
        } else if (metrosConsumidos >= 29) {
            costoTotal = 6.0 + (28 - 18) * 0.45 + (metrosConsumidos - 28) * 0.65;
        }
        return costoTotal;
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

    private void mostrarResultadoArea(double resultado) { // Cambio de nombre
        String mensaje = String.format("Resultado de la conversión: %.1f", resultado);
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
    }

    private void mostrarErrorArea(String mensaje) { // Cambio de nombre
        Toast.makeText(getApplicationContext(), "Error en la conversión: " + mensaje, Toast.LENGTH_LONG).show();
    }
}

class Area {
    double[][] valores =  {
            {1, 6988, 75218.1332, 10000, 0.0001187, 16, 0.6988}
    };

    public double convertir(int opcion, int de, int a, double cantidad) {
        return valores[opcion][a] / valores[opcion][de] * cantidad;
    }
}

