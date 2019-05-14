package com.example.user.furnitureproject2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.furnitureproject2.Auth.LoginActivity;
import com.example.user.furnitureproject2.Auth.RegisterActivity;
import com.example.user.furnitureproject2.Auth.UserLocalStore;
import com.example.user.furnitureproject2.tabClass.DashboardPage;
import com.example.user.furnitureproject2.tabClass.HomePage;
import com.example.user.furnitureproject2.tabClass.ProfilePage;
import com.example.user.furnitureproject2.tabClass.SearchPage;

import com.google.ar.core.ArCoreApk;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private HomePage homePage;
//    private NotificationFragment notificationFragment;
    private ProfilePage profilePage;
    private SearchPage searchPage;
    private int[] dataset;
//    private DashboardFragment dashboardFragment;
    private Bundle bundle;
    private Fragment active;
    private UserLocalStore userLocalStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActionBarTitle("Furniture EZ");
        // Enable AR related functionality on ARCore supported devices only.
//        maybeEnableArButton();

        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.navigation);

//        homePage = new HomePage();
//        setFragment(homePage);
////        notificationFragment = new NotificationFragment();
//        profilePage = new ProfilePage();
////        dashboardFragment = new DashboardFragment();
//
//        searchPage = new SearchPage();
        final Fragment fragment1 = new HomePage();
        final Fragment fragment2 = new DashboardPage();
        final Fragment fragment3 = new SearchPage();
        final Fragment fragment4 = new ProfilePage();

        final FragmentManager fm = getSupportFragmentManager();
        active = fragment1;

        fm.beginTransaction().add(R.id.main_frame, fragment4, "4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.main_frame, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.main_frame, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.main_frame,fragment1, "1").commit();


//        dataset = new int[]{R.drawable.sofa_2d,R.drawable.chair,R.drawable.sofa_2d,R.drawable.chair,R.drawable.sofa_2d,R.drawable.sofa_2d,R.drawable.sofa_2d,R.drawable.sofa_2d,R.drawable.sofa_2d,R.drawable.sofa_2d,R.drawable.sofa_2d,R.drawable.sofa_2d,R.drawable.sofa_2d};
//        searchPage.setMyDataset(dataset);

//        mTextMessage = (TextView) findViewById(R.id.message);

        mMainNav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener(){
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                setActionBarTitle("Furniture EZ");
                                fm.beginTransaction().hide(active).show(fragment1).commit();
                                active = fragment1;
                                Log.i("act","active: fragment1");
                                return true;

                            case R.id.navigation_dashboard:
                                setActionBarTitle("Library");
                                fm.beginTransaction().hide(active).show(fragment2).commit();
                                active = fragment2;
                                Log.i("act","active: fragment2");
                                return true;

                            case R.id.navigation_search:
                                setActionBarTitle("Search Furniture");
                                fm.beginTransaction().hide(active).show(fragment3).commit();
                                active = fragment3;
                                Log.i("act","active: fragment3");
                                return true;

                            case R.id.navigation_profile:
                                setActionBarTitle("Profile");
                                fm.beginTransaction().hide(active).show(fragment4).commit();
                                active = fragment4;
                                Log.i("act","active: fragment4");
                                return true;
                        }
                        return false;
                    }
        });


        userLocalStore = new UserLocalStore(this);
//        =====================

//        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()) {
//                    case R.id.navigation_home:
//                        getSupportActionBar().show();
//                        setActionBarTitle("Furniture EZ");
//                        setFragment(homePage);
//                        return true;
////                    case R.id.navigation_dashboard:
//////                        mTextMessage.setText(R.string.title_dashboard);
////                        setFragment(dashboardFragment);
////                        return true;
////                    case R.id.navigation_notifications:
//////                        mTextMessage.setText(R.string.title_notifications);
////                        setFragment(notificationFragment);
////                        return true;
//                    case R.id.navigation_profile:
////                        mTextMessage.setText(R.string.title_profile);
//                        getSupportActionBar().hide();
//                        setFragment(profilePage);
//                        return true;
//                    case R.id.navigation_search:
////                        mTextMessage.setText(R.string.title_profile);
//                        getSupportActionBar().show();
//                        setActionBarTitle("Search Furniture");
//                        setFragment(searchPage);
//                        return true;
//                }
//                return false;
//            }
//        });





//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id= item.getItemId();

        if(id==R.id.signin){
            startActivity(new Intent(this, LoginActivity.class));
        }
        if(id==R.id.signout){
            userLocalStore.clearUserData();
            userLocalStore.setUserLoggedIn(false);

        }

        return super.onOptionsItemSelected(item);
    }

    //    @Override
//    protected void onResume() {
//        super.onResume();
//
//        // ARCore requires camera permission to operate.
//        if (!CameraPermissionHelper.hasCameraPermission(this)) {
//            CameraPermissionHelper.requestCameraPermission(this);
//            return;
//        }
//
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] results) {
//        if (!CameraPermissionHelper.hasCameraPermission(this)) {
//            Toast.makeText(this, "Camera permission is needed to run this application", Toast.LENGTH_LONG)
//                    .show();
//            if (!CameraPermissionHelper.shouldShowRequestPermissionRationale(this)) {
//                // Permission denied with checking "Do not ask again".
//                CameraPermissionHelper.launchPermissionSettings(this);
//            }
//            finish();
//        }
//    }

//    void maybeEnableArButton() {
//        ArCoreApk.Availability availability = ArCoreApk.getInstance().checkAvailability(this);
//        if (availability.isTransient()) {
//            // Re-query at 5Hz while compatibility is checked in the background.
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    maybeEnableArButton();
//                }
//            }, 200);
//        }
//
//        bundle = new Bundle();
//
//        if (availability.isSupported()) {
//            bundle.putBoolean("arAvail",true);
////            mArButton.setVisibility(View.VISIBLE);
////            mArButton.setEnabled(true);
//            // indicator on the button.
//        } else { // Unsupported or unknown.
////            mArButton.setVisibility(View.INVISIBLE);
////            mArButton.setEnabled(false);
//            bundle.putBoolean("arAvail",false);
//        }
//    }

//    private void setFragment(Fragment fragment){
//        fragment.setArguments(bundle);
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.main_frame, fragment);
//        fragmentTransaction.commit();
//    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

}
