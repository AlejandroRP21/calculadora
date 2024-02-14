package com.ugb.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


import javax.crypto.NullCipher;

public class MainActivity extends AppCompatActivity {
        TabHost tbh;
        Spinner spnDe, spnA, spnMonedaDe, spnMonedaA;
        Button btn;
        EditText txtCantidad;
        Conversores miObj = new Conversores();
        ConversorMoneda conversorMoneda = new ConversorMoneda();



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            tbh = findViewById(R.id.tbhConversores);
            tbh.setup();
            tbh.addTab(tbh.newTabSpec("LON").setContent(R.id.tabLongitud).setIndicator("LONGITUD", null));
            tbh.addTab(tbh.newTabSpec("ALM").setContent(R.id.tabAlmacenamiento).setIndicator("ALMACENAMIENTO", null));
            tbh.addTab(tbh.newTabSpec("MON").setContent(R.id.tabMONEDA).setIndicator("MONEDA", null));

            btn = findViewById(R.id.btnCalcularLongitud);
            spnDe = findViewById(R.id.spnDeLongitud);
            spnA = findViewById(R.id.spnALongitud);
            txtCantidad = findViewById(R.id.txtCantidadLongitud);
            spnMonedaDe = findViewById(R.id.spnDeMoneda);
            spnMonedaA = findViewById(R.id.spnAMoneda);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.spnMoneda, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnMonedaDe.setAdapter(adapter);
            spnMonedaA.setAdapter(adapter);

            spnMonedaDe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    // Update conversion rates based on selected currency
                    conversorMoneda.actualizarTipoCambio(spnMonedaDe.getSelectedItem().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    double cantidad = Double.parseDouble(txtCantidad.getText().toString());
                    double resultado = conversorMoneda.convertir(
                            spnMonedaDe.getSelectedItem().toString(),
                            spnMonedaA.getSelectedItem().toString(),
                            cantidad);
                    TextView txtRespuestaMoneda = findViewById(R.id.txtRespuestaMoneda);
                    txtRespuestaMoneda.setText("Respuesta: " + resultado);
                    Toast.makeText(getApplicationContext(), "Respuesta: " + resultado, Toast.LENGTH_LONG).show();

                }
            });
        }

        public class Conversores {
            double[][] valores = {
                    {1, 100, 39.3701, 3.28084, 1.193, 1.09361, 0.001, 0.000621371},
                    {}
            };

            public double convertir(int opcion, int de, int a, double cantidad) {
                return valores[opcion][a] / valores[opcion][de] * cantidad;
            }
        }

        public class ConversorMoneda {
            double[] tipoCambio = {1, 20.25, 8.75};

            public void actualizarTipoCambio(String monedaBase) {

                switch (monedaBase) {
                    case "USD":

                        tipoCambio = new double[]{1, 20.25, 8.75};
                        break;

                }
            }

            public double convertir(String monedaDe, String monedaA, double cantidad) {
                int indiceMonedaDe = obtenerIndiceMoneda(monedaDe);
                int indiceMonedaA = obtenerIndiceMoneda(monedaA);
                return cantidad * tipoCambio[indiceMonedaA] / tipoCambio[indiceMonedaDe];
            }

            private int obtenerIndiceMoneda(String moneda) {
                switch (moneda) {
                    case "USD":
                        return 0;
                    case "MXN":
                        return 1;
                    case "SVC":
                        return 2;
                    default:
                        return -1; // Invalid currency
                }
            }
        }
    }