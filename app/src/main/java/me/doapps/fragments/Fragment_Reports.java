package me.doapps.fragments;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import me.doapps.database.DataBaseHelper;
import me.doapps.database.DataBaseManager;
import me.doapps.voluntades.R;

/**
 * Created by jnolascob on 07/09/2014.
 */
public class Fragment_Reports extends Fragment {
    private SQLiteDatabase db;

    private EditText txt_name;
    private EditText txt_lastname;
    private TextView txtResultado;

    private Button btnInsertar;
    private Button btnActualizar;
    private Button btnEliminar;
    private Button btnConsultar;
    private Button btnLimpiar;

    public static final Fragment_Reports newInstance(){
        return new Fragment_Reports();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reports, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Obtenemos las referencias a los controles
        txt_name = (EditText)getView().findViewById(R.id.txt_name);
        txt_lastname = (EditText)getView().findViewById(R.id.txt_lastname);
        txtResultado = (TextView)getView().findViewById(R.id.txtResultado);

        btnInsertar = (Button)getView().findViewById(R.id.btnInsertar);
        btnActualizar = (Button)getView().findViewById(R.id.btnActualizar);
        btnEliminar = (Button)getView().findViewById(R.id.btnEliminar);
        btnConsultar = (Button)getView().findViewById(R.id.btnConsultar);
        btnLimpiar = (Button)getView().findViewById(R.id.btnLimpiar);





    }
}
