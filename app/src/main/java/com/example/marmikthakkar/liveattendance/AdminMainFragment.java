package com.example.marmikthakkar.liveattendance;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class AdminMainFragment extends Fragment implements View.OnClickListener{

    View view;
    FloatingActionButton fab;
    User user;
    public AdminMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_main, container, false);

        if ( getArguments().getParcelable("user") != null ){
            user = getArguments().getParcelable("user");
        }else {
            Toast.makeText(getActivity(), "Invalid Login", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }

        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == fab){
            Intent intent = new Intent(getActivity().getBaseContext(), AddUserActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }
    }
}
