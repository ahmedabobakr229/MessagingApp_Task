package com.example.documents.messagingapp_task;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText edName , password ;
    Button login , sign ;
    private ProgressDialog LoginProgress;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        edName = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.pass);
        login = (Button)findViewById(R.id.loginBtn);
        sign = (Button)findViewById(R.id.regiBtn);
        LoginProgress = new ProgressDialog(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = edName.getText().toString().trim();
                String Pass = password.getText().toString().trim();

                if (!TextUtils.isEmpty(Email) || !TextUtils.isEmpty(Pass)){
                    LoginProgress.setTitle("Logging in");
                    LoginProgress.setMessage("Please wait while checking your information");
                    LoginProgress.setCanceledOnTouchOutside(false);
                    LoginProgress.show();
                    loginUser(Email , Pass);
                }
            }
        });
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    private void loginUser(String email, String pass) {
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "sucsess.",
                                    Toast.LENGTH_SHORT).show();
                            LoginProgress.dismiss();
                            Intent i = new Intent(MainActivity.this,HomeActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            finish();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent y = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(y);
                            // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            // Toast.makeText(Login.this, "Error", Toast.LENGTH_SHORT).show();
                            Toast.makeText(MainActivity.this, "Please make sure your information is true.",
                                    Toast.LENGTH_LONG).show();
                            LoginProgress.hide();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }
    }
