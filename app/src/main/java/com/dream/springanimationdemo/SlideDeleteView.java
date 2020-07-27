package com.dream.springanimationdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

public class SlideDeleteView extends View {

    private VelocityTracker velocityTracker;

    private float lastMoveX;
    private float lastMoveY;

    public SlideDeleteView(Context context) {
        super(context);
    }

    public SlideDeleteView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideDeleteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SlideDeleteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(velocityTracker == null){
            velocityTracker = VelocityTracker.obtain();//必须和recycle()配对
        }
        velocityTracker.addMovement(event);


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastMoveX = event.getX();
                lastMoveY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                velocityTracker.computeCurrentVelocity(1000,2000);
                int X = (int)velocityTracker.getXVelocity();
                int Y = (int)velocityTracker.getYVelocity();
                this.setTranslationX(this.getX() + (event.getX() - lastMoveX));
                break;

            case MotionEvent.ACTION_CANCEL:
                break;

            case MotionEvent.ACTION_UP:
                if(velocityTracker != null){
                    velocityTracker.recycle();
                    velocityTracker.clear();
                    velocityTracker = null;
                }
                reset();
                break;

            default:
                break;
        }
        return true;
    }


    //复位view
    private void reset() {
        SpringForce spring = new SpringForce(0)
                .setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY)
                .setStiffness(SpringForce.STIFFNESS_LOW);
        final SpringAnimation anim = new SpringAnimation(this, DynamicAnimation.X)
                .setStartVelocity(20).setSpring(spring);
        anim.start();
    }
}
