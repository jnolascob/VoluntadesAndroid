package me.doapps.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import me.doapps.model.Value_DTO;
import me.doapps.voluntades.R;

/**
 * Created by jnolascob on 06/09/2014.
 */
public class Value_Adapter extends BaseAdapter {
    private ArrayList<Value_DTO> value_dtos;
    private Context context;
    private LayoutInflater inflater;

    public Value_Adapter(ArrayList<Value_DTO> value_dtos, Context context){
        this.value_dtos = value_dtos;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return value_dtos.size();
    }

    @Override
    public Object getItem(int i) {
        return value_dtos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = null;
        Value_DTO value_dto = value_dtos.get(i);

        if(view == null){
            view = inflater.inflate(R.layout.item_take_assistance,viewGroup, false);
            holder = new Holder();

            holder.txt_value_name = (TextView)view.findViewById(R.id.txt_value_name);
            holder.img_value = (ImageView)view.findViewById(R.id.img_value);

            view.setTag(holder);
        }
        else{
            holder = (Holder)view.getTag();
        }

        holder.txt_value_name.setText(value_dto.getName());
        switch (value_dto.getState()){
            case 1:
                holder.img_value.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_good));
                break;
            case 2:
                holder.img_value.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_middle));
                break;
            case 3:
                holder.img_value.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_bad));
                break;
            default:
                holder.img_value.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_good));
                break;
        }
        return view;
    }

    class Holder{
        TextView txt_value_name;
        ImageView img_value;
    }
}
