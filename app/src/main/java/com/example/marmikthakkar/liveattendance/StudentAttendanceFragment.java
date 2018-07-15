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
        import java.text.NumberFormat;
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
        listView = view.findViewById(R.id.listView);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        attendance = new Attendance[subject.length];
        for (int i = 0; i < subject.length ; i++){
            Query attendanceQuery = mDatabaseRef.child("courses/"+user.getCourse()+"/"+user.getProgramme()+"/"+user.getSem()+"/"+subject[i].getName()+"/total").orderByKey();
            final int finalI = i;
            attendanceQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        Log.d("SSSsubejctdataSnapshot", dataSnapshot.child("attendance/"+user.getUid()).getValue().toString());
                        Log.d("SSSasd", dataSnapshot.child("maxhours").toString());
                        int attendedHours = Integer.parseInt(dataSnapshot.child("attendance/"+user.getUid()).getValue().toString());
                        int maxHours = Integer.parseInt(dataSnapshot.child("maxhours").getValue().toString());
                        double percentAttendedHours = ( (float) attendedHours / maxHours ) * 100;

//                          For Float number format
                        NumberFormat numberFormat = NumberFormat.getNumberInstance();
                        numberFormat.setMinimumIntegerDigits(1);
                        numberFormat.setMaximumFractionDigits(2);

                        attendance[finalI] = new Attendance(
                                subject[finalI].getImgURL(),
                                subject[finalI].getName(),
                                subject[finalI].getFaculty(),
                                Float.parseFloat(numberFormat.format(percentAttendedHours)),
                                attendedHours,
                                maxHours
                        );
                        AttendanceArrayAdapter attendanceArrayAdapter = new AttendanceArrayAdapter(getActivity(), R.layout.custom_layout_attendance, attendance);
                        listView.setAdapter(attendanceArrayAdapter);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        return view;
    }
}
