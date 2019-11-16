package com.example.android.AmritaResouce.models;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.google.firebase.storage.UploadTask;

public class UploadDocumentViewmodel extends AndroidViewModel {
    private UploadTask DocumentUploadTask;

    public UploadDocumentViewmodel(@NonNull Application application) {
        super(application);
    }

    public UploadTask getDocumentUploadTask() {
        return DocumentUploadTask;
    }

    public void setDocumentUploadTask(UploadTask documentUploadTask) {
        DocumentUploadTask = documentUploadTask;
    }
}
