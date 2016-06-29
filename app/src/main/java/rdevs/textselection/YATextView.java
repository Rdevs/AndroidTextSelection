package rdevs.textselection;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Kandeparker on 21-Jun-16.
 *  Code from the below link
 *  https://code.google.com/p/android/issues/detail?id=23381
 *  Using it as a workaround for the bug in which action mode is finished
 *  when overflow icon is clicked even before any overflow menu items are clicked
 */
public class YATextView extends TextView {

    private boolean shouldWindowFocusWait;

    public YATextView(Context context) {
        super(context);
    }

    public YATextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YATextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setWindowFocusWait(boolean shouldWindowFocusWait) {
        this.shouldWindowFocusWait = shouldWindowFocusWait;
    }


    /**
     *  This method is called whenever the window focus changes.
     * @param hasWindowFocus
     *  if param is true , we do not pass it to super method which will prevent the abrupt finishing of action mode
     *  if param is false , we pass it to super method which eventually finishes the action mode.
     *
     */
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if(!shouldWindowFocusWait)
        {
            super.onWindowFocusChanged(hasWindowFocus);
        }
    }

}
