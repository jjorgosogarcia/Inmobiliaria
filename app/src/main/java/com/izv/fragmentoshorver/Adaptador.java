package com.izv.fragmentoshorver;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class Adaptador extends ArrayAdapter<Inmuebles>{


        private Context contexto;
        private ArrayList<Inmuebles> lista;
        private int recurso;
        private LayoutInflater i;

        public Adaptador(Context context, int resource, ArrayList<Inmuebles> objects) {
            super(context, resource, objects);
            this.contexto = context;
            this.lista = objects;
            this.recurso = resource;
            this.i = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public class ViewHolder {
            public TextView tv1, tv2, tv3, tv4, tv5;
            public ImageView iv;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.v("LOG", "" + lista.size());
            ViewHolder vh = null;
            if (convertView == null) {
                convertView = i.inflate(recurso, null);
                vh = new ViewHolder();
                vh.tv1 = (TextView) convertView.findViewById(R.id.tvId);
                vh.tv2 = (TextView) convertView.findViewById(R.id.tvLocalidad);
                vh.tv3 = (TextView) convertView.findViewById(R.id.tvCalle);
                vh.tv4 = (TextView)convertView.findViewById(R.id.tvTipo);
                vh.tv5 = (TextView)convertView.findViewById(R.id.tvPrecio);
                vh.iv = (ImageView) convertView.findViewById(R.id.ivImagen);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            vh.tv1.setText(lista.get(position).getId());
            vh.tv2.setText(lista.get(position).getLocalidad());
            vh.tv3.setText(lista.get(position).getCalle()+" "+lista.get(position).getNumero());
            vh.tv4.setText(lista.get(position).getTipo());
            vh.tv5.setText(lista.get(position).getPrecio()+"€");
            vh.iv.setTag(position);
            return convertView;
        }
}