package me.doapps.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import me.doapps.model.Volunteer_DTO;
import me.doapps.voluntades.R;

/**
 * Created by jnolascob on 06/09/2014.
 */
public class Volunteer_Adapter extends BaseAdapter {
    private ArrayList<Volunteer_DTO> volunteer_dtos;
    private Context context;
    private LayoutInflater inflater;

    public Volunteer_Adapter(ArrayList<Volunteer_DTO> volunteer_dtos, Context context){
        this.volunteer_dtos = volunteer_dtos;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return volunteer_dtos.size();
    }

    @Override
    public Object getItem(int i) {
        return volunteer_dtos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = null;
        Volunteer_DTO volunteer_dto = volunteer_dtos.get(i);

        if (view == null) {
            view = inflater.inflate(R.layout.item_volunteer, viewGroup, false);
            holder = new Holder();

            holder.img_profile = (ImageView) view.findViewById(R.id.img_profile);
            holder.txt_fullname = (TextView) view.findViewById(R.id.txt_fullname);
            holder.txt_state = (TextView) view.findViewById(R.id.txt_state);
            holder.img_state = (ImageView)view.findViewById(R.id.img_state);

            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        holder.txt_fullname.setText(volunteer_dto.getName()+" "+volunteer_dto.getLast_name());
        holder.txt_state.setText("peligro");
        if(volunteer_dto.getGender() == 2){
            holder.img_profile.setImageResource(R.drawable.image_profile_default_female);
        }
        if(volunteer_dto.getState()==1){
            holder.img_state.setImageResource(R.drawable.ic_good);
        }
        else{
            if(volunteer_dto.getState()==2){
                holder.img_state.setImageResource(R.drawable.ic_middle);
            }
            else{
                holder.img_state.setImageResource(R.drawable.ic_bad);
            }
        }

        return view;
    }

    class Holder{
        ImageView img_profile;
        ImageView img_state;
        TextView txt_fullname;
        TextView txt_state;
    }
}
