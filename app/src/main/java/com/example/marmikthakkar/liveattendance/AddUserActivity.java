package com.example.marmikthakkar.liveattendance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddUserActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    LinearLayout linearLayout;
    Spinner typeSpinner, courseSpinner, programmeSpinner, semSpinner;
    EditText nameEditText, uidEditText, passwordEditText;
    Button createUserButton;
    DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        
        User user = getIntent().getParcelableExtra("user");

        linearLayout = findViewById(R.id.addUserLinearLayour);
        typeSpinner = findViewById(R.id.addUserSpinner);
        nameEditText= findViewById(R.id.addUserName);
        uidEditText= findViewById(R.id.addUserUid);
        passwordEditText= findViewById(R.id.addUserPassword);
        createUserButton = findViewById(R.id.addUserCreateUser);
        typeSpinner.setOnItemSelectedListener(this);
        createUserButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == createUserButton){
            String uid = uidEditText.getText().toString().trim();
            String pass = passwordEditText.getText().toString().trim();
            String name = nameEditText.getText().toString().trim();
            String type = typeSpinner.getSelectedItem().toString().trim();

            mDatabaseRef = FirebaseDatabase.getInstance().getReference();
            if ( ! (uid.isEmpty() && pass.isEmpty() && name.isEmpty()) ){
                Map<String, Object> result = new HashMap<>();
                result.put("uid", uid);
                result.put("name", name);
                result.put("pass", pass);
                result.put("type", type);

                if (typeSpinner.getSelectedItem().equals("student")){
                    courseSpinner = findViewById(R.id.courseSpinner);
                    programmeSpinner = findViewById(R.id.programmeSpinner);
                    semSpinner = findViewById(R.id.semSpinner);
                    String course = courseSpinner.getSelectedItem().toString().trim();
                    String programme = programmeSpinner.getSelectedItem().toString().trim();
                    String sem = semSpinner.getSelectedItem().toString().trim();

                    result.put("course", course);
                    result.put("programme", programme);
                    result.put("sem", sem);

                    mDatabaseRef.child("courses/"+course+"/"+programme+"/"+sem+"/students").push().setValue(uid);
                }

                mDatabaseRef.child("users").push().setValue(result);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Success");
                alertDialogBuilder.setMessage("User Successfully added");
                alertDialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }else{
                Toast.makeText(this, "Please Enter all the fields", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinner semSpinner = null;
        Spinner programmeSpinner = null;
        Spinner courseSpinner = null;
        if (typeSpinner.getSelectedItem().equals("student")){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            courseSpinner = new Spinner(this);
            courseSpinner.setId(R.id.courseSpinner);
            courseSpinner.setLayoutParams(params);
            String[] courseArray = getResources().getStringArray(R.array.course);
            ArrayAdapter<String> courseAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,courseArray);
            courseSpinner.setAdapter(courseAdapter);


            programmeSpinner = new Spinner(this);
            programmeSpinner.setId(R.id.programmeSpinner);
            programmeSpinner.setLayoutParams(params);
            String[] programmeArray = getResources().getStringArray(R.array.programme);
            ArrayAdapter<String> programmeAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,programmeArray);
            programmeSpinner.setAdapter(programmeAdapter);

            semSpinner = new Spinner(this);
            semSpinner.setId(R.id.semSpinner);
            semSpinner.setLayoutParams(params);
            String[] semArray = getResources().getStringArray(R.array.sem);
            ArrayAdapter<String> semAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,semArray);
            semSpinner.setAdapter(semAdapter);

            if (linearLayout.findViewById(R.id.courseSpinner) == null && linearLayout.findViewById(R.id.programmeSpinner) == null && linearLayout.findViewById(R.id.semSpinner) == null){
                linearLayout.addView(courseSpinner);
                linearLayout.addView(programmeSpinner);
                linearLayout.addView(semSpinner);
            }
        }else{
            if (linearLayout.findViewById(R.id.courseSpinner) != null && linearLayout.findViewById(R.id.programmeSpinner) != null && linearLayout.findViewById(R.id.semSpinner) != null){
                linearLayout.removeView(linearLayout.findViewById(R.id.courseSpinner));
                linearLayout.removeView(linearLayout.findViewById(R.id.programmeSpinner));
                linearLayout.removeView(linearLayout.findViewById(R.id.semSpinner));
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
