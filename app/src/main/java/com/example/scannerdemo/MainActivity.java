package com.example.scannerdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    //Init var
    Button btScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign var
        btScan = findViewById(R.id.bt_scan);

        btScan.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //Init intent integrator
               IntentIntegrator intentIntegrator = new IntentIntegrator(
                       MainActivity.this
               );
               //Set prompt
               intentIntegrator.setPrompt("Flash = volume up");
               //Beep
               intentIntegrator.setBeepEnabled(true);
               //Orient lock
               intentIntegrator.setOrientationLocked(true);
               //Set capture activity
               intentIntegrator.setCaptureActivity(Capture.class);
               //Init scan
               intentIntegrator.initiateScan();
           }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Init intent result
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data
        );
        //Check
        if (intentResult.getContents() != null){
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    MainActivity.this
            );
            //Set title
            builder.setTitle("Result");
            //Set message
            builder.setMessage(intentResult.getContents());
            //Set positive button
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Dismiss dialog
                    dialogInterface.dismiss();
                }
            });
            EditText eanf = (EditText)findViewById(R.id.eanf);
            eanf.setText(intentResult.getContents());
            //Show alert dialog
            builder.show();
        }else {
            //When result contents is null
            Toast.makeText(getApplicationContext()
                    , "Something went wrong. Try scanning again",Toast.LENGTH_SHORT)
                    .show();
        }
    }
}