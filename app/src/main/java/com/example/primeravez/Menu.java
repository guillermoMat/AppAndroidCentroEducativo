package com.example.primeravez;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class Menu extends AppCompatActivity {
    public static String usuario;
    private DrawerLayout drawerLayout;
    private ImageButton btnOpenDrawer;
    private ImageButton btnCloseDrawer;
    private Button btnBuscarProf,btnListarEstudiantes,btnMisAlumnos,btnHorarios,btnDatosCompletos;
    private Button btnCerrarSesion;
    TextView textViewNombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        btnOpenDrawer = (ImageButton) findViewById(R.id.btnOpenDrawer);
        btnCloseDrawer = (ImageButton)findViewById(R.id.btnCloseDrawer);

        btnBuscarProf=(Button)findViewById(R.id.btnBuscarProfesor);
        btnListarEstudiantes=(Button)findViewById(R.id.btnListadoDeAlumnos);
        btnMisAlumnos=(Button)findViewById(R.id.btnMisAlumnos);
        btnHorarios=(Button)findViewById(R.id.btnHorarios);
        btnDatosCompletos=(Button)findViewById(R.id.btnTodosLosDatos);

        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        textViewNombreUsuario = (TextView) findViewById(R.id.textViewNombreUsuario);
        //textViewIdUsuario= (TextView)findViewById(R.id.textViewIdUsuario);

        Intent intent=getIntent();
        usuario=intent.getStringExtra("usuario");
        //usuario=getIntent().getStringExtra("usuario");
        //String id=intent.getStringExtra("id");
        int id=intent.getIntExtra("id",-1);


        textViewNombreUsuario.setText("¡Hola "+Menu.usuario+"!");



        // Configura el botón para abrir el menú lateral
        btnOpenDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(findViewById(R.id.drawer));
            }
        });

        // Configura el botón para cerrar el menú lateral
        btnCloseDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(findViewById(R.id.drawer));
            }
        });

        btnBuscarProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, BuscarProfesor.class);
                intent.putExtra("usuario",Menu.usuario);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        btnListarEstudiantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, ListadoAlumnos.class);
                intent.putExtra("usuario",Menu.usuario);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        btnMisAlumnos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, AlumnosXProfesor.class);
                intent.putExtra("usuario",Menu.usuario);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        btnHorarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, horariosClaseProf.class);
                intent.putExtra("usuario",Menu.usuario);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        btnDatosCompletos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, InformeCompleto.class);
                intent.putExtra("usuario",Menu.usuario);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        // Manejar el clic del botón de cerrar sesión
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí implementa la lógica para cerrar sesión
                // Por ejemplo, podrías abrir la pantalla de inicio de sesión nuevamente
                Intent intent = new Intent(Menu.this, MainActivity.class);
                startActivity(intent);
                finish(); // Cierra la actividad actual
            }
        });



    }






}