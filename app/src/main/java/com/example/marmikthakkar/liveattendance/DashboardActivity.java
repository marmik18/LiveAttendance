package com.example.marmikthakkar.liveattendance;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    User user;
    StudentAttendanceFragment studentAttendanceFragment;
    DatabaseReference mDatabaseRef;
    AdminMainFragment adminMainFragment;
    EditProfileFragment editProfileFragment;
    Bundle bundle;
    TextView sapIDTextView, fullNameTextView;
    ImageView userImageView;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        user = getIntent().getParcelableExtra("user");
        drawer = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mDatabaseRef.keepSynced(true);

        navigationView = findViewById(R.id.nav_view);
        fullNameTextView = navigationView.getHeaderView(0).findViewById(R.id.fullNameTextViewNavHeader);
        sapIDTextView = navigationView.getHeaderView(0).findViewById(R.id.sapIDTextViewNavHeader);
        userImageView = navigationView.getHeaderView(0).findViewById(R.id.imageViewNavHeader);


        bundle = new Bundle();
        bundle.putParcelable("user", user);

        fullNameTextView.setText(user.getName());
        sapIDTextView.setText(user.getUid());
        Picasso.get()
                .load(user.getImgURL())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(userImageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get()
                                .load(user.getImgURL())
                                .placeholder(R.drawable.ic_launcher_foreground)
                                .error(R.drawable.ic_launcher_background)
                                .into(userImageView);
                    }
                });


        if (user.getType().equals("student")){
            final Query subjectQuery = mDatabaseRef.child("courses/"+user.getCourse()+"/"+user.getProgramme()+"/"+user.getSem()+"/subjects").orderByKey();
            subjectQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        Subject subject[]= new Subject[(int)dataSnapshot.getChildrenCount()];
                        int i = 0;
                        for (DataSnapshot subjectSnapShot: dataSnapshot.getChildren()){
                            Log.d("subjects", subjectSnapShot.getValue().toString());
                            subject[i] = new Subject();
                            subject[i] = subjectSnapShot.getValue(Subject.class);
                            Log.d("subject_class","sub["+i+"] : "+subject[i].getName());
                            i++;
                        }

                        studentAttendanceFragment = new StudentAttendanceFragment();
                        bundle.putParcelableArray("subjects", subject);

                        studentAttendanceFragment.setArguments(bundle);
                        loadFragment(studentAttendanceFragment);

                    }else{
                        Toast.makeText(DashboardActivity.this, "Data Not Found", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else if (user.getType().equals("faculty")){
//            Change the fragment to home of faculty
//            studentAttendanceFragment = new StudentAttendanceFragment();
//            studentAttendanceFragment.setArguments(bundle);
            loadFragment(new EditProfileFragment());
        }else if (user.getType().equals("admin")){
            adminMainFragment = new AdminMainFragment();
            adminMainFragment.setArguments(bundle);
            loadFragment(adminMainFragment);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            if (user.getType().equals("student")){
                loadFragment(studentAttendanceFragment);
            }else if (user.getType().equals("faculty")){
//                load a different fragment here
            }else if (user.getType().equals("admin")){
                loadFragment(adminMainFragment);
            }
        } else if (id == R.id.nav_manage) {
            editProfileFragment = new EditProfileFragment();
            editProfileFragment.setArguments(bundle);
            loadFragment(editProfileFragment);
        }else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.dashboardFrameLayout, fragment);
        fragmentTransaction.commit();
    }


}
