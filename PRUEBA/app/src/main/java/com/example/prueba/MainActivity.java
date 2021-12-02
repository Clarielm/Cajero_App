package com.example.prueba;

import static android.view.View.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    EditText txtNombre,txtApellido,txtUsuario,txtContra;
    Button btnCrear,btnLimpiar,btnLog;

    RequestQueue requestQueue;

    private static final String URL1 = "http://192.168.1.3/banco/insertar.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);

        //UT
        initUT();

        btnCrear.setOnClickListener(this);
        btnLimpiar.setOnClickListener(this);
        btnLog.setOnClickListener(this);

    }

    private void initUT(){

        //EditText
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtApellido = (EditText) findViewById(R.id.txtApellido);
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtContra = (EditText) findViewById(R.id.txtContra);

        //Buttons
        btnCrear = (Button) findViewById(R.id.btnCrear);
        btnLimpiar = (Button) findViewById(R.id.btnLimpiar);
        btnLog = (Button) findViewById(R.id.btnLog);

    }


    @Override
    public void onClick(View v) {

        int id = v.getId();

        if(id == R.id.btnCrear){

            String nombre = txtNombre.getText().toString().trim();
            String apellido = txtApellido.getText().toString().trim();
            String usuario = txtUsuario.getText().toString().trim();
            String contra = txtContra.getText().toString().trim();

            if(nombre != "" && apellido != "" && usuario != "" && contra != ""){

                crearusuario(nombre,apellido,usuario,contra);
            }
            else{
                Toast.makeText(MainActivity.this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
            }



        }
        else if (id == R.id.btnLimpiar){

            txtNombre.setText("");
            txtApellido.setText("");
            txtUsuario.setText("");
            txtContra.setText("");

        }

        else if (id == R.id.btnLog){
            Intent i = new Intent(MainActivity.this, SegundaVista.class);
            startActivity(i);

        }

    }

    private void crearusuario(final String nombre, final String apellido, final String usuario, final String contra) {

        if(!txtNombre.getText().toString().equals("") && !txtApellido.getText().toString().equals("") && !txtUsuario.getText().toString().equals("")  && !txtContra.getText().toString().equals("") ){


            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    URL1,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(MainActivity.this, "Usuario Insertado", Toast.LENGTH_SHORT).show();
                            txtNombre.setText("");
                            txtApellido.setText("");
                            txtUsuario.setText("");
                            txtContra.setText("");
                            Intent i = new Intent(MainActivity.this, SegundaVista.class);
                            startActivity(i);

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Nombre",nombre);
                    params.put("Apellido",apellido);
                    params.put("Usuario",usuario);
                    params.put("Contra",contra);

                    return params;
                }
            };

            requestQueue.add(stringRequest);


        }
        else{
            Toast.makeText(MainActivity.this, "Todos los campos son necesarios", Toast.LENGTH_SHORT).show();
        }


    }
}