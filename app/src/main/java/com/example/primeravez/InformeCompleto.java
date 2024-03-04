package com.example.primeravez;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.primeravez.Models.Estudiante;
import com.example.primeravez.Models.ModelInformeComp;
import com.example.primeravez.Models.alumnXprofesor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class InformeCompleto extends AppCompatActivity {

    private TableLayout tableLayout;
    private Button btnVolver;
    public static String usuario;
    public int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informe_completo);

        tableLayout = findViewById(R.id.tableLayout);
        btnVolver=findViewById(R.id.btnVolver);


        Intent intent=getIntent();
        usuario=intent.getStringExtra("usuario");
        id=intent.getIntExtra("id",-1);

        // Realiza la solicitud a tu API
        obtenerDatosDesdeAPI();

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InformeCompleto.this, Menu.class);
                intent.putExtra("usuario",InformeCompleto.usuario);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

    }


    private void obtenerDatosDesdeAPI() {
        String url = "https://conectmybd.000webhostapp.com/todosLosDatos.php?id="+id+""; // Reemplaza con tu URL

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("API Response1", response.toString());
                        ArrayList<ModelInformeComp> listaEstudiantes = parsearRespuesta(response);
                        mostrarDatosEnTabla(listaEstudiantes);
                        // Agregar mensaje Toast para indicar éxito
                        Toast.makeText(InformeCompleto.this, "Solicitud exitosa", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar el error
                        error.printStackTrace();
                        // Agregar mensaje Toast para indicar error
                        Toast.makeText(InformeCompleto.this, "Error en la solicitud1", Toast.LENGTH_SHORT).show();
                        // Agregar mensaje de registro en Logcat para ver detalles adicionales
                        Log.e("VolleyError1", "Error en la solicitud1: " + error.getMessage());
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private ArrayList<ModelInformeComp> parsearRespuesta(JSONArray jsonArray) {
        ArrayList<ModelInformeComp> lista = new ArrayList<>();

        try {
            // Verificar si el JSONArray contiene al menos un elemento
            if (jsonArray.length() > 0) {
                // Obtener el primer elemento del JSONArray (que es un JSONArray)
                JSONArray innerArray = jsonArray.getJSONArray(0);

                // Iterar sobre el JSONArray interno
                for (int i = 0; i < innerArray.length(); i++) {
                    // Obtener un objeto JSON de la posición actual en el JSONArray interno
                    JSONObject jsonObject = innerArray.getJSONObject(i);

                    // Obtener datos del objeto JSON
                    String nombre = jsonObject.getString("Nombre");
                    String apellido = jsonObject.getString("Apellido");
                    String correo = jsonObject.getString("Correo");
                    int legajo = Integer.parseInt(jsonObject.getString("Legajo"));
                    String materia = jsonObject.getString("Curso");
                    String entrada = jsonObject.getString("Hora Inicio");
                    String salida = jsonObject.getString("Hora Finalizacion");
                    String aula = jsonObject.getString("Aula");


                    // Crear un objeto Estudiante y agregarlo a la lista
                    ModelInformeComp obj=new ModelInformeComp();
                    obj.setNombre(nombre);
                    obj.setApellido(apellido);
                    obj.setCorreo(correo);
                    obj.setLegajo(legajo);
                    obj.setMateria(materia);
                    obj.setHorarioInicio(entrada);
                    obj.setHorarioFin(salida);
                    obj.setAula(aula);

                    lista.add(obj);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lista;
    }

    //Cosa que modifique

    // Método para mostrar datos en la tabla
    private void mostrarDatosEnTabla(ArrayList<ModelInformeComp> datos) {
        // Crear una fila de encabezado
        TableRow headerRow = new TableRow(this);

        // Agregar encabezados de columnas
        addHeaderToRow(headerRow, "Nombre");
        addHeaderToRow(headerRow, "Apellido");
        addHeaderToRow(headerRow, "Correo");
        addHeaderToRow(headerRow, "Legajo");
        addHeaderToRow(headerRow, "Materia");
        addHeaderToRow(headerRow, "Hora Entrada");
        addHeaderToRow(headerRow, "Hora Salida");
        addHeaderToRow(headerRow, "Aula");

        // Agregar la fila de encabezado a la tabla
        tableLayout.addView(headerRow);

        // Agregar datos de estudiantes a la tabla
        for (ModelInformeComp estudiante : datos) {
            // Crear una nueva fila
            TableRow row = new TableRow(this);

            // Agregar datos de estudiante a cada columna
            addDataToRow(row, estudiante.getNombre(),  2);
            addDataToRow(row, estudiante.getApellido(),2);
            addDataToRow(row, estudiante.getCorreo(),3);
            addDataToRow(row, String.valueOf(estudiante.getLegajo()),2);
            addDataToRow(row, estudiante.getMateria(),3);
            addDataToRow(row, estudiante.getHorarioInicio(),3);
            addDataToRow(row, estudiante.getHorarioFin(),3);
            addDataToRow(row, estudiante.getAula(),2);

            // Agregar la fila a la tabla
            tableLayout.addView(row);

            // Agregar una línea divisoria horizontal después de cada fila, excepto la última
            if (datos.indexOf(estudiante) < datos.size() - 1) {
                addHorizontalDivider();
            }
        }
    }

    // Método para agregar una línea divisoria horizontal
    private void addHorizontalDivider() {
        View line = new View(this);
        line.setBackgroundColor(getResources().getColor(android.R.color.black));
        TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 2);
        layoutParams.setMargins(0, 10, 0, 10);
        line.setLayoutParams(layoutParams);
        tableLayout.addView(line);
    }


    // Método para agregar encabezados a la fila
    private void addHeaderToRow(TableRow row, String headerText) {
        TextView headerTextView = new TextView(this);
        headerTextView.setText(headerText);
        headerTextView.setGravity(Gravity.CENTER);
        headerTextView.setTextSize(18);  // Tamaño de texto más grande para encabezados
        headerTextView.setBackgroundColor(getResources().getColor(R.color.colorHeaderBackground));
        headerTextView.setTextColor(getResources().getColor(android.R.color.white));

        // Agregar un divisor vertical entre las columnas, excepto la primera
        if (row.getChildCount() > 0) {
            View line = new View(this);
            line.setBackgroundColor(getResources().getColor(android.R.color.black));
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(2, TableRow.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(10, 0, 10, 0);
            line.setLayoutParams(layoutParams);
            row.addView(line);
        }

        row.addView(headerTextView);
    }

    private void addDataToRow(TableRow row, String data,int weight) {
        TextView textView = new TextView(this);
        textView.setText(data);
        textView.setGravity(Gravity.CENTER);

        // Agregar un divisor vertical entre las columnas, excepto la primera
        if (row.getChildCount() > 0) {
            View line = new View(this);
            line.setBackgroundColor(getResources().getColor(android.R.color.black));
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(2, TableRow.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(10, 0, 10, 0);
            line.setLayoutParams(layoutParams);
            row.addView(line);
        }
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, weight);
        textView.setLayoutParams(layoutParams);

        row.addView(textView);
    }


}