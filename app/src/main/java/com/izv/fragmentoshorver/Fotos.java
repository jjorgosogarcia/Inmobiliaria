package com.izv.fragmentoshorver;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.File;
import java.util.ArrayList;


public class Fotos extends Activity {
    Inmuebles propiedad;
    ArrayList<File> fotos;
    ImageView ivFoto;
    TextView tv1;
    int fotoActual;
    Button btSiguiente, btAnterior;

    /***************************************************************/
    /*                                                             */
    /*                         METODOS ON                          */
    /*                                                             */
    /***************************************************************/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_fragmento_dos);
        fotoActual=0;
        ivFoto = (ImageView) findViewById(R.id.ivFotos);
        btSiguiente = (Button)findViewById(R.id.btSiguiente);
        btAnterior = (Button)findViewById(R.id.btAnterior);
        tv1 = (TextView)findViewById(R.id.tvTituloFoto);

        btSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fotoActual+1<=fotos.size()-1){
                    ivFoto.setImageURI(Uri.fromFile(fotos.get(fotoActual + 1)));
                    fotoActual++;
                }else{
                    ivFoto.setImageURI(Uri.fromFile(fotos.get(0)));
                    fotoActual=0;
                }
            }
        });

        btAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fotoActual-1>=0){
                    ivFoto.setImageURI(Uri.fromFile(fotos.get(fotoActual-1)));
                    fotoActual--;
                }else{
                    ivFoto.setImageURI(Uri.fromFile(fotos.get(fotos.size()-1)));
                    fotoActual=fotos.size()-1;
                }
            }
        });
        propiedad =(Inmuebles) getIntent().getExtras().getSerializable("inmueble");
        fotos = (ArrayList) getIntent().getExtras().getSerializable("fotos");
        if(fotos.size()>0){
            ivFoto.setImageURI(Uri.fromFile(fotos.get(0)));
            botonesVisibles();
        }else
        if(fotos.size()==0){
            ivFoto.setImageResource(R.drawable.ic_nofoto);
            botonesInvisibles();
        }
tv1.setText(propiedad.getCalle() + " " + propiedad.getNumero() + ", " + propiedad.getLocalidad());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Intent i = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("inmueble", propiedad);
        bundle.putSerializable("fotos",fotos);
        i.putExtras(bundle);
        setResult(Activity.RESULT_OK, i);
        finish();
    }

    /***************************************************************/
    /*                                                             */
    /*                         AUXILIARES                          */
    /*                                                             */
    /***************************************************************/

    private void botonesInvisibles(){
        btAnterior.setVisibility(View.INVISIBLE);
        btSiguiente.setVisibility(View.INVISIBLE);
    }

    private void botonesVisibles(){
        btAnterior.setVisibility(View.VISIBLE);
        btSiguiente.setVisibility(View.VISIBLE);
    }

}