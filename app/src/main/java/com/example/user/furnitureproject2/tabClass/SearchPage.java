package com.example.user.furnitureproject2.tabClass;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.furnitureproject2.Adapter.SearchAdapter;
import com.example.user.furnitureproject2.Helper.JsonApiSearch;
import com.example.user.furnitureproject2.Model.VrObject;
import com.example.user.furnitureproject2.Activity.ObjectDescriptionActivity;
import com.example.user.furnitureproject2.R;
import com.example.user.furnitureproject2.Helper.RecyclerItemClickListener;

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

public class SearchPage extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView tv;
    private JsonApiSearch jsonApiSearch;

    private List<VrObject> myDataset;

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

//        mAdapter.notifyDataSetChanged();

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText==null|| newText.trim().isEmpty()){
            resetSearch();
            return false;
        }


        createPost(newText);

        return false;
    }

    public void resetSearch(){
        //TODO
    }

    public void setMyDataset(List<VrObject> myDataset) {
        this.myDataset = myDataset;
    }

    public List<VrObject> getMyDataset() {
        return myDataset;
    }

    public SearchPage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.search_page, container, false);

        setHasOptionsMenu(true);

        tv = (TextView) v.findViewById(R.id.resp);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://206.189.47.125/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApiSearch = retrofit.create(JsonApiSearch.class);
        myDataset = new ArrayList<VrObject>();

        Log.i("info","============masuk1");

        mRecyclerView = (RecyclerView) v.findViewById(R.id.rView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a Grid layout manager
        mLayoutManager = new GridLayoutManager(getActivity(),2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new SearchAdapter(getContext(), myDataset);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Log.i("info","position1 ="+position);
                        int id = 0;
                        if(myDataset != null){
                            id = myDataset.get(position).getId();
                        }
                        Log.i("info","position2 ="+ id);
                        callObjDEscActivity(id);


                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }


    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){

        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search_menu_item);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search Here");

        super.onCreateOptionsMenu(menu, inflater);


    }

    private void createPost(String text){
        myDataset.clear();
        VrObject vro = new VrObject();
        vro.setName(text);

        Call<List<VrObject>> call = jsonApiSearch.createPost(vro);

        call.enqueue(new Callback<List<VrObject>>() {
            @Override
            public void onResponse(Call<List<VrObject>> call, Response<List<VrObject>> response) {
                if(!response.isSuccessful()){
                    tv.setText("Code: "+ response.code());
                    return;
                }
                List<VrObject> vros = response.body();

                for(VrObject vro : vros){
                    myDataset.add(vro);

//                    String content="";
//                    content +=  "ID: "+ vro.getId() + "\n";
//                    content +=  "Name: "+ vro.getName() + "\n\n";
//
//                    tv.append(content);

                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<VrObject>> call, Throwable t) {
                tv.setText(t.getMessage());
            }
        });
    }
    public void callObjDEscActivity(int obj_id){
        Intent intent = new Intent(getActivity(), ObjectDescriptionActivity.class);
        intent.putExtra("OBJID",obj_id);
        startActivity(intent);
    }

}
