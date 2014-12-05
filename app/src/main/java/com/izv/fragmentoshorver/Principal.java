package com.izv.fragmentoshorver;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Xml;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

public class Principal extends Activity {

    private ArrayList<Inmuebles> inmueble = new ArrayList<Inmuebles>();
    private Adaptador ad;
    private ListView lv;
    private Button btAnterior;
    private Button btSiguiente;
    private static final int CREAR = 0;
    private static final int MODIFICAR = 1;
    private final int VISUALIZAR = 2;
    private final int FOTO = 3;
    private Inmuebles inmuebleCamara;
    int fotoActual =0;
    private ArrayList<File> fotos;
    double precio = 0;

    /***************************************************************/
    /*                                                             */
    /*                         METODOS ON                          */
    /*                                                             */
    /***************************************************************/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String id = data.getStringExtra("id");
            String localidad = data.getStringExtra("localidad");
            String calle = data.getStringExtra("calle");
            String tipo = data.getStringExtra("tipo");
            String numero = data.getStringExtra("numero");
            double precio = 0;
            precio = data.getDoubleExtra("precio", precio);
            int index = data.getIntExtra("index", -1);
            Inmuebles propiedad = new Inmuebles(id, localidad, calle, tipo, numero, precio);
            switch (requestCode) {
                case CREAR:
                    //Añado el inmueble ordenado siempre que no esté repetido en la lista
                    if(propiedad.getId().equals("") || propiedad.getNumero().equals("") || propiedad.getCalle().equals("") || propiedad.getLocalidad().equals("") || propiedad.getPrecio()==0){
                        tostada(getString(R.string.error));
                    } else { if (!inmueble.contains(propiedad)) {
                        inmueble.add(propiedad);
                        guardar();
                        Collections.sort(inmueble);
                        ad.notifyDataSetChanged();
                    } else {
                        tostada(getString(R.string.Yatiene));
                    }}

                    break;
                case MODIFICAR:
                    //Modifico el inmueble ordenado siempre que no esté repetido en la lista
                    if(propiedad.getId().equals("") || propiedad.getNumero().equals("") || propiedad.getCalle().equals("") || propiedad.getLocalidad().equals("") || propiedad.getPrecio()==0){
                        tostada(getString(R.string.error));
                    }
                     else {if (!inmueble.contains(propiedad)) {
                        inmueble.set(index, propiedad);
                        guardar();
                        Collections.sort(inmueble);
                        ad.notifyDataSetChanged();
                    } else {
                        tostada(getString(R.string.Yatiene));
                    }}
                    break;
                case VISUALIZAR:
                    propiedad =(Inmuebles) data.getSerializableExtra("inmueble");
                    ArrayList <File>fotos = new ArrayList<File>();
                    FragmentoDos fd = (FragmentoDos)getFragmentManager().findFragmentById(R.id.fragment4);
                    fotos = (ArrayList)data.getExtras().get("fotos");
                    if(fotos.size()>0){
                        fd.iv.setImageURI(Uri.fromFile(fotos.get(0)));
                    }
                    fd.setText(propiedad.getCalle() + ", " + propiedad.getLocalidad());
                    break;

                case FOTO:
                    Bitmap foto = (Bitmap) data.getExtras().get("data");
                    FileOutputStream fos;
                    propiedad = inmuebleCamara;
                    Calendar calendario = new GregorianCalendar();
                    Date date = calendario.getTime();
                    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
                    String fecha = formatoFecha.format(date);
                    try {
                        fos = new FileOutputStream(getExternalFilesDir(Environment.DIRECTORY_DCIM)
                                + "/inmueble_" + propiedad.getId() + "_" + fecha + ".jpg");
                        foto.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                    } catch (FileNotFoundException e) {
                    }
                    break;
            }
        } else {
        }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);
        initComponents();

        final FragmentoDos fdos = (FragmentoDos) getFragmentManager().findFragmentById(R.id.fragment4);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int posicion, long id) {

                fotos = new ArrayList<File>();
                String ruta = getExternalFilesDir(Environment.DIRECTORY_DCIM).getPath();
                File direccion = new File(ruta);
                File[] archivoFotos = direccion.listFiles();
                for (int i = 0; i < archivoFotos.length; i++) {
                    String idInmueble = "";
                    idInmueble = archivoFotos[i].getName().split("_")[1];

                    if (idInmueble.equals(inmueble.get(posicion).getId() + "")) {
                        fotos.add(archivoFotos[i]);
                    }
                }
                Inmuebles propiedad = inmueble.get(posicion);

                if (fdos != null && fdos.isInLayout()) {


                    btSiguiente.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (fotoActual + 1 <= fotos.size() - 1) {
                                fdos.iv.setImageURI(Uri.fromFile(fotos.get(fotoActual + 1)));
                                fotoActual++;
                            } else {
                                fdos.iv.setImageURI(Uri.fromFile(fotos.get(0)));
                                fotoActual = 0;
                            }
                        }
                    });
                    btAnterior.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (fotoActual - 1 >= 0) {
                                fdos.iv.setImageURI(Uri.fromFile(fotos.get(fotoActual - 1)));
                                fotoActual--;
                            } else {
                                fdos.iv.setImageURI(Uri.fromFile(fotos.get(fotos.size() - 1)));
                                fotoActual = fotos.size() - 1;
                            }
                        }
                    });
                    fdos.setText(propiedad.getCalle() + " " + propiedad.getNumero() + ", " + propiedad.getLocalidad());
                    if (fotos.size() > 0) {
                        fdos.iv.setImageURI(Uri.fromFile(fotos.get(0)));
                        botonesVisibles();
                    }else
                        if(fotos.size()==0){
                            fdos.iv.setImageResource(R.drawable.ic_nofoto);
                            botonesInvisibles();
                        }


                } else {
                    Intent i = new Intent(Principal.this, Fotos.class);
                    i.putExtra("inmueble", propiedad);
                    i.putExtra("fotos", fotos);
                    startActivityForResult(i, VISUALIZAR);
                }
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextual, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;

        Inmuebles i = inmueble.get(index);

        if (id == R.id.action_editar) {
            editar(index);
        } else if (id == R.id.action_eliminar) {
            eliminar(index);
        } else if (id == R.id.action_foto) {
            inmuebleCamara = i;
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivityForResult(intent, FOTO);
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_anadir) {
            Intent i = new Intent(this, gestionarInmobiliaria.class);
            startActivityForResult(i, CREAR);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle savingInstanceState) {
        super.onSaveInstanceState(savingInstanceState);
        savingInstanceState.putSerializable("objeto", inmueble);
    }


    /***************************************************************/
    /*                                                             */
    /*                       METODOS CLICK                         */
    /*                                                             */
    /***************************************************************/

    public void editar(final int index) {
        Intent i = new Intent(this, gestionarInmobiliaria.class);
        Bundle b = new Bundle();
        b.putSerializable("inmueble", inmueble.get(index));
        b.putInt("index", index);
        i.putExtras(b);
        startActivityForResult(i, MODIFICAR);
    }

    public void eliminar(final int index) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.eliminarUno);
        alert.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        inmueble.remove(index);
                        guardar();
                        ad.notifyDataSetChanged();
                    }
                });
        alert.setNegativeButton(android.R.string.no, null);
        alert.show();
    }

    public class OrdenaPrecios implements Comparator<Inmuebles> {
        @Override
        public int compare(Inmuebles i1, Inmuebles i2) {
            if (i1.getPrecio() > (i2.getPrecio()))
                return 1;
            if (i1.getPrecio() < (i2.getPrecio()))
                return -1;
            return 0;
        }
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



    //Guarda nuestra lista de viviendas en un archivo xml
    private void guardar() {
        //Preparamos el archivo
        FileOutputStream fosxml = null;
        try {
            fosxml = new FileOutputStream(new File(getExternalFilesDir(null), "inmobiliaria.xml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //Preparamos el documento XML
        XmlSerializer docxml = Xml.newSerializer();
        try {
            docxml.setOutput(fosxml, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            docxml.startDocument(null, Boolean.valueOf(true));
        } catch (IOException e) {
            e.printStackTrace();
        }
        docxml.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

        //Creo las etiquetas
        try {
            docxml.startTag(null, "inmuebles"); //Etiqueta Raiz

            for (int i = 0; i < inmueble.size(); i++) {
                docxml.startTag(null, "inmueble");
                docxml.startTag(null, "id");
                docxml.text(inmueble.get(i).getId());
                docxml.endTag(null, "id");
                docxml.startTag(null, "localidad");
                docxml.text(inmueble.get(i).getLocalidad());
                docxml.endTag(null, "localidad");
                docxml.startTag(null, "calle");
                docxml.text(inmueble.get(i).getCalle());
                docxml.endTag(null, "calle");
                docxml.startTag(null, "numero");
                docxml.text(String.valueOf(inmueble.get(i).getNumero()));
                docxml.endTag(null, "numero");
                docxml.startTag(null, "tipo");
                docxml.text(inmueble.get(i).getTipo());
                docxml.endTag(null, "tipo");
                docxml.startTag(null, "precio");
                docxml.text(String.valueOf(inmueble.get(i).getPrecio()));
                docxml.endTag(null, "precio");
                docxml.endTag(null, "inmueble");
            }
            // Cierro el documento
            docxml.endDocument();
            docxml.flush();
            fosxml.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        ad = new Adaptador(this, R.layout.detalle, inmueble);
        lv = (ListView) findViewById(R.id.listView);
        btAnterior = (Button) findViewById(R.id.btAnterior);
        btSiguiente = (Button) findViewById(R.id.btSiguiente);
        lv.setAdapter(ad);
        registerForContextMenu(lv);
        leer();
        Collections.sort(inmueble);

    }

    //Lee los inmuebles de un archivo xml
    private void leer() {
        try {
            XmlPullParser lectorxml = Xml.newPullParser();
            lectorxml.setInput(new FileInputStream(new File(getExternalFilesDir(null), "inmobiliaria.xml")), "utf-8");
            int evento = lectorxml.getEventType();
            Inmuebles propiedad = new Inmuebles();

            while (evento != XmlPullParser.END_DOCUMENT) {
                if (evento == XmlPullParser.START_TAG) {
                    String etiqueta = lectorxml.getName();
                    if (etiqueta.compareTo("id") == 0) {
                        propiedad.setId(lectorxml.nextText());
                    } else if (etiqueta.compareTo("localidad") == 0) {
                        propiedad.setLocalidad(lectorxml.nextText());
                    } else if (etiqueta.compareTo("calle") == 0) {
                        propiedad.setCalle(lectorxml.nextText());
                    } else if (etiqueta.compareTo("numero") == 0) {
                        propiedad.setNumero(lectorxml.nextText());
                    } else if (etiqueta.compareTo("tipo") == 0) {
                        propiedad.setTipo(lectorxml.nextText());
                    } else if (etiqueta.compareTo("precio") == 0) {
                        propiedad.setPrecio(Double.parseDouble(lectorxml.nextText()));
                    }
                }
                if (evento == XmlPullParser.END_TAG) {
                    String etiqueta = lectorxml.getName();
                    if (etiqueta.compareTo("inmueble") == 0) {
                        inmueble.add(propiedad);
                        propiedad = new Inmuebles();
                    }
                }
                evento = lectorxml.next();
            }
        } catch (XmlPullParserException e) {
        } catch (IOException e) {
        }
    }


    private void tostada(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


}
