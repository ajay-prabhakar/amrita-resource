package com.chromicle.amritaResource.models;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.google.firebase.auth.FirebaseUser;

public class UserViewModel extends AndroidViewModel {

    private FirebaseUser user;
    private int identifier;

    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public FirebaseUser getUser() {
        return user;
    }

    public int getIdentifier() {
        return identifier;
    }
}
