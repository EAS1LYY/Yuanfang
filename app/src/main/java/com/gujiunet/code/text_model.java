package com.gujiunet.code;

public class text_model {
    private String text_id;
    private String text_title;
    private String text_text;
    private String text_author;
    private String text_time;
    private String text_views;
    private String text_author_name;
    private String text_author_imgurl;
    public text_model(String text_id, String text_title, String text_text, String text_author,String text_time,String text_views,String text_author_name,String text_author_imgurl) {
        this.text_id=text_id;
        this.text_title=text_title;
        this.text_text=text_text;
        this.text_author=text_author;
        this.text_time=text_time;
        this.text_views=text_views;
        this.text_author_name=text_author_name;
        this.text_author_imgurl=text_author_imgurl;
    }
    public String getText_id(){
        return text_id;
    }
    public String getText_title(){
        return text_title;
    }
    public String getText_text(){
        return text_text;
    }
    public String getText_author(){return text_author;}
    public String getText_time(){return  text_time;}
    public  String getText_views(){return text_views;}

    public  String getText_author_name(){return text_author_name;}
    public String getText_author_imgurl(){return text_author_imgurl;}

    public void setText_id(String text_id){
        this.text_id=text_id;
    }
    public void setText_text(String text_text){
        this.text_text=text_text;
    }
    public void setText_title(String text_title){
        this.text_title=text_title;
    }
    public void setText_author(String text_author){
        this.text_author=text_author;
    }
    public void setText_time(String  text_time){this.text_time=text_time;}
    public void  setText_views(String text_views){this.text_views=text_views;}
}
