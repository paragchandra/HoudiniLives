package com.example.managed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MAIN";
    private final NativeLibA nativeLibA = new NativeLibA();
    private final NativeLibB nativeLibB = new NativeLibB();

    private static final String[] ASSET_LIBS = new String[] {
            "libnativeliba_armeabi-v7a.so",
            "libnativeliba_x86.so",
            "libnativelibb_armeabi-v7a.so",
            "libnativelibb_x86.so"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void copyAssetFile(String assetFilePath, String destinationFilePath) throws IOException
    {
        InputStream in = getApplicationContext().getAssets().open(assetFilePath);
        OutputStream out = new FileOutputStream(destinationFilePath);

        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0)
            out.write(buf, 0, len);
        in.close();
        out.close();
    }

    public void onButtonClicked(View v) {
        Log.i(TAG, "MainActivity - onButtonClicked: Invoked");
        // Enumerate supported ABIs
        for (String s : Build.SUPPORTED_ABIS) {
            Log.i(TAG, "MainActivity - onButtonClicked: Claims to support: " + s);
        }

        // Take all the .so files we packaged up as assets and write them out to the app's private
        // sandbox
        try {
            for (String s : ASSET_LIBS) {
                File outputFile = new File(getApplicationContext().getFilesDir() + "/" + s);
                if (outputFile.exists()) {
                    outputFile.delete();
                }
                copyAssetFile(s, outputFile.getAbsolutePath());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // Load the native libraries in different ways
        System.loadLibrary("nativeliba");
        System.loadLibrary("nativelibb");
//        System.load(getApplicationContext().getFilesDir() + "/libnativeliba_x86.so");
//        System.load(getApplicationContext().getFilesDir() + "/libnativelibb_armeabi-v7a.so");

        nativeLibA.sayHello();
        nativeLibB.sayHola();
    }
}
