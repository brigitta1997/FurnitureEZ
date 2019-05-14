package com.example.user.furnitureproject2.tabClass;

import android.content.Intent;
import android.content.pm.FeatureGroupInfo;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.user.furnitureproject2.Activity.ObjectDescriptionActivity;
import com.example.user.furnitureproject2.Activity.SearchByCompanyActivity;
import com.example.user.furnitureproject2.Adapter.CompanyAdapter;
import com.example.user.furnitureproject2.Adapter.SearchAdapter;
import com.example.user.furnitureproject2.Helper.DownloadImageTask;
import com.example.user.furnitureproject2.Helper.JsonApiSearch;
import com.example.user.furnitureproject2.Helper.RecyclerItemClickListener;
import com.example.user.furnitureproject2.Model.Company;
import com.example.user.furnitureproject2.Model.VrObject;
import com.example.user.furnitureproject2.R;
import com.example.user.furnitureproject2.Activity.SelectCollectionsActivity;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomePage extends Fragment {
    private static final String TAG = HomePage.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;

    private ArFragment arFragment;
    private ModelRenderable andyRenderable;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private JsonApiSearch jsonApiSearch;
    private String companyName;

    private List<Company> myDataset;

    public HomePage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.home_page, container, false);

        Button b = (Button) v.findViewById(R.id.sel_by_col);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SelectCollectionsActivity.class);
                startActivity(intent);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://206.189.47.125/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApiSearch = retrofit.create(JsonApiSearch.class);
        myDataset = new ArrayList<Company>();

        Log.i("info","============masuk1");

        mRecyclerView = (RecyclerView) v.findViewById(R.id.rView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a Grid layout manager
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);;
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new CompanyAdapter(getContext(), myDataset);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Log.i("info","position1 ="+position);
                        int id = 0;
                        if(myDataset != null){
                            id = myDataset.get(position).getId();
                            companyName = myDataset.get(position).getName();
                        }
                        Log.i("info","position2 ="+ id);
                        SearchByCompanyActivity(companyName);


                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        getAllLogo();


        return v;
    }

    public void SearchByCompanyActivity(String name){
        Intent intent = new Intent(getActivity(), SearchByCompanyActivity.class);
        startActivity(intent);
    }

    private void getAllLogo(){
        Call<List<Company>> call = jsonApiSearch.getAllLogo();
        call.enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {
                if(!response.isSuccessful()){
                    Log.i("info","Code: "+ response.code());
                    return;
                }
                List<Company> cos = response.body();

                for(Company co : cos){
                    myDataset.add(co);
                    Log.i("info","+++"+co.getLogo());

                }
                Log.i("info","+++" + myDataset.size());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Company>> call, Throwable t) {

            }
        });
    }


}
