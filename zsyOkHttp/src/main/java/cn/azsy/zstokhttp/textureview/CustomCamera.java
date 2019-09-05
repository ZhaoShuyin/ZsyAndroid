package cn.azsy.zstokhttp.textureview;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.TextureView;

import java.io.IOException;

//import android.hardware.Camera;

//import android.graphics.Camera;

/**
 * Created by zsy on 2018/2/23.
 */

public class CustomCamera extends TextureView implements TextureView.SurfaceTextureListener {
    private Camera mCamera;
    public CustomCamera(Context context) {
        super(context);
        init();
    }
    public CustomCamera(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public CustomCamera(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    void init() {
        mCamera = Camera.open();
        setSurfaceTextureListener(this);
    }
    public void takePic(Camera.ShutterCallback shutter, Camera.PictureCallback raw,                        Camera.PictureCallback postview, Camera.PictureCallback jpeg) {
        if (mCamera != null) {
            mCamera.takePicture(shutter, raw, postview, jpeg);
        }
    }
    public void takePic(Camera.ShutterCallback shutter, Camera.PictureCallback raw, Camera.PictureCallback jpeg) {
        if (mCamera != null) {
            mCamera.takePicture(shutter, raw, jpeg);
        }
    }
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        if (mCamera == null) {
            mCamera = Camera.open();
        }
        try {
            mCamera.setPreviewTexture(surface);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mCamera.stopPreview();
        mCamera.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }
}