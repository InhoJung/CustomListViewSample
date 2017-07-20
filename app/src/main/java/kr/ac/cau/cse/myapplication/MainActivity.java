package kr.ac.cau.cse.myapplication;

import android.content.Context;
import android.content.Intent;
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

import static android.R.attr.value;


public class MainActivity extends AppCompatActivity {
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
         adapt = new PostAdapter(this,R.layout.customlayout,data);
        listView.setAdapter(adapt);
        listView .setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,data.get(position).title,Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                intent.putExtra("postdata",data.get(position));



                PostData data = (PostData) getIntent().getSerializableExtra("postdata");
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
//        Intent intent = new Intent();
//        intent.putExtra("result", "abc");
//        setResult(1, intent);
//        finish();

    }
    private class GetPostsTask extends AsyncTask<String,Void,ArrayList<PostData>>{
        @Override
        protected ArrayList<PostData> doInBackground(String... params) {
            InputStream inputStream;
            String strResult = "";
            ArrayList<PostData> tempArray = new ArrayList<>();
            try {
                //서버랑 연결하는 부분
                URL url = new URL("http://jsonplaceholder.typicode.com/posts");     //연결할 주소
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setUseCaches(false);
                urlConnection.setConnectTimeout(10000);
                urlConnection.setReadTimeout(10000);
                inputStream = urlConnection.getInputStream();               //서버의 응답을 읽는다.
                BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
                String line = "";
                while((line = bufferedReader.readLine()) != null)
                    strResult += line;
                inputStream.close();
                Log.d("get Posts",strResult);

                //json 파싱하는 부분
                JSONArray array = new JSONArray(strResult);             //서버에서 받아온 string을 jsonArray로 변환
                for(int i = 0;i<array.length();i++){
                    JSONObject tempObject = array.getJSONObject(i);     //for문 돌면서 postdata 객체를 하나씩 생성
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
        protected void onPostExecute(ArrayList<PostData> dataFromServer) {
            super.onPostExecute(dataFromServer);
            data.clear();                           //기존에 있던 데이터를 지움
            data.addAll(dataFromServer);            //서버에서 받은 데이터를 넣어줌
            adapt.notifyDataSetChanged();           //리스트뷰 refresh
        }
    }
}
