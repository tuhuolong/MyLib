
package app.lib.commonui.stickyball.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import app.lib.commonui.stickyball.utils.GeometryUtil;

/**
 * 粘性球View，分为原点和目标点两部分； 其中，原点是被动移动点，目标点是主动移动点； 两个点最开始是重合的，可以通过设置偏移量来改变目标点和原点的相对位置；
 * <p>
 */
public class StickyBallView extends View {

    private final static int DEFAULT_COLOR = Color.parseColor("#FF4081");
    private Paint paintBall;
    private PointF pointSource, pointTarget;// 原点、目标点
    private PointF pointSourceCache, pointTargetCache;// 保存原点、目标点数据
    private PointF[] points;// 贝塞尔介质各点
    private PointF pointControl;
    private Path movePath;
    private int color = DEFAULT_COLOR;

    private float mCircleCenter = 10f;
    private float mRadius = 10f;// 直径

    private float mMoveCircleCenter = 10f;
    private float mMoveRadius = 10f;// 直径

    private OnTranslationListener listener;

    public StickyBallView(Context context) {
        this(context, null);
    }

    public StickyBallView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickyBallView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setColor(int color) {
        this.color = color;
        if (paintBall != null)
            paintBall.setColor(color);
        invalidate();
    }

    private void init() {
        pointSource = new PointF(mCircleCenter, mCircleCenter);
        pointTarget = new PointF(mMoveCircleCenter, mMoveCircleCenter);
        pointSourceCache = new PointF(mCircleCenter, mCircleCenter);
        pointTargetCache = new PointF(mMoveCircleCenter, mMoveCircleCenter);

        paintBall = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintBall.setColor(color);
        paintBall.setStrokeCap(Paint.Cap.ROUND);
        paintBall.setStyle(Paint.Style.FILL);

        resetPath(pointSource, pointTarget);
    }

    private void resetPath(PointF pointSource, PointF pointTarget) {
        float offX = pointSource.x - pointTarget.x;
        float offY = pointSource.y - pointTarget.y;
        double k = offY / offX;

        points = GeometryUtil.getIntersectionPoints(pointTarget, pointSource, mMoveRadius, mRadius,
                k);

        pointControl = GeometryUtil.getControlPoint(pointTarget, pointSource, mMoveRadius, mRadius);

        if (movePath == null)
            movePath = new Path();
        movePath.reset();
        movePath.moveTo(points[1].x, points[1].y);
        movePath.quadTo(pointControl.x, pointControl.y, points[0].x, points[0].y);
        movePath.lineTo(points[2].x, points[2].y);
        movePath.quadTo(pointControl.x, pointControl.y, points[3].x, points[3].y);
        movePath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(pointSource.x, pointSource.y, mRadius, paintBall);
        canvas.drawCircle(pointTarget.x, pointTarget.y, mMoveRadius, paintBall);
        canvas.drawPath(movePath, paintBall);
    }

    public void setSourceRadius(float mRadius) {
        this.mRadius = mRadius;
        resetPath(pointSource, pointTarget);
        invalidate();
    }

    public void setTargetRadius(float mMoveRadius) {
        this.mMoveRadius = mMoveRadius;
        resetPath(pointSource, pointTarget);
        invalidate();
    }

    public void setSourceXY(float sourceX, float sourceY) {
        pointSource.set(sourceX, sourceY);
        pointSourceCache.set(sourceX, sourceY);
        invalidate();
    }

    public void setTargetXY(float targetX, float targetY) {
        pointTarget.set(targetX, targetY);
        pointTargetCache.set(targetX, targetY);
        invalidate();
    }

    /**
     * 更新原点的缓存位置（缓存位置代表改点初始化的位置，如果在平移之后需要保持在最新位置，应该调用该方法）
     */
    public void updateSourceCache() {
        pointSourceCache.set(pointSource.x, pointSource.y);
    }

    /**
     * 更新目标点的缓存位置（缓存位置代表改点初始化的位置，如果在平移之后需要保持在最新位置，应该调用该方法）
     */
    public void updateTargetCache() {
        pointTargetCache.set(pointTarget.x, pointTarget.y);
    }

    /**
     * 设置原点的x轴平移量
     *
     * @param dX x轴平移量
     */
    public void setSourceTranslationX(float dX) {
        pointSource.set(pointSourceCache.x + dX, pointSource.y);
        if (listener != null)
            listener.onSourceTranslation(dX, 0);
        resetPath(pointSource, pointTarget);
        invalidate();
    }

    /**
     * 设置原点的y轴平移量
     *
     * @param dY y轴平移量
     */
    public void setSourceTranslationY(float dY) {
        pointSource.set(pointSource.x, pointSourceCache.y + dY);
        if (listener != null)
            listener.onSourceTranslation(0, dY);
        resetPath(pointSource, pointTarget);
        invalidate();
    }

    /**
     * 设置目标点的x轴平移量
     *
     * @param dX x轴平移量
     */
    public void setTargetTranslationX(float dX) {
        pointTarget.set(pointTargetCache.x + dX, pointTarget.y);
        if (listener != null)
            listener.onTargetTranslation(dX, 0);
        resetPath(pointSource, pointTarget);
        invalidate();
    }

    /**
     * 设置目标点的y轴平移量
     *
     * @param dY y轴平移量
     */
    public void setTargetTranslationY(float dY) {
        pointTarget.set(pointTarget.x, pointTargetCache.y + dY);
        if (listener != null)
            listener.onTargetTranslation(0, dY);
        resetPath(pointSource, pointTarget);
        invalidate();
    }

    public void setOnTranslationListener(OnTranslationListener listener) {
        this.listener = listener;
    }

    public interface OnTranslationListener {
        void onSourceTranslation(float dX, float dY);

        void onTargetTranslation(float dX, float dY);
    }
}
