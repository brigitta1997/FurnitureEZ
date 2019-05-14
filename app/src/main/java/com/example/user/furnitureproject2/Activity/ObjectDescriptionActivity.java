package com.example.user.furnitureproject2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.furnitureproject2.Adapter.ViewPagerAdapter;
import com.example.user.furnitureproject2.Auth.UserLocalStore;
import com.example.user.furnitureproject2.Helper.DownloadImageTask;
import com.example.user.furnitureproject2.Helper.JsonApiSearch;
import com.example.user.furnitureproject2.Model.ResponseMessage;
import com.example.user.furnitureproject2.Model.SavedObject;
import com.example.user.furnitureproject2.Model.Slider;
import com.example.user.furnitureproject2.Model.User;
import com.example.user.furnitureproject2.Model.VrObject;
import com.example.user.furnitureproject2.R;
import com.google.android.filament.View;
import com.example.user.furnitureproject2.Activity.ObjectDescriptionChildFragment;

import java.net.Authenticator;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ObjectDescriptionActivity extends AppCompatActivity {

    private JsonApiSearch jsonApiSearch;
    private ImageView img;
    private String objName;
    private TextView name;
    private TextView price;
    private TextView body;
    private ImageView addBtn;
    private UserLocalStore userLocalStore;
    private VrObject vro;
    private Menu menu;
    private CollapsingToolbarLayout col;
    private ViewPager viewPager;
    private LinearLayout sliderDotsPanel;
    private int dotsCount;
    private ImageView[] dots;
    private Toolbar toolbar;
    private boolean is_favourite;
    private int obj_id;
    private int user_id;
    private List<Slider> myDataset;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.cart:
//                cartActivity();
                return true;
            case R.id.favourite:
                if(!authenticate()){
                    Log.e("error","+++++ggmasuk sini95");
                    Toast.makeText(getApplicationContext(),"Need to Login to Use Favourites",Toast.LENGTH_LONG).show();
                }else{
                    if(is_favourite){
                        Log.e("error","+++++ggmasuk sini96");

                        menu.findItem(R.id.favourite).setIcon(R.drawable.heart_empty);
                        deleteSavedObj(obj_id);
                        is_favourite = false;
                    }else{
                        Log.e("error","+++++ggmasuk sini97");
                        menu.findItem(R.id.favourite).setIcon(R.drawable.heart_filled);
                        SavedObject svo = new SavedObject(user_id,obj_id);
                        postSavedObject(svo);
                        is_favourite = true;
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.od_menu, menu);
        this.menu = menu;
        return true;
    }

//    @Override
//    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//
//
//            dotsCount = viewPagerAdapter.getCount();
//            Log.e("error","+++++++ "+dotsCount);
//            if(dotsCount != 0){
//                addDots();
//            }
//
//
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_obj_desc_2);




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Item Details");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://206.189.47.125/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApiSearch = retrofit.create(JsonApiSearch.class);

        userLocalStore = new UserLocalStore(this);

        user_id = userLocalStore.getLoggedInUser().getId();
//        col = findViewById(R.id.collapseToolbar);

        Intent intent = getIntent();
        int obj_id = intent.getExtras().getInt("OBJID");
        getVro(obj_id);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        sliderDotsPanel = (LinearLayout) findViewById(R.id.sliderDots);

        myDataset = new ArrayList<Slider>();

        viewPagerAdapter = new ViewPagerAdapter(this,myDataset);
        viewPager.setAdapter(viewPagerAdapter);

//        dotsCount = viewPagerAdapter.getCount();
//        dots = new ImageView[dotsCount];
//
//        for(int i = 0; i < dotsCount; i++){
//
//            dots[i] = new ImageView(this);
//            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
//
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//            params.setMargins(8, 0, 8, 0);
//
//            sliderDotsPanel.addView(dots[i], params);
//
//        }
//
//        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotsCount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }


                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




//        img = (ImageView) findViewById(R.id.desc_img);
        name = (TextView) findViewById(R.id.desc_name);
        price = (TextView) findViewById(R.id.desc_price);
        body = (TextView) findViewById(R.id.desc_body);


//        addBtn = (ImageView) findViewById(R.id.add_btn);
//        addBtn.setOnClickListener(new android.view.View.OnClickListener() {
//            @Override
//            public void onClick(android.view.View v) {
//                Log.i("ggg","masukclick");
//                if(authenticate() == true){
//                    Log.i("ggg","masukclick2");
//                    //post saved objects
//                    int userId = userLocalStore.getLoggedInUser().getId();
//                    Log.i("ggg","masukclick   "+userId);
//                    int objId = vro.getId();
//                    SavedObject svo = new SavedObject(userId,objId);
//                    postSavedObject(svo);
//                }
//            }
//        });
        //---------------------------------------------------
