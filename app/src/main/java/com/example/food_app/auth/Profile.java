package com.example.food_app.auth;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.food_app.MainActivity;
import com.example.food_app.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {

    FirebaseAuth mAuth;

    public String SImage;

    CardView logOut, back, profile;
    ImageView userProfile;
    String currentPhotoPath;


    private FirebaseStorage storage;
    private StorageReference mStorageRef;


    public static final int CAMERA_PERM_CODE = 101;
    public static final int GALLERY_REQUEST_CODE = 105;

    public Profile() {
        // Required empty public constructor
    }


    public static Profile newInstance() {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);



        mStorageRef = FirebaseStorage.getInstance().getReference();


        logOut  = view.findViewById(R.id.log_out);
        back = view.findViewById(R.id.backBtn);
        userProfile = view.findViewById(R.id.userProfile);


        logOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d("TAG", "loutout button clicked: ");
                mAuth.getInstance().signOut();
                // Set to start login activity
                Intent intent = new Intent(getContext(), login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d("TAG", "back button clicked button clicked: ");
                //Toast.makeText(CameraView.this, "Gallery button is clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);

            }
        });

        userProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d("TAG", "user profile button clicked ");
                //Toast.makeText(CameraView.this, "Gallery button is clicked", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getContext(), MainActivity.class);
//                startActivity(intent);
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                gallery.setType("image/*");
                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
            }
        });


        // getting the image URL that is accessed from firebase storage and saved to shared preferences so it can on the app instead of downloading and saving image to cache..
        SharedPreferences locSharedPreferences = getContext().getApplicationContext().getSharedPreferences("LocationSharedPrefers", getContext().MODE_PRIVATE);
        String imageUrl = locSharedPreferences.getString("uImageUri", "");
        if(imageUrl != null){
            Log.d("TAG", "onCreateView: Image link from shared resorces " + imageUrl);
            Glide.with(getContext()).load(imageUrl).apply(RequestOptions.circleCropTransform()).into(userProfile);
        }

        return view;
    }




    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*
        * Accessing the  gallery for getting the image URI
        *
        * */

        if (requestCode == GALLERY_REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK){
//                Log.d("tag", "url from gallery image");
                // uri to get the image data.
                Uri contentUri  = data.getData();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "." + getFileExten(contentUri);
                uploadPicture(contentUri);

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentUri);
                    //Initilize byte stream
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    // compress bitmap
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    // initialise byte array
                    byte[] bytes = stream.toByteArray();
                    SImage  = Base64.encodeToString(bytes, Base64.DEFAULT);
//                    Log.d("tag", "Base 64 Encoded image"+ SImage);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void uploadPicture(Uri contentUri) {
        // Reference : Firebase official  Documentation
        // accessing progress bar dialog.
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading Image......");

        progressDialog.show();
        //image path for firebase database.
       final StorageReference riversRef = mStorageRef.child("images/profilepic");
       //putting image reference link  to the database
        riversRef.putFile(contentUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
//                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();

//                        Glide.with(getContext()).load(contentUri).apply(RequestOptions.circleCropTransform()).into(userProfile);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Image Upload unsuccessful", Toast.LENGTH_SHORT).show();
                        // ...
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    // showing the progress.
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        Log.d("TAG", "Upload is " + progress + "% done");
                        progressDialog.setMessage("Progress " + (int)  progress + "%");
                        downloadPicture();

//                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    }
                });

    }




    private  void downloadPicture(){
//        StorageReference riversRef = mStorageRef.child("images/profilepic");
        mStorageRef.child("images/profilepic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'

                Log.d("TAG", "onSuccess: " + uri);
                Glide.with(getContext()).load(uri).apply(RequestOptions.circleCropTransform()).into(userProfile);

                SharedPreferences sharedPreferences = getContext().getSharedPreferences("LocationSharedPrefers", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("uImageUri", uri.toString());
                editor.commit();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.d("TAG", "unsuccessful: ");

            }
        });



    }


    private String getFileExten(Uri contentUri) {
        // accepting images with different extensions
        ContentResolver c = getActivity().getContentResolver();
        MimeTypeMap mine = MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(c.getType(contentUri));
    }












}