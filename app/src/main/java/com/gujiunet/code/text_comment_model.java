package com.gujiunet.code;

public class text_comment_model {
    private String text_commentid;
    private String text_commentauthor;
    private String text_commenttext;
    private String text_commenttime;
    private String text_commentimgurl;
    private String text_commentauthorname;
    private String text_commentqx;
    public text_comment_model(String text_comment_id, String text_comment_author, String text_comment_text, String text_comment_time,String text_author_name,String text_author_imgurl,String text_comment_qx) {
        this.text_commentid=text_comment_id;
        this.text_commentauthor=text_comment_author;
        this.text_commenttext=text_comment_text;
        this.text_commenttime=text_comment_time;
        this.text_commentauthorname=text_author_name;
        this.text_commentimgurl=text_author_imgurl;
        this.text_commentqx=text_comment_qx;
    }
    public String getText_commentid(){
        return text_commentid;
    }
    public String getText_commenttext(){
        return text_commenttext;
    }
    public String getText_commentauthor(){
        return text_commentauthor;
    }
    public String getText_commenttime(){return text_commenttime;}
    public String getText_commentimgurl(){return text_commentimgurl;}
    public String getText_commentauthorname(){return text_commentauthorname;}
    public String getText_commentqx(){return text_commentqx;}

}
