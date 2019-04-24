package me.samlss.bubble;

import android.animation.TimeInterpolator;
import android.app.Activity;
import android.graphics.Color;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description To display the bubble when touch or other ..., aha don't care about grammar, just for fun.
 */
public class Bubble {
    private BubbleView mBubbleView;

    private Bubble(Builder builder){
        mBubbleView = new BubbleView(builder);
        builder.rootView.addView(mBubbleView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

    /**
     * Shoot >>>>>>>>>>>>>>>>>>>>.
     * @param x The X coordinate relative to the root view.
     * @param y The y coordinate relative to the root view.
     * */
    public void shoot(int x, int y){
        mBubbleView.shoot(x, y);
    }

    /**
     * Stop the bubble animations.
     * */
    public void stop(){
        mBubbleView.stop();
    }

    /**
     * Stop the bubble animations & Remove the bubble view so that you can not show bubbles anymore.
     * */
    public void destroy(){
        mBubbleView.destroy();
    }

    /**
     * To config the params of {@link Bubble} uniformly, the you can build it.
     * */
    public static class Builder{
        private ViewGroup rootView;
        private float alpha = 1;
        private int duration = 500;
        private TimeInterpolator timeInterpolator = new LinearInterpolator();
        private float bubbleRadius = 100;
        private int bubbleColor = Color.WHITE;

        /**
         * Will use the activity's decorView as the root view to add {@link BubbleView}.
         * */
        public Builder(Activity activity){
            this((ViewGroup) activity.getWindow().getDecorView());
        }

        /**
         * Will use the viewGroup as the root view to add {@link BubbleView}.
         * Note: It's recommended to use {@link android.widget.RelativeLayout},
         * {@link android.widget.FrameLayout}.It is not recommended to use {@link android.widget.LinearLayout}.
         * */
        public Builder(ViewGroup viewGroup){
            if (viewGroup == null){
                throw new NullPointerException("activity can not be null.");
            }

            if (viewGroup instanceof ViewGroup == false){
                throw new IllegalArgumentException("It's not a view group.");
            }

            rootView = viewGroup;
        }

        public ViewGroup getRootView() {
            return rootView;
        }

        /**
         * Set the alpha of bubble, [0, 1].
         * If not specified, the default is 1.
         * */
        public Builder setAlpha(float alpha){
            this.alpha = alpha;
            return this;
        }


        public float getAlpha() {
            return alpha;
        }

        /**
         * Set the duration of animation in millisecond.
         * If not specified, the default is 500ms.
         * */
        public Builder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public int getDuration() {
            return duration;
        }

        /**
         * Set the interpolator of animation.
         * If not specified, the default is {@link LinearInterpolator}.
         * */
        public Builder setInterpolator(TimeInterpolator timeInterpolator) {
            this.timeInterpolator = timeInterpolator;
            return this;
        }

        public TimeInterpolator getInterpolator() {
            return timeInterpolator;
        }

        /**
         * Set the radius of bubble.
         * If not specified, the default is 100.
         * */
        public Builder setBubbleRadius(float bubbleRadius) {
            this.bubbleRadius = bubbleRadius;
            return this;
        }

        public float getBubbleRadius() {
            return bubbleRadius;
        }

        /**
         * Set the color of bubble.
         * If not specified, the default is {@link Color#WHITE}.
         * */
        public Builder setBubbleColor(int bubbleColor) {
            this.bubbleColor = bubbleColor;
            return this;
        }

        public int getBubbleColor() {
            return bubbleColor;
        }

        /**
         * To build a bubble obj to use the function of {@link Bubble}.
         * */
        public Bubble build(){
            return new Bubble(this);
        }
    }
}
