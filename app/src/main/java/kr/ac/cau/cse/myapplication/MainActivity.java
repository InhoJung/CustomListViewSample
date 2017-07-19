package kr.ac.cau.cse.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity","onCreate");
        ListView listView = (ListView)findViewById(R.id.mylist);
        ArrayList<PostData> data = new ArrayList<>();
        for(int i = 0; i<30;i++){
            data.add(new PostData(
                    i,
                    1+i%4,
                    "post"+i,
                    "body"+i
            ));
        }
        PostAdapter adapt = new PostAdapter(this,R.layout.customlayout,data);
        listView.setAdapter(adapt);
    }
}
