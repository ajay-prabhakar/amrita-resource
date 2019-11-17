package com.example.android.AmritaResouce.activies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import com.example.android.AmritaResouce.R;
import com.example.android.AmritaResouce.models.SubjectModel;
import com.example.android.AmritaResouce.models.UploadDocumentModel;
import com.example.android.AmritaResouce.models.UploadDocumentViewmodel;
import com.example.android.AmritaResouce.models.UserViewModel;
import com.example.android.AmritaResouce.utils.FireStoreQueryLiveData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UploadActivity extends AppCompatActivity {

    private static final int RC_SELECT_DOCUMENT = 100;

    private UploadDocumentViewmodel viewModel;
    private UserViewModel userViewModel;
    private UploadTask uploadTask;
    private FirebaseUser userof;

    private FirebaseFirestore mDb;
    private CollectionReference mCollectionReference;
    private FirebaseStorage mStorage;
    private StorageReference mRef;
    private StorageReference child;

    private List<String> branches = new ArrayList<>(new ArrayList<>(Arrays.asList("Please Select")));
    private List<String> subjects = new ArrayList<>(new ArrayList<>(Arrays.asList("Please Select")));
    private List<String> sems = new ArrayList<>(new ArrayList<>(Arrays.asList("Please Select")));

    private String userName = "ANONYMOUS";
    private String selectedSem;
    private String selectedBranch;
    private String selectedSub;
    private Uri downloadUri;
    private String name = "";
    private Uri selectedDocument;

    private Spinner spinnerBranch;
    private Spinner spinnerSem;
    private Spinner spinnerSub;
    TextView uploadDocumentName;
    Button btnUpload;
    Button btnFetchSubjects;
    Button btnSelectDocument;
    TextInputLayout inputName;
    TextInputEditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        viewModel = ViewModelProviders.of(this).get(UploadDocumentViewmodel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        uploadTask = viewModel.getDocumentUploadTask();

        layoutsInit();
        populateUI();

        btnFetchSubjects.setOnClickListener(new fetchRelatedSubjects());
        btnSelectDocument.setOnClickListener(new SelectDocument());
        btnUpload.setOnClickListener(new UploadDocument());
    }

    private void layoutsInit() {
        spinnerBranch = findViewById(R.id.spinner_upload_document_branch);
        spinnerSem = findViewById(R.id.spinner_upload_document_sem);
        spinnerSub = findViewById(R.id.spinner_upload_document_subject);
        uploadDocumentName = findViewById(R.id.tv_upload_document_preview_document);
        btnUpload = findViewById(R.id.btn_upload_document_upload);
        btnFetchSubjects = findViewById(R.id.btn_upload_document_fetch_subjects);
        btnSelectDocument = findViewById(R.id.btn_upload_document_select);
        etName = findViewById(R.id.et_upload_document_item_name);
        inputName = findViewById(R.id.text_input_upload_document_item_name);
    }

    private void populateUI() {
        initDatabase();
        setUpSpinners();
        uploadContinueIfAvailable();
        btnUpload.setEnabled(false);
    }

    private void uploadContinueIfAvailable() {
        if (uploadTask != null) {
            uploadTask
                    .addOnSuccessListener(
                            taskSnapshot -> {
                                getDownloadUrl(taskSnapshot);
                                viewModel.setDocumentUploadTask(null);
                            })
                    .addOnFailureListener(
                            e -> {
                                Toast.makeText(
                                                UploadActivity.this,
                                                "Error while uploading image in database",
                                                Toast.LENGTH_SHORT)
                                        .show();
                                Toast.makeText(
                                                UploadActivity.this, "Failed due to " + e.getMessage(), Toast.LENGTH_SHORT)
                                        .show();
                                viewModel.setDocumentUploadTask(null);
                                btnUpload.setEnabled(false);
                            })
                    .addOnProgressListener(
                            taskSnapshot -> {
                                long progress =
                                        (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                Toast.makeText(UploadActivity.this, "Progress : " + progress, Toast.LENGTH_SHORT)
                                        .show();
                            });
        }
    }

    private void getDownloadUrl(UploadTask.TaskSnapshot taskSnapshot) {
        taskSnapshot
                .getStorage()
                .getDownloadUrl()
                .addOnSuccessListener(
                        uri -> {
                            downloadUri = uri;
                            uploadDocumentName.setText("Document is Uploaded!");
                            btnUpload.setEnabled(true);
                        })
                .addOnFailureListener(
                        e -> {
                            downloadUri = null;
                            Toast.makeText(UploadActivity.this, "Download URI not found", Toast.LENGTH_SHORT)
                                    .show();
                            btnUpload.setEnabled(false);
                        });
    }

    private void setUpSpinners() {
        branches = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.branches)));
        sems = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.sems)));

        selectedSem = sems.get(0);
        selectedBranch = branches.get(0);

        setUpBranchSpinners();
        setUpSemSpinners();
        setUpSubjectSpinners();
    }

    private void setUpSubjectSpinners() {
        ArrayAdapter<String> adapterSubject =
                new ArrayAdapter<>(this, R.layout.spinner_simple, subjects);
        adapterSubject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSub.setAdapter(adapterSubject);
        spinnerSub.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedSub = subjects.get(position);
                        Toast.makeText(
                                        UploadActivity.this,
                                        "Selected Subject : " + subjects.get(position),
                                        Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });
    }

    private void setUpSemSpinners() {
        ArrayAdapter<String> adapterSem = new ArrayAdapter<>(this, R.layout.spinner_simple, sems);
        adapterSem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSem.setAdapter(adapterSem);

        spinnerSem.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedSem = sems.get(position);
                        Toast.makeText(
                                        UploadActivity.this, "Selcetd Sem :" + sems.get(position), Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });
    }

    private void setUpBranchSpinners() {
        ArrayAdapter<String> branchAdapter =
                new ArrayAdapter<>(this, R.layout.spinner_simple, branches);
        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerBranch.setAdapter(branchAdapter);
        spinnerBranch.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedBranch = branches.get(position);
                        Toast.makeText(
                                        UploadActivity.this,
                                        "Selected Branch" + branches.get(position),
                                        Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });
    }

    private void initDatabase() {
        mDb = FirebaseFirestore.getInstance();
        mCollectionReference = mDb.collection("Subjects");
        mStorage = FirebaseStorage.getInstance();
        mRef = mStorage.getReference();
    }

    private class fetchRelatedSubjects implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (selectedBranch == null || selectedSem == null) {
                Toast.makeText(UploadActivity.this, "Branch or Sem is invalid", Toast.LENGTH_SHORT).show();
                return;
            }
            setUpLiveData();
        }
    }

    private void setUpLiveData() {
        Query query =
                mCollectionReference
                        .whereEqualTo("branch", selectedBranch)
                        .whereEqualTo("sem", selectedSem);
        FireStoreQueryLiveData SubjectliveData = new FireStoreQueryLiveData(query);
        SubjectliveData.observe(
                this,
                queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        Toast.makeText(UploadActivity.this, "Subject is not available", Toast.LENGTH_SHORT)
                                .show();
                        return;
                    }
                    List<SubjectModel> subjectModels = queryDocumentSnapshots.toObjects(SubjectModel.class);
                    subjects = new ArrayList<>();
                    for (int i = 0; i < subjectModels.size(); i++) {
                        subjects.add(subjectModels.get(i).getSubject());
                    }
                    setUpSubjectSpinners();
                });
    }

    private class SelectDocument implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            if (!validateName()) {
                Toast.makeText(UploadActivity.this, "Name not selected", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
            getIntent.setType("*/*");

            Intent pickIntent = new Intent(Intent.ACTION_PICK);
            pickIntent.setDataAndType(
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "*/*");

            Intent chooserIntent = Intent.createChooser(getIntent, "Select Documents");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

            startActivityForResult(chooserIntent, RC_SELECT_DOCUMENT);
        }
    }

    private boolean validateSubject() {
        if (selectedSub.length() == 0) {
            return false;
        } else return !selectedSub.equals("Please Select");
    }

    private boolean validateName() {
        name = etName.getText().toString().trim();
        if (name.length() == 0) {
            inputName.setError("Please Input Name");
            return false;
        } else {
            inputName.setErrorEnabled(false);
            return true;
        }
    }

    private class UploadDocument implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (downloadUri == null | name.length() == 0 | selectedSub.length() == 0) {
                Toast.makeText(UploadActivity.this, "Please Fill in details", Toast.LENGTH_SHORT).show();
                return;
            } else {
                UploadDocumentModel model = new UploadDocumentModel();
                model.setBranch(selectedBranch);
                model.setSem(selectedSem);
                model.setSubject(selectedSub);
                model.setTimestamp(System.currentTimeMillis());
                model.setUrl(downloadUri.toString());
                model.setTitle(name);
                model.setUserName(userName);
                startUploadInDatabase(model);
                // TODO handle life cycle changes in spinner and variables
            }
        }
    }

    private void startUploadInDatabase(UploadDocumentModel model) {
        CollectionReference uploads = mDb.collection("Uploads");
        uploads
                .add(model)
                .addOnSuccessListener(
                        new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(UploadActivity.this, "Uploaded Just Now", Toast.LENGTH_SHORT).show();
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(
                                                UploadActivity.this,
                                                "something error in uploading" + e.toString(),
                                                Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SELECT_DOCUMENT) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    selectedDocument = data.getData();
                    if (getContentResolver().getType(selectedDocument) != null) {
                        Log.e("EEE", getContentResolver().getType(selectedDocument));
                        startUploadingFile(selectedDocument);
                    }
                }
            }
        }
    }

    private void startUploadingFile(Uri selectedDocument) {
        Snackbar snackbar =
                Snackbar.make(findViewById(R.id.parent_view), "Uploading...", Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
        child =
                mRef.child(
                        "images/"
                                + userName
                                + "/items/"
                                + name
                                + name
                                + name
                                + "169961"
                                + selectedDocument.getLastPathSegment());
        uploadTask = child.putFile(selectedDocument);
        viewModel.setDocumentUploadTask(uploadTask);
        uploadTask
                .addOnSuccessListener(
                        taskSnapshot -> {
                            getDownloadUrl(taskSnapshot);
                            viewModel.setDocumentUploadTask(null);
                            snackbar.dismiss();
                        })
                .addOnFailureListener(
                        e -> {
                            Toast.makeText(
                                            UploadActivity.this,
                                            "Error while uploading image in database" + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                            viewModel.setDocumentUploadTask(null);
                            btnUpload.setEnabled(false);
                        })
                .addOnProgressListener(
                        taskSnapshot -> {
                            long progress =
                                    (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            Toast.makeText(UploadActivity.this, "Progress : " + progress, Toast.LENGTH_SHORT)
                                    .show();
                            snackbar.setText("Uploading... " + progress + "% Uploaded..");
                        });
    }
}
