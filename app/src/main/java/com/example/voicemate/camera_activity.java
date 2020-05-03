package com.example.voicemate;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class camera_activity extends AppCompatActivity {

    private static final int INPUT_SIZE = 224;
    private static final int IMAGE_MEAN = 128;
    private static final float IMAGE_STD = 128.0f;
    private static final String INPUT_NAME = "input";
    private static final String OUTPUT_NAME = "final_result";

    private static final String MODEL_FILE = "file:///android_asset/tensorflow_inception_graph.pb";
    private static final String LABEL_FILE = "file:///android_asset/imagenet_comp_graph_label_strings.txt";


    private Executor executor = Executors.newSingleThreadExecutor();

    private Button btnDetectObject;

    private ImageView imageViewResult;
    private CameraView cameraView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_activity);

        cameraView = (CameraView) findViewById(R.id.cameraView);
        imageViewResult = (ImageView) findViewById(R.id.imageViewResult);




        btnDetectObject = (Button) findViewById(R.id.button);

        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {

                Bitmap bitmap = cameraKitImage.getBitmap();
                bitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);
                imageViewResult.setImageBitmap(bitmap);
                }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });



        btnDetectObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.captureImage();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
    }


    private void makeButtonVisible(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnDetectObject.setVisibility(View.VISIBLE);
            }
        });
    }


}
