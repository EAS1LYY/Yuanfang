package com.gujiunet.code;

public class random_code_model {
    private String code_title;
    private String code_id;
    private String code_text;
    private String code_imageurl;
    public random_code_model(String codeTitle,String codeid,String codeimageurl) {
        this.code_title=codeTitle;
        this.code_id=codeid;
        this.code_imageurl=codeimageurl;
    }
    public String getCode_title(){
        return code_title;
    }
    public String getCode_id(){
        return code_id;
    }
    public String getCode_imageurl(){return code_imageurl;}
    public void setCode_title(String codeTitle){
        this.code_title=code_title;
    }
    public void setCode_id(String codeid){
        this.code_id=code_id;
    }
    public void setCode_image(String codeimageurl){
        this.code_imageurl=code_imageurl;
    }

}
