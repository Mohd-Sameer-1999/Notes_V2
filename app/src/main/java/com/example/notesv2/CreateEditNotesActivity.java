package com.example.notesv2;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class CreateEditNotesActivity extends AppCompatActivity {

    public static final String Extra_ID = "com.example.notesv2.Extra_ID";
    public static final String Extra_Title = "com.example.notesv2.Extra_Title";
    public static final String Extra_Description = "com.example.notesv2.Extra_Description";

    private EditText titleEditText;
    private EditText descriptionEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notes);

        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);


        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(Extra_ID)){
            setTitle("Edit Note");
            titleEditText.setText(intent.getStringExtra(Extra_Title));
            descriptionEditText.setText(intent.getStringExtra(Extra_Description));
        }else{
            setTitle("Add Note");
        }


    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        long id = getIntent().getLongExtra(Extra_ID, -1);
//        Intent intent = new Intent();
//        intent.putExtra(Extra_ID, id);
//        setResult(Activity.RESULT_OK,intent);
//        finish();
//    }

    //    private void checkPermission(int permissionCode){
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//
//            if (permissionCode == PERMISSION_CODE){
//                if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);
//                }
//            }
//
//            else if(permissionCode == MY_CAMERA_PERMISSION_CODE){
//                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
//                    requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_CODE);
//                }
//            }
//
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//        if(requestCode == PERMISSION_CODE){
//            openGallery();
//        }
//        else if (requestCode == MY_CAMERA_PERMISSION_CODE){
//            openCamera();
//        }
//
//    }

//    private void openGallery(){
//        Intent galleryIntent = new Intent(
//                Intent.ACTION_PICK,
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//
//        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
//    }
//
//    private void openCamera(){
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(cameraIntent, CAMERA_REQUEST);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
//
//            Uri selectedImage = data.getData();
//
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
//                image.setImageBitmap(bitmap);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//        }
//        else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            image.setImageBitmap(photo);
//        }
//
//
//    }

//    private void addViews(){
//        image = new ImageView(getApplicationContext());
//        text = new EditText(getApplicationContext());
//        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        textParams.setMargins(16, 16, 16, 16 );
//        textParams.gravity = 30;
//        image.setLayoutParams(imageParams);
//        text.setLayoutParams(textParams);
//        text.setBackground(null);
//
//        LinearLayout layout = findViewById(R.id.linearLayout);
//        layout.addView(image);
//        layout.addView(text);
//    }

    public void saveNote(){

        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        if(title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this, "Please insert title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(Extra_Title, title);
        data.putExtra(Extra_Description, description);

        long id = getIntent().getLongExtra(Extra_ID, -1);
        if(id != -1){
            data.putExtra(Extra_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.save_note) {
            saveNote();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

//    BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
//        @RequiresApi(api = Build.VERSION_CODES.P)
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//            if (item.getItemId() == R.id.insert_image) {
//                addViews();
//                checkPermission(PERMISSION_CODE);
//
//            }
//            else if (item.getItemId() == R.id.take_a_picture){
//                addViews();
//                checkPermission(MY_CAMERA_PERMISSION_CODE);
//            }
//            else if (item.getItemId() == R.id.bullet_points){
//                descriptionEditText.getText().setSpan(new BulletSpan(10, Color.GREEN, 10),descriptionEditText.getSelectionStart(), descriptionEditText.getSelectionEnd(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//            }
//
//
//            return true;
//        }
//    };




}