package kz.mytazalyk.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FullInfActivity extends AppCompatActivity {

    private ImageButton back_button;
    private TextView name;
    private EditText passedTrash;
    private RadioGroup radioGroup;
    private Button addTrash;

    String qr_id;
    String fullname;
    int check;
    int passedStr;
    int passedTr;
    int correct;
    int uncorrect;
    boolean ch = false;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_full_inf);


        qr_id = getIntent().getExtras().getString("ID_USER");
        Log.d("ID1", qr_id);


        back_button = (ImageButton) findViewById(R.id.back_button);
        name = (TextView) findViewById(R.id.fullName);
        passedTrash = (EditText)findViewById(R.id.passedTrash);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        addTrash = (Button) findViewById(R.id.addTrash);


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == -1) {
                    check = -1;
                } else if (checkedId == R.id.truely) {
                    check = 1;
                } else if(checkedId == R.id.wrongly) {
                    check = 0;
                }
            }
        });


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    String s = ds.getKey().toString();
                    if (qr_id.equals(s)){

                        fullname = ds.child("name").getValue().toString();
                        name.setText(fullname);

                        passedTr = Integer.parseInt(ds.child("trash").getValue().toString());
                        uncorrect = Integer.parseInt(ds.child("uncorrectly_sorted").getValue().toString());
                        correct = Integer.parseInt(ds.child("correctly_sorted").getValue().toString());
                        ch = true;

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("onCancelled", databaseError.toString());
            }
        });

        addTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (passedStr > 0){
                    Toast.makeText(getApplicationContext(), "Пожалуйста, введите сколько количество мусоров сдно!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (check == -1) {
                    Toast.makeText(getApplicationContext(), "Выберите один из пунктов", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = new User();
                user.setName(fullname);
                passedStr = Integer.parseInt(passedTrash.getText().toString());
                user.setTrash(passedTr + passedStr);
                if (check == 0) {
                    user.setUncorrectly_sorted(uncorrect + 1);
                }else  {
                    user.setUncorrectly_sorted(uncorrect);
                }
                if (check == 1) {
                    user.setCorrectly_sorted(correct + 1);
                }else {
                    user.setCorrectly_sorted(correct);
                }
                mDatabase.child("User").child(qr_id).setValue(user);
                startActivity(new Intent(FullInfActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
