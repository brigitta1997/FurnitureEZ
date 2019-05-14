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
import com.example.user.furnitureproject2.Model.VrObject;
import com.example.user.furnitureproject2.R;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private List<VrObject> myDataset;
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
            tView = (TextView) v.findViewById(R.id.furniName);
            price = (TextView) v.findViewById(R.id.furniPrice);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HomeAdapter(Context context, List<VrObject> myDataset) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.myDataset = myDataset;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        new DownloadImageTask(myViewHolder.mImgView).execute(myDataset.get(i).getObj2dl());
        Log.i("info","+++++name: " +myDataset.get(i).getName());
        myViewHolder.tView.setText(myDataset.get(i).getName());
        Log.i("info","+++++price: " +myDataset.get(i).getPrice());
        myViewHolder.price.setText(String.valueOf(myDataset.get(i).getPrice()));
    }

    @Override
    public HomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = mInflater.inflate(R.layout.search_page_item,viewGroup,false);

        MyViewHolder vh =  new MyViewHolder(v);
        return vh;
    }

    @Override
    public int getItemCount() {
        if(myDataset != null){
            return myDataset.size();
        }else{
            return 0;
        }
    }

}
