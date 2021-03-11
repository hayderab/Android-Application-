package com.example.food_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraView extends AppCompatActivity {

    public static final int CAMERA_PERM_CODE = 101;
    public static final int GALLERY_REQUEST_CODE = 105;
    ImageView imageSleted ;
    TextView textView;
    public String SImage;
    Button cameraBtn, galleryBtn;
    String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_view);
        //Toast.makeText(CameraView.this, "Camera ............", Toast.LENGTH_SHORT).show();

        imageSleted = findViewById(R.id.imageView);
        textView = findViewById(R.id.btextview);
        cameraBtn  = findViewById(R.id.camerabtn);
        galleryBtn = findViewById(R.id.galleryBtn);
        //askCameraPermissions();

        cameraBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Toast.makeText(CameraView.this, "Camera button is clicked", Toast.LENGTH_SHORT).show();
                askCameraPermissions();
            }
        });

        galleryBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Toast.makeText(CameraView.this, "Gallery button is clicked", Toast.LENGTH_SHORT).show();
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                gallery.setType("image/*");

                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
            }
        });



    }


    private void askCameraPermissions() {
        if (ContextCompat.checkSelfPermission( this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        } else{
            dispatchTakePictureIntent();
        }

    }



    //https://developer.android.com/guide/topics/permissions/overview

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERM_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted. Continue the action or workflow
                    dispatchTakePictureIntent();
                    // in your app.
                }  else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                    //Toast.makeText(CameraView.this, "Camera permission is required to use app", Toast.LENGTH_SHORT).show();

                }
                return;
        }
        // Other 'case' lines to check for other
        // permissions this app might request.
    }

    //    private void openCamera() {
//        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(camera, CAMERA_PERM_CODE);
//        //Toast.makeText(CameraView.this, "Camera open request", Toast.LENGTH_SHORT).show();
//    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // checking if user gratnted the permission
        if(requestCode == CAMERA_PERM_CODE){
//            Bitmap image = (Bitmap) data.getExtras().get("data");
//            imageSleted.setImageBitmap(image);

            if (resultCode == Activity.RESULT_OK){
                File f = new File((currentPhotoPath));
                imageSleted.setImageURI(Uri.fromFile(f));
                Log.d("tag", "Absolute Url of image is" + Uri.fromFile(f));

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(f));
                    //Initilize byte stream
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    // compress bitmap
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    // initialise byte array
                    byte[] bytes = stream.toByteArray();
                    SImage  = Base64.encodeToString(bytes, Base64.DEFAULT);
                    Log.d("tag", "Base 64 Encoded image"+ SImage);

                    //textView.setText(SImage);

                } catch (IOException e) {
                    e.printStackTrace();
                }

//                Bitmap bm = BitmapFactory.decodeFile(currentPhotoPath);
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
//                byte[] b = baos.toByteArray();

                //textView.setText(imageString);

            }
        }

        if (requestCode == GALLERY_REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK){
//                Log.d("tag", "url from gallery image");
                // uri to get the image data.
                Uri contentUri  = data.getData();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "." + getFileExten(contentUri);
                Log.d("tag", "url from gallery image" + contentUri);
                imageSleted.setImageURI(contentUri);

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), contentUri);
                    //Initilize byte stream
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    // compress bitmap
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    // initialise byte array
                    byte[] bytes = stream.toByteArray();
                    SImage  = Base64.encodeToString(bytes, Base64.DEFAULT);
                    Log.d("tag", "Base 64 Encoded image"+ SImage);

                    //textView.setText(SImage);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private String getFileExten(Uri contentUri) {
        // accepting images with different extensions
        ContentResolver c = getContentResolver();
        MimeTypeMap mine = MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(c.getType(contentUri));

    }


    private File createImageFile() throws IOException {
        // Create an image file name
        // 1st timestamp
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        // image file with timestamp.
        String imageFileName = "JPEG_" + timeStamp + "_";
        // get the directory where we want to store the image.
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

//    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_PERM_CODE);
            }
        }
    }
}
