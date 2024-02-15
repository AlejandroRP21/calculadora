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
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    TabHost tbh;
    Spinner spnDe, spnA, spnMonedaDe, spnMonedaA;
    Button btn;
    EditText txtCantidad;
    TextView txtRespuestaMoneda;

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

        spnMonedaDe = findViewById(R.id.spnDeMoneda);
        spnMonedaA = findViewById(R.id.spnAMoneda);
        txtRespuestaMoneda = findViewById(R.id.txtRespuestaMoneda);
        btn = findViewById(R.id.btnCalcularMoneda);
        txtCantidad = findViewById(R.id.txtCantidadDeMoneda);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spnMoneda, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMonedaDe.setAdapter(adapter);
        spnMonedaA.setAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Cantidad", "Valor ingresado: " + txtCantidad.getText().toString());
                String monedaDe = spnMonedaDe.getSelectedItem().toString();
                String monedaA = spnMonedaA.getSelectedItem().toString();
                double cantidad = Double.parseDouble(txtCantidad.getText().toString());

                double resultado = realizarConversion(monedaDe, monedaA, cantidad);

                // Mostrar el resultado
                txtRespuestaMoneda.setText("Respuesta: " + resultado);
                Toast.makeText(getApplicationContext(), "Respuesta: " + resultado, Toast.LENGTH_LONG).show();
            }
        });
    }

    // Método para realizar la conversión de moneda
    private double realizarConversion(String monedaDe, String monedaA, double cantidad) {
        double resultado = 0.0;
        switch (monedaDe) {
            case "USD":
                switch (monedaA) {
                    case "MXN":
                        resultado = ConvertirDolaresPesosMexicanos(cantidad);
                        break;
                    case "EUR":
                        resultado = ConvertirDolaresEuros(cantidad);
                        break;
                }
                break;
            case "MXN":
                switch (monedaA) {
                    case "USD":
                        resultado = ConvertirPesosMexicanosDolares(cantidad);
                        break;
                    case "EUR":
                        resultado = ConvertirPesosMexicanosEuros(cantidad);
                        break;
                }
                break;
            case "EUR":
                switch (monedaA) {
                    case "USD":
                        resultado = ConvertirEurosDolares(cantidad);
                        break;
                    case "MXN":
                        resultado = ConvertirEurosPesosMexicanos(cantidad);
                        break;
                }
                break;
        }
        return resultado;
    }

    private double ConvertirDolaresPesosMexicanos(double cantidad) {
        double tipoCambio = 17.06;
        return cantidad * tipoCambio;
    }

    private double ConvertirDolaresEuros(double cantidad) {
        double tipoCambio = 0.93;
        return cantidad * tipoCambio;
    }

    private double ConvertirPesosMexicanosDolares(double cantidad) {
        double tipoCambio = 0.059;
        return cantidad * tipoCambio;
    }

    private double ConvertirPesosMexicanosEuros(double cantidad) {
        double tipoCambio = 0.054;
        return cantidad * tipoCambio;
    }

    private double ConvertirEurosDolares(double cantidad) {
        double tipoCambio = 1.08;
        return cantidad * tipoCambio;
    }

    private double ConvertirEurosPesosMexicanos(double cantidad) {
        double tipoCambio = 18.37;
        return cantidad * tipoCambio;
    }
}