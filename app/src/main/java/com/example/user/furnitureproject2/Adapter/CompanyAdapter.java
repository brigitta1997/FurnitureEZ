package com.example.user.furnitureproject2.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.furnitureproject2.Helper.DownloadImageTask;
import com.example.user.furnitureproject2.Model.Company;
import com.example.user.furnitureproject2.R;

import java.util.List;

//Pull Logo from API

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.MyViewHolder>{
    private List<Company> myDataset;
    private LayoutInflater mInflater;
    private Context context;


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImgView;
        public TextView tView;
        public TextView price;
        public CardView cv;

        public MyViewHolder(View v) {
            super(v);
            mImgView = (ImageView) v.findViewById(R.id.logo);


        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CompanyAdapter(Context context, List<Company> myDataset) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.myDataset = myDataset;
    }


    @Override
    public void onBindViewHolder(@NonNull CompanyAdapter.MyViewHolder myViewHolder, int i) {
        new DownloadImageTask(myViewHolder.mImgView).execute(myDataset.get(i).getLogo());
        Log.i("info","+++++name: " +myDataset.get(i).getName());
    }

    @Override
    public CompanyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = mInflater.inflate(R.layout.logo_item,viewGroup,false);

        CompanyAdapter.MyViewHolder vh =  new CompanyAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public int getItemCount() {
        if(myDataset != null){
            Log.i("info","+++"+myDataset.size());
            return myDataset.size();
        }else{
            return 0;
        }
    }
}
