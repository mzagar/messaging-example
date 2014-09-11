package com.github.mzagar.messaging.example.common.message;

/**
 * Created by mzagar on 11.9.2014.
 */
public class MessageData {
    private int mMX;
    private int mPermGen;

    public MessageData(int mMX, int mPermGen) {
        this.mMX = mMX;
        this.mPermGen = mPermGen;
    }

    public int getmMX() {
        return mMX;
    }

    public void setmMX(int mMX) {
        this.mMX = mMX;
    }

    public int getmPermGen() {
        return mPermGen;
    }

    public void setmPermGen(int mPermGen) {
        this.mPermGen = mPermGen;
    }
}
