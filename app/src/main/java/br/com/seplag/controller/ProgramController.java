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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import br.com.seplag.model.ProgramModel;

/**
 * Created by Manoel Neto on 05/05/2017.
 */

public class ProgramController {

    private static final String URL_POST = "http://192.168.112.105:8000/rest-api/programs-methods/create";
    RetryPolicy policy = new DefaultRetryPolicy(45000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

    public void CreateProgram(Context mContext, final ProgramModel program, final VolleyCallback callback){
        final ProgressDialog dialog = ProgressDialog.show(mContext, "Carregando", "Estamos realizando seu cadastro, por favor aguarde...", true);

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
                params.put("image", program.getImage());


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
}
