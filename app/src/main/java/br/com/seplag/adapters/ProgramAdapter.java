package br.com.seplag.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.seplag.R;
import br.com.seplag.helper.ImageHelper;
import br.com.seplag.interfaces.RecyclerViewOnClickListenerHack;
import br.com.seplag.model.Program;
import br.com.seplag.view.SolicitationsActivity;

/**
 * Created by Manoel Neto on 05/04/2017.
 */

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.MyViewHolder>{
    private Context mContext;
    private List<Program> mListProgram;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private float scale;
    private int width;
    private int height;

    public ProgramAdapter(Context mContext, List<Program> mListProgram){
        this.mContext = mContext;
        this.mListProgram = mListProgram;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        scale = mContext.getResources().getDisplayMetrics().density;
        width = mContext.getResources().getDisplayMetrics().widthPixels - (int)(14 * scale + 0.5f);
        height = (width / 16) * 9;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.appearance_card_view, viewGroup, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {

        myViewHolder.titleProgram.setText(mListProgram.get(position).getTitleProgram());
        myViewHolder.infoProgram.setText(mListProgram.get(position).getInfoProgram());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            myViewHolder.imageProgram.setImageResource(mListProgram.get(position).getImageProgram());
        } else {
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), mListProgram.get(position).getImageProgram());
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);

            bitmap = ImageHelper.getRoundedCornerBitmap(mContext, bitmap, 4, width, height, false, false, true, true);
            myViewHolder.imageProgram.setImageBitmap(bitmap);
        }

    }
    @Override
    public int getItemCount() {
        return mListProgram.size();
    }


    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }


    public void addListItem(Program p, int position){
        mListProgram.add(p);
        notifyItemInserted(position);
    }


    public void removeListItem(int position){
        mListProgram.remove(position);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageProgram;
        public TextView titleProgram;
        public TextView infoProgram;

        public MyViewHolder(View itemView) {
            super(itemView);

            imageProgram = (ImageView) itemView.findViewById(R.id.image_card);
            titleProgram = (TextView) itemView.findViewById(R.id.title_program);
            infoProgram = (TextView) itemView.findViewById(R.id.info_program);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Program program = mListProgram.get(getPosition());
            Bundle params = new Bundle();
            params.putString("name_program", program.getTitleProgram());
            Intent intent = new Intent(mContext, SolicitationsActivity.class);
            intent.putExtras(params);
            mContext.startActivity(intent);

        }
    }
}
