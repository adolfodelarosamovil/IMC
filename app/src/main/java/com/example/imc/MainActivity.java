package com.example.imc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {


    TextView textoResultado;
    ImageView imagenAsociada;
    Double imc = null;
    private final String URL_IMC = "https://es.wikipedia.org/wiki/%C3%8Dndice_de_masa_corporal";

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
            Double imcGuadado = savedInstanceState.getDouble("imc");
            imc = imcGuadado;
            asignarResultados(imcGuadado);
        }
    }

    public void calcularIMC(View view) {

        TextView editEstatura = (TextView) findViewById(R.id.editEstatura);
        TextView editPeso = (TextView) findViewById(R.id.editPeso);

        String estatura = editEstatura.getText().toString();
        String peso = editPeso.getText().toString();

        Log.d("IMC", estatura);
        Log.d("IMC", peso);

        Double estaturaNum = Double.parseDouble(estatura);
        Double pesoNum = Double.parseDouble(peso);

        imc = (pesoNum/(estaturaNum*estaturaNum));

        asignarResultados(imc);
    }

    private void asignarResultados(Double imc){

        TextView cabecera = (TextView) findViewById(R.id.textCabecera);
        TextView resultado = (TextView) findViewById(R.id.textResultado);
        imagenAsociada = findViewById(R.id.imageAsociada);
        String descripcion = "";
        if(imc > 0 ){
            cabecera.setText(getResources().getString(R.string.result));
            if(16 > imc ){
                Log.d("IMC", "DESNUTRIDO");
                descripcion = getResources().getString(R.string.undernourished);
                imagenAsociada.setImageResource(R.drawable.girl);
            } else if(16 <= imc && imc <= 18 ){
                Log.d("IMC", "DELGADO");
                descripcion = getResources().getString(R.string.thin);
                imagenAsociada.setImageResource(R.drawable.pantera_rosa);
            } else if(18 <= imc && imc <= 25 ){
                Log.d("IMC", "NORMAL");
                descripcion = getResources().getString(R.string.normal);
                imagenAsociada.setImageResource(R.drawable.normal);
            } else if(25 <= imc && imc <= 31 ){
                Log.d("IMC", "SOBREPESO");
                descripcion = getResources().getString(R.string.overweight);
                imagenAsociada.setImageResource(R.drawable.sobrepeso);
            } else if(31 <= imc ) {
                Log.d("IMC", "OBESO");
                descripcion = getResources().getString(R.string.obese);
                imagenAsociada.setImageResource(R.drawable.obeso);
            }

            NumberFormat formatter = new DecimalFormat("#0.00");

            resultado.setText(getResources().getString(R.string.response) + " " + formatter.format(imc) + " - " + descripcion);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d("IMC", "EN onSaveInstanceState");
        if( imc != null){
            outState.putDouble("imc", imc);
        }
    }

    public void limpiarCampos(View view) {

        TextView editEstatura = (TextView) findViewById(R.id.editEstatura);
        TextView editPeso = (TextView) findViewById(R.id.editPeso);

        TextView cabecera = (TextView) findViewById(R.id.textCabecera);
        TextView resultado = (TextView) findViewById(R.id.textResultado);

        ImageView imagen_asociada = findViewById(R.id.imageAsociada);

        editEstatura.setText("");
        editPeso.setText("");
        cabecera.setText("");
        resultado.setText("");
        imagen_asociada.setImageResource(0);
        imc = null;
    }

    //Debo sobrrescribir este método para cargar mi menú
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();//este objeto será el encargado de cargar/inlfar mi menú
        mi.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //ESTE MÉTODO SE INVOCA AL TOCAR UNA OPCIÓN DEL MENÚ
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d("MIAPP", "Se ha tocado un elemento de la barra/menú");

        switch (item.getItemId())
        {
            case R.id.info:
                Log.d("MIAPP", "Ha tocado info");
                //Intent que abre enlace URL
                Intent intent = new Intent();
                intent.setData(Uri.parse(URL_IMC));
                intent.setAction(Intent.ACTION_VIEW);
                if(intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                }
                break;
        }

        return true;//super.onOptionsItemSelected(item);
    }
}
