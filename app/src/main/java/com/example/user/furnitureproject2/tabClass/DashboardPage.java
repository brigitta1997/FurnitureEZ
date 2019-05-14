package com.example.user.furnitureproject2.tabClass;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.example.user.furnitureproject2.Adapter.LibraryAdapter;
import com.example.user.furnitureproject2.Auth.UserLocalStore;
import com.example.user.furnitureproject2.Helper.JsonApiSearch;
import com.example.user.furnitureproject2.Helper.RecyclerItemClickListener;
import com.example.user.furnitureproject2.Model.VrObject;
import com.example.user.furnitureproject2.Activity.ObjectDescriptionActivity;
import com.example.user.furnitureproject2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardPage extends Fragment{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout mySwipeRefreshLayout;
    private TextView tv;
    private JsonApiSearch jsonApiSearch;
    private UserLocalStore userLocalStore;
    private int userId;

    private List<VrObject> myDataset;

    public DashboardPage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.dashboard_page, container, false);

        tv = (TextView) v.findViewById(R.id.resplib);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://206.189.47.125/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApiSearch = retrofit.create(JsonApiSearch.class);

        userLocalStore = new UserLocalStore(getContext());



        mRecyclerView = (RecyclerView) v.findViewById(R.id.rViewLib);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a Grid layout manager
        mLayoutManager = new GridLayoutManager(getActivity(),2);
        mRecyclerView.setLayoutManager(mLayoutManager);


        /*
         * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
         * performs a swipe-to-refresh gesture.
         */

        mySwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swiperefresh);



        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        myDataset = new ArrayList<VrObject>();

        // specify an adapter (see also next example)
        mAdapter = new LibraryAdapter(getContext(), myDataset);
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

        if(authenticate() == true){
            userId = userLocalStore.getLoggedInUser().getId();
            getSaved(userId);
        }

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i("ggr", "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        myDataset.clear();
                        getSaved(userId);

                    }
                }
        );
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){

        inflater.inflate(R.menu.menu_dash, menu);

        super.onCreateOptionsMenu(menu, inflater);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.look:
                Intent intent = new Intent(getContext(), ARViewActivity.class);
                startActivity(intent);
                return true;
            case R.id.deleteItem:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }



    }

    public void callObjDEscActivity(int obj_id){
        Intent intent = new Intent(getActivity(), ObjectDescriptionActivity.class);
        intent.putExtra("OBJID",obj_id);
        startActivity(intent);
    }

    private void getSaved(int userId){

        Call<List<VrObject>> call = jsonApiSearch.getSavedObj(userId);

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
                mySwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<VrObject>> call, Throwable t) {
                tv.setText(t.getMessage());
            }
        });

    }

    private boolean authenticate(){
        return userLocalStore.getUserLoggedIn();
    }


}
