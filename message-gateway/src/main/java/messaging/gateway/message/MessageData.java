package messaging.gateway.message;

/**
 * Message payload.
 *
 * Created by mzagar on 11.9.2014.
 */
public class MessageData {
    private final int mMX;
    private final int mPermGen;
    private final Integer mOldGen;
    private final Integer mYoungGen;

    public MessageData(int mMX, int mPermGen) {
        this(mMX, mPermGen, null, null);
    }

    public MessageData(int mMX, int mPermGen, int mOldGen) {
        this(mMX, mPermGen, mOldGen, null);
    }

    public MessageData(int mMX, int mPermGen, Integer mOldGen, Integer mYoungGen) {
        this.mMX = mMX;
        this.mPermGen = mPermGen;
        this.mOldGen = mOldGen;
        this.mYoungGen = mYoungGen;
    }

    public int getmMX() {
        return mMX;
    }

    public int getmPermGen() {
        return mPermGen;
    }

    public Integer getmOldGen() {
        return mOldGen;
    }

    public Integer getmYoungGen() {
        return mYoungGen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageData that = (MessageData) o;

        if (mMX != that.mMX) return false;
        if (mPermGen != that.mPermGen) return false;
        if (mOldGen != null ? !mOldGen.equals(that.mOldGen) : that.mOldGen != null) return false;
        if (mYoungGen != null ? !mYoungGen.equals(that.mYoungGen) : that.mYoungGen != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mMX;
        result = 31 * result + mPermGen;
        result = 31 * result + (mOldGen != null ? mOldGen.hashCode() : 0);
        result = 31 * result + (mYoungGen != null ? mYoungGen.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MessageData{" +
                "mMX=" + mMX +
                ", mPermGen=" + mPermGen +
                ", mOldGen=" + mOldGen +
                ", mYoungGen=" + mYoungGen +
                '}';
    }
}