//        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
//        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
//        mViewPagerAdapter.addFragment(new ObjectDescriptionChildFragment(), "ABOUT");
//        mViewPagerAdapter.addFragment(new ObjectDescriptionChildFragment(), "REVIEWS");
//
//        mViewPager.setAdapter(mViewPagerAdapter);
//
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(mViewPager);
//-------------------------------------------------------------------


    }


    private void getVro(int id){
        Call<VrObject> call = jsonApiSearch.getObj(id);
        call.enqueue(new Callback<VrObject>() {
            @Override
            public void onResponse(Call<VrObject> call, Response<VrObject> response) {
                if(!response.isSuccessful()){
                    objName = "Code: "+ response.code();
                    return;
                }
                vro = response.body();
                Log.e("error","+++++ggmasuk sini");
//                new DownloadImageTask(img).execute(vro.getObj2dl());

                Log.e("error","+++++ggmasuk sini2");
                objName = vro.getName();
//                col.setTitle(objName);
                name.setText(vro.getName());
                Log.e("error","+++++ggmasuk sini3");
                price.setText(String.valueOf(vro.getPrice()));
                body.setText(vro.getDescr());
                obj_id = vro.getId();
                if(authenticate()){
                    is_savedObject(obj_id);
                    Log.e("error","+++++ggmasuk sini9");
                }
                getImgSlider(obj_id);

            }

            @Override
            public void onFailure(Call<VrObject> call, Throwable t) {
                objName = t.getMessage();
            }
        });

    }

    private boolean authenticate(){
        return userLocalStore.getUserLoggedIn();
    }

    private void postSavedObject(SavedObject savedObj){
        Call<ResponseMessage> call = jsonApiSearch.postSavedObj(savedObj);
        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
                ResponseMessage mes = response.body();
                Toast.makeText(getApplicationContext(),mes.getSuccess(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {

            }
        });
    }

    private void is_savedObject(int obj_id){
        Log.e("error","+++++ggmasuk sini99");
        int user_id = userLocalStore.getLoggedInUser().getId();

        Call<ResponseMessage> call = jsonApiSearch.is_savedObject(user_id,obj_id);
        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
                ResponseMessage mes = response.body();
                if(Integer.parseInt(mes.getMessage()) == 0){
                    is_favourite = false;
                    if(menu != null) {
                        menu.findItem(R.id.favourite).setIcon(R.drawable.heart_empty);
                    }
                    Log.e("error","+++++ggmasuk sini91 "+mes.getMessage());
                }else{
                    is_favourite = true;
                    if(menu!=null) {
                        menu.findItem(R.id.favourite).setIcon(R.drawable.heart_filled);
                    }
                    Log.e("error","+++++ggmasuk sini92 "+mes.getMessage());
                }
//                Toast.makeText(getApplicationContext(),mes.getSuccess(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                Log.e("error",t.getMessage());
            }
        });


    }

    private void deleteSavedObj(int obj_id){
        Log.e("error","+++++ggmasuk sini94");
        Call<ResponseMessage> call = jsonApiSearch.deleteSavedObj(user_id,obj_id);
        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
                ResponseMessage mes = response.body();
                Toast.makeText(getApplicationContext(),mes.getSuccess(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                Log.e("error",t.getMessage());
            }
        });
    }

    public void getImgSlider(int obj_id){
        Call<List<Slider>> call= jsonApiSearch.getImgSlider(obj_id);
       call.enqueue(new Callback<List<Slider>>() {
           @Override
           public void onResponse(Call<List<Slider>> call, Response<List<Slider>> response) {
               if(!response.isSuccessful()){
                   Toast.makeText(getApplicationContext(),response.code(),Toast.LENGTH_SHORT).show();
                   return;
               }
               List<Slider> sliders = response.body();
               if(sliders!= null) {
                   for (Slider slider : sliders) {
                       myDataset.add(slider);
                       Log.i("info", "+++++++" + slider.getObjId());
                   }
                   viewPagerAdapter.notifyDataSetChanged();
//               dotsCount = viewPagerAdapter.getCount();
//               Log.e("error","+++++++ "+dotsCount);
//               if(dotsCount != 0){
//                   addDots();
//               }
                   addDots();
               }
           }

           @Override
           public void onFailure(Call<List<Slider>> call, Throwable t) {

           }
       });
    }

    public void addDots(){
        dotsCount = viewPagerAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotsPanel.addView(dots[i], params);

        }

        if(dotsCount !=0) {
            dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
        }
    }


}
