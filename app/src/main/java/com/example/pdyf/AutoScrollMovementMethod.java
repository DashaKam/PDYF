package com.example.pdyf;

import android.text.Spannable;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.widget.TextView;

public class AutoScrollMovementMethod extends ScrollingMovementMethod {
    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            widget.post(new Runnable() {
                @Override
                public void run() {
                    widget.scrollTo(0, widget.getBottom());
                }
            });
        }
        return super.onTouchEvent(widget, buffer, event);
    }
}