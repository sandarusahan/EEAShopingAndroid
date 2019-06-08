package com.apiit.eeashopingandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
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

public class RegisterActivity extends AppCompatActivity {

    EditText address;
    EditText password;
    EditText Fname;
    EditText Lname;
    EditText email;
    EditText contact;
    TextView linkAlready;
    Button registerBtn;

    String url = "http://10.0.3.2:8080/users/public/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Fname = (EditText) findViewById(R.id.f_name);
        Lname = (EditText) findViewById(R.id.l_name);
        address = (EditText) findViewById(R.id.address);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        contact = (EditText) findViewById(R.id.contact);
        registerBtn = (Button) findViewById(R.id.register_btn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User customer = new User();
                customer.setfName(Fname.getText().toString());
                customer.setlName(Lname.getText().toString());
                customer.setAddress(address.getText().toString());
                customer.setPassword(password.getText().toString());
                customer.setEmail(email.getText().toString());
                customer.setContact(contact.getText().toString());
                customer.setRole("jfnjk33");

                Gson gson = new Gson();
                String Jsonuser = gson.toJson(customer);

                JsonParser parser = new JsonParser();
                JsonObject json = (JsonObject) parser.parse(Jsonuser);

                JSONObject userjsonObject = null;
                try{
                    userjsonObject = new JSONObject(json.toString());
                }
                catch(Exception e){
                    e.printStackTrace();
                }

                RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        url,
                        userjsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                System.out.println(response.toString());
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println("Error registering user"+error);
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams(){
                                Map<String, String> userMap = new HashMap<>();
                                userMap.put("fName",Fname.getText().toString());
                                userMap.put("lName",Lname.getText().toString());
                                userMap.put("email",email.getText().toString());
                                userMap.put("password",password.getText().toString());
                                userMap.put("address",address.getText().toString());
                                userMap.put("role", "jfnjk33");

                                return userMap;
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
                finish();
            }
        });
    }
}
