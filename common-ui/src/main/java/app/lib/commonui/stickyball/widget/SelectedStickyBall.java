
package app.lib.commonui.stickyball.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import java.util.LinkedList;

public class SelectedStickyBall
        implements ISelectedView, Animator.AnimatorListener, StickyBallView.OnTranslationListener {

    private StickyBallView ballView;
    private DotIndicatorInfo info;

    private long time = 300;
    private float translationX;
    private AnimatorSet set;
    private ObjectAnimator animator, animatorSource, animatorSourceScale;

    private int prePosition;

    private LinkedList<Integer> positionQueue;

    public SelectedStickyBall(Context context) {
        ballView = new StickyBallView(context);
        ballView.setOnTranslationListener(this);
        positionQueue = new LinkedList<>();
    }

    @Override
    public void onCreatedIndicator(DotIndicatorInfo info) {
        this.info = info;
        PointF pointF = info.getDotPoints()[info.getPosition()];
        ballView.setSourceXY(pointF.x - info.getPaddingLeft(), pointF.y - info.getPaddingTop());
        ballView.setTargetXY(pointF.x - info.getPaddingLeft(), pointF.y - info.getPaddingTop());
        ballView.setColor(info.getColorSelected());
        ballView.setSourceRadius(info.getDotRadius());
        ballView.setTargetRadius(info.getDotRadius());
        prePosition = info.getPosition();
    }

    @Override
    public void onSelected(int position) {
        if (prePosition == position)
            return;
        positionQueue.offer(position);
        start();
    }

    private void start() {
        if (set != null && set.isRunning())
            return;
        Integer position = positionQueue.poll();
        if (position == null)
            return;
        animator = ObjectAnimator.ofFloat(ballView, "targetTranslationX", 0,
                getTranslationDimension(position));
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(time);

        animatorSource = ObjectAnimator.ofFloat(ballView, "sourceTranslationX", 0,
                getTranslationDimension(position));
        animatorSource.setInterpolator(
                Math.abs(getTranslationDimension(position)) > info.getDotCenterDistance() * 2
                        ? new OvershootInterpolator(0.8f) : new OvershootInterpolator(1.1f));
        animatorSource.setStartDelay((long) (time * 0.8f));
        animatorSource.setDuration(time);

        animatorSourceScale = ObjectAnimator.ofFloat(ballView, "sourceRadius", info.getDotRadius(),
                0);
        animatorSourceScale.setInterpolator(new DecelerateInterpolator());
        animatorSourceScale.setDuration(time + (long) (time * 0.8f));

        set = new AnimatorSet();
        set.play(animator).with(animatorSource).with(animatorSourceScale);
        set.start();
        set.addListener(this);

        prePosition = position;
    }

    private float getTranslationDimension(int position) {
        PointF curPointF = info.getDotPoints()[position];
        PointF prePointF = info.getDotPoints()[prePosition];
        return translationX = curPointF.x - prePointF.x;
    }

    @Override
    public View getView(View parent) {
        return ballView;
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        ballView.updateSourceCache();
        ballView.updateTargetCache();
        ballView.setSourceRadius(info.getDotRadius());
        set = null;
        start();
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    @Override
    public void onSourceTranslation(float dX, float dY) {
        if (Math.abs(dX) >= Math.abs(translationX) || Math.abs(dY) >= Math.abs(translationX)) {
            ballView.setSourceRadius(info.getDotRadius());
        }
    }

    @Override
    public void onTargetTranslation(float dX, float dY) {

    }
}
