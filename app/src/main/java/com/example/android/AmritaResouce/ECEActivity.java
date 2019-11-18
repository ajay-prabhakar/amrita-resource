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

import static android.widget.Toast.LENGTH_LONG;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import com.example.android.AmritaResouce.utils.AlertDialogUtil;
import com.example.android.AmritaResouce.utils.NetworkUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class ECEActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private WordAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        // Create a list of words
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("SEMESTER 3:", "", ""));
        words.add(
                new Word(
                        "Applied Electromagnetics",
                        "Semester 3",
                        "http://classesatamrita.in/pdf/Applied%20Electromagnetics.pdf"));
        words.add(
                new Word(
                        "Maths Notes", "Semester 3", "http://classesatamrita.in/pdf/MathsLinearAlgebra.pdf"));
        words.add(
                new Word(
                        "Digital Circuits",
                        "Semester 3",
                        "http://classesatamrita.in/pdf/Fundamentals%20of%20Digital%20Logic%20with%20Verilog%20Design.pdf"));
        words.add(
                new Word(
                        "Digital Circuits Notes",
                        "Semester 3",
                        "http://classesatamrita.in/pdf/Digital%20Circuits%20notes.pdf"));
        words.add(
                new Word(
                        "Network Theory",
                        "Semester 3",
                        "http://classesatamrita.in/pdf/Fundamental%20of%20Electric%20Elements.pdf"));
        words.add(
                new Word(
                        "Network Theory Notes",
                        "Semester 3",
                        "http://classesatamrita.in/pdf/Network%20Theory.pdf"));
        words.add(
                new Word(
                        "Signal Processing",
                        "Semester 3",
                        "http://classesatamrita.in/pdf/Signal%20and%20Systems-Simon%20Haykin-Wiley.pdf"));
        words.add(
                new Word(
                        "Signal Processing Notes",
                        "Semester 3",
                        "http://classesatamrita.in/pdf/Signal%20Processing.pdf"));

        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        adapter = new WordAdapter(this, words);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        listView = (ListView) findViewById(R.id.list);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Word word = words.get(position);

                        if (!NetworkUtil.isNetworkConnected(ECEActivity.this)) {
                            AlertDialogUtil.showAlertDialog(
                                    ECEActivity.this,
                                    getString(R.string.txt_no_internet_connection),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    });

                            return;
                        }

                        Toast.makeText(getBaseContext(), "Downloading " + word.getSubjectName(), LENGTH_LONG)
                                .show();
                        String URL = word.getURL();
                        if (URL.length() == 0) {
                            Toast.makeText(
                                            getBaseContext(),
                                            "from this " + word.getSubjectName().substring(0, 10) + " subjects starts",
                                            LENGTH_LONG)
                                    .show();
                        } else {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(word.getURL())));
                        }
                    }
                });

        setUpFab();
    }

    private void setUpFab() {
        final FloatingActionButton fabScrollUp =
                (FloatingActionButton) findViewById(R.id.fab_scroll_up);
        fabScrollUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listView.setSelection(0);
                        fabScrollUp.hide();
                    }
                });
        fabScrollUp.hide();
        listView.setOnScrollListener(
                new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {}

                    @Override
                    public void onScroll(
                            AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                        int lastItem = firstVisibleItem + visibleItemCount;
                        if (lastItem == totalItemCount && firstVisibleItem > 0) {
                            fabScrollUp.show();
                        } else {
                            fabScrollUp.hide();
                        }
                    }
                });
    }

    // to open searchview
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.search(s.trim());
        listView.invalidate();
        return false;
    }
}
