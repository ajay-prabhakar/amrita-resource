/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.AmritaResouce;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private static final String LICENSES_HTML_PATH = "file:///android_asset/about_app.html";
    public static final String OPEN_URL = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        // Find the View that shows the numbers category
        TextView numbers = findViewById(R.id.numbers);


        //getting the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);

        //setting the title
        toolbar.setTitle("Amrita Resource");

        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);

        //remove icon from toolbar
        /*getSupportActionBar().setIcon(R.mipmap.ic_launcher);*/


        // Set a click listener on that View
        numbers.setOnClickListener(new OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link CSEActivity}
                Intent numbersIntent = new Intent(MainActivity.this, CSEActivity.class);

                // Start the new activity
                startActivity(numbersIntent);
            }
        });

        // Find the View that shows the family category
        TextView family = findViewById(R.id.family);

        // Set a click listener on that View
        family.setOnClickListener(new OnClickListener() {
            // The code in this method will be executed when the family category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link ECEActivity}
                Intent familyIntent = new Intent(MainActivity.this, ECEActivity.class);

                // Start the new activity
                startActivity(familyIntent);
            }
        });

        // Find the View that shows the colors category
        TextView colors = findViewById(R.id.colors);

        // Set a click listener on that View
        colors.setOnClickListener(new OnClickListener() {
            // The code in this method will be executed when the colors category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link EEEActivity}
                Intent colorsIntent = new Intent(MainActivity.this, EEEActivity.class);

                // Start the new activity
                startActivity(colorsIntent);
            }
        });

        // Find the View that shows the phrases category
        TextView phrases = findViewById(R.id.phrases);

        // Set a click listener on that View
        phrases.setOnClickListener(new OnClickListener() {
            // The code in this method will be executed when the phrases category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link MECActivity}
                Intent phrasesIntent = new Intent(MainActivity.this, MECActivity.class);

                // Start the new activity
                startActivity(phrasesIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.menuAboutAuthor:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://chromicle.github.io/")));
                break;

            case R.id.menuAboutApp:
                Intent intent = new Intent(this, webViewActivity.class);
                intent.putExtra(OPEN_URL, LICENSES_HTML_PATH);
                startActivity(intent);
                break;

            case R.id.aums:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://aumsamstudents.amrita.edu:8443/cas/login?service=https%3A%2F%2Faumsamstudents.amrita.edu%3A8443%2Faums%2FJsp%2FCore_Common%2FindexIPad.jsp%3Ftask%3Doff")));


        }
        return true;
    }
}
