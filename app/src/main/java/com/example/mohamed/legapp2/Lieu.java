package com.example.mohamed.legapp2;

/**
 * Created by Mohamed on 03/07/2015.
 */
public class Lieu {

    private long id;
    private String name;
    private String major;
    private String minor;
    private String type;

   /* public Lieu(String name, String major, String minor, String type){
    setName(name);
    setMajor(major);
    setMinor(minor);
    setType(type);

    }*/

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    @Override
    public String toString(){
        return "("+getName()+" , "+getMajor()+" , "+getMinor()+" , "+getType()+")" ;
    }

}
