package br.com.seplag.view;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import br.com.seplag.R;
import br.com.seplag.controller.GameController;
import br.com.seplag.model.GameModel;
import br.com.seplag.model.GameOptionsModel;

public class QuestionsActivity extends AppCompatActivity {
    private List<GameModel> listActive;
    private List<GameOptionsModel> listOptions = new ArrayList<GameOptionsModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("O que precisamos?");
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);

        final GameController game = new GameController();
        game.ListActive(this, new GameController.VolleyCallbackGet() {
            @Override
            public void onResult(boolean result) {

            }

            @Override
            public void ListOptions(List<GameOptionsModel> options) {

            }

            @Override
            public void ListGame(List<GameModel> listGame) {
                if(listGame.size() > 0){
                    for(int i = 0; i < listGame.size(); i++){
                        game.ListOptions(QuestionsActivity.this, listGame.get(i).getName_list(), new GameController.VolleyCallbackGet() {
                            @Override
                            public void onResult(boolean result) {

                            }

                            @Override
                            public void ListOptions(List<GameOptionsModel> options) {
                                Log.i("Options", "" + options.size());
                            }

                            @Override
                            public void ListGame(List<GameModel> listGame) {

                            }
                        });
                    }
                }
            }
        });
    }
}
