package com.example.food_app;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Camera#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Camera extends Fragment {



    public static final int CAMERA_PERM_CODE = 101;
    public static final int GALLERY_REQUEST_CODE = 105;
    ImageView imageSleted ;
    TextView textView;
    public String SImage;
    //    Button cameraBtn, galleryBtn;
    String currentPhotoPath;

    CardView camerabtn, galleryBtn;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Camera() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Camera.
     */
    // TODO: Rename and change types and number of parameters
    public static Camera newInstance(String param1, String param2) {
        Camera fragment = new Camera();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_camera, container, false);

        // Inflate the layout for this fragment
        imageSleted = view.findViewById(R.id.imageView);
//        textView = findViewById(R.id.btextview);
//        cameraBtn  = findViewById(R.id.camerabtn);
//        galleryBtn = findViewById(R.id.galleryBtn);

//        textView = findViewById(R.id.btextview);
        camerabtn  = view.findViewById(R.id.foodSearch);
        galleryBtn = view.findViewById(R.id.imageGallery);

        camerabtn.setOnClickListener(new View.OnClickListener(){
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

        return view;
    }

    private void askCameraPermissions() {
        if (ContextCompat.checkSelfPermission( getContext(), Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.fromFile(f));
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
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentUri);
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
        ContentResolver c = getActivity().getContentResolver();
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
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
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
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_PERM_CODE);
            }
        }
    }





}