package com.itca.appmysql.ui.productos;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.itca.appmysql.MySingleton;
import com.itca.appmysql.R;
import com.itca.appmysql.Setting_VAR;
import com.itca.appmysql.dto_categorias;
import com.itca.appmysql.dto_productos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class Producto extends Fragment {

    private EditText et_id, et_nombre_prod, et_descripcion, et_stock,et_precio,et_unidadmedida;
    private Spinner sp_estadoProductos, sp_fk_categoria;
    private TextView tv_fechahora;
    private Button btnSave, btnNew;

    ProgressDialog progressDialog;
    ArrayList<String> lista = null;
    ArrayList<dto_categorias> listaCategorias; //Va a representar la informacion que se va a mostrar en el combo

    //Arreglos para efectuar pruebas de carga de opciones  en spinner.
    String elementos[]= {"Uno","Dos","Tres","Cuatro","Cinco"};
    final String[] elementos1 = new String[]{
            "Seleccione",
            "1",
            "2",
            "3",
            "4",
            "5"
    };
    String idcategoria="";
    String nombrecategoria="";
    int conta=0;

    String datoStatusProduct="";

    //Instancia DTO
    dto_productos dto = new dto_productos();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        //Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_producto, container,false);

        et_id=view.findViewById(R.id.et_id);
        et_nombre_prod=view.findViewById(R.id.et_nombre_prod);
        et_descripcion=view.findViewById(R.id.et_descripcion);
        et_stock = view.findViewById(R.id.et_stock);
        et_precio=view.findViewById(R.id.et_precio);
        et_unidadmedida = view.findViewById(R.id.et_unidadmedida);
        sp_estadoProductos = view.findViewById(R.id.sp_estadoProductos);
        sp_fk_categoria = view.findViewById(R.id.sp_fk_categoria);
        tv_fechahora = view.findViewById(R.id.tv_fechahora);
        tv_fechahora.setText(timedate());
        btnSave = view.findViewById(R.id.btnSave);
        btnNew = view.findViewById(R.id.btnNew);

        sp_estadoProductos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                if (sp_estadoProductos.getSelectedItemPosition()>0){
                    datoStatusProduct=sp_estadoProductos.getSelectedItem().toString();
                    sp_estadoProductos.getSelectedItem().toString();

                }else {
                    datoStatusProduct="";
                }
                //Toast.makeText(getContext(), ""+datoStatusProduct,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //Llamo al metodo para que muestre los datos de la busqueda al carga al carga de la actividad.
        fk_categorias(getContext());

        //ArrayAdapter<String> adaptador = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item, obtenerListaCategorias(););
        //ArrayAdapter<String> adaptador =new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, elementos);
        //sp_fk_categoria.setAdapter(adaptador);

        //Evento del spinner creado para extraer la información seleccionada en cada item/opción.

        sp_fk_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                if(conta>=1 && sp_fk_categoria.getSelectedItemPosition()>0){
                    String item_spinner= sp_fk_categoria.getSelectedItem().toString();
                    //Hago una busqueda en la cadena seleccionada en el spinner para separar el idcategoria y el nombre de la categoria
                    //Esto es neceserio, debido a que lo que debe enviarse a guardar la base de datos es unicamente el idcategoria.
                    String s[]= item_spinner.split("~");
                    //Dato ID CATEGORIA
                    idcategoria = s[0].trim(); ////Con trim elimino espacios al inicio y final de la cadena para enviar limpio el ID CATEGORIA.
                    //Dato NOMBRE DE LA CATEGORIA
                    nombrecategoria = s[1];
                    Toast toast = Toast.makeText(getContext(),
                            "Id cat:"+ idcategoria + "\n" +
                                    "Nombre Categoria", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();


                }else {
                    idcategoria="";
                    nombrecategoria="";
                }
                conta++;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = et_id.getText().toString();
                String nombre = et_nombre_prod.getText().toString();
                String descripcion = et_descripcion.getText().toString();
                String stock = et_stock.getText().toString();
                String precio = et_precio.getText().toString();
                String unidad = et_unidadmedida.getText().toString();

                if (id.length()==0){
                    et_id.setError("Campo Obligatorio");
                }else if (nombre.length()==0){
                    et_nombre_prod.setError("Campo Obligatorio");
                }else if (descripcion.length()==0){
                    et_descripcion.setError("Campo Obligatorio");
                }else if (stock.length()==0){
                    et_stock.setError("Capo Obligatorio");
                }else if (precio.length()==0){
                    et_precio.setError("Campo Obligatorio");
                }else if (unidad.length()==0){
                    et_unidadmedida.setError("Campo Obligatorio");
                }else if (sp_estadoProductos.getSelectedItemPosition()==0){
                    Toast.makeText(getContext(), "Debe seleccionar el estado del producto.", Toast.LENGTH_SHORT).show();
                }else if (sp_fk_categoria.getSelectedItemPosition() > 0){
                    save_productos(getContext(), id , nombre, descripcion, stock,precio,unidad, datoStatusProduct,idcategoria);
                }else {
                    Toast.makeText(getContext(), "Debe seleccionar la categoria.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_product();
            }
        });
        return view;
    }

    private String timedate(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
        String fecha = sdf.format(cal.getTime());
        return fecha;
    }

    public void fk_categorias(final Context context){
        listaCategorias=new ArrayList<dto_categorias>();
        lista = new ArrayList<String>();
        lista.add("Selecciona una Categoria");

        String url = Setting_VAR.URL_consultaAllCategorias;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response){
                try {
                    JSONArray array = new JSONArray(response);
                    int totalEncontrados = array.length();
                    dto_categorias obj_categorias = null;
                    //dto_categorias obj_categorias = new dto_categorias

                    for (int i = 0; i < array.length(); i++){
                        JSONObject categoriasObject=array.getJSONObject(i);
                        int id_categoria = categoriasObject.getInt("id_categoria");
                        String nombre_categoria = categoriasObject.getString("nom_categoria");
                        int estado_categoria = Integer.parseInt(categoriasObject.getString("estado_categoria"));
                        //Encapsulo registro por registro encontrado dentro del objeto de manera temporal
                        obj_categorias = new dto_categorias(id_categoria, nombre_categoria, estado_categoria);

                        //Agrego todos los registros en el arraylist
                        listaCategorias.add(obj_categorias);
                        //Saco la informacion del arraylist y personalizo en la forma en que deseo se muestren los en el spinner y
                        //Selecciono que datos se van a mostrar del resultado.

                        lista.add(listaCategorias.get(i).getId_categoria()+" ~ "+listaCategorias.get(i).getNom_categoria());
                        //Creo un adaptador para cargar la lista preparada anteriormente
                        ArrayAdapter<String> adaptador = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,lista);
                        //Cargo los datos en el Spinner
                        sp_fk_categoria.setAdapter(adaptador);

                        //Muestro datos en LOgCat para verificar la respuesta obtenida desde el servidor
                        Log.i("Id Categoria",String.valueOf(obj_categorias.getId_categoria()));
                        Log.i("Nombre Categoria",obj_categorias.getNom_categoria());
                        Log.i("Estado Categoria",String.valueOf(obj_categorias.getEstado_categoria()));
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        } , new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error. Compuebre su acceso a Internet", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }


    private void save_productos(final Context context,final String id_prod,final String nom_prod,final String des_prod, final String stock,
                               final String precio, final String uni_medida, final String estado_prod, final String categoria){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Setting_VAR.URL_registrar_productos, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject requestJSON = null;
                try {
                    requestJSON = new JSONObject(response.toString());
                    String estado = requestJSON.getString("estado");
                    String mensaje = requestJSON.getString("mensaje");

                    if (estado.equals("1")) {
                        Toast.makeText(context, "mensaje", Toast.LENGTH_SHORT).show();
                    } else if (estado.equals("2")) {
                        Toast.makeText(context, "" + mensaje, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "No se puedo guardar. \n", Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError {

                //En este metodo se colocan o se setean los valores a recibir por el fichero *.php
                Map<String,String> map = new HashMap<>();
                map.put("Content-Type", "application/json; charset=utf-8");
                map.put("Accept", "application/json");
                map.put("id_prod", id_prod);
                map.put("nom_prod", nom_prod);
                map.put("des_prod", des_prod);
                map.put("stock", stock);
                map.put("precio", precio);
                map.put("uni_medida", uni_medida);
                map.put("estado_prod", estado_prod);
                map.put("categoria", categoria);
                return map;
            }

        };
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    private void new_product(){
        et_id.setText(null);
        et_nombre_prod.setText(null);
        et_descripcion.setText(null);
        et_stock.setText(null);
        et_precio.setText(null);
        et_unidadmedida.setText(null);
        sp_estadoProductos.setSelection(0);
        sp_fk_categoria.setSelection(0);
    }
    //No utilizo este metodo en nada por el momento
    public ArrayList<String>obtenerListaCategorias(){
        lista=new ArrayList<String>();
        lista.add("Selecciona Categoria");
        for (int i=0; i<=listaCategorias.size();i++){
            lista.add(listaCategorias.get(i).getId_categoria()+"~"+listaCategorias.get(i).getNom_categoria());
        }
        return lista;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

}
