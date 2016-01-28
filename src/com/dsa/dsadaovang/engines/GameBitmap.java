package com.dsa.dsadaovang.engines;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.util.SparseArray;

public class GameBitmap {

    private static GameBitmap mInstance;

    public static int A_COUNT = 0;
    public static final int BAG_0 = A_COUNT++;
    public static final int BAG_1 = A_COUNT++;
    public static final int BKG_0 = A_COUNT++;
    public static final int BKG_1 = A_COUNT++;
    public static final int BKG_2 = A_COUNT++;
    public static final int BKG_3 = A_COUNT++;
    public static final int BKG_4 = A_COUNT++;
    public static final int BKG_5 = A_COUNT++;
    public static final int BKG_6 = A_COUNT++;
    public static final int BONE_0 = A_COUNT++;
    public static final int BONE_1 = A_COUNT++;
    public static final int BOOM_0 = A_COUNT++;
    public static final int BOOM_1 = A_COUNT++;
    public static final int BOOM_2 = A_COUNT++;
    public static final int BOOM_3 = A_COUNT++;
    public static final int BOOM_4 = A_COUNT++;
    public static final int BOOM_5 = A_COUNT++;
    public static final int BOOM_6 = A_COUNT++;
    public static final int BOOM_7 = A_COUNT++;
    public static final int BOOM_8 = A_COUNT++;
    public static final int BOOM_9 = A_COUNT++;
    public static final int DIAMOND_0 = A_COUNT++;
    public static final int DIAMOND_1 = A_COUNT++;
    public static final int FLASH_0 = A_COUNT++;
    public static final int FLASH_1 = A_COUNT++;
    public static final int FLASH_2 = A_COUNT++;
    public static final int FLASH_3 = A_COUNT++;
    public static final int FLASH_4 = A_COUNT++;
    public static final int FLASH_5 = A_COUNT++;
    public static final int FLASH_6 = A_COUNT++;
    public static final int FLASH_7 = A_COUNT++;
    public static final int FLASH_8 = A_COUNT++;
    public static final int FLASH_9 = A_COUNT++;
    public static final int GOLD_BIG_0 = A_COUNT++;
    public static final int GOLD_BIG_1 = A_COUNT++;
    public static final int GOLD_MEDIUM_0 = A_COUNT++;
    public static final int GOLD_MEDIUM_1 = A_COUNT++;
    public static final int GOLD_SMALL_0 = A_COUNT++;
    public static final int GOLD_SMALL_1 = A_COUNT++;
    public static final int GOLD_TINY_0 = A_COUNT++;
    public static final int GOLD_TINY_1 = A_COUNT++;
    public static final int HOOK = A_COUNT++;
    public static final int ITEM_DIAMOND = A_COUNT++;
    public static final int ITEM_LUCKY = A_COUNT++;
    public static final int ITEM_MINE = A_COUNT++;
    public static final int ITEM_POWER = A_COUNT++;
    public static final int ITEM_STONE = A_COUNT++;
    public static final int ITEM_TIME = A_COUNT++;
    public static final int MINER_EASY_0 = A_COUNT++;
    public static final int MINER_EASY_1 = A_COUNT++;
    public static final int MINER_EASY_2 = A_COUNT++;
    public static final int MINER_EASY_3 = A_COUNT++;
    public static final int MINER_EASY_4 = A_COUNT++;
    public static final int MINER_EASY_5 = A_COUNT++;
    public static final int MINER_EASY_6 = A_COUNT++;
    public static final int MINER_EASY_7 = A_COUNT++;
    public static final int MINER_HARD_0 = A_COUNT++;
    public static final int MINER_HARD_1 = A_COUNT++;
    public static final int MINER_HARD_2 = A_COUNT++;
    public static final int MINER_HARD_3 = A_COUNT++;
    public static final int MINER_HARD_4 = A_COUNT++;
    public static final int MINER_HARD_5 = A_COUNT++;
    public static final int MINER_HARD_6 = A_COUNT++;
    public static final int MINER_HARD_7 = A_COUNT++;
    public static final int MINER_POWER_0 = A_COUNT++;
    public static final int MINER_POWER_1 = A_COUNT++;
    public static final int MINER_POWER_2 = A_COUNT++;
    public static final int MINER_POWER_3 = A_COUNT++;
    public static final int MINER_SHOOT_0 = A_COUNT++;
    public static final int MINER_SHOOT_1 = A_COUNT++;
    public static final int MINER_SHOOT_2 = A_COUNT++;
    public static final int MINER_SHOOT_3 = A_COUNT++;
    public static final int MINER_THROW_0 = A_COUNT++;
    public static final int MINER_THROW_1 = A_COUNT++;
    public static final int MINER_THROW_2 = A_COUNT++;
    public static final int MINER_THROW_3 = A_COUNT++;
    public static final int PIG_DIAMOND_0 = A_COUNT++;
    public static final int PIG_DIAMOND_1 = A_COUNT++;
    public static final int PIG_DIAMOND_2 = A_COUNT++;
    public static final int PIG_DIAMOND_3 = A_COUNT++;
    public static final int PIG_DIAMOND_4 = A_COUNT++;
    public static final int PIG_DIAMOND_5 = A_COUNT++;
    public static final int PIG_DIAMOND_6 = A_COUNT++;
    public static final int PIG_DIAMOND_7 = A_COUNT++;
    public static final int PIG_DIAMOND_8 = A_COUNT++;
    public static final int PIG_DIAMOND_9 = A_COUNT++;
    public static final int PIG_RUN_0 = A_COUNT++;
    public static final int PIG_RUN_1 = A_COUNT++;
    public static final int PIG_RUN_2 = A_COUNT++;
    public static final int PIG_RUN_3 = A_COUNT++;
    public static final int PIG_RUN_4 = A_COUNT++;
    public static final int PIG_RUN_5 = A_COUNT++;
    public static final int PIG_RUN_6 = A_COUNT++;
    public static final int PIG_RUN_7 = A_COUNT++;
    public static final int PIG_RUN_8 = A_COUNT++;
    public static final int PIG_RUN_9 = A_COUNT++;
    public static final int SKULL_0 = A_COUNT++;
    public static final int SKULL_1 = A_COUNT++;
    public static final int STONE_BIG_0 = A_COUNT++;
    public static final int STONE_BIG_1 = A_COUNT++;
    public static final int STONE_SMALL_0 = A_COUNT++;
    public static final int STONE_SMALL_1 = A_COUNT++;
    public static final int TNT_0 = A_COUNT++;
    public static final int TNT_1 = A_COUNT++;
    public static final int ZERO_DIALOG = A_COUNT++;
    public static final int ZERO_HELP = A_COUNT++;
    public static final int ZERO_MENU = A_COUNT++;
    public static final int ZERO_NEXT = A_COUNT++;
    public static final int ZERO_OFF = A_COUNT++;
    public static final int ZERO_ON = A_COUNT++;
    public static final int ZERO_OTHER = A_COUNT++;
    public static final int ZERO_PAUSE = A_COUNT++;
    public static final int ZERO_PLAY = A_COUNT++;
    public static final int ZERO_QUIT = A_COUNT++;
    public static final int ZERO_RATE = A_COUNT++;
    public static final int ZERO_SAVE = A_COUNT++;
    public static final int ZERO_START = A_COUNT++;

    private Context mContext;
    private Options mOptions;

    private SparseArray<Bitmap> mSparseArray;

    private GameBitmap(Context context) {
        mContext = context;
        mOptions = new Options();
        mOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        mOptions.inScaled = false;
        mSparseArray = new SparseArray<Bitmap>();
    }

    public static GameBitmap getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new GameBitmap(context);
        }
        return mInstance;
    }

    public void put(int key, int id) {
        mSparseArray.put(key, load(id));
    }

    private Bitmap load(int id) {
        return BitmapFactory.decodeResource(mContext.getResources(), id,
                mOptions);
    }

    public Bitmap get(int key) {
        return mSparseArray.get(key);
    }

    public static Bitmap flipX(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.preScale(-1, 1);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
    }
}
