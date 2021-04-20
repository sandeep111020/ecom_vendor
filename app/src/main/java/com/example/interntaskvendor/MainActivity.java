package com.example.interntaskvendor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;

public class MainActivity extends AppCompatActivity {

    EditText mail, password;
    Button lgbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lgbutton = findViewById(R.id.login);
        mail = findViewById(R.id.emailid);
        password = findViewById(R.id.password);
        lgbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logintouser();

            }
        });
    }
    private void Logintouser()
    {
        String _password=password.getText().toString();
        String _mail = mail.getText().toString();


        if(mail.getText().toString().contentEquals(""))
        {
            Toast.makeText(MainActivity.this, "Please type your Mail Id", Toast.LENGTH_SHORT).show();
        }
        else if (password.getText().toString().contentEquals(""))
        {
            Toast.makeText(MainActivity.this, "Please type your Password", Toast.LENGTH_SHORT).show();
        }
        else
        {
            /*loadingbar.setMessage("Please Wait..");
            loadingbar.setTitle("Login Account");
            loadingbar.setCancelable(false);
            loadingbar.show();*/


            Query checkuser = FirebaseDatabase.getInstance().getReference("Admin");
            checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.exists()){


                        String systempassword = snapshot.child("password").getValue(String.class);
                        String systemmail = snapshot.child("email").getValue(String.class);


                        if (systemmail.equals(_mail) && systempassword.equals(_password)){

                            Intent intent = new Intent(MainActivity.this,Home.class);



                            startActivity(intent);
                            finish();
                        }
                        else{

                            Toast.makeText(MainActivity.this,"wrong password and mail",Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {


                }
            });
        }
    }
}