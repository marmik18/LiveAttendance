package com.example.marmikthakkar.liveattendance;

        import android.os.Bundle;
        import android.app.Fragment;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ListView;
        import android.widget.Toast;

        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.Query;
        import com.google.firebase.database.ValueEventListener;


public class StudentAttendanceFragment extends Fragment implements ValueEventListener{

    View view;
    User user;
    ListView listView;
    DatabaseReference mDatabaseRef;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_student_attendance, container, false);

        if ( getArguments().getParcelable("User") != null ){
            user = getArguments().getParcelable("User");
        }else {
            Toast.makeText(getActivity(), "Invalid Login", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
        listView = view.findViewById(R.id.listView);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabaseRef.child("courses").child(user.getCourse()).child(user.getProgramme()).child(user.getSem()).orderByChild(user.getUid());
        query.addListenerForSingleValueEvent(this);

        return view;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if(dataSnapshot.exists()){
            for (DataSnapshot subject: dataSnapshot.getChildren()){
                Log.d("subject", subject.child("faculty").getValue().toString());
            }
        }else{
            Toast.makeText(getActivity(), "Data Not Found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
