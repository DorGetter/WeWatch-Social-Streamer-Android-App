package com.example.wewatchapp.managerPack;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wewatchapp.R;
import com.example.wewatchapp.utilitiesPack.UploadThumnailActivity;
import com.example.wewatchapp.utilitiesPack.VideoDetailUpload;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;
import java.util.List;

public class UploadMovie extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Uri videoUri;
    TextView textViewImage;
    String movieCategory;
    String video_title ;
    String  currentuid;
    StorageReference mStrogeref;
    StorageTask mUploadTask;
    DatabaseReference referenceVideos;
    EditText video_description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_movie);

        textViewImage = findViewById(R.id.txtViewSongFileSelected);
        video_description = findViewById(R.id.movies_description);


        referenceVideos = FirebaseDatabase.getInstance().getReference().child("videos");
        mStrogeref      = FirebaseStorage.getInstance().getReference().child("videos");



        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Action");
        categories.add("Adventure");
        categories.add("Sports");
        categories.add("Romantic");
        categories.add("Comedy");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // On selecting a spinner item
        movieCategory = adapterView.getItemAtPosition(i).toString();
        // Showing selected spinner item
        Toast.makeText(adapterView.getContext(), "Selected: " + movieCategory, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    private void uploadFiles() {
        if(videoUri != null){
            Toast.makeText(this, "uploads movies please wait!", Toast.LENGTH_SHORT).show();
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();
            final StorageReference storageReference = mStrogeref.child(video_title + "."+getfileextension(videoUri));

            mUploadTask = storageReference.putFile(videoUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uris) {

                                    String video_url1 = uris.toString();

                                    VideoDetailUpload videoDetailUpload = new VideoDetailUpload("",""
                                            ,"",video_url1
                                            ,video_title, video_description.getText().toString(),movieCategory
                                    );

                                    String uploadId = referenceVideos.push().getKey();
                                    referenceVideos.child(uploadId).setValue(videoDetailUpload);
                                    currentuid = uploadId;
                                    progressDialog.dismiss();
                                    if(currentuid.equals(uploadId))
                                    {
                                        startthunailActivity();
                                    }


                                }
                            });



                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            double progress = (100.0 * taskSnapshot.getBytesTransferred()/ taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");

                        }
                    });
        }else {
            Toast.makeText(this, "No file seleted to uploads", Toast.LENGTH_SHORT).show();
        }
    }

    private String getfileextension(Uri videouri) {

        ContentResolver contentResolver =getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(videouri));

    }

    public void openVideoFiles(View v){

        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("video/*");
        startActivityForResult(i,101);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK && data.getData() != null) {

            videoUri = data.getData();
            String fileNames = getFileName(videoUri);
            textViewImage.setText(fileNames);
            String absolutepathThum = null;
            Cursor cursor;
            int coloum_index_data;
            String[] projection = {MediaStore.MediaColumns.DATA,
                    MediaStore.Video.Media.BUCKET_DISPLAY_NAME, MediaStore.Video.Media._ID
                    , MediaStore.Video.Thumbnails.DATA};
            final String orderby = MediaStore.Video.Media.DEFAULT_SORT_ORDER;
            cursor = UploadMovie.this.getContentResolver().query(videoUri, projection,
                    null,null,orderby);
            coloum_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            while (cursor.moveToNext()) {
                absolutepathThum = cursor.getString(coloum_index_data);
                video_title = FilenameUtils.getBaseName(absolutepathThum);

            }
        }


    }
    private String getFileName(Uri uri) {

        String result = null;
        if(uri.getScheme().equals("content")){

            Cursor cursor = getContentResolver().query(uri,null,null,null,null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
            finally {
                cursor.close();
            }
        }

        if(result == null){

            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if(cut != -1){
                result = result.substring(cut + 1);

            }

        }
        return result;
    }


    public   void  uploadfileToFirebase (View v ){
        if(textViewImage.getText().equals("no video selected")){
            Toast.makeText(this, "Please selected an Video!", Toast.LENGTH_SHORT).show();

        }else {


            if(mUploadTask != null && mUploadTask.isInProgress()){
                Toast.makeText(this, "video upload in allready in progress!", Toast.LENGTH_SHORT).show();

            }else {
                uploadFiles();
            }
        }
    }


    private void startthunailActivity(){
        Intent in = new Intent(UploadMovie.this, UploadThumnailActivity.class);
        in.putExtra("thumnail_name",video_title);
        in.putExtra("currentuid", currentuid);
        startActivity(in);
        Toast.makeText(UploadMovie.this,
                "movies uploaded sucessfully upload movies thumnail", Toast.LENGTH_LONG).show();
    }
}
