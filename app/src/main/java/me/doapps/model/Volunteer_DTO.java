package me.doapps.model;

/**
 * Created by jnolascob on 24/08/2014.
 */
public class Volunteer_DTO {
    private int id;
    private String name;
    private String last_name;
    private String created_at;
    private int gender;
    private int state;
    private int assistance_id;

    public Volunteer_DTO(){}

    public Volunteer_DTO(String name, String last_name){
        this.name = name;
        this.last_name = last_name;
    }

    public Volunteer_DTO(String name, String last_name, String created_at){
        this.name = name;
        this.last_name = last_name;
        this.created_at = created_at;
    }

    public Volunteer_DTO(int id, String name, String last_name, String created_at){
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.created_at = created_at;
    }
    public Volunteer_DTO(int id, String name, String last_name, int gender, int state){
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.gender = gender;
        this.state = state;
    }

    /*assistance*/
    public Volunteer_DTO(int id, String name, String last_name, int gender, int state, int assistance_id){
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.gender = gender;
        this.state = state;
        this.assistance_id = assistance_id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getAssistance_id() {
        return assistance_id;
    }

    public void setAssistance_id(int assistance_id) {
        this.assistance_id = assistance_id;
    }
}
