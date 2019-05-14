package com.example.user.furnitureproject2.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.furnitureproject2.Helper.JsonApiSearch;
import com.example.user.furnitureproject2.Model.ResponseMessage;
import com.example.user.furnitureproject2.Model.User;
import com.example.user.furnitureproject2.Model.VrObject;
import com.example.user.furnitureproject2.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private TextInputLayout name;
    private TextInputLayout email;
    private TextInputLayout password;
    private JsonApiSearch jsonApiSearch;
    private UserLocalStore userLocalStore;
    private User user;
    private boolean isSuccesful;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        TextView t = (TextView) findViewById(R.id.go_to_login);
        t.setOnClickListener(this);

        Button regBtn = (Button) findViewById(R.id.btn_register);
        regBtn.setOnClickListener(this);
//        t.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://206.189.47.125/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApiSearch = retrofit.create(JsonApiSearch.class);

        userLocalStore = new UserLocalStore(this);


        name = (TextInputLayout) findViewById(R.id.til_name);
        email = (TextInputLayout) findViewById(R.id.til_email);
        password = (TextInputLayout) findViewById(R.id.til_password);
        isSuccesful = false;



    }


    private void goToLogin(){
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_register:
                String name = this.name.getEditText().getText().toString();
                String email = this.email.getEditText().getText().toString();
                String password = this.email.getEditText().getText().toString();

                user = new User(-1,name,email,password);
                register(user);


                break;
            case R.id.go_to_login:
                goToLogin();
                break;

        }
    }

    public void register(User user){
        Call<ResponseMessage> call = jsonApiSearch.register(user);
        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {

                ResponseMessage mes = response.body();
                if(!response.isSuccessful()){
                    Log.i("info","===masuk sini1");
                    Toast.makeText(getApplicationContext(),mes.getError(),Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i("info","===masuk sini2");
                if(mes.getError() == null){
                    Toast.makeText(getApplicationContext(),mes.getSuccess(),Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),mes.getError(),Toast.LENGTH_SHORT).show();
                }

//                ResponseMessage mes = response.body();

                isSuccesful = true;
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                isSuccesful=false;
            }
        });
        Log.i("info","+++ "+isSuccesful);
        if(isSuccesful){
            finish();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
