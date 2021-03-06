package com.adrenastudies.intercorptest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Eder on 04-02-2016.
 */
public abstract class DrawerAdapter extends BaseAdapter {

        private ArrayList<?> aData;
        private int R_layout_IdView;
        private Context contexto;

        public DrawerAdapter(Context contexto, int R_layout_IdView, ArrayList<?> aData) {
            super();
            this.contexto = contexto;
            this.aData = aData;
            this.R_layout_IdView = R_layout_IdView;
        }

        @Override
        public View getView(int posicion, View view, ViewGroup pariente) {

            if (view == null) {
                LayoutInflater vi = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = vi.inflate(R_layout_IdView, null);
            }

            onEntry(aData.get(posicion), view);
            return view;
        }

        @Override
        public int getCount() {
            return aData.size();
        }

        @Override
        public Object getItem(int posicion) {
            return aData.get(posicion);
        }

        @Override
        public long getItemId(int posicion) {
            return posicion;
        }

        public abstract void onEntry (Object data, View view);

    }

