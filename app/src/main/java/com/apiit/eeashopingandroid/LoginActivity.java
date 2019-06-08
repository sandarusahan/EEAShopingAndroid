package com.apiit.eeashopingandroid;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private TextView reg_link;
    private EditText email;
    private EditText password;
    private Button signin_btn;
    private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.emai_login);
        password = findViewById(R.id.password_login);
        signin_btn = findViewById(R.id.sign_in_btn);

        session = new Session(this);
        session.setUserEmail("sandaru.sahan@gmail.com");
        session.setPassword("Sahan");
        reg_link = findViewById(R.id.register_link);

        final String url = "http://10.0.3.2:8080/users/public/authenticate";

        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                final Auth auth = new Auth();
//                auth.setEmail("sandaru.sahan@gmail.com");
//                auth.setPassword("Sahan");

                Gson gson = new Gson();
                String authJson = gson.toJson(auth);

                JsonParser parser = new JsonParser();
                JsonObject json = (JsonObject) parser.parse(authJson);

                JSONObject authjsonObject = null;
                try{
                    authjsonObject = new JSONObject(json.toString());
                }
                catch(Exception e){
                    e.printStackTrace();
                }

                if(email.getText().toString() != null && password.getText().toString() != null ){

                    RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                            Request.Method.POST,
                            url,
                            authjsonObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    if(response != null) {
                                        Gson gson = new Gson();
                                        getUser user = gson.fromJson(response.toString(), getUser.class);
                                        session.setUserEmail(user.email);
                                        session.setPassword(user.password);
                                        System.out.println(user);
                                        finish();
                                    }else{
                                        System.out.println("Auth failed");
                                        Snackbar.make(v ,"Email/Password not valid", Snackbar.LENGTH_LONG)
                                                .show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    System.out.println("Error registering user"+error);
                                }
                            })
                    {
                        @Override
                        protected Map<String, String> getParams(){
                            Map<String, String> authMap = new HashMap<>();
                            authMap.put("Accept", "application/json");
                            authMap.put("email", email.getText().toString());
//                            authMap.put("email", "sandaru.sahan@gmail.com");
                            authMap.put("password", password.getText().toString());
//                            authMap.put("password", "Sahan");

                            return authMap;
                        }

//                    @Override
//                    public Map<String, String> getHeaders() throws AuthFailureError
//                    {
//                        Map<String, String> headers = new HashMap<String, String>();
//                        headers.put("Content-Type", "application/form-data");
//                        return headers;
//                    }
                    };

                    requestQueue.add(jsonObjectRequest);



            }else{
                    Snackbar.make(v ,"Email/Password not valid", Snackbar.LENGTH_LONG)
                            .show();
                }
            }
            });



        reg_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
