package com.example.primeravez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BuscarProfesor extends AppCompatActivity {
    public static String usuario;
    EditText edtNombre,edtApellido,edtNacimiento,edtCorreo,edtBuscarNombre;
    Button btnBuscar,btnRegresar;
    RequestQueue requestQueue;

    public int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_profesor);

        Intent intent=getIntent();
        usuario=intent.getStringExtra("usuario");
        id=intent.getIntExtra("id",-1);


        edtBuscarNombre=(EditText)findViewById(R.id.edtBuscarNombre);
        edtNombre=(EditText)findViewById(R.id.edtNombre);
        edtApellido=(EditText)findViewById(R.id.edtApellido);
        edtNacimiento=(EditText)findViewById(R.id.edtNacimiento);
        edtCorreo=(EditText)findViewById(R.id.edtCorreo);

        btnBuscar=(Button)findViewById(R.id.btnBuscar);
        btnRegresar=(Button)findViewById(R.id.btnRegresar);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarCliente("https://conectmybd.000webhostapp.com/buscarCliente.php?nombre="+edtBuscarNombre.getText()+"");
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BuscarProfesor.this, Menu.class);
                intent.putExtra("usuario",BuscarProfesor.usuario);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });



    }


    private void buscarCliente(String URL){
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);

                        edtNombre.setText(jsonObject.getString("nombre"));
                        edtApellido.setText(jsonObject.getString("apellido"));
                        edtNacimiento.setText(jsonObject.getString("fechaNacimiento"));
                        edtCorreo.setText(jsonObject.getString("correo"));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

}