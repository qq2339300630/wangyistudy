package event;


import event.litener.OnClickListener;
import event.litener.OnTouchListener;

public class View {
    private int left;
    private int top;
    private int right;
    private int bottom;

    private OnTouchListener mOnTouchListener;
    private OnClickListener mOnClickListener;

    public View() {
    }

    public View(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public void setOnTouchListener(OnTouchListener mOnTouchListener) {
        this.mOnTouchListener = mOnTouchListener;
    }

    public void setOnClickListener(OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public boolean isContainer(int x, int y) {
        if (x >= left && x <= right && y >= top && y <= bottom) {
            return true;
        }
        return false;
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        System.out.println("view dispatchTouchEvent");
        boolean result = false;

        if (mOnTouchListener != null && mOnTouchListener.onTouch(this,event)) {
            result = true;
        }

        if (!result && OnTouchEvent(event)) {
            result = true;
        }

        return result;
    }

    private boolean OnTouchEvent(MotionEvent event) {
        if (mOnClickListener != null) {
            mOnClickListener.onClick(this);
            return true;
        }
        return false;
    }

}
