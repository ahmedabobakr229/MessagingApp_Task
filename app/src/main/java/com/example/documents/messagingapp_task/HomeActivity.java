package com.example.documents.messagingapp_task;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    Button upload , out , chatPage;
    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;
    private CircleImageView mDisplayImage;
    private StorageReference mImageStorage;
    private ProgressDialog mProgressDilog;
    private static final int REQUEST_CODE = 100 ;
    Uri image_uri;
    CircleImageView imageView;

    private static final int GALLERY_PICK = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        upload = (Button) findViewById(R.id.upload_image);
        out = (Button) findViewById(R.id.logOut);
        chatPage = (Button)findViewById(R.id.chatting);
        imageView = (CircleImageView) findViewById(R.id.imagePick);

        chatPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //.... Chatting Page here ......
                Intent i = new Intent(HomeActivity.this,ChattingActivity.class);
                startActivity(i);

            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(HomeActivity.this, "choose image", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Intent.ACTION_PICK , MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(i,REQUEST_CODE);

            }
        });
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                sendToMain();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            image_uri = data.getData();
            imageView.setImageURI(image_uri);
        }
    }

    private void sendToMain() {
        Intent i = new Intent(HomeActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
