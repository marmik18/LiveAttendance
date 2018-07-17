package com.example.marmikthakkar.liveattendance;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


public class EditProfileFragment extends Fragment {
    View view;
    User user;
    ImageView profileImageView;
    EditText nameEditText, sapIDEditText, courseEditText, programmeEditText, semEditText, roleEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        if ( getArguments().getParcelable("user") != null ){
            user = getArguments().getParcelable("user");
        }else {
            Toast.makeText(getActivity(), "Invalid Login", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }

        profileImageView = view.findViewById(R.id.profileImageView);
        nameEditText = view.findViewById(R.id.nameEditText);
        sapIDEditText = view.findViewById(R.id.sapIDEditText);
        courseEditText = view.findViewById(R.id.courseEditText);
        programmeEditText = view.findViewById(R.id.programmeEditText);
        semEditText = view.findViewById(R.id.semEditText);
        roleEditText = view.findViewById(R.id.roleEditText);

        nameEditText.clearFocus();
        nameEditText.setEnabled(false);
        nameEditText.setText(user.getName());
        sapIDEditText.setEnabled(false);
        sapIDEditText.setText(user.getUid());
        courseEditText.setEnabled(false);
        courseEditText.setText(user.getCourse());
        programmeEditText.setEnabled(false);
        programmeEditText.setText(user.getProgramme());
        semEditText.setEnabled(false);
        semEditText.setText(user.getSem());
        roleEditText.setEnabled(false);
        roleEditText.setText(user.getType());

        Picasso.get()
                .load(user.getImgURL())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(profileImageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get()
                                .load(user.getImgURL())
                                .placeholder(R.drawable.ic_launcher_foreground)
                                .error(R.drawable.ic_launcher_background)
                                .into(profileImageView);
                    }
                });


        return view;
    }

}
