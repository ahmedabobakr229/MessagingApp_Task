package com.example.documents.messagingapp_task;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText name_reg ,email_reg, pass_reg ;
    Button regi ;

    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name_reg = (EditText)findViewById(R.id.nameReg);
        email_reg = (EditText)findViewById(R.id.emailReg);
        pass_reg = (EditText)findViewById(R.id.passReg);

        regi = (Button) findViewById(R.id.RegBtn);

        mProgress = new ProgressDialog(this);

//Unable to connect to the editor to retrieve text.
        mAuth = FirebaseAuth.getInstance();

        regi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getemail = email_reg.getText().toString().trim();
                String getpassword = pass_reg.getText().toString().trim();

                if (!TextUtils.isEmpty(getemail) || !TextUtils.isEmpty(getpassword)){
                    mProgress.setTitle("Registering User");
                    mProgress.setMessage("Wait while we create your account");
                    mProgress.setCanceledOnTouchOutside(false);
                    mProgress.show();
                    callsignup(getemail,getpassword);
                }
            }
        });
    }

    private void callsignup( final String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Testing", "Signup successful" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            mProgress.dismiss();
                        } else {

                            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = current_user.getUid();

                            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);


                            HashMap<String, String> userMap = new HashMap<>();
                            userMap.put("email", email);
                            userMap.put("Image", "default");
                            userMap.put("Msg", "default");


                            mDatabase.setValue(userMap);


                        }
                        if (task.isSuccessful()) {
                            userProfile();
                            Toast.makeText(RegisterActivity.this, "Created Account", Toast.LENGTH_SHORT).show();
                            Log.d("TESTING", "Created Account");
                            mProgress.hide();
                            Intent i = new Intent(RegisterActivity.this, HomeActivity.class);
                            startActivity(i);
                        }
                    }
                });
    }

    private void userProfile() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user!=null){
            UserProfileChangeRequest profileupdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name_reg.getText().toString().trim()).build();

            user.updateProfile(profileupdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()){
                        Log.d("TESTING","User profile updated.");
                    }
                }
            });

        }
    }
}
