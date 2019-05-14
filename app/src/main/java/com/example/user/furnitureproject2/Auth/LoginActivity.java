package com.example.user.furnitureproject2.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.furnitureproject2.Helper.JsonApiSearch;
import com.example.user.furnitureproject2.Model.User;
import com.example.user.furnitureproject2.Model.VrObject;
import com.example.user.furnitureproject2.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Button loginBtn;
    private TextInputLayout tilemail;
    private TextInputLayout tilpassword;
    private UserLocalStore userLocalStore;
    private JsonApiSearch jsonApiSearch;
    private User us;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        TextView t = (TextView) findViewById(R.id.go_to_register);
        t.setOnClickListener(this);

        loginBtn = (Button) findViewById(R.id.btn_login);
        tilemail = (TextInputLayout) findViewById(R.id.til_email);
        tilpassword = (TextInputLayout) findViewById(R.id.til_password);

        loginBtn.setOnClickListener(this);

        userLocalStore = new UserLocalStore(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://206.189.47.125/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonApiSearch = retrofit.create(JsonApiSearch.class);

    }

    void goToRegister(){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_login:
                getUserAPI();

                Log.i("info","=======settotrue");


                break;

            case R.id.go_to_register:
                goToRegister();
                break;
        }

    }

    public void getUserAPI(){
        String email = tilemail.getEditText().getText().toString();
        String password = tilpassword.getEditText().getText().toString();
        User user = new User(-1,"",email,password);

        Call<User> call = jsonApiSearch.getUserAPI(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    tilemail.setHint("Code: "+ response.code());
                    return;
                }

                User resp = response.body();
//                us  = response.body();
                user.setId(resp.getId());
                user.setName(resp.getName());
                Log.i("info","ggg1 :"+resp.getId());
                Log.i("info","ggg2 :"+user.getId());
                Toast.makeText(getApplicationContext(),"You are signed in",Toast.LENGTH_SHORT).show();
                userLocalStore.storeUserData(user);
                userLocalStore.setUserLoggedIn(true);
                if(user!=null){
                    Log.i("info", "=======name: "+user.getName());
                    Log.i("info","=======id : " +user.getId());
                    Log.i("info", "=======email: " +user.getEmail());
                    Log.i("info", "=======password: "+user.getPassword());

                }
                finish();



            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Email Not Registered",Toast.LENGTH_SHORT).show();
            }
        });


    }
}
