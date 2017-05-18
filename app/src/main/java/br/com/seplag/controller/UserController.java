package br.com.seplag.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.com.seplag.model.UserModel;
import br.com.seplag.view.MainActivity;

/**
 * Created by Manoel Neto on 29/04/2017.
 */

public class UserController {
    private static final String URL_REGISTER = "http://usepio.com/WebServiceSeplag/public/rest-api/user-methods/create";
    private static final String URL_LOGIN = "http://usepio.com/WebServiceSeplag/public/rest-api/user-methods/get/";
    private static final String URL_UPDATE_SCORE = "http://usepio.com/WebServiceSeplag/public/rest-api/user-methods/updatescore";
    private static final String URL_GET_SCORE = "http://usepio.com/WebServiceSeplag/public/rest-api/user-methods/getscore/";
    private static final String URL_VERIFY_NUMBER = "http://usepio.com/WebServiceSeplag/public/rest-api/user-methods/verifynumber/";
    private static final String URL_UPDATE_OFFICE = "http://usepio.com/WebServiceSeplag/public/rest-api/user-methods/updateoffice";
    private static final String URL_UPDATE_USER = "http://usepio.com/WebServiceSeplag/public/rest-api/user-methods/updateuser";
    RetryPolicy policy = new DefaultRetryPolicy(45000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

    public void CreateUser(final Context context, final UserModel user, final VolleyCallback callback) {
        final ProgressDialog dialog = ProgressDialog.show(context, "Carregando", "Estamos realizando seu cadastro, por favor aguarde...", true);

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL_REGISTER, new Response.Listener<String>() {
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
                params.put("user_name", user.getUser_name());
                params.put("user_phone", user.getUser_phone());
                params.put("user_neighborhood", user.getUser_neighborhood());
                params.put("user_street", user.getUser_street());
                params.put("user_score", Integer.toString(user.getUser_score()));
                params.put("user_office", user.getUser_office());
                params.put("user_phone_invite", user.getUser_invite());

                return params;
            }
        };

        postRequest.setRetryPolicy(policy);
        queue.add(postRequest);
    }

    public UserModel LoginUser(final Context mContext, String UserNumber, final VolleyCallbackGet callback){
        final ProgressDialog dialog = ProgressDialog.show(mContext, "Carregando", "Realizando login, por favor aguarde...", true);
        final UserModel user = new UserModel();
        RequestQueue queue = Volley.newRequestQueue(mContext);

        // prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, URL_LOGIN + UserNumber, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray array) {
                        JSONObject jsonObject;
                        JSONArray array1;

                        try{
                            if(array.getJSONArray(0).length() > 0){

                                array1 = new JSONArray(array.getJSONArray(0).toString());
                                jsonObject = new JSONObject(array1.getJSONObject(0).toString());

                                user.setUser_id(jsonObject.getInt("user_id"));
                                user.setUser_name(jsonObject.getString("user_name"));
                                user.setUser_phone(jsonObject.getString("user_phone"));
                                user.setUser_neighborhood(jsonObject.getString("user_neighborhood"));
                                user.setUser_score(jsonObject.getInt("user_score"));
                                user.setUser_office(jsonObject.getString("user_office"));
                                user.setUser_income(jsonObject.getString("user_income"));
                                user.setUser_sex(jsonObject.getString("user_sex"));
                                user.setUser_scholarity(jsonObject.getString("user_scholarity"));
                                user.setNumbers_residents(jsonObject.getString("numbers_residents"));

                                callback.onSucess(true);
                            }else if(array.getJSONArray(0).length() == 0){
                                Toast.makeText(mContext, "Número inválido, verifique seu número...", Toast.LENGTH_LONG).show();
                            }

                            dialog.dismiss();
                        }catch (JSONException e){
                                e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
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

        return user;
    }

    public void UpdateScore(Context mContext, final int user_id, final int add_score, final VolleyCallbackScore callback){
        final ProgressDialog dialog = ProgressDialog.show(mContext, "Carregando", "Atualizando pontuação...", true);
        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest putRequest = new StringRequest(Request.Method.PUT, URL_UPDATE_SCORE,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        callback.result(response);
                        dialog.dismiss();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<> ();
                params.put("user_id", Integer.toString(user_id));

                return params;
            }

        };
        putRequest.setRetryPolicy(policy);
        queue.add(putRequest);
    }

