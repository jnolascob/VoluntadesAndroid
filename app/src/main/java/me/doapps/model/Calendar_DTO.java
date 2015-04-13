package me.doapps.model;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by jnolascob on 24/08/2014.
 */
public class Calendar_DTO {
    private long id;
    private String date;
    private String created_at;
    private String updated_at;

    public Calendar_DTO(){}

    public Calendar_DTO(String date, String created_at){
        this.date = date;
        this.created_at = created_at;
    }

    public Calendar_DTO(long id, String date, String created_at){
        this.id = id;
        this.date = date;
        this.created_at = created_at;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCreated_at(){
        return  created_at;
    }

    public void setCreated_at(String created_at){
        this.created_at = created_at;
    }
}
