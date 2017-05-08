package br.com.seplag.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.seplag.model.GameModel;
import br.com.seplag.model.GameOptionsModel;

/**
 * Created by Manoel Neto on 02/05/2017.
 */

public class GameController {
    private static final String URL_GET = "http://192.168.0.104/SeplagAppService/public/rest-api/game-methods/app/all";
    private static final String URL_GET_OPTIONS = "http://192.168.0.104/SeplagAppService/public/rest-api/game-methods/options/get/";
    RetryPolicy policy = new DefaultRetryPolicy(45000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

    public List<GameModel> ListActive(final Context context, final VolleyCallbackGet callback){
        final ProgressDialog dialog = ProgressDialog.show(context, "Carregando", "Buscando opções, por favor aguarde...", true);
        final List<GameModel> listActive = new ArrayList<GameModel>();
        RequestQueue queue = Volley.newRequestQueue(context);

        // prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, URL_GET, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray array) {
                        JSONObject jsonObject;
                        JSONArray array1;

                        try{
                            if(array.getJSONArray(0).length() > 0){

                                array1 = new JSONArray(array.getJSONArray(0).toString());

                                for(int i = 0; i < array1.length(); i++){
                                    jsonObject = new JSONObject(array1.getJSONObject(i).toString());

                                    GameModel game = new GameModel();
                                    game.setArea_questions(jsonObject.getString("area_questions"));
                                    game.setName_list(jsonObject.getString("name_list"));

                                    listActive.add(game);
                                }

                                callback.onResult(true);
                                callback.ListGame(listActive);
                            }else if(array.getJSONArray(0).length() == 0){
                                Toast.makeText(context, "Ops, não há nenhuma nova lista para responder...", Toast.LENGTH_LONG).show();
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
                        callback.onResult(false);

                        dialog.dismiss();
                    }
                }
        );

        getRequest.setRetryPolicy(policy);
        queue.add(getRequest);

        return listActive;
    }

    public List<GameOptionsModel> ListOptions(final Context context, String name_list, final VolleyCallbackGet callback){
        final ProgressDialog dialog = ProgressDialog.show(context, "Carregando", "Buscando opções, por favor aguarde...", true);
        final List<GameOptionsModel> listOptions = new ArrayList<GameOptionsModel>();
        RequestQueue queue = Volley.newRequestQueue(context);

        // prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, URL_GET_OPTIONS + name_list, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray array) {
                        JSONObject jsonObject;
                        JSONArray array1;

                        try{
                            if(array.getJSONArray(0).length() > 0){

                                array1 = new JSONArray(array.getJSONArray(0).toString());

                                for(int i = 0; i < array1.length(); i++){
                                    GameOptionsModel game = new GameOptionsModel();
                                    jsonObject = new JSONObject(array1.getJSONObject(i).toString());
                                    game.setOption_one(jsonObject.getString("option_one"));
                                    game.setOption_two(jsonObject.getString("option_two"));
                                    game.setOption_three(jsonObject.getString("option_three"));
                                    game.setOption_four(jsonObject.getString("option_four"));

                                    listOptions.add(game);
                                }

                                callback.ListOptions(listOptions);
                            }else if(array.getJSONArray(0).length() == 0){
                                Toast.makeText(context, "Ops, não há nenhuma nova lista para responder...", Toast.LENGTH_LONG).show();
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

                        dialog.dismiss();
                    }
                }
        );

        getRequest.setRetryPolicy(policy);
        queue.add(getRequest);

        return listOptions;
    }

    public interface VolleyCallbackGet{
        void onResult(boolean result);
        void ListOptions(List<GameOptionsModel> options);
        void ListGame(List<GameModel> listGame);
    }
}
