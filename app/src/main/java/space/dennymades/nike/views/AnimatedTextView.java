package space.dennymades.nike.views;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

/**
 * Created by abrain on 2/20/17.
 */

public class AnimatedTextView extends TextView
        implements Animator.AnimatorListener,
        ValueAnimator.AnimatorUpdateListener{

    private String TAG = this.getClass().getSimpleName();
    private ValueAnimator animatorShow;
    private ValueAnimator animatorHide;

    public AnimatedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        animatorShow = ValueAnimator.ofFloat(0f, 1f);
        animatorShow.setDuration(500);
        animatorShow.setInterpolator(new FastOutSlowInInterpolator());
        animatorShow.addUpdateListener(this);
        animatorShow.addListener(this);

        animatorHide = ValueAnimator.ofFloat(1f, 0f);
        animatorHide.setDuration(100);
        animatorHide.setInterpolator(new FastOutLinearInInterpolator());
        animatorHide.addUpdateListener(this);
        animatorHide.addListener(this);
    }

    public void showText(){
        animatorShow.start();
    }

    public void hideText(){
        animatorHide.start();
    }

    public boolean isVisible(){
        return getAlpha()==1 ? true:false;
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
        //Log.v(TAG, "animation updating");
        this.setAlpha((float)valueAnimator.getAnimatedValue());
    }
}
