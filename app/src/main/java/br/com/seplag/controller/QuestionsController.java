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
import java.util.List;
import java.util.Map;

import br.com.seplag.model.GameAuxModel;
import br.com.seplag.model.GameOptionsModel;

/**
 * Created by Manoel Neto on 02/05/2017.
 */

public class QuestionsController {
    private static final String URL_GET_OPTIONS = "http://usepio.com/WebServiceSeplag/public/rest-api/game-methods/options/get/";
    private static final String URL_POST_ANSWER = "http://usepio.com/WebServiceSeplag/public/rest-api/game-methods/aux/create";
    private RetryPolicy policy = new DefaultRetryPolicy(45000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    private ArrayList<GameOptionsModel> listOptions;


    public List<GameOptionsModel> ListOptions(final Context context, int user_id, final VolleyCallbackGetOptions callback){
        final ProgressDialog dialog = ProgressDialog.show(context, "Carregando", "Buscando opções, por favor aguarde...", true);
        listOptions = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(context);

        // prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, URL_GET_OPTIONS + user_id, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray array) {
                        JSONObject jsonObject;

                        try {

                            for(int i = 0; i < array.length(); i++){
                                GameOptionsModel game = new GameOptionsModel();
                                jsonObject = new JSONObject(array.getJSONObject(i).toString());
                                game.setName_list(jsonObject.getString("name_list"));
                                game.setOptions_id(jsonObject.getInt("options_id"));
                                game.setOption_one(jsonObject.getString("option_one"));
                                game.setOption_two(jsonObject.getString("option_two"));
                                game.setOption_three(jsonObject.getString("option_three"));
                                game.setOption_four(jsonObject.getString("option_four"));
                                game.setArea_questions(jsonObject.getString("area_questions"));

                                listOptions.add(game);
                            }

                            callback.ListOptions(listOptions);
                            dialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                        dialog.dismiss();
                    }
                }
        );

        getRequest.setRetryPolicy(policy);
        queue.add(getRequest);

        return listOptions;
    }


    public void CreateAnswer(Context mContext, final GameAuxModel auxModel, final ProgramController.VolleyCallback callback){
        final ProgressDialog dialog = ProgressDialog.show(mContext, "Carregando", "Estamos realizando seu cadastro, por favor aguarde...", true);

        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL_POST_ANSWER, new Response.Listener<String>() {
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
                params.put("user_id", Integer.toString(auxModel.getUser_id()));
                params.put("name_list", auxModel.getName_list());
                params.put("user_answers", auxModel.getUser_answers());
                params.put("options_id", Integer.toString(auxModel.getOptions_id()));

                return params;
            }
        };

        postRequest.setRetryPolicy(policy);
        queue.add(postRequest);
    }


    public interface VolleyCallbackGetOptions{
        void ListOptions(ArrayList<GameOptionsModel> options);
    }


}
