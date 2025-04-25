package com.gujiunet.code;

public class assort_model {
    private String assort_name;
    private String assort_ID;
    private String assort_text;
    public assort_model(String assortname,String assortID,String assorttext) {
        this.assort_name=assortname;
        this.assort_ID=assortID;
        this.assort_text=assorttext;
    }
    public String getAssort_ID(){
        return assort_ID;
    }
    public String getAssort_name(){return assort_name;}
    public String getAssort_text(){return assort_text;}
}
