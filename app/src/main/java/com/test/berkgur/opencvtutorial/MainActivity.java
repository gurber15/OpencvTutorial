package com.test.berkgur.opencvtutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {
    JavaCameraView javaCameraView;
    Mat mRbga;
    BaseLoaderCallback mLoaderCallBack = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case BaseLoaderCallback.SUCCESS: {
                    javaCameraView.enableView();
                    break;
                }

                default: {
                    super.onManagerConnected(status);
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        javaCameraView = (JavaCameraView)findViewById(R.id.java_camera_view);
        javaCameraView.setVisibility(SurfaceView.VISIBLE);
        javaCameraView.setCvCameraViewListener(this);




    }

    @Override
    protected void onPause() {
        super.onPause();
        if (javaCameraView != null)
            javaCameraView.disableView();
    }

    @Override
    protected  void onDestroy(){
        super.onDestroy();
        if (javaCameraView != null)
            javaCameraView.disableView();


    }

    @Override
    protected void onResume(){
            super.onResume();
            if(OpenCVLoader.initDebug()){
                //Toast.makeText(getApplicationContext(),"Opencv is loaded succcesfully", Toast.LENGTH_LONG).show();
                mLoaderCallBack.onManagerConnected(LoaderCallbackInterface.SUCCESS);
            }
            else{
                //Toast.makeText(getApplicationContext(),"You fucked up", Toast.LENGTH_SHORT).show();
                OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0,this,mLoaderCallBack);
            }
            mLoaderCallBack.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }



    @Override
    public void onCameraViewStarted(int width, int height) {
        mRbga = new Mat(height,width, CvType.CV_8UC4); //we have 4 channgel r,g,b,a
    }

    @Override
    public void onCameraViewStopped() {
        mRbga.release();

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRbga = inputFrame.rgba();
        return mRbga;

    }
}
