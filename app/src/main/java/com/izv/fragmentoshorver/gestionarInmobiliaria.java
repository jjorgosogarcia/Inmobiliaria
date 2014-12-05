package com.izv.fragmentoshorver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class gestionarInmobiliaria extends Activity {

    EditText etId, etLocalidad,etCalle, etNumero, etTipo, etPrecio;
    Button btAceptar,btCancelar;
    int index;


    /***************************************************************/
    /*                                                             */
    /*                         METODOS ON                          */
    /*                                                             */
    /***************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_inmobiliaria);
        initComponents();
        Bundle b = getIntent().getExtras();
        index=0;
        if(b !=null ){
            index = b.getInt("index");
            Inmuebles propiedad = (Inmuebles)b.getSerializable("inmueble");
            etId.setText(propiedad.getId());
            etLocalidad.setText(propiedad.getLocalidad());
            etCalle.setText(propiedad.getCalle());
            etTipo.setText(propiedad.getTipo());
            etNumero.setText(propiedad.getNumero());
            etPrecio.setText(propiedad.getPrecio()+"");
        }
        if(savedInstanceState != null){
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.gestionar_inmobiliaria, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /***************************************************************/
    /*                                                             */
    /*                       METODOS CLICK                         */
    /*                                                             */
    /***************************************************************/

    public void aceptar(View v){
        String id,localidad,calle,tipo,numero;
        double precio;
        id = etId.getText().toString();
        localidad = etLocalidad.getText().toString();
        calle = etCalle.getText().toString();
        tipo = etTipo.getText().toString();
        numero = etNumero.getText().toString();
        precio = Double.parseDouble(etPrecio.getText().toString());
        Intent i = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        bundle.putString("localidad",localidad);
        bundle.putString("calle",calle);
        bundle.putString("tipo",tipo);
        bundle.putString("numero",numero);
        bundle.putDouble("precio", precio);
        bundle.putInt("index",index);
        i.putExtras(bundle);
        setResult(Activity.RESULT_OK, i);
        finish();
    }

    public void cancelar(View v){
        Intent i = new Intent();
        setResult(Activity.RESULT_CANCELED, i);
        finish();
    }


    /***************************************************************/
    /*                                                             */
    /*                         AUXILIARES                          */
    /*                                                             */
    /***************************************************************/


    private void initComponents(){
        etId = (EditText)findViewById(R.id.etId);
        etLocalidad = (EditText)findViewById(R.id.etLocalidad);
        etCalle =(EditText)findViewById(R.id.etCalle);
        etNumero = (EditText)findViewById(R.id.etNumero);
        etTipo = (EditText)findViewById(R.id.etTipo);
        etPrecio =(EditText)findViewById(R.id.etPrecio);
        btAceptar = (Button)findViewById(R.id.btAceptar);
        btCancelar = (Button)findViewById(R.id.btCancelar);
    }



}
