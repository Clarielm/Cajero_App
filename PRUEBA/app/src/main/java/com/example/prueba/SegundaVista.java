package com.example.prueba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SegundaVista extends AppCompatActivity  implements View.OnClickListener{

    EditText txtUser,txtPass;
    Button btnIngresar,btnRegistrar;

    RequestQueue requestQueue;

    private static final String URL1 = "http://192.168.1.3/banco/validar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda_vista);

        requestQueue = Volley.newRequestQueue(this);

        //UT
        initUT();

        btnIngresar.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);

    }

    private void initUT() {

        //EditText
        txtUser = (EditText) findViewById(R.id.txtUser);
        txtPass = (EditText) findViewById(R.id.txtPass);

        //Buttons
        btnIngresar = (Button) findViewById(R.id.btnIngresar);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if(id == R.id.btnIngresar){

            String user = txtUser.getText().toString().trim();
            String pass = txtPass.getText().toString().trim();

                validarusuario(user,pass);
            }
        else if(id == R.id.btnRegistrar){

            Intent i = new Intent(SegundaVista.this, MainActivity.class);
            startActivity(i);

        }




        }

    private void validarusuario(String user, String pass) {

        if(!txtUser.getText().toString().equals("") && !txtPass.getText().toString().equals("")){

            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    URL1,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(SegundaVista.this, "Usuario Validado", Toast.LENGTH_SHORT).show();
                            txtUser.setText("");
                            txtPass.setText("");
                            Intent i = new Intent(SegundaVista.this, Abono.class);
                            startActivity(i);


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SegundaVista.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();

                        }
                    }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Usuario",user);
                    params.put("Contra",pass);

                    return params;
                }
            };

            requestQueue.add(stringRequest);


        }
        else{
            Toast.makeText(SegundaVista.this, "Todos los campos son necesarios", Toast.LENGTH_SHORT).show();
        }




    }



}