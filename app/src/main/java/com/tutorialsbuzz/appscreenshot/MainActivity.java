package com.tutorialsbuzz.appscreenshot;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Date;

import static android.R.attr.bitmap;

public class MainActivity extends AppCompatActivity implements MediaScannerConnection.MediaScannerConnectionClient {


    public String[] allFiles;
    private String SCAN_PATH;
    private static final String FILE_TYPE = "*/*";

    private MediaScannerConnection conn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

//        iv = (ImageView) findViewById(R.id.iv);
//        tv_saved = (TextView) findViewById(R.id.tv_saved);

        setSupportActionBar(toolbar);

        Log.d("MediaPathValue", getInGalleryPath());


        // File folder = new File("/sdcard/Photo/");
        File folder = new File("/storage/emmc/DCIM/100MEDIA");
        allFiles = folder.list();
        //   uriAllFiles= new Uri[allFiles.length];
        for (int i = 0; i < allFiles.length; i++) {
            Log.d("all file path" + i, allFiles[i] + allFiles.length);
        }
        //  Uri uri= Uri.fromFile(new File(Environment.getExternalStorageDirectory().toString()+"/yourfoldername/"+allFiles[0]));
        SCAN_PATH = Environment.getExternalStorageDirectory().toString() + "/Photo/" + allFiles[0];
        System.out.println(" SCAN_PATH  " + SCAN_PATH);
        Log.d("SCAN PATH", "Scan Path " + SCAN_PATH);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //takeScreenshot();

                //  laucnhGallery();

                startScan();

            }
        });
    }


    private void laucnhGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath()
                + "/CameraExample/");
        intent.setDataAndType(uri, "*/*");
        String directoryName = "Camera shots";
        startActivity(Intent.createChooser(intent, directoryName));
    }

    private void startScan() {
        Log.d("Connected", "success" + conn);
        if (conn != null) {
            conn.disconnect();
        }
        conn = new MediaScannerConnection(this, this);
        conn.connect();
    }


    @Override
    public void onMediaScannerConnected() {
        Log.d("onMediaScannerConnected", "success" + conn);
        conn.scanFile(SCAN_PATH, FILE_TYPE);
    }

    @Override
    public void onScanCompleted(String s, Uri uri) {


        try {
            Log.d("onScanCompleted", uri + "success" + conn);
            // System.out.println("URI " + uri);

            uri = Uri.fromFile(new File(s));

            if (uri != null) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                startActivity(intent);
            }
        } finally {
            conn.disconnect();
            conn = null;
        }


    }

    static String photoDir;

    private static String getGalleryPath() {
        return photoDir = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/";
    }


    private static String getInGalleryPath() {

        //return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();


    }


    //    private void takeScreenshot() {
//
//        try {
//            // image naming and path  to include sd card  appending name you choose for file
//
//            // String mPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/" + now + ".jpg";
//
//
//            // create bitmap screen capture
//            View v1 = getWindow().getDecorView().getRootView();
//            v1.setDrawingCacheEnabled(true);
//            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
//            v1.setDrawingCacheEnabled(false);
//
//            saveBitMapImageInternally(bitmap);
//
//            //saveFile(getApplicationContext(), bitmap, now + ".jpg");
//
//            //  iv.setImageBitmap(bitmap);
//
//
////            SaveBitMap(bitmap);
////
////            File imageFile = new File(mPath);
////
////            FileOutputStream outputStream = new FileOutputStream(imageFile);
////            int quality = 100;
////            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
////            outputStream.flush();
////            outputStream.close();
//
//            //   openScreenshot(imageFile);
//        } catch (Throwable e) {
//            // Several error may come out with file handling or OOM
//            e.printStackTrace();
//        }
//    }
//
//
//    private void openScreenshot(File imageFile) {
////        Intent intent = new Intent();
////        intent.setAction(Intent.ACTION_VIEW);
////        Uri uri = Uri.fromFile(imageFile);
////        Log.d("URIValue", uri.toString());
////        intent.setDataAndType(uri, "image/*");
////        startActivity(intent);
//    }
//
//
//    private void saveBitMapImageInternally(Bitmap bitmap) {
//
//        ContextWrapper wrapper = new ContextWrapper(getApplicationContext());
//
//        Date now = new Date();
//        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
//        String fileName = now + ".jpg";
//        // String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";
//
//
//        File file = wrapper.getDir("Images", MODE_PRIVATE);
//        file = new File(file, fileName);
//
//
//        try {
//
//            OutputStream stream = null;
//
//            stream = new FileOutputStream(file);
//
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//
//            stream.flush();
//
//            stream.close();
//
//
//        } catch (IOException ex) // Catch the exception
//        {
//        }
//
//
//        // Parse the gallery image url to uri
//        Uri savedImageURI = Uri.parse(file.getAbsolutePath());
//
//        // Display the saved image to ImageView
//        iv.setImageURI(savedImageURI);
//
//        // Display saved image uri to TextView
//        tv_saved.setText("Image saved in internal storage.\n" + savedImageURI);
//
//
//        Uri uri = Uri.fromFile(file);
//
//        tv_saved.append("\n" + uri.toString());
//
//
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        //Uri uri = Uri.fromFile(imageFile);
//        Log.d("URIValue", uri.toString());
//        intent.setDataAndType(uri, "image/*");
//        startActivity(intent);
//
//
//    }


}
