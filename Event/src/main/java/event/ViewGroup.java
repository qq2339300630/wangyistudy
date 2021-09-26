package event;

import java.util.ArrayList;
import java.util.List;

public class ViewGroup extends View {
    public ViewGroup(int left, int top, int right, int bottom) {
        super(left, top, right, bottom);
    }

    List<View> childList = new ArrayList<>();
    private View[] mChildren;

    public void addView(View view) {
        if (view == null) {
            return;
        }
        childList.add(view);
        mChildren = (View[]) childList.toArray(new View[childList.size()]);
    }

    private TouchTarget mFirstTouchTarget;

    public boolean dispatchTouchEvent(MotionEvent event) {
        System.out.println("dispatchTouchEvent");
        boolean handle = false;
        boolean intercepted = onInterceptTouchEvent(event);
        TouchTarget newTouchTarget = null;
        int actionMasked = event.getActionMasked();
        if (actionMasked != MotionEvent.ACTION_CANCEL && !intercepted) {
            if (actionMasked == MotionEvent.ACTION_DOWN) {
                final View[] children = mChildren;
                for (int i = children.length - 1; i >= 0; i--) {
                    View child = mChildren[i];
                    if (!child.isContainer(event.getX(), event.getY())) {
                        continue;
                    }
                    if (dispatchTransformedTouchEvent(event, child)) {
                        handle = true;
                        newTouchTarget = addTouchTarget(child);
                        break;
                    }
                }
            }
            if (mFirstTouchTarget == null) {
                handle = dispatchTransformedTouchEvent(event, null);
            }
        }
        return handle;
    }

    private TouchTarget addTouchTarget(View child) {
        final TouchTarget target = TouchTarget.obtain(child);
        target.next = mFirstTouchTarget;
        mFirstTouchTarget = target;
        return target;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    private boolean dispatchTransformedTouchEvent(MotionEvent event, View child) {
        boolean handle = false;
        if (child != null) {
            handle = child.dispatchTouchEvent(event);
        } else {
            handle = super.dispatchTouchEvent(event);
        }
        return handle;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static final class TouchTarget {
        public View child;
        private static TouchTarget sRecycleBin;
        private static final Object sRecycleLock = new Object[0];
        public TouchTarget next;

        private static int sRecycledCount;

        public static TouchTarget obtain(View child) {
            TouchTarget target;
            synchronized (sRecycleLock) {
                if (sRecycleBin == null) {
                    target = new TouchTarget();
                } else {
                    target = sRecycleBin;
                }
                sRecycleBin = target.next;
                sRecycledCount--;
                target.next = null;
            }
            target.child = child;
            return target;
        }

        public void recycle() {
            if (child == null) {
                throw new IllegalStateException("已经被回收过了");
            }

            synchronized (sRecycleLock) {
                if (sRecycledCount < 32) {
                    next = sRecycleBin;
                    sRecycleBin = this;
                    sRecycledCount++;
                }
            }
        }
    }
}
