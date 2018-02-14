package kz.mytazalyk.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class UserInfoActivity extends AppCompatActivity {

    private TextView name;
    private TextView id;
    private TextView trash;
    private TextView correct;
    private TextView uncorrect;

    private ImageButton back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_user_info);

        back_button = (ImageButton) findViewById(R.id.back_button);

        name = (TextView) findViewById(R.id.name);
        id = (TextView) findViewById(R.id.id);
        trash = (TextView) findViewById(R.id.trash);
        correct = (TextView) findViewById(R.id.correct);
        uncorrect = (TextView) findViewById(R.id.uncorrect);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String nameStr = getIntent().getExtras().getString("name");
        name.setText("Полное имя: " + nameStr);

        String idStr = getIntent().getExtras().getString("Id");
        id.setText("QR код: " + idStr);

        String trashStr = getIntent().getExtras().getString("trash");
        trash.setText("Количество сданных отходов: " + trashStr);

        String correctStr = getIntent().getExtras().getString("correct");
        correct.setText("Правильные сортировки: " + correctStr);

        String uncorrectStr = getIntent().getExtras().getString("uncorrect");
        uncorrect.setText("Неправильные сортировки: " + uncorrectStr);
    }
}
