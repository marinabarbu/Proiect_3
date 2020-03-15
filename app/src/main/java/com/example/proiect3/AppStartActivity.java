package com.example.proiect3;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

public class AppStartActivity extends AppCompatActivity {

    HttpParse httpParse = new HttpParse();
    ProgressDialog pDialog;

    String HttpURL = "http://10.0.2.2/proiect3/DisplayUserData.php";

    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    String ParseResult ;
    HashMap<String,String> ResultHash = new HashMap<>();
    String FinalJSonObject ;
    TextView username,email, mobile, gender;
    String IdHolder, UsernameHolder, EmailHolder, MobileHolder, GenderHolder;
    Button UpdateButton;
    String TempItem;
    ProgressDialog progressDialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_start);
        username = (TextView)findViewById(R.id.textUsername);
        email = (TextView)findViewById(R.id.textEmail);
        mobile = (TextView)findViewById(R.id.textMobile);
        gender = (TextView)findViewById(R.id.textGender);
        UpdateButton = (Button)findViewById(R.id.buttonUpdate);
        TempItem = getIntent().getStringExtra("Email");
        HttpWebCall(TempItem);


        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppStartActivity.this,UpdateActivity.class);
                intent.putExtra("ID", IdHolder);
                intent.putExtra("Username", UsernameHolder);
                intent.putExtra("Email", EmailHolder);
                intent.putExtra("Mobile", MobileHolder);
                intent.putExtra("Gender", GenderHolder);
                startActivity(intent);
                finish();
            }
        });
    }


    public void HttpWebCall(final String PreviousListViewClickedItem){
        class HttpWebCallFunction extends AsyncTask<String,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = ProgressDialog.show(AppStartActivity.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                pDialog.dismiss();
                FinalJSonObject = httpResponseMsg ;
                new AppStartActivity.GetHttpResponse(AppStartActivity.this).execute();
            }

            @Override
            protected String doInBackground(String... params) {
                ResultHash.put("Email",params[0]);
                ParseResult = httpParse.postRequest(ResultHash, HttpURL);
                return ParseResult;
            }
        }

        HttpWebCallFunction httpWebCallFunction = new HttpWebCallFunction();
        httpWebCallFunction.execute(PreviousListViewClickedItem);
    }

    private class GetHttpResponse extends AsyncTask<Void, Void, Void> {
        public Context context;
        public GetHttpResponse(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                if(FinalJSonObject != null) {
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(FinalJSonObject);
                        JSONObject jsonObject;
                        for(int i=0; i<jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);

                            IdHolder = jsonObject.getString("ID").toString() ;
                            UsernameHolder = jsonObject.getString("username").toString() ;
                            EmailHolder = jsonObject.getString("email").toString() ;
                            MobileHolder = jsonObject.getString("mobile").toString() ;
                            GenderHolder = jsonObject.getString("gender").toString() ;
                        }
                    }
                    catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            username.setText(UsernameHolder);
            email.setText(EmailHolder);
            mobile.setText(MobileHolder);
            gender.setText(GenderHolder);

        }
    }
}