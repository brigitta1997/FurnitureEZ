package com.example.user.furnitureproject2.tabClass;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.user.furnitureproject2.Auth.LoginActivity;
import com.example.user.furnitureproject2.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilePage extends Fragment {

    public ProfilePage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.profile_page, container, false);

        Button bt = (Button) v.findViewById(R.id.edit_profile);
        bt.setVisibility(View.INVISIBLE);

        Button log_in = (Button) v.findViewById(R.id.loginPrompt);
        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        return v;
    }
}
