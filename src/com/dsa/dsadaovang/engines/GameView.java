package com.dsa.dsadaovang.engines;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.dsa.dsadaovang.actors.Map;

public class GameView extends SurfaceView implements SurfaceHolder.Callback,
        Runnable {

    private static final String TAG = GameView.class.getSimpleName();

    public static final int FPS = 30;
    public static final int PER = 1000 / FPS;

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Rect mRectGame;
    private Rect mRectScreen;
    private Thread mThread;
    private boolean mRunning;
    private Map mMap;

    public GameView(Context context, Map map) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        mMap = map;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mBitmap = Bitmap.createBitmap(GameUtility.mGameWidth,
                GameUtility.mGameHeight, Bitmap.Config.RGB_565);
        mCanvas = new Canvas(mBitmap);
        mRectGame = new Rect(0, 0, GameUtility.mGameWidth,
                GameUtility.mGameHeight);
        mRectScreen = new Rect(0, 0, GameUtility.mScreenWidth,
                GameUtility.mScreenHeight);
        mRunning = true;
        mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                mRunning = false;
                mThread.join();
                retry = false;
            } catch (InterruptedException ie) {
                Log.i(TAG, ie.toString());
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        mMap.onTouchEvent(motionEvent);
        return super.onTouchEvent(motionEvent);
    }

    public void render(Canvas canvas) {
        mMap.update();
        mMap.draw(mCanvas);
        canvas.drawBitmap(mBitmap, mRectGame, mRectScreen, null);
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    @Override
    public void run() {
        while (mRunning) {
            Canvas canvas = null;
            try {
                canvas = getHolder().lockCanvas();
                if (canvas != null) {
                    synchronized (getHolder()) {
                        long start = System.currentTimeMillis();
                        render(canvas);
                        long end = System.currentTimeMillis();
                        long elapsed = end - start;
                        long sleep = PER - elapsed;
                        if (sleep > 0) {
                            try {
                                Thread.sleep(sleep);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } finally {
                if (canvas != null) {
                    getHolder().unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
