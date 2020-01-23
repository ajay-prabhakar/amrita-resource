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
package com.chromicle.amritaResource.activies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chromicle.amritaResource.R;
import com.chromicle.amritaResource.adapter.UploadDocumentAdapter;
import com.chromicle.amritaResource.clicklistners.RecyclerItemClickListener;
import com.chromicle.amritaResource.models.UploadDocumentModel;
import com.chromicle.amritaResource.webViewActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.robertlevonyan.views.customfloatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LICENSES_HTML_PATH = "file:///android_asset/about_app.html";
    public static final String OPEN_URL = "url";

    private FloatingActionButton btnUpload;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private List<UploadDocumentModel> documentList;
    private UploadDocumentAdapter adapter;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Amrita Resource");
//        setSupportActionBar(toolbar);

        layoutsInit();
        forRecyclerView();
        fetchDocuments();

        btnUpload.setOnClickListener(new openUploadActivity());
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        MainActivity.this,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View v, int position) {
                                UploadDocumentModel doc = new UploadDocumentModel();
                                doc = documentList.get(position);
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(doc.getUrl())));
                            }
                        }));
    }

    private void fetchDocuments() {
        db = FirebaseFirestore.getInstance();

        db.collection("Uploads")
                .get()
                .addOnSuccessListener(
                        new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                progressBar.setVisibility(View.GONE);

                                if (!queryDocumentSnapshots.isEmpty()) {

                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                                    for (DocumentSnapshot d : list) {

                                        UploadDocumentModel p = d.toObject(UploadDocumentModel.class);
                                        documentList.add(p);
                                    }

                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
    }

    private void forRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        documentList = new ArrayList<>();
        adapter = new UploadDocumentAdapter(this, documentList);
        recyclerView.setAdapter(adapter);
    }

    private void layoutsInit() {
        btnUpload = findViewById(R.id.fab_home_frag_upload);
        recyclerView = findViewById(R.id.recycler_home_frag_main);
        progressBar = findViewById(R.id.progressbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.menuAboutAuthor:
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://chromicle.github.io/")));
//                break;
//
//            case R.id.menuAboutApp:
//                Intent intent = new Intent(this, webViewActivity.class);
//                intent.putExtra(OPEN_URL, LICENSES_HTML_PATH);
//                startActivity(intent);
//                break;
//
//            case R.id.aums:
//                startActivity(
//                        new Intent(
//                                Intent.ACTION_VIEW,
//                                Uri.parse(
//                                        "https://aumsamstudents.amrita.edu:8443/cas/login?service=https%3A%2F%2Faumsamstudents.amrita.edu%3A8443%2Faums%2FJsp%2FCore_Common%2FindexIPad.jsp%3Ftask%3Doff")));
//        }
//        return true;
//    }

    private class openUploadActivity implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, UploadActivity.class);
            startActivity(intent);
        }
    }
}
