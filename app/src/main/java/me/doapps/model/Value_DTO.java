package me.doapps.model;

/**
 * Created by jnolascob on 06/09/2014.
 */
public class Value_DTO {
    private String name;
    private int state;

    public Value_DTO() {
    }

    public Value_DTO(String name, int state) {
        this.name = name;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}
