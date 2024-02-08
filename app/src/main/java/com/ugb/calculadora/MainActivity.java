package com.ugb.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tempVal;
    Spinner spn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spn = findViewById(R.id.spnOpciones);
        tempVal = findViewById(R.id.txtnum1);
        double num1 = Double.parseDouble(tempVal.getText().toString());
        tempVal = findViewById(R.id.txtnum2);
        double num2 = Double.parseDouble(tempVal.getText().toString());
        double respuesta = 0;
        {
        }
        switch (spn.getSelectedItemPosition()) {
            case 0:
                respuesta = num1 + num2;
                break;
            case 1:
                respuesta = num1 - num2;
                break;
            case 2:
                respuesta = num1 * num2;
                break;
            case 3:
                respuesta = num1 / num2;
                break;
            case 4:
                respuesta = (num1 * num2) / 100;
                break;
            case 5:
                respuesta = Math.pow(num1, num2);
                break;
            case 6:
                respuesta = calcularFactorial((int) num1);
                break;
            case 7:
                respuesta = Math.sqrt(num1);
                break;
        }
        tempVal = findViewById(R.id.lblrespuesta);
        tempVal.setText("Respuesta: " + respuesta);
    }
    private long calcularFactorial(int n) {
        long factorial = 1;

        for (int i = 1; i <= n; i++) {
            factorial *= i;
        }
        return factorial;
    }
}