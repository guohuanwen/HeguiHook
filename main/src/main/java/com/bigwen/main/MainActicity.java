package com.bigwen.main;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.huiwan.interview.R;

public class MainActicity extends Activity {

    private static final String TAG = "MainActicity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo("com.wepie.wespy", 0);
            Log.i(TAG, "onCreate: " + packageInfo.packageName);
            Log.i(TAG, "onCreate: " + packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
