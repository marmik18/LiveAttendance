package com.example.marmikthakkar.liveattendance;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class StudentAttendanceFragment extends Fragment {

    View view;
    User user;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_student_attendance, container, false);

        textView = view.findViewById(R.id.studentAttendanceTitle);
        if ( getArguments().getParcelable("User") != null ){
            user = getArguments().getParcelable("User");
            textView.setText(user.getName());
        }else {
            Toast.makeText(getActivity(), "Invalid Login", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
        return view;
    }
}
