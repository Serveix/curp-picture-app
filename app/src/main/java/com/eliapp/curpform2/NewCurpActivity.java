package com.eliapp.curpform2;

import com.eliapp.curpform2.models.Curp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewCurpActivity extends AppCompatActivity {
    private final int RESULT_LOAD_IMG = 1;
    private final int PICTURE_REQUEST_CODE = 2;

    EditText formInputBirthdayDay, formInputBirthdayMonth, formInputBirthdayYear, formInputName, formInputFatherLastName, formInputMotherLastName;
    RadioGroup formRadioGroupGender;
    Spinner formSpinnerState;
    ImageView formImageView;
    String imgpath;
    Uri uriImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_curp);
        formInputBirthdayDay = findViewById(R.id.inputBirthdayDay);
        formInputBirthdayMonth = findViewById(R.id.inputBirthdayMonth);
        formInputBirthdayYear = findViewById(R.id.inputBirthdayYear);
        formInputName = findViewById(R.id.inputName);
        formInputFatherLastName = findViewById(R.id.inputFatherLastName);
        formInputMotherLastName = findViewById(R.id.inputMotherLastName);
        formRadioGroupGender = findViewById(R.id.FormRadioGroupGender);
        formSpinnerState = findViewById(R.id.spinnerState);
        formImageView = findViewById(R.id.imageViewForm);

    }

    public void formBtnClicked(View view) {
        try{
            int day = Integer.parseInt(((EditText)findViewById(R.id.inputBirthdayDay)).getText().toString());
            int month = Integer.parseInt(((EditText)findViewById(R.id.inputBirthdayMonth)).getText().toString());
            int year = Integer.parseInt(((EditText)findViewById(R.id.inputBirthdayYear)).getText().toString());


            String name = ((EditText)findViewById(R.id.inputName)).getText().toString();
            String fLastName = ((EditText)findViewById(R.id.inputFatherLastName)).getText().toString();
            String mLastName = ((EditText)findViewById(R.id.inputMotherLastName)).getText().toString();
            String gender =((RadioButton)findViewById(((RadioGroup)findViewById(R.id.FormRadioGroupGender)).getCheckedRadioButtonId())).getText().toString();
            String state = ((Spinner)findViewById(R.id.spinnerState)).getSelectedItem().toString();
            String imgpath = this.imgpath;

            Curp curp = new Curp(name, fLastName, mLastName, gender, day, month, year, state, imgpath);

            Intent intent = new Intent(NewCurpActivity.this, CurpInfoActivity.class);
            intent.putExtra("curp", curp);
            startActivity(intent);

        }
        catch (Exception e){
            AlertDialog alertDialog = new AlertDialog.Builder(NewCurpActivity.this).create();
            alertDialog.setTitle(getString(R.string.title_error));
            alertDialog.setMessage(getString(R.string.body_error));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok_label),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }

    }

    public void saveCurpButton(View view) {
        try{
            int day = Integer.parseInt((formInputBirthdayDay).getText().toString());
            int month = Integer.parseInt((formInputBirthdayMonth).getText().toString());
            int year = Integer.parseInt((formInputBirthdayYear).getText().toString());


            String name = (formInputName).getText().toString();
            String fLastName = (formInputFatherLastName).getText().toString();
            String mLastName = (formInputMotherLastName).getText().toString();
            String gender =((RadioButton)findViewById((formRadioGroupGender).getCheckedRadioButtonId())).getText().toString();
            String state = (formSpinnerState).getSelectedItem().toString();
            String path = this.imgpath;


            Curp curp = new Curp(name, fLastName, mLastName, gender, day, month, year, state, path);

            curp.save(this);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(R.string.save_curp_title);
            alertDialogBuilder.setMessage(R.string.save_curp_body).setCancelable(true);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        catch (Exception e){
            AlertDialog alertDialog = new AlertDialog.Builder(NewCurpActivity.this).create();
            alertDialog.setTitle(getString(R.string.title_error));
            alertDialog.setMessage(getString(R.string.body_error));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok_label),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuBack:
                finish();
                return true;
            case R.id.menuListView:
                Intent intent = new Intent(NewCurpActivity.this, RecyclerViewActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getImageFromGallery(View view) throws IOException {

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
                uriImage = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriImage);
                startActivityForResult(takePictureIntent, RESULT_LOAD_IMG);
            }
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK){
            imgpath = uriImage.toString();
            formImageView.setImageURI(uriImage);

        }
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


}
