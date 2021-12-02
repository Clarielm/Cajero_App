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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Abono extends AppCompatActivity implements View.OnClickListener{


    EditText txtId,txtTransaccion,txtSaldo;
    Button btnSaldo,btnRetirar,btnAbonar,btnCerrarSesion;

    RequestQueue requestQueue;

    private static final String URL1 = "http://192.168.1.3/banco/buscar.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abono);

        requestQueue = Volley.newRequestQueue(this);

        //UT
        initUT();
        Inabilitar();


        btnSaldo.setOnClickListener(this);
        btnRetirar.setOnClickListener(this);
        btnAbonar.setOnClickListener(this);
        btnCerrarSesion.setOnClickListener(this);
    }

    private void initUT() {

        //EditText
        txtId = (EditText) findViewById(R.id.txtId);
        txtTransaccion = (EditText) findViewById(R.id.txtTransaccion);
        txtSaldo = (EditText) findViewById(R.id.txtSaldo);

        //Buttons
        btnSaldo = (Button) findViewById(R.id.btnSaldo);
        btnRetirar = (Button) findViewById(R.id.btnRetirar);
        btnAbonar = (Button) findViewById(R.id.btnAbonar);
        btnCerrarSesion = (Button) findViewById(R.id.btnCerrarSesion);

    }

    private void Inabilitar(){

        txtTransaccion.setEnabled(false);
        btnAbonar.setEnabled(false);
        btnRetirar.setEnabled(false);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if(id == R.id.btnSaldo){

            String idCuenta = txtId.getText().toString().trim();

            if(!txtId.getText().toString().equals("")){

                buscarCuenta(idCuenta);
                txtTransaccion.setEnabled(true);
                btnAbonar.setEnabled(true);
                btnRetirar.setEnabled(true);
                txtId.setEnabled(false);

            }
            else{
                Toast.makeText(Abono.this, "Debe Insertar su Cuenta", Toast.LENGTH_SHORT).show();
            }




        }
        else if (id == R.id.btnAbonar){

            String saldo1 = txtSaldo.getText().toString().trim();
            String saldo2 = txtTransaccion.getText().toString().trim();
            String idCuenta = txtId.getText().toString().trim();

            float saldo3 = Float.parseFloat(saldo1);
            float saldo4 = Float.parseFloat(saldo2);

            float transaccion1 = saldo3 + saldo4;
            String transaccion = Float.toString(transaccion1);

            ActualizarCuenta(idCuenta,transaccion);



        }
        else if (id == R.id.btnRetirar){

            String saldo1 = txtSaldo.getText().toString().trim();
            String saldo2 = txtTransaccion.getText().toString().trim();
            String idCuenta = txtId.getText().toString().trim();


            float saldo3 = Float.parseFloat(saldo1);
            float saldo4 = Float.parseFloat(saldo2);

            float transaccion1 = saldo3 - saldo4;
            String transaccion = Float.toString(transaccion1);

            ActualizarCuenta2(idCuenta,transaccion);

        }
        else if (id == R.id.btnCerrarSesion){

            Intent i = new Intent(Abono.this, SegundaVista.class);
            startActivity(i);

        }


    }

    private void ActualizarCuenta2(String idCuenta, String transaccion) {

        String numero = txtTransaccion.getText().toString().trim();
        String saldo = txtSaldo.getText().toString().trim();
        float numero2 = Float.parseFloat(numero);
        float numero3 = Float.parseFloat(saldo);

        if(!txtTransaccion.getText().toString().equals("")){

            if(numero2 <= numero3){

                String URL3 = "http://192.168.1.3/banco/editarAbono.php";

                StringRequest stringRequest = new StringRequest(
                        Request.Method.POST,
                        URL3,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(Abono.this, "Transaccion exitosa", Toast.LENGTH_SHORT).show();
                                txtTransaccion.setText("");
                                buscarCuenta(idCuenta);


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
                        params.put("idCuenta", idCuenta);
                        params.put("Saldo", transaccion);

                        return params;
                    }
                };

                requestQueue.add(stringRequest);



            }
            else{
                Toast.makeText(Abono.this, "El Retiro sobrepasa el Saldo disponible", Toast.LENGTH_SHORT).show();
            }


        }
        else {
            Toast.makeText(Abono.this, "Todos los campos son necesarios", Toast.LENGTH_SHORT).show();
        }




    }

    private void ActualizarCuenta(final String idCuenta, final String transaccion) {

        String numero = txtTransaccion.getText().toString().trim();
        float numero2 = Float.parseFloat(numero);

        if(!txtTransaccion.getText().toString().equals("")){

            if(numero2 >0){

                String URL3 = "http://192.168.1.3/banco/editarAbono.php";

                StringRequest stringRequest = new StringRequest(
                        Request.Method.POST,
                        URL3,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(Abono.this, "Transaccion exitosa", Toast.LENGTH_SHORT).show();
                                txtTransaccion.setText("");
                                buscarCuenta(idCuenta);


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
                        params.put("idCuenta", idCuenta);
                        params.put("Saldo", transaccion);

                        return params;
                    }
                };

                requestQueue.add(stringRequest);



            }
            else{
                Toast.makeText(Abono.this, "El abono debe ser mayor a $0 dolares", Toast.LENGTH_SHORT).show();
            }


        }
        else {
            Toast.makeText(Abono.this, "Todos los campos son necesarios", Toast.LENGTH_SHORT).show();
        }



    }

    private void buscarCuenta(String idCuenta) {

        if(!txtId.getText().toString().equals("")){

            String URL2 = "http://192.168.1.3/banco/buscar.php?idCuenta=" + idCuenta;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    URL2,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String Saldo;

                            try {

                                Saldo = response.getString("Saldo");
                                txtSaldo.setText(Saldo);

                            } catch (JSONException e){

                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }


            );

            requestQueue.add(jsonObjectRequest);

        }
        else{
            Toast.makeText(Abono.this, "Todos los campos son necesarios", Toast.LENGTH_SHORT).show();
        }



    }
}