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
    ArrayList<Integer> maxHoursPerSubject;
    ArrayList<Integer> attended;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_student_attendance, container, false);

        if ( getArguments().getParcelable("User") != null ){
            user = getArguments().getParcelable("User");
        }else {
            Toast.makeText(getActivity(), "Invalid Login", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
        textView = view.findViewById(R.id.testTextView);
        listView = view.findViewById(R.id.listView);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        Query subjectQuery = mDatabaseRef.child("courses/"+user.getCourse()+"/"+user.getProgramme()+"/"+user.getSem()+"/subjects");
        subjectQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    maxHoursPerSubject = new ArrayList<>();
                    attended = new ArrayList<>();

                    for (DataSnapshot subject: dataSnapshot.getChildren()){
                        Log.d("subject", subject.getValue().toString());
                        Query attendance = mDatabaseRef.child("courses/"+user.getCourse()+"/"+user.getProgramme()+"/"+user.getSem()+"/"+subject.getValue().toString()+"/total/attendance/").orderByKey().equalTo(user.getUid());
                        attendance.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                attended.add(Integer.parseInt(dataSnapshot.child(user.getUid()).getValue().toString()));
                                Log.d("attendance", dataSnapshot.child(user.getUid()).getValue().toString());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        Query maxhours = mDatabaseRef.child("courses/"+user.getCourse()+"/"+user.getProgramme()+"/"+user.getSem()+"/"+subject.getValue().toString()+"/total/maxhours");
                        maxhours.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                maxHoursPerSubject.add(Integer.parseInt(dataSnapshot.getValue().toString()));
                                textView.setText("");
                                for (int i=0; i<attended.size();i++){
                                    textView.append(attended.get(i).toString()+"\t"+maxHoursPerSubject.get(i).toString());
                                    textView.append("\n");
                                }
                                Log.d("maxhours", dataSnapshot.getValue().toString());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }else{
                    Toast.makeText(getActivity(), "Data Not Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        Toast.makeText(getActivity(), maxHoursPerSubject.toArray().toString(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(getActivity(), attended.toArray().toString(), Toast.LENGTH_SHORT).show();
        return view;
    }
}
