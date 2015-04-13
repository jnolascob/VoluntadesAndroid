package me.doapps.fragments;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import me.doapps.database.DataBaseManager;
import me.doapps.voluntades.R;

/**
 * Created by jnolascob on 07/09/2014.
 */
public class Fragment_Lastest extends Fragment {
    private RadioGroup radio_gender;
    private Button btn_volunteer_save;

    private DataBaseManager manager;
    private Cursor cursor;
    private int gender = 1;

    private ProgressDialog dialog;

    public static final Fragment_Lastest newInstance(){
        return new Fragment_Lastest();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lastest, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*create database*/
        manager = new DataBaseManager(getActivity());

        /**/
        radio_gender = (RadioGroup)getView().findViewById(R.id.radio_gender);
        btn_volunteer_save = (Button)getView().findViewById(R.id.btn_volunteer_save);

        radio_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rdb_male:
                        gender = 1;
                        break;
                    case R.id.rdb_female:
                        gender = 2;
                        break;
                    default:
                        gender = 1;
                        break;
                }
            }
        });

        btn_volunteer_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp_name = ((EditText) getView().findViewById(R.id.txt_volunteer_name)).getText().toString();
                String temp_lastname = ((EditText) getView().findViewById(R.id.txt_volunteer_lastname)).getText().toString();

                String[] params = new String[]{temp_name, temp_lastname, String.valueOf(gender)};
                new insertVolunteer().execute(params);

            }
        });
    }

    private class insertVolunteer extends AsyncTask<String, Void, Void>{
        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(getActivity(), null, getString(R.string.save_volunteer), true, false);
        }

        @Override
        protected Void doInBackground(String... params) {
            manager.insertVolunteer(params[0], params[1], params[2]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialog.dismiss();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, Fragment_Assistance.newInstance()).commit();
        }
    }
}
