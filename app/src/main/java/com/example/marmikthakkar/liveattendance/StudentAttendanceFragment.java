package com.example.marmikthakkar.liveattendance;

        import android.os.Bundle;
        import android.app.Fragment;
        import android.provider.ContactsContract;
        import android.util.Log;
        import android.util.TimingLogger;
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


public class StudentAttendanceFragment extends Fragment{

    View view;
    User user;
    ListView listView;
    DatabaseReference mDatabaseRef;
    TimingLogger timings;
    int maxhours;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_student_attendance, container, false);
        timings = new TimingLogger("exec", "methodA");
        if ( getArguments().getParcelable("User") != null ){
            user = getArguments().getParcelable("User");
        }else {
            Toast.makeText(getActivity(), "Invalid Login", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
        listView = view.findViewById(R.id.listView);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        timings.addSplit("Getting references");
        mDatabaseRef.child("courses/"+user.getCourse()+"/"+user.getProgramme()+"/"+user.getSem()+"/"+"subjects");

        return view;
    }

//    @Override
//    public void onDataChange(DataSnapshot dataSnapshot) {
//        timings.addSplit("Getting snapshot");
//        if(dataSnapshot.exists()){
//            for (DataSnapshot subject: dataSnapshot.getChildren()){
//                Log.d("faculty", subject.child("faculty").getValue().toString());
//                if (subject.child("total").exists()){
//                    maxhours = Integer.parseInt(subject.child("total").child("maxhours").getValue().toString());
//                    Log.d("max_hours", subject.getKey().toString());
////                    Query query = mDatabaseRef.child("courses/"+user.getCourse()+"/"+user.getProgramme()+"/"+user.getSem()+"/"+subject.getKey()+"/attendance").orderByChild(user.getUid()).equalTo(user.getUid());
//                    for (DataSnapshot uid: subject.child("total/attendance").getChildren()){
//                        if (uid.getKey().toString().equals(user.getUid())){
//                            Toast.makeText(getActivity(), uid.getValue().toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//            }
//            timings.addSplit("display");
//            timings.dumpToLog();
//        }else{
//            Toast.makeText(getActivity(), "Data Not Found", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    public void onCancelled(DatabaseError databaseError) {
//
//    }
}
