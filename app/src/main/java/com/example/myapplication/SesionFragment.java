package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SesionFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    RequestQueue rq;
    JsonRequest jrq;
    EditText cajaUser, cajaPwd;
    Button btnConsultar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_sesion, container, false);
        cajaUser = (EditText) vista.findViewById(R.id.txtUser);
        cajaPwd = (EditText) vista.findViewById(R.id.txtPwd);
        btnConsultar = (Button) vista.findViewById(R.id.btnSesion);
        rq = Volley.newRequestQueue(getContext());

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cajaUser.getText().toString().isEmpty() && !cajaPwd.getText().toString().isEmpty()) {
                    iniciarSesion();
                } else {
                    Toast.makeText(getContext(), "Por favor, completa ambos campos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return vista;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "No se ha encontrado el usuario " + error.toString().toString(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResponse(JSONObject response) {
        User usuario = new User();
        Toast.makeText(getContext(), "se ha encontrado el usuario " + cajaUser.getText().toString(), Toast.LENGTH_SHORT).show();
        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObject = null;

        try {
            jsonObject = jsonArray.getJSONObject(0);
            usuario.setUser(jsonObject.optString("user"));
            usuario.setPwd(jsonObject.optString("pwd"));
            usuario.setNames(jsonObject.optString("names"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intencion = new Intent(getContext(), MainActivity2.class);
        intencion.putExtra(MainActivity2.nombres, usuario.getNames());
        startActivity(intencion);
    }

    private void iniciarSesion() {
        String url = "https://app2023caraballo.000webhostapp.com/sesion.php?user=" + cajaUser.getText().toString() + "&pwd=" + cajaPwd.getText().toString();

        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);

    }

}