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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;

public class ECEActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        // Create a list of words
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("SEMESTER 3:","",""));
        words.add(new Word("Applied Electromagnetics", "Semester 3","http://classesatamrita.in/pdf/Applied%20Electromagnetics.pdf"));
        words.add(new Word("Maths Notes", "Semester 3","http://classesatamrita.in/pdf/MathsLinearAlgebra.pdf"));
        words.add(new Word("Digital Circuits", "Semester 3","http://classesatamrita.in/pdf/Fundamentals%20of%20Digital%20Logic%20with%20Verilog%20Design.pdf"));
        words.add(new Word("Digital Circuits Notes", "Semester 3","http://classesatamrita.in/pdf/Digital%20Circuits%20notes.pdf"));
        words.add(new Word("Network Theory", "Semester 3","http://classesatamrita.in/pdf/Fundamental%20of%20Electric%20Elements.pdf"));
        words.add(new Word("Network Theory Notes", "Semester 3","http://classesatamrita.in/pdf/Network%20Theory.pdf"));
        words.add(new Word("Signal Processing", "Semester 3","http://classesatamrita.in/pdf/Signal%20and%20Systems-Simon%20Haykin-Wiley.pdf"));
        words.add(new Word("Signal Processing Notes", "Semester 3","http://classesatamrita.in/pdf/Signal%20Processing.pdf"));


        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(this, words);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView = (ListView) findViewById(R.id.list);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Word word = words.get(position);

                Toast.makeText(getBaseContext(),"Downloading "+word.getMiwokTranslation(), LENGTH_LONG).show();
                String URL=word.getURL();
                if(URL.length()==0){
                    Toast.makeText(getBaseContext(),"from this "+word.getMiwokTranslation().substring(0,10)+" subjects starts", LENGTH_LONG).show();
                }
                else {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(word.getURL())));
                }


            }
        });
    }
}
