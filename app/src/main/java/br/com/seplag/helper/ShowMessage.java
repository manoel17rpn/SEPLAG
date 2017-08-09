package br.com.seplag.helper;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;

import br.com.seplag.R;

/**
 * Created by manoelramos on 08/08/17.
 */

public class ShowMessage {

    public void showSnackBar(View view, String text){
        Snackbar.make(view, text, Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.BLACK)
                .show();
    }

}
