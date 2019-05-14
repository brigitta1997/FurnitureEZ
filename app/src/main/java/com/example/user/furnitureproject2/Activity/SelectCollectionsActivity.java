package com.example.user.furnitureproject2.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.user.furnitureproject2.R;

public class SelectCollectionsActivity extends AppCompatActivity {
    private ConstraintLayout select;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_by_type_h);

        select = (ConstraintLayout) findViewById(R.id.home_item_1);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
