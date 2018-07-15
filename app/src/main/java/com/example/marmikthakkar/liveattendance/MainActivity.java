package com.example.marmikthakkar.liveattendance;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.firebase.FirebaseApp;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseAuthException;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.Query;
        import com.google.firebase.database.ValueEventListener;

        import java.io.Serializable;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    DatabaseReference mDatabaseReference;
    Query query;

    Button loginButton;

    EditText uidEditText, passwordEditText;

    Intent dashboardIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.loginButton);
        uidEditText = findViewById(R.id.uid);
        passwordEditText = findViewById(R.id.password);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == loginButton){
            final String uid = uidEditText.getText().toString().trim();
            final String password = passwordEditText.getText().toString().trim();

            query = mDatabaseReference.child("users").orderByChild("uid").equalTo(uid);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                            User user = snapshot.getValue(User.class);
                            if (snapshot.child("pass").getValue().equals(password)){

                                dashboardIntent = new Intent(MainActivity.this, DashboardActivity.class);
                                dashboardIntent.putExtra("user", user);
                                startActivity(dashboardIntent);
                            }else {
                                Toast.makeText(MainActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else{
                        Toast.makeText(MainActivity.this, "User Not Found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}


