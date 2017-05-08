package br.com.seplag.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Created by Manoel Neto on 26/04/2017.
 */

public class SpinnerAdapter extends ArrayAdapter {

    public SpinnerAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        int count = super.getCount();

        return count>0 ? count-1 : count ;
    }
}
