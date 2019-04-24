package me.samlss.bubble;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description To display the bubble.
 */
class BubbleView extends View {
    private static float INITIAL_SCALE = 1;
    private static float MAX_SCALE = 2;

    private Paint mBubblePaint;
    private Bubble.Builder mBuilder;

    private List<BubbleBaby> mBubbleBabyList = new ArrayList<>();
    private List<ValueAnimator> mAnimatorList = new ArrayList<>();
    private Stack<BubbleBaby> mRecycler;
    private int mCenterColor;

    public BubbleView(Bubble.Builder builder) {
        super(builder.getRootView().getContext());

        mBuilder = builder;
        initialize();
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }


    //do the init work
    private void initialize(){
        mRecycler = new Stack<>();

        mBubblePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBubblePaint.setColor(mBuilder.getBubbleColor());
        mBubblePaint.setMaskFilter(new BlurMaskFilter(mBuilder.getBubbleRadius() / 2, BlurMaskFilter.Blur.SOLID));
        mCenterColor = getAlphaComponent(mBuilder.getBubbleColor(), 0x80);
        setAlpha(mBuilder.getAlpha());

    }

    public void shoot(final int x, final int y){
        final BubbleBaby curBaby = obtainBubbleBaby();
        curBaby.x = x;
        curBaby.y = y;
        curBaby.scale = INITIAL_SCALE;
        mBubbleBabyList.add(curBaby);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(INITIAL_SCALE, MAX_SCALE);
        valueAnimator.setDuration(mBuilder.getDuration());
        valueAnimator.setInterpolator(mBuilder.getInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                curBaby.scale = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mBubbleBabyList.remove(curBaby);
                mAnimatorList.remove(animation);
                recycle(curBaby);
            }
        });

        mAnimatorList.add(valueAnimator);
        valueAnimator.start();
    }

    public void stop(){
        stopAllAnimations();
        mBubbleBabyList.clear();
        invalidate();
    }

    public void stopAllAnimations(){
        List<ValueAnimator> copyAnimatorList = new ArrayList<>(mAnimatorList);
        for (ValueAnimator animator : copyAnimatorList){
            animator.cancel();
        }
    }

    public void destroy(){
        stop();
        ((ViewGroup)getParent()).removeView(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mBubbleBabyList == null || mBubbleBabyList.isEmpty()){
            return;
        }

        for (BubbleBaby bubbleBaby : mBubbleBabyList){

            float radius = bubbleBaby.scale * mBuilder.getBubbleRadius();
            mBubblePaint.setAlpha((int) (255 - 255 * (bubbleBaby.scale - INITIAL_SCALE) / (MAX_SCALE - INITIAL_SCALE)));
            if (radius > 0) {
                RadialGradient radialGradient = new RadialGradient(
                        bubbleBaby.x,
                        bubbleBaby.y,
                        radius,
                        mCenterColor,
                        mBuilder.getBubbleColor(),
                        Shader.TileMode.CLAMP);

                mBubblePaint.setShader(radialGradient);
            }else{
                mBubblePaint.setShader(null);
            }

            canvas.drawCircle(bubbleBaby.x, bubbleBaby.y, radius, mBubblePaint);
        }
    }

    public static int getAlphaComponent(final int color, int alpha) {
        return (color & 0x00ffffff) | (alpha << 24);
    }

    private BubbleBaby obtainBubbleBaby(){
        if (mRecycler.isEmpty()){
            return new BubbleBaby();
        }

        return mRecycler.pop();
    }

    private void recycle(BubbleBaby bubbleBaby){
        if (bubbleBaby == null){
            return;
        }

        if (mRecycler.size() >= 50){
            mRecycler.pop();
        }

        mRecycler.push(bubbleBaby);
    }

    private class BubbleBaby{
        int x;
        int y;
        float scale; // from 0 - 1
    }
}
