package kz.mytazalyk.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllUsersActivity extends AppCompatActivity {

    private ListView all;
    private ImageButton back_button;

    DatabaseReference mDatabase;
    ArrayList<String> list= new ArrayList<>();
    ArrayList<String> ids = new ArrayList<>();
    ArrayList<String> correct = new ArrayList<>();
    ArrayList<String> uncorrect = new ArrayList<>();
    ArrayList<String> trash = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_all_users);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        all = (ListView) findViewById(R.id.all);
        back_button = (ImageButton) findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        all.setAdapter(adapter);
        mDatabase.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    list.add(ds.child("name").getValue(String.class));
                    ids.add(ds.getKey());
                    trash.add(String.valueOf(ds.child("trash").getValue()));
                    correct.add(String.valueOf(ds.child("correctly_sorted").getValue()));
                    uncorrect.add(String.valueOf(ds.child("uncorrectly_sorted").getValue()));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("onCancelled", databaseError.toString());
            }
        });
        all.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AllUsersActivity.this, UserInfoActivity.class);
                intent.putExtra("name", list.get(position));
                intent.putExtra("Id", ids.get(position));
                intent.putExtra("trash", trash.get(position));
                intent.putExtra("correct", correct.get(position));
                intent.putExtra("uncorrect", uncorrect.get(position));
                startActivity(intent);
            }
        });

    }
}
