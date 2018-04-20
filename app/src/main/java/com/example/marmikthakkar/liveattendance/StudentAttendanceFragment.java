package com.example.marmikthakkar.liveattendance;

        import android.os.Bundle;
        import android.app.Fragment;
        import android.provider.ContactsContract;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.firebase.database.ChildEventListener;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.Query;
        import com.google.firebase.database.ValueEventListener;

        import java.lang.reflect.Array;
        import java.util.ArrayList;


public class StudentAttendanceFragment extends Fragment{

    View view;
    User user;
    ListView listView;
    TextView textView;
    DatabaseReference mDatabaseRef;
    Subject subject[];
    Attendance attendance[];
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_student_attendance, container, false);

        if ( getArguments().getParcelable("user") != null ){
            user = getArguments().getParcelable("user");
            subject = (Subject[]) getArguments().getParcelableArray("subjects");
        }else {
            Toast.makeText(getActivity(), "Invalid Login", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
        textView = view.findViewById(R.id.testTextView);
        listView = view.findViewById(R.id.listView);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        attendance = new Attendance[subject.length];
        for (int i = 0; i < subject.length ; i++){
            Query attendanceQuery = mDatabaseRef.child("courses/"+user.getCourse()+"/"+user.getProgramme()+"/"+user.getSem()+"/"+subject[i].getName()+"/total/attendance/").orderByKey().equalTo(user.getUid());
            final int finalI = i;
            attendanceQuery.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    attendance[finalI] = new Attendance(subject[finalI].getImgURL(),subject[finalI].getName(),subject[finalI].getFaculty(), Integer.parseInt(dataSnapshot.getValue().toString()));
                    AttendanceArrayAdapter attendanceArrayAdapter = new AttendanceArrayAdapter(getActivity(), R.layout.custom_layout_attendance, attendance);
                    listView.setAdapter(attendanceArrayAdapter);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        return view;
    }
}
