 package kz.mytazalyk.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

 public class MainActivity extends AppCompatActivity {

    private Button checkQRCode;
    private Button allUsers;

    private IntentIntegrator qrScan;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkQRCode = (Button) findViewById(R.id.checkQRCode);
        allUsers = (Button) findViewById(R.id.allUsers);

         qrScan = new IntentIntegrator(this);

        checkQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScan.initiateScan();
            }
        });

        allUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AllUsersActivity.class));
            }
        });
    }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
         if (result != null) {
             //if qrcode has nothing in it
             if (result.getContents() == null) {
                 Toast.makeText(this, "Не найдено!", Toast.LENGTH_LONG).show();
             } else {
                 //if qr contains data
                 try {
                     //converting the data to json
                     JSONObject obj = new JSONObject(result.getContents());

                    Intent intent = new Intent(MainActivity.this, FullInfActivity.class);
                    intent.putExtra("ID_USER", obj.getString("qr_id"));
                    startActivity(intent);

                 } catch (JSONException e) {
                     e.printStackTrace();
                     //if control comes here
                     //that means the encoded format not matches
                     //in this case you can display whatever data is available on the qrcode
                     //to a toast
                     Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                 }
             }
         } else {
             super.onActivityResult(requestCode, resultCode, data);
         }
     }
 }
