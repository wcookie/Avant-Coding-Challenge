package com.parse.avantmobilechallenge;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;
import com.parse.avantmobilechallenge.R;

public class Documents extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);
        //Creating ParseQueryAdapter
        ParseQueryAdapter<ParseObject> adapter = new ParseQueryAdapter<ParseObject>(this, "Document");
        adapter.setTextKey("Document_Type");
        adapter.setImageKey("picture");
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        //For when an item is clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ParseObject object = (ParseObject) listView.getItemAtPosition(position);
                //Passing objectID
                Intent intent = new Intent(Documents.this, ViewDocument.class);
                intent.putExtra("objectID",object.getObjectId());
                startActivity(intent);
            }
        });
    }


    public void returnHome(View view){
        Intent intent = new Intent(Documents.this, MainActivity.class);
        startActivity(intent);
    }

}
