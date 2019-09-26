package com.example.imc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("IMC", "onCreate");

        if(savedInstanceState==null){
            Log.d("IMC", "No hay nada guardado, entró por 1er vez");
        }else{
            Log.d("IMC", "Hay cosas guardas");
            //Coger las cosas guardadas
            String textoGuardado = savedInstanceState.getString("resultado");
            TextView textoCabecera = findViewById(R.id.textCabecera);
            TextView textoResultado = findViewById(R.id.textResultado);
            textoResultado.setText(textoGuardado);
            textoCabecera.setText(getResources().getString(R.string.result));
        }
    }

    public void calcularIMC(View view) {

        TextView editEstatura = (TextView) findViewById(R.id.editEstatura);
        TextView editPeso = (TextView) findViewById(R.id.editPeso);

        TextView cabecera = (TextView) findViewById(R.id.textCabecera);
        TextView resultado = (TextView) findViewById(R.id.textResultado);

        String estatura = editEstatura.getText().toString();
        String peso = editPeso.getText().toString();

        Log.d("IMC", estatura);
        Log.d("IMC", peso);

        Double estaturaNum = Double.parseDouble(estatura);
        Double pesoNum = Double.parseDouble(peso);

        Double imc = (pesoNum/(estaturaNum*estaturaNum));
        String descripcion = "";

        if(imc > 0 ){
            cabecera.setText(getResources().getString(R.string.result));
            if(16 > imc ){
                descripcion = getResources().getString(R.string.undernourished);
            } else if(16 <= imc && imc <= 18 ){
                descripcion = getResources().getString(R.string.thin);
            } else if(18 <= imc && imc <= 25 ){
                descripcion = getResources().getString(R.string.normal);
            } else if(25 <= imc && imc <= 31 ){
                descripcion = getResources().getString(R.string.overweight);
            } else if(31 <= imc ) {
                descripcion = getResources().getString(R.string.obese);
            }

            NumberFormat formatter = new DecimalFormat("#0.00");

            resultado.setText(getResources().getString(R.string.response) + " " + formatter.format(imc) + " - " + descripcion);
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        //1. Obtener el componente
        TextView textoResultado = findViewById(R.id.textResultado);
        //2. Recuperar el valor
        String resultado = textoResultado.getText().toString();
        //3 Guardar en el BUNDLE
        outState.putString("resultado", resultado);
    }

    public void limpiarCampos(View view) {

        TextView editEstatura = (TextView) findViewById(R.id.editEstatura);
        TextView editPeso = (TextView) findViewById(R.id.editPeso);

        TextView cabecera = (TextView) findViewById(R.id.textCabecera);
        TextView resultado = (TextView) findViewById(R.id.textResultado);

        editEstatura.setText("");
        editPeso.setText("");
        cabecera.setText("");
        resultado.setText("");
    }
}
