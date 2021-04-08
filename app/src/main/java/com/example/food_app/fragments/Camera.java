package com.example.food_app.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.food_app.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Camera#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Camera extends Fragment {

    private static final int AUDIO_PERM_CODE = 1 ;
    private boolean isLocationPremEnabled;
    public static final int PRMISSION_REQUEST_CODE  = 3002;
    private FusedLocationProviderClient mLocationProviderClient;
    //----------------------------------------------------------
    public static final int CAMERA_PERM_CODE = 101;
    public static final int GALLERY_REQUEST_CODE = 105;
    //----------------------------------------------------------

    ImageView imageSleted ;
    TextView textView;
    public String SImage;
    //    Button cameraBtn, galleryBtn;
    String currentPhotoPath;
    ArrayList<String> foodresults = new ArrayList<String>();

    CardView camerabtn, galleryBtn, voiceBtn;


   private SpeechRecognizer mSpeachRecogniser;




    public Camera() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @ra.
     */
    // TODO: Rename and change types and number of parameters
    public static Camera newInstance() {
        Camera fragment = new Camera();
        Bundle args = new Bundle();
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
        imageSleted = view.findViewById(R.id.favImg);
        camerabtn  = view.findViewById(R.id.foodSearch);
        galleryBtn = view.findViewById(R.id.imageGallery);
        voiceBtn = view.findViewById(R.id.speactoText);


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
                // Accessing the gallery.

                //Toast.makeText(CameraView.this, "Gallery button is clicked", Toast.LENGTH_SHORT).show();
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                gallery.setType("image/*");

                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
            }
        });


        voiceBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Accessing the gallery.
                Log.d("TAG", "onClick: "+  "text to voice");


                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO)!=
                        PackageManager.PERMISSION_GRANTED){
                    checkSoundPermission();
                }


//                mSpeachRecogniser = SpeechRecognizer.createSpeechRecognizer(getContext());
                Intent speachIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                speachIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                //getting local lanuguage from phone.
                speachIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                speachIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Search food by Name");


                try {
                    startActivityForResult(speachIntent, AUDIO_PERM_CODE);

                } catch (ActivityNotFoundException e ){
                Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_LONG);

                }


            }
        });


        return view;
    }

    private void checkSoundPermission() {
        // Checking Sound Permissions
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.RECORD_AUDIO}, AUDIO_PERM_CODE);
        }

    }

    private void askCameraPermissions() {
        //checking permission
        if (ContextCompat.checkSelfPermission( getContext(), Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        } else{
            dispatchTakePictureIntent();
        }

    }


    private void getUserPremission() {
        if (isLocationPremEnabled){
            Toast.makeText(getActivity(), "readyMap", Toast.LENGTH_SHORT).show();
        } else {
            // Checking if we got GPS permission
            if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PRMISSION_REQUEST_CODE);
                }

            }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
//                    Log.d("tag", "Base 64 Encoded image"+ SImage);
                    //-----------------------------
//                    buildListData();
                    volleyPost();
                    CustormProgressbar();
//                    getUserPremission();
//
//                    getDeviceLocation();
                    homeActivity();
                    //-----------------------------
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

        if  (requestCode == GALLERY_REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK){
//                Log.d("tag", "url from gallery image");
                // uri to get the image data.
                Uri contentUri  = data.getData();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "." + getFileExten(contentUri);
//                Log.d("tag", "url from gallery image" + contentUri);
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
//                    Log.d("tag", "Base 64 Encoded image"+ SImage);
                        //-----------------------------
//                          buildListData();
                          volleyPost();  // making post request with json data.
                          CustormProgressbar(); // showing progressbar while the data is loading for next activity;
                    //-----------------------------

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }




        switch (requestCode){
            case  AUDIO_PERM_CODE:
                if (resultCode == Activity.RESULT_OK && null != data){
                 //getting the speach data as array
                ArrayList<String> speachText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                Log.d("TAG", "After permission: "+  speachText.get(0));

                //saving search results as array which is send to array
                    if(speachText.get(0) != null ){
                        foodresults.add(speachText.get(0));
                        //starting home activity.
                        homeActivity();
                    }
                }
                break;
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


    public void volleyPost(){
        /*
        * Making post request to foodapi and sending the image as base64 string
        * getting the results and saving it to array which is send to other home activity.
        * */
        String postUrl = "https://api.foodai.org/v1/classify";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JSONObject postData = new JSONObject();
        try {
            postData.put("image_url", "data:image/jpeg;base64," + SImage);
            postData.put("num_tag", "2");
            postData.put("qid", "12321");
            postData.put("api_key", "a0f21f3a072c4c726a0101ec6c76fa0d1db9dadf");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                System.out.println(response);

                try {

                    // Getting whole json object
                    JSONArray jsonArray = response.getJSONArray("food_results");
//                    Log.d("tag", "testing" + jsonArray);

                    for (int i= 0; i < 1  ; i++ ){

                        JSONArray  foodList = jsonArray.getJSONArray(i);

                        Log.d("tag", "testing" + foodList.getString(i));
                        foodresults.add(foodList.getString(i));
                    }

                    homeActivity();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                homeActivity();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }


    void homeActivity(){
        saveData();
        Fragment fragment = Home.newInstance();
        Bundle argument = new Bundle();
//        argument.putString("foodResult",foodresults.get(0) );

        // default value to deal with null return
        argument.putString("foodResult","kebab" );


        fragment.setArguments(argument);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_layout, fragment).commit();



    };

    public void CustormProgressbar(){
        // custom loading progress bar.
        final load_dialog LoadingDialog = new load_dialog(getActivity());
        LoadingDialog.startLoadingDialog();
        Handler handler = new Handler();
        handler.postDelayed(new  Runnable() {
            @Override
            public void run() {
                LoadingDialog.dismissDialog();
            }
        } ,5000);//close the progressbar after this much delay;
    }






    private void getDeviceLocation(){
        Log.d("TAG", "getDeviceLocation: Getting device location");
        mLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        try {
            if(isLocationPremEnabled){
                Task location = mLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener(){
                    @Override
                    public void onComplete(@NonNull Task task){
                        if(task.isSuccessful()){
                            Log.d("TAG", "onComplete: found location");
                            Location currentLocation = (Location) task.getResult();
                            Log.d("TAG", "current user location: " + currentLocation );
                            Double lng = currentLocation.getLatitude();
                            Double lat = currentLocation.getLongitude();
                            Log.d("TAG", "current user location: " + lat +  lng);


                        }else{
                            Log.d("TAG", "onComplete: Current Location is null");
                        }
                    }
                });
            }
        }
        catch(SecurityException e){
            Log.e("TAG","getDeviceLocation: SecurityExcetion" + e.getMessage() );
        }
    }




    public void saveData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mySharedPrefers", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", foodresults.get(0));
        editor.commit();
        Toast.makeText(getActivity(), "Data saved", Toast.LENGTH_SHORT).show();

    }

}