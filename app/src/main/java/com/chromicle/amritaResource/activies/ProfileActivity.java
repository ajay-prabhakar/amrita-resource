package com.chromicle.amritaResource.activies;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.chromicle.amritaResource.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.profileName)
    TextView profileName;

    @BindView(R.id.profileMail)
    TextView profileMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        profileName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        profileMail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }
}
