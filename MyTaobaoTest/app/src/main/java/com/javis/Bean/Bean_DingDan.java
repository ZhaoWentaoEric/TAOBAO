package com.javis.Bean;

public class Bean_DingDan {
    public  String user_id;
    public String project_id;
    public int buysum;

    public Bean_DingDan() {
    }

    public Bean_DingDan(String uid, String pid,int buysum) {
        this.user_id = uid;
        this.project_id = pid;
        this.buysum = buysum;
    }

    public String getUid() {
        return user_id;
    }

    public void setUid(String uid) {
        this.user_id = uid;
    }

    public String getPid() {
        return project_id;
    }

    public void setPid(String pid) {
        this.project_id = pid;
    }
    public  void Set_sum(int buysum){
        this.buysum =buysum;
    }
    public int Get_sum(){
        return  buysum;
    }


}
