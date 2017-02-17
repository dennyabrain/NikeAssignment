package space.dennymades.nike;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by abrain on 2/16/17.
 */

public class MyCarouselItem extends FrameLayout {
    public final static float VIEW_SCALE_BIG = 1.0f;
    public final static float VIEW_SCALE_SMALL = 0.7f;
    private float mScale=1.0f;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = this.getWidth();
        int h = this.getHeight();
        canvas.scale(mScale, mScale, w/2, h/2);
    }

    public MyCarouselItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    public void setScale(float scale){
        mScale = scale;
        invalidate();
    }
}
