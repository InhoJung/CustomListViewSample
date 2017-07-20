package kr.ac.cau.cse.myapplication;

import java.io.Serializable;

/**
 * Created by dhtpr on 2017-07-19.
 */

public class PostData implements Serializable{
    int id, userid;
    String title,body;
    public PostData(int id, int userid, String title, String body){
        this.id= id;
        this.userid= userid;
        this.title= title;
        this.body= body;
    }
}
