package me.doapps.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import me.doapps.adapters.Value_Adapter;
import me.doapps.adapters.Volunteer_Adapter;
import me.doapps.database.DataBaseManager;
import me.doapps.model.Value_DTO;
import me.doapps.model.Volunteer_DTO;
import me.doapps.voluntades.R;

/**
 * Created by jnolascob on 24/08/2014.
 */
public class Fragment_Assistance extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private DataBaseManager manager;
    private Cursor cursor;

    private ArrayList<Volunteer_DTO> volunteer_dtos = new ArrayList<Volunteer_DTO>();
    private Volunteer_Adapter volunteer_adapter;

    private Button btn_save;
    private Button btn_update;
    private ListView list_volunteer;

    private String today;

    public static final Fragment_Assistance newInstance() {
        return new Fragment_Assistance();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_assitance, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        today = formater.format(calendar.getTime());

        ((TextView) getView().findViewById(R.id.txt_today)).setText(today);
        btn_save = (Button) getView().findViewById(R.id.btn_save);
        btn_update = (Button) getView().findViewById(R.id.btn_update);
        list_volunteer = (ListView) getView().findViewById(R.id.list_volunteer);
        btn_save.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        list_volunteer.setOnItemClickListener(this);

        /*create database*/
        manager = new DataBaseManager(getActivity());

        if (manager.countCalendar(today) > 0) {
            Log.e("coutn calendar", manager.countCalendar(today)+"");
            volunteer_dtos.clear();
            btn_update.setVisibility(View.VISIBLE);
            btn_save.setVisibility(View.GONE);

            int calendar_id = manager.getCalendarId(today);
            Log.e("calendar_id", calendar_id+"");
            cursor = manager.selectAssitance(String.valueOf(calendar_id));
            if(cursor.moveToFirst()){
                do{
                    int assistance_id = cursor.getInt(0);
                    String volunteer_id = cursor.getString(2);
                    int volunteer_state = cursor.getInt(3);
                    Cursor c = manager.selectVolunteer(volunteer_id);
                    if(c.moveToFirst()){
                        int temp_id = c.getInt(0);
                        String temp_name = c.getString(1);
                        String temp_lastname = c.getString(2);
                        int temp_gender = c.getInt(3);
                        volunteer_dtos.add(new Volunteer_DTO(temp_id,temp_name, temp_lastname, temp_gender, volunteer_state, assistance_id));
                    }

                }while(cursor.moveToNext());
            }
            volunteer_adapter = new Volunteer_Adapter(volunteer_dtos, getActivity());
            list_volunteer.setAdapter(volunteer_adapter);
        } else {
            Log.e("count calendar", "0");
             /*select*/
            volunteer_dtos.clear();
            cursor = manager.selectVolunteer();
            String dtos = "";
            if (cursor.moveToFirst()) {
                Log.e("entre", "entre");
                do {
                    int temp_id = cursor.getInt(0);
                    String temp_name = cursor.getString(1);
                    String temp_lastname = cursor.getString(2);
                    int temp_gender = cursor.getInt(3);
                    volunteer_dtos.add(new Volunteer_DTO(temp_id,temp_name, temp_lastname, temp_gender, 1));
                    dtos = dtos + temp_name + " - 0" + "\n";
                } while (cursor.moveToNext());
            }
            Log.e("volunteer dtos", dtos);

            volunteer_adapter = new Volunteer_Adapter(volunteer_dtos, getActivity());
            list_volunteer.setAdapter(volunteer_adapter);
        }
    }

    @Override
    public void onItemClick(final AdapterView<?> adapterView, View view, final int i, long l) {

        ArrayList<Value_DTO> value_dtos = new ArrayList<Value_DTO>();
        value_dtos.add(new Value_DTO("Llegó temprano", 1));
        value_dtos.add(new Value_DTO("Llegó tarde", 2));
        value_dtos.add(new Value_DTO("No llegó", 3));

        Value_Adapter value_adapter = new Value_Adapter(value_dtos, getActivity());

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setAdapter(value_adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                ListView listView = ((AlertDialog) dialogInterface).getListView();
                int state = ((Value_DTO) listView.getAdapter().getItem(which)).getState();
                Log.e("asistencia", state + "");

                ((Volunteer_DTO) adapterView.getAdapter().getItem(i)).setState(state);
                volunteer_adapter.notifyDataSetChanged();
                String temp_dtos = "";
                for (int j = 0; j < volunteer_dtos.size(); j++) {
                    temp_dtos = temp_dtos + volunteer_dtos.get(j).getName() + " - " + volunteer_dtos.get(j).getState() + "\n";
                }
                Log.e("volunteer dtoss", temp_dtos);
            }
        });
        builder.create();
        builder.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_save:
                manager.insertCalendar(today);
                int temp_calendar_id = manager.getCalendarId(today);
                for (int i = 0; i < volunteer_dtos.size(); i++) {
                    manager.insertAssitance(temp_calendar_id, volunteer_dtos.get(i).getId(), volunteer_dtos.get(i).getState());
                }
                Log.e("save", "guardado...");
                break;
            case R.id.btn_update:
                for (int i = 0; i < volunteer_dtos.size(); i++) {
                    manager.updateAssistance(String.valueOf(volunteer_dtos.get(i).getAssistance_id()), String.valueOf(volunteer_dtos.get(i).getState()));
                }
                Log.e("update", "updating ...");
                break;
            default:break;
        }
    }
}
