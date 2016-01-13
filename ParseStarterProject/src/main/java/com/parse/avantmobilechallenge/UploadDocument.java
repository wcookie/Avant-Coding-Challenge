package com.parse.avantmobilechallenge;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.avantmobilechallenge.R;

import java.io.ByteArrayOutputStream;


public class UploadDocument extends Activity {
    private String choice;
    private Bitmap rotatedBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_document);
        //Setting up the select Spinner
        Spinner select = (Spinner) findViewById(R.id.SelectType);
        String[] choices = new String[]{"W2", "Voided Check", "Utility Bill"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, choices);
        select.setAdapter(adapter);
        select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //making the global variable choice the choice
                choice = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Auto generated stub thanks to Android Studio
            }
        });
        //Getting photo
        String photoPath = "/storage/emulated/0/Android/Data/com.parse.starter/files/pic.jpg";
        ImageView myPhoto = (ImageView) findViewById(R.id.myPhoto);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);
        //Set image view to saved image
        myPhoto.setImageBitmap(bitmap);
    }

    public void rotatePhoto(View view) {
        Toast.makeText(UploadDocument.this, "Image Rotated",
                Toast.LENGTH_LONG).show();
        ImageView myPhoto = (ImageView) findViewById(R.id.myPhoto);
        Matrix matrix = new Matrix();
        myPhoto.buildDrawingCache();
        Bitmap bmap = myPhoto.getDrawingCache();
        matrix.postRotate(90);

        rotatedBitmap = Bitmap.createBitmap(bmap, 0, 0, bmap.getWidth(), bmap.getHeight(), matrix, true);
        myPhoto.setImageBitmap(rotatedBitmap);

    }

    public void submitButton(View view) {
        //final because of the saving in background

        final ParseObject Document = new ParseObject("Document");
        Document.put("User", ParseUser.getCurrentUser());
        Document.put("Document_Type", choice);
        EditText text = (EditText) findViewById(R.id.customerID);
        Document.put("Customer_ID",text.getText().toString());
        //Get current photo, taking into account rotation
        ImageView myPhoto = (ImageView) findViewById(R.id.myPhoto);
        myPhoto.buildDrawingCache();
        Bitmap bmap;
        //Check for rotation
        if (rotatedBitmap==null){
            //get current photo if not rotated
            bmap = myPhoto.getDrawingCache();
        }
        else{
            // use rotatedBitmap if it is
            bmap=rotatedBitmap;
        }
        //Convert bitmap to byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        //save file
        final ParseFile file = new ParseFile("picture.jpg",byteArray);
        file.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                // Handle success or failure here ...
                if (e == null) {
                    // data has the bytes for the resume
                    Toast.makeText(UploadDocument.this, "File is saving",
                            Toast.LENGTH_LONG).show();
                    Document.put("picture", file);
                    //save Document
                    Document.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                //Go back to Main Activity
                                Toast.makeText(
                                        UploadDocument.this,
                                        "Avant will get back to you within 24 Hours",
                                        Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(UploadDocument.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                //We have an error
                                Toast.makeText(UploadDocument.this, e.toString(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } else {
                    // We have an error
                    Toast.makeText(UploadDocument.this, e.toString(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }


}
