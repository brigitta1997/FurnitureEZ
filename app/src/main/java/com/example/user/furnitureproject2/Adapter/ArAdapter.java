package com.example.user.furnitureproject2.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.furnitureproject2.Helper.DownloadImageTask;
import com.example.user.furnitureproject2.Model.VrObject;
import com.example.user.furnitureproject2.R;

import java.util.List;

public class ArAdapter extends RecyclerView.Adapter<ArAdapter.MyViewHolder> {
    private List<VrObject> mDataset;
    private LayoutInflater mInflater;
    private Context context;

    public ArAdapter(Context context, List<VrObject> myDataset){
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        mDataset = myDataset;
    }


    @Override
    public ArAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = mInflater.inflate(R.layout.gallery_item, viewGroup, false);

        ArAdapter.MyViewHolder vh = new ArAdapter.MyViewHolder(v);
        return vh;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
//        public TextView mTextView;
        public ImageView mImgView;



        public MyViewHolder(View v) {
            super(v);
            mImgView = (ImageView) v.findViewById(R.id.preview_fur);

        }
    }

    @Override
    public void onBindViewHolder(ArAdapter.MyViewHolder myViewHolder, int i) {
        new DownloadImageTask(myViewHolder.mImgView).execute(mDataset.get(i).getObj2dl());
    }

    @Override
    public int getItemCount() {
        if(mDataset != null){
            return mDataset.size();
        }else{
            return 0;
        }
    }


}
