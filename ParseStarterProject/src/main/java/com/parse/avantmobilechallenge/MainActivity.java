/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.avantmobilechallenge;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
   private ParseUser currentUser;
    @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
   // setContentView(R.layout.activity_main);
     currentUser = ParseUser.getCurrentUser();
    if(currentUser.getUsername()==null)
    {
        ParseLoginBuilder builder = new ParseLoginBuilder(MainActivity.this);
        startActivityForResult(builder.build(), 0);
    }
    else
    {

    }

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onStart(){
    super.onStart();

      currentUser = ParseUser.getCurrentUser();

      setContentView(R.layout.activity_main);

      TextView textView = (TextView)findViewById(R.id.textHere);
      if(currentUser!=null) {

          textView.setText("Welcome "+ currentUser.getString("name"));
      }
      else
      {
          textView.setText("There is an error");
      }
  }

    public void initiateCamera(View view){
        Intent intent = new Intent(MainActivity.this, CameraActivity.class);
        startActivity(intent);
  }
    public void viewDocuments(View view){
        Intent intent = new Intent(MainActivity.this, Documents.class);
        startActivity(intent);
    }
    public void logOut(View view){
        ParseUser.logOut();
        ParseLoginBuilder builder = new ParseLoginBuilder(MainActivity.this);
        startActivityForResult(builder.build(), 0);
    }


}



