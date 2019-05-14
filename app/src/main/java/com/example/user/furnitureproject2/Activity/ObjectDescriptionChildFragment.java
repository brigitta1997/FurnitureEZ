package com.example.user.furnitureproject2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.furnitureproject2.Auth.UserLocalStore;
import com.example.user.furnitureproject2.Helper.DownloadImageTask;
import com.example.user.furnitureproject2.Helper.JsonApiSearch;
import com.example.user.furnitureproject2.Model.ResponseMessage;
import com.example.user.furnitureproject2.Model.SavedObject;
import com.example.user.furnitureproject2.Model.VrObject;
import com.example.user.furnitureproject2.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ObjectDescriptionChildFragment extends Fragment {

    private JsonApiSearch jsonApiSearch;
    private ImageView img;
    private TextView name;
    private TextView price;
    private TextView body;
    private UserLocalStore userLocalStore;
    private VrObject vro;

    public ObjectDescriptionChildFragment(){
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://206.189.47.125/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApiSearch = retrofit.create(JsonApiSearch.class);

        userLocalStore = new UserLocalStore(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.oda_child_fragment, container,false);

//        img = (ImageView) v.findViewById(R.id.desc_img);
        name = (TextView) v.findViewById(R.id.desc_name);
        price = (TextView) v.findViewById(R.id.desc_price);
        body = (TextView) v.findViewById(R.id.desc_body);

        Intent intent = getActivity().getIntent();
        int obj_id = intent.getExtras().getInt("OBJID");
        getVro(obj_id);

        return v;
    }

    private boolean authenticate(){
        return userLocalStore.getUserLoggedIn();
    }

    private void getVro(int id){
        Call<VrObject> call = jsonApiSearch.getObj(id);
        call.enqueue(new Callback<VrObject>() {
            @Override
            public void onResponse(Call<VrObject> call, Response<VrObject> response) {
                if(!response.isSuccessful()){
                    name.setText("Code: "+ response.code());
                    return;
                }
                vro = response.body();
                Log.e("error","+++++ggmasuk sini4");
//                new DownloadImageTask(img).execute(vro.getObj2dl());
                Log.e("error","+++++ggmasuk sini5");
                name.setText(vro.getName());
                price.setText(String.valueOf(vro.getPrice()));
                body.setText(vro.getDescr());

            }

            @Override
            public void onFailure(Call<VrObject> call, Throwable t) {
                name.setText(t.getMessage());
            }
        });

    }

    private void postSavedObject(SavedObject savedObj){
        Call<ResponseMessage> call = jsonApiSearch.postSavedObj(savedObj);
        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(),response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
                ResponseMessage mes = response.body();
                Toast.makeText(getContext(),mes.getSuccess(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {

            }
        });
    }
}
