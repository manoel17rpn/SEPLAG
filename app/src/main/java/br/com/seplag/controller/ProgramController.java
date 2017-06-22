package br.com.seplag.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.com.seplag.model.AllProgramsModel;
import br.com.seplag.model.ProgramModel;

/**
 * Created by Manoel Neto on 05/05/2017.
 */

public class ProgramController {

    private static final String URL_POST = "http://usepio.com/WebServiceSeplag/public/rest-api/programs-methods/create";
    private static final String URL_GET_PROGRAMS = "http://usepio.com/WebServiceSeplag/public/rest-api/programs-methods/allPrograms";
    RetryPolicy policy = new DefaultRetryPolicy(45000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

    public void CreateProgram(Context mContext, final ProgramModel program, final VolleyCallback callback){
        final ProgressDialog dialog = ProgressDialog.show(mContext, "Carregando", "Estamos processando sua contribuição, por favor aguarde...", true);

        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                callback.onSuccess(response);
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                error.printStackTrace();

                NetworkResponse response = error.networkResponse;
                if(response != null && response.data != null){
                    callback.onFailed(response.statusCode, "");
                }else{
                    String errorMessage=error.getClass().getSimpleName();
                    if(!TextUtils.isEmpty(errorMessage)){
                        callback.onFailed(0, errorMessage);
                    }
                }
            }
        }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("user_id", Integer.toString(program.getUser_id()));
                params.put("name_program", program.getName_program());
                params.put("service_program", program.getService_program());
                params.put("name_neighborhood", program.getName_neighborhood());
                params.put("name_street", program.getName_street());
                params.put("reference_point", program.getReference_point());
                params.put("user_comment", program.getUser_comment());
                params.put("latitude", program.getLatitude());
                params.put("longitude", program.getLongitude());
                params.put("image", program.getImage());
                params.put("barramento", program.getPoste());


                return params;
            }
        };

        postRequest.setRetryPolicy(policy);
        queue.add(postRequest);
    }

    public interface VolleyCallback{
        void onSuccess(String result);
        void onFailed(int result, String menssager);
    }

    public void GetAllPrograms(final Context mContext, final ProgramsCallback callback) {
        final ProgressDialog dialog = ProgressDialog.show(mContext, "Carregando", "Carregando Programas...", true);
        final AllProgramsModel programs = new AllProgramsModel();
        final ArrayList<AllProgramsModel> allPrograms = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(mContext);

        // prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, URL_GET_PROGRAMS, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray array) {
                        JSONObject jsonObject;

                        try {
                            if (array.getJSONArray(0).length() > 0) {

                                for (int i = 0; i < array.length(); i++) {
                                    jsonObject = new JSONObject(array.getJSONObject(i).toString());
                                    programs.setName_program(jsonObject.getString("name_program"));
                                    programs.setSubtitle(jsonObject.getString("subtitle"));
                                    programs.setImage(jsonObject.getString("image_program"));

                                    allPrograms.add(programs);
                                }

                                callback.onSuccess(allPrograms);
                            }

                            dialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        callback.onFailed(false);

                        dialog.dismiss();
                    }
                }
        );

        getRequest.setRetryPolicy(policy);
        queue.add(getRequest);
    }

    public interface ProgramsCallback {
        void onSuccess(ArrayList<AllProgramsModel> programs);

        void onFailed(boolean result);
    }
}
