package event.litener;


import event.MotionEvent;
import event.View;

public interface OnTouchListener {
    boolean onTouch(View v, MotionEvent event);
}
