package space.dennymades.nike.util;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;

/**
 * Created by abrain on 2/20/17.
 */

public class AnimatedTextView extends TextView
        implements Animator.AnimatorListener,
        ValueAnimator.AnimatorUpdateListener{

    private String TAG = this.getClass().getSimpleName();
    private ValueAnimator animator;

    public AnimatedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(500);
        animator.setInterpolator(new BounceInterpolator());
        animator.addUpdateListener(this);
        animator.addListener(this);
    }

    public void showText(){
        animator.start();
    }

    public void hideText(){

    }

    @Override
    public void onAnimationStart(Animator animator) {
        Log.d(TAG, "animation started");
    }

    @Override
    public void onAnimationEnd(Animator animator) {
        Log.d(TAG, "animation ended");
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        Log.d(TAG, "animation updating");
        this.setAlpha((float)valueAnimator.getAnimatedValue());
    }
}
