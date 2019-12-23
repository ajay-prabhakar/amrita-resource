package com.chromicle.amritaResource;

import android.app.Application;
import android.content.Context;
import org.acra.ACRA;
import org.acra.annotation.AcraCore;
import org.acra.annotation.AcraMailSender;
import org.acra.config.CoreConfigurationBuilder;
import org.acra.config.MailSenderConfigurationBuilder;
import org.acra.config.ToastConfigurationBuilder;
import org.acra.data.StringFormat;

@AcraMailSender(mailTo = MainApplication.EMAIL_TO)
@AcraCore(buildConfigClass = BuildConfig.class)
public class MainApplication extends Application {
    public static final String EMAIL_TO =
            "email@provider.bla"; // replace this value with your own(owner of the app) email id

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        CoreConfigurationBuilder builder = new CoreConfigurationBuilder(this);
        builder.setBuildConfigClass(BuildConfig.class).setReportFormat(StringFormat.JSON);

        builder
                .getPluginConfigurationBuilder(MailSenderConfigurationBuilder.class)
                .setEnabled(true)
                .setMailTo(EMAIL_TO)
                .setSubject("Crash occurred on user device.");
        builder
                .getPluginConfigurationBuilder(ToastConfigurationBuilder.class)
                .setResText(R.string.crash_toast_msg);

        ACRA.init(this, builder);
        // The following line triggers the initialization of ACRA
    }
}
