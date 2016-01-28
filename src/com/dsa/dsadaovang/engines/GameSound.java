package com.dsa.dsadaovang.engines;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.SparseIntArray;

public class GameSound {

    public static int A_COUNT = 0;
    public static final int BIG = A_COUNT++;
    public static final int BUY = A_COUNT++;
    public static final int CLICK = A_COUNT++;
    public static final int COUNT = A_COUNT++;
    public static final int EARN = A_COUNT++;
    public static final int NORMAL = A_COUNT++;
    public static final int OVER = A_COUNT++;
    public static final int PASS = A_COUNT++;
    public static final int REWIND = A_COUNT++;
    public static final int SHOOT = A_COUNT++;
    public static final int SMALL = A_COUNT++;
    public static final int START = A_COUNT++;
    public static final int TNT = A_COUNT++;

    public static final int MAX_STREAMS = 13;
    private static GameSound mInstance;

    private SoundPool mSoundPool;
    private SparseIntArray mSparseIntArray;
    private AudioManager mAudioManager;
    private Context mContext;
    private boolean mSound;

    @SuppressWarnings("deprecation")
    private GameSound(Context context) {
        mContext = context;
        mSparseIntArray = new SparseIntArray();
        mSoundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        mAudioManager = (AudioManager) mContext
                .getSystemService(Context.AUDIO_SERVICE);
        mSound = true;
    }

    public static GameSound getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new GameSound(context);
        }
        return mInstance;
    }

    public void onSound() {
        mSound = true;
    }

    public void offSound() {
        mSound = false;
    }

    public boolean isSound() {
        return mSound;
    }

    public void put(int key, int id) {
        mSparseIntArray.put(key, load(id));
    }

    public int load(int id) {
        return mSoundPool.load(mContext, id, 1);
    }

    public void play(int key, int loop) {
        if (mSound) {
            int streamVolume = mAudioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC);
            mSoundPool.play(mSparseIntArray.get(key), streamVolume,
                    streamVolume, 1, loop, 1);
        }
    }
}
