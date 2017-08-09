package br.com.seplag.interfaces;

import android.view.View;

/**
 * Created by Manoel Neto on 05/04/2017.
 */

public interface RecyclerViewOnClickListenerHack {
    public void onClickListener(View view, int position);
    public void onLongPressClickListener(View view, int position);
}
