package kr.ac.cau.cse.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText id,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id = (EditText)findViewById(R.id.editText);
        password = (EditText)findViewById(R.id.editText2);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(
                        id.getText().toString().equals("")||// id값이 입력되었는지
                        password.getText().toString().equals("")){//pw값이 입력되었는지

                    Toast.makeText(MainActivity.this,"칸을 모두 채워주세요",Toast.LENGTH_SHORT).show();
                }else {
                    new LoginTask().execute(
                            id.getText().toString(),
                            password.getText().toString()
                    );
                }
            }
        });
    }
    private class LoginTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params) {
            InputStream inputStream;
            String strResult = "";
            try {
                //서버랑 연결하는 부분
                URL url = new URL("http://oracletest.run.goorm.io/login/");     //연결할 주소
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setUseCaches(false);
                urlConnection.setConnectTimeout(10000);
                urlConnection.setReadTimeout(10000);
                String postParam ="data=";

                JSONObject object = new JSONObject();
                object.put("user_id",params[0]);
                object.put("password",params[1]);
                postParam+=object.toString();
                DataOutputStream dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
                dataOutputStream.writeBytes(postParam);
                dataOutputStream.flush();
                dataOutputStream.close();

                inputStream = urlConnection.getInputStream();               //서버의 응답을 읽는다.
                BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
                String line = "";
                while((line = bufferedReader.readLine()) != null)
                    strResult += line;
                inputStream.close();
                Log.d("get Posts",strResult);

            }catch (Exception e){
                e.printStackTrace();
            }

            return strResult;
        }

        @Override
        protected void onPostExecute(String dataFromServer) {
            super.onPostExecute(dataFromServer);
            Toast.makeText(MainActivity.this,dataFromServer,Toast.LENGTH_SHORT).show();
        }
    }
}
