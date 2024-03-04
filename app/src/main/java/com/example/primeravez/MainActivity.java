package com.example.primeravez;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.primeravez.ConexionLogin.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

Button btnLogin;
EditText editTextUsername,editTextPassword;
public int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin=(Button)findViewById(R.id.btnLogin);
        editTextUsername=(EditText) findViewById(R.id.editTextUsername);
        editTextPassword=(EditText) findViewById(R.id.editTextPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username=editTextUsername.getText().toString();
                final String password=editTextPassword.getText().toString();

            Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);

                        if (response.startsWith("{")) {

                            try {
                                JSONObject jsonResponse=new JSONObject(response);
                                boolean success=jsonResponse.getBoolean("success");

                                if(success){
                                    id=jsonResponse.getInt("id");
                                    String usuario=jsonResponse.getString("usuario");

                                    Intent intent = new Intent(MainActivity.this, Menu.class);
                                    intent.putExtra("id",id);
                                    intent.putExtra("usuario",usuario);


                                    mostrarMensaje("Inicio de sesión exitoso!");
                                    MainActivity.this.startActivity(intent);



                                }else{
                                    AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                                    editTextUsername.setText("");
                                    editTextPassword.setText("");
                                    builder.setMessage("Error login").setNegativeButton("Reintentar",null)
                                            .create().show();
                                }

                            }catch (JSONException e){
                                mostrarMensaje(e.getMessage());
                                e.printStackTrace();
                            }

                        }else if (response.startsWith("<html>")) {
                            // La respuesta es HTML (posiblemente una página de error)
                            mostrarMensaje("El servidor devolvió una página HTML. Verifica la configuración del servidor.");
                        } else {
                            mostrarMensaje("Respuesta del servidor no es un JSON válido.");
                        }


                    }
                };

                LoginRequest loginRequest=new LoginRequest(username,password,id,responseListener);
                RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);

            }
        });

}
    private void mostrarMensaje(String msj) {
        Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
    }
}