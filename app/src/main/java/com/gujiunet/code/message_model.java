package com.gujiunet.code;

public class message_model {
    private String inform_name;
    private String inform_text;
    private String inform_id;
    public message_model(String informname,String informtext,String informid) {
        this.inform_name=informname;
        this.inform_text=informtext;
        this.inform_id=informid;
    }
    public String getInform_name(){
        return inform_name;
    }
    public String getInform_text(){return inform_text;}
    public String getInform_id(){return inform_id;}
}
