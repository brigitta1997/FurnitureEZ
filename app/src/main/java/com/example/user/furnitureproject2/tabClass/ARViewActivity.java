package com.example.user.furnitureproject2.tabClass;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.Button;
import android.view.View;

import com.example.user.furnitureproject2.Adapter.ArAdapter;
import com.example.user.furnitureproject2.Auth.UserLocalStore;
import com.example.user.furnitureproject2.Helper.JsonApiSearch;
import com.example.user.furnitureproject2.Helper.RecyclerItemClickListener;
import com.example.user.furnitureproject2.Model.VrObject;
import com.example.user.furnitureproject2.R;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.example.user.furnitureproject2.Helper.OnSwipeTouchListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ARViewActivity extends AppCompatActivity {
    private static final String TAG = ARViewActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;

    private ArFragment arFragment;

    private UserLocalStore userLocalStore;
    private int userId;
    private JsonApiSearch jsonApiSearch;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView rvHori;
    private List<VrObject> myDataset;
    private static String SFB_ASSET;
    private Context context;
    private boolean fab_select;


    @Override
    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    // CompletableFuture requires api level 24
    // FutureReturnValueIgnored is not valid
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }

        setContentView(R.layout.activity_ux);

        context = getApplicationContext();
        fab_select = false;

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://206.189.47.125/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApiSearch = retrofit.create(JsonApiSearch.class);

        userLocalStore = new UserLocalStore(context);

        //Floating Action Button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fab_select == false){
                    fab_select = true;
                    ConstraintLayout.LayoutParams nl = (ConstraintLayout.LayoutParams) fab.getLayoutParams();
                    nl.bottomMargin = dpToPx(156);
                    fab.setLayoutParams(nl);
                    fab.setImageResource(R.drawable.ic_arrow_down);
                    rvHori.setVisibility(View.VISIBLE);
                }else{
                    fab_select = false;
                    ConstraintLayout.LayoutParams nl = (ConstraintLayout.LayoutParams) fab.getLayoutParams();
                    nl.bottomMargin = dpToPx(16);
                    fab.setLayoutParams(nl);
                    fab.setImageResource(R.drawable.ic_arrow);
                    rvHori.setVisibility(View.INVISIBLE);
                }


            }
        });


        rvHori = (RecyclerView) findViewById(R.id.pictureListHori);
        rvHori.setVisibility(View.INVISIBLE);

        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvHori.setLayoutManager(horizontalLayoutManagaer);
//
//        LayoutInflater inflater = LayoutInflater.from(this);

        if(authenticate() == true){
            userId = userLocalStore.getLoggedInUser().getId();
            getObj(userId);
        }

        myDataset = new ArrayList<VrObject>();

        Log.i("info","====lewat1");
        mAdapter = new ArAdapter(context,myDataset);
        Log.i("info","====lewat2");
        rvHori.setAdapter(mAdapter);
        rvHori.addOnItemTouchListener(
                new RecyclerItemClickListener(context, rvHori, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.i("info","position1 ="+position);
                        int id = 0;
                        if(myDataset != null){
                            id = myDataset.get(position).getId();
                        }
                        SFB_ASSET = myDataset.get(position).getSfbLink();

                        Log.i("info","position2 ="+ id);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );






//        Uri model = Uri.parse("https://s3-ap-southeast-1.amazonaws.com/fypprojectstoragevr/3dobject/1555328456Table_Large_Rectangular_gg.gltf");
        ///====================
//         When you build a Renderable, Sceneform loads its resources in the background while returning
//         a CompletableFuture. Call thenAccept(), handle(), or check isDone() before calling get().
//        ModelRenderable.builder()
//                .setSource(this, R.raw.andy)
//                .build()
//                .thenAccept(renderable -> andyRenderable = renderable)
//                .exceptionally(
//                        throwable -> {
//                            Toast toast =
//                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
//                            toast.setGravity(Gravity.CENTER, 0, 0);
//                            toast.show();
//                            return null;
//                        });
//==========================

        arFragment.setOnTapArPlaneListener(
                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
//                    if (duckRenderable == null) {
//                        return;
//                    }

                    // Create the Anchor.
                    Anchor anchor = hitResult.createAnchor();
//                    AnchorNode anchorNode = new AnchorNode(anchor);
//                    anchorNode.setParent(arFragment.getArSceneView().getScene());
//
//                    // Create the transformable andy and add it to the anchor.
//                    TransformableNode andy = new TransformableNode(arFragment.getTransformationSystem());
////                    andy.getScaleController().setMaxScale(0.01f);
////                    andy.getScaleController().setMinScale(0.01f);
//                    andy.setParent(anchorNode);
//                    andy.setRenderable(duckRenderable);
//                    andy.select();
                    if(SFB_ASSET == null){
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("you have not logged in")
                                .setTitle("error");
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }else {
                        placeObject(arFragment, anchor, SFB_ASSET);
                    }
                });

        Button clearButton = findViewById(R.id.clear_button);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClear(arFragment);
            }
        });

