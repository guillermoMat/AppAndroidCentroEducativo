package com.example.primeravez.ConexionLogin;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL="https://conectmybd.000webhostapp.com/login.php";
    private Map<String,String> params;
    private int id;
    public LoginRequest(String usuario, String contraseña,int id, Response.Listener<String> listener){

        super(Request.Method.POST, LOGIN_REQUEST_URL,listener,null);
        params=new HashMap<>();
        params.put("usuario",usuario);
        params.put("contraseña",contraseña);
        //this.id = id;  // Asigna el ID proporcionado
        params.put("id", String.valueOf(id));


    }

    @Override
    public Map<String,String> getParams(){
        //params.put("id", String.valueOf(id));  // Agrega el ID a los parámetros
        return params;
    }

}