    public void UpdateOffice(Context mContext, final int user_id, final String new_office, final VolleyCallbackOffice callbackOffice){
        final ProgressDialog dialog = ProgressDialog.show(mContext, "Carregando", "Atualizando seu perfil, por favor aguarde...", true);
        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest putRequest = new StringRequest(Request.Method.PUT, URL_UPDATE_OFFICE,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        callbackOffice.onResult(true);
                        dialog.dismiss();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callbackOffice.onResult(false);
                        dialog.dismiss();
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<> ();
                params.put("user_id", Integer.toString(user_id));
                params.put("new_office", new_office);

                return params;
            }

        };
        putRequest.setRetryPolicy(policy);
        queue.add(putRequest);
    }

    public void UpdateUser(Context mContext, final int user_id, final String sex, final String scholarity,
                           final String residents, final String income, final VolleyCallbackUpdateUser callback){
        final ProgressDialog dialog = ProgressDialog.show(mContext, "Carregando", "Atualizando seu perfil, por favor aguarde...", true);
        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest putRequest = new StringRequest(Request.Method.PUT, URL_UPDATE_USER,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        callback.onResult(true);
                        dialog.dismiss();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onResult(false);
                        dialog.dismiss();
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<> ();
                params.put("user_id", Integer.toString(user_id));
                params.put("user_sex", sex);
                params.put("user_scholarity", scholarity);
                params.put("user_residents", residents);
                params.put("user_income", income);

                return params;
            }

        };
        putRequest.setRetryPolicy(policy);
        queue.add(putRequest);
    }

    public void VerifyUserScore(Context mContext, int user_id, final VolleyCallbackScore callback) {
        final ProgressDialog dialog = ProgressDialog.show(mContext, "Carregando", "Atualizando seu perfil...", true);
        RequestQueue queue = Volley.newRequestQueue(mContext);

        // prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, URL_GET_SCORE + user_id, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray array) {
                        JSONObject jsonObject;

                        try {

                            jsonObject = new JSONObject(array.getJSONObject(0).toString());

                            int score = jsonObject.getInt("user_score");

                            callback.result(Integer.toString(score));


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
                        callback.result("");

                        dialog.dismiss();
                    }
                }
        );

        getRequest.setRetryPolicy(policy);
        queue.add(getRequest);
    }

    public void VerifyNumber(Context mContext, String number, final VolleyCallbackVerifyNumber callback){
        final ProgressDialog dialog = ProgressDialog.show(mContext, "Carregando", "Vefirificando número...", true);
        RequestQueue queue = Volley.newRequestQueue(mContext);

        // prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, URL_VERIFY_NUMBER + number, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray array) {
                        if(array.length() > 0){
                            callback.onResult(false);
                        }else{
                            callback.onResult(true);
                        }

                        dialog.dismiss();
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        callback.error("Error");

                        dialog.dismiss();
                    }
                }
        );

        getRequest.setRetryPolicy(policy);
        queue.add(getRequest);
    }

    public interface VolleyCallback{
        void onSuccess(String result);
        void onFailed(int result, String menssager);
    }

    public interface VolleyCallbackVerifyNumber{
        void onResult(boolean result);
        void error(String error);
    }

    public interface VolleyCallbackScore{
        void result(String result);
    }

    public interface VolleyCallbackOffice{
        void onResult(boolean resultOffice);
    }

    public interface VolleyCallbackUpdateUser{
        void onResult(boolean resultOffice);
    }

    public interface VolleyCallbackGet{
        void onSucess(boolean result);
        void onFailed(boolean error);
    }
}
