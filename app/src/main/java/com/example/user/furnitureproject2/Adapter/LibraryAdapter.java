package com.example.user.furnitureproject2.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
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

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.MyViewHolder> {
    private List<VrObject> mDataset;
    private LayoutInflater mInflater;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
//        public TextView mTextView;
        public ImageView mImgView;
        public TextView tView;
        public CardView cv;


        public MyViewHolder(View v) {
            super(v);
            mImgView = (ImageView) v.findViewById(R.id.img_companylib);
            tView = (TextView) v.findViewById(R.id.furniNamelib);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public LibraryAdapter(Context context, List<VrObject> myDataset) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public LibraryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
//        // create a new view
//        TextView v = (TextView) LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.my_text_view, parent, false);

        View i = mInflater.inflate(R.layout.library_item, parent, false);

        LibraryAdapter.MyViewHolder vh = new LibraryAdapter.MyViewHolder(i);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(LibraryAdapter.MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        holder.mImgView.setImageResource(mDataset[position]);
//        holder.mImgView.setImageResource(mDataset.get(position).getObj2dl());

        holder.tView.setText(mDataset.get(position).getName());
        new DownloadImageTask(holder.mImgView).execute(mDataset.get(position).getObj2dl());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(mDataset != null){
            return mDataset.size();
        }else{
            return 0;
        }

    }
}
