package kr.ac.cau.cse.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    Context mContext;
    PostAdapter adapt;
    ArrayList<PostData> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity","onCreate");
        ListView listView = (ListView)findViewById(R.id.mylist);
        for(int i = 0; i<30;i++){
            data.add(new PostData(
                    i,
                    1+i%4,
                    "post"+i,
                    "body"+i
            ));
        }
        mContext = this;
         adapt = new PostAdapter(this,R.layout.customlayout,data);
        listView.setAdapter(adapt);
        listView .setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,data.get(position).title,Toast.LENGTH_SHORT).show();
            }
        });
        GetPostsTask myTask = new GetPostsTask();
        myTask.execute("");
//        for(int i = 0; i<30;i++){
//            data.add(new PostData(
//                    i,
//                    1+i%4,
//                    "post"+i,
//                    "body"+i
//            ));
//        }
//        adapt.notifyDataSetChanged();
    }
    private class GetPostsTask extends AsyncTask<String,Void,ArrayList<PostData>>{
        @Override
        protected ArrayList<PostData> doInBackground(String... params) {
            InputStream inputStream;
            String strResult = "";
            ArrayList<PostData> tempArray = new ArrayList<>();
            try {
                Thread.sleep(10000);

                URL url = new URL("http://jsonplaceholder.typicode.com/posts");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setUseCaches(false);
                urlConnection.setConnectTimeout(10000);
                urlConnection.setReadTimeout(10000);
                inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
                String line = "";
                while((line = bufferedReader.readLine()) != null)
                    strResult += line;
                inputStream.close();
                Log.d("get Posts",strResult);


                JSONArray array = new JSONArray(strResult);
                for(int i = 0;i<array.length();i++){
                    JSONObject tempObject = array.getJSONObject(i);
                    tempArray.add(
                            new PostData(
                                    tempObject.getInt("id"),
                                    tempObject.getInt("userId"),
                                    tempObject.getString("title"),
                                    tempObject.getString("body")
                            )
                    );
                }

            }catch (Exception e){
                e.printStackTrace();
            }

                return tempArray;
        }

        @Override
        protected void onPostExecute(ArrayList<PostData> datas) {
            super.onPostExecute(datas);
            data.clear();
            data.addAll(datas);
            adapt.notifyDataSetChanged();
        }
    }
}
