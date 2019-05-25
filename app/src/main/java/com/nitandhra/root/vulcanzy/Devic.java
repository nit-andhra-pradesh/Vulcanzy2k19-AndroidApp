package com.nitandhra.root.vulcanzy;

public class Devic {
    public String getDeviceid() {
        return deviceid;
    }

    public String getUsername() {
        return username;
    }

    String deviceid,username;
    public Devic()
    {

    }
    public Devic(String did,String uname)
    {
        this.deviceid=did;
        this.username=uname;
    }
}
