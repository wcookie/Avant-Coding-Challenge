package com.parse.avantmobilechallenge;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.avantmobilechallenge.R;

public class ViewDocument extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_document);
        Bundle extras = getIntent().getExtras();
        String objectID="";
        if (extras != null) {
             objectID = extras.getString("objectID");
        }
        else
        {
            //ERROR
            Toast.makeText(ViewDocument.this, "ID does not exist",
                    Toast.LENGTH_LONG).show();
        }
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Document");
        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    String type = object.getString("Document_Type");
                    //setting type of document
                    TextView textView = (TextView) findViewById(R.id.passedIn);
                    textView.setText(type);
                    //getting picture
                    ParseFile picture = object.getParseFile("picture");
                    final ParseImageView imageView=(ParseImageView)findViewById(R.id.docPicture);
                    //setting parseimage to picture
                    imageView.setParseFile(picture);
                    imageView.loadInBackground(new GetDataCallback() {
                        public void done(byte[] data, ParseException e) {
                            // We loaded the image

                        }
                    });
                } else {
                    // ERROR
                    Toast.makeText(ViewDocument.this, e.toString(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public void viewMore(View view){
        Intent intent = new Intent(ViewDocument.this, Documents.class);
        startActivity(intent);
    }

}