//        ImageView colPic = (ImageView) findViewById(R.id.col_pic);
//        colPic.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                if(rvHori.getVisibility() == View.VISIBLE){
//                    rvHori.setVisibility(View.INVISIBLE);
//                }
//                else{
//                    rvHori.setVisibility(View.VISIBLE);
//                }
//            }
//        });

}

    /**
     * Returns false and displays an error message if Sceneform can not run, true if Sceneform can run
     * on this device.
     *
     * <p>Sceneform requires Android N on the device as well as OpenGL 3.0 capabilities.
     *
     * <p>Finishes the activity if Sceneform can not run
     */
    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Log.e(TAG, "Sceneform requires Android N or later");
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }
        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show();
            activity.finish();
            return false;
        }
        return true;
    }

//    private void placeObject(ArFragment fragment, Anchor anchor, Uri model) {
//        ModelRenderable.builder()
//                .setSource(fragment.getContext(), model)
//                .build()
//                .thenAccept(renderable -> addNodeToScene(fragment, anchor, renderable))
//                .exceptionally((throwable -> {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                    builder.setMessage(throwable.getMessage())
//                            .setTitle("Error!");
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                    return null;
//                }));
//
//    }

    private void placeObject(ArFragment fragment, Anchor anchor, String model) {
        ModelRenderable.builder()
                .setSource(this, Uri.parse(model))
                .build()
                .thenAccept(renderable -> addNodeToScene(fragment, anchor, renderable))
                .exceptionally((throwable -> {
                    Log.i("ggr","error "+throwable.getMessage());
//                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                    builder.setMessage(throwable.getMessage())
//                            .setTitle("Error!");
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
                    return null;
                }));

    }

    private void addNodeToScene(ArFragment fragment, Anchor anchor, Renderable renderable) {
        Log.i("ggr","sudah terender");
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode node = new TransformableNode(fragment.getTransformationSystem());
        node.getScaleController().setMaxScale(1f);
        node.getScaleController().setMinScale(0.01f);
        node.setRenderable(renderable);
        node.setParent(anchorNode);
        fragment.getArSceneView().getScene().addChild(anchorNode);
        node.select();
    }

    private void onClear(ArFragment fragment) {
        List<Node> children = new ArrayList<>(fragment.getArSceneView().getScene().getChildren());
        for (Node node : children) {
            if (node instanceof AnchorNode) {
                if (((AnchorNode) node).getAnchor() != null) {
                    ((AnchorNode) node).getAnchor().detach();
                }
            }
//            if (!(node instanceof Camera) && !(node instanceof Sun)) {
////                node.setParent(null);
////            }
        }
    }

    private void getObj(int userId){

        Call<List<VrObject>> call = jsonApiSearch.getSfbObj(userId);

        call.enqueue(new Callback<List<VrObject>>() {
            @Override
            public void onResponse(Call<List<VrObject>> call, Response<List<VrObject>> response) {
                if(!response.isSuccessful()){
                    Log.i("info","Code: "+ response.code());

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
                    Log.i("info","====add "+vro.getName());

                }
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<VrObject>> call, Throwable t) {
                Log.i("info",t.getMessage());

            }
        });

    }

    private boolean authenticate(){
        return userLocalStore.getUserLoggedIn();
    }

    public int dpToPx(int dp) {
        float density = context.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }


}

