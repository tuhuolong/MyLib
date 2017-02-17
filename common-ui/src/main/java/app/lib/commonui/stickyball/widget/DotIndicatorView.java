
package app.lib.commonui.stickyball.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import app.lib.commonui.R;
import app.lib.commonui.stickyball.utils.DimensionUtil;

public class DotIndicatorView extends RelativeLayout implements ViewPager.OnPageChangeListener {

    private final static int DEFAULT_COLOR_UNSELECTED = Color.parseColor("#a9a9a9");
    private final static int DEFAULT_COLOR_SELECTED = Color.parseColor("#444444");
    public static ISelectedView NORMAL_BALL;
    public static ISelectedView STICKY_BALL;
    private static int DEFAULT_DOT_SPACING, DEFAULT_DOT_RADIUS;
    private int colorUnSelected;
    private int colorSelected;
    private int dotCenterDistance;// the distance of the dots centers. px
    private int dotRadius;// the radius of the dots(circle). px
    private int dotCount;
    private int paddingLeft, paddingTop, paddingRight, paddingBottom;
    private Paint paintSelected, paintUnSelected;
    private PointF[] dotPoints;
    private ISelectedView selectedView;
    private LayoutParams selectedViewParams;
    private DotIndicatorInfo info;
    private int position = 0;

    public DotIndicatorView(Context context) {
        this(context, null);
    }

    public DotIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(Color.TRANSPARENT);
        DEFAULT_DOT_SPACING = DimensionUtil.dip2px(getContext(), 10);
        DEFAULT_DOT_RADIUS = DimensionUtil.dip2px(getContext(), 3);
        initAttrs(attrs);
        initData();
        setClipChildren(false);
        setClipToPadding(false);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs,
                R.styleable.DotIndicatorView);
        colorSelected = ta.getColor(R.styleable.DotIndicatorView_color_selected,
                DEFAULT_COLOR_SELECTED);
        colorUnSelected = ta.getColor(R.styleable.DotIndicatorView_color_unselected,
                DEFAULT_COLOR_UNSELECTED);
        dotCenterDistance = ta.getDimensionPixelSize(
                R.styleable.DotIndicatorView_dot_center_distance, DEFAULT_DOT_SPACING);
        dotRadius = ta.getDimensionPixelSize(R.styleable.DotIndicatorView_dot_radius,
                DEFAULT_DOT_RADIUS);
        dotCount = ta.getInt(R.styleable.DotIndicatorView_dot_count, 2);
        ta.recycle();

        paddingLeft = getPaddingLeft();
        paddingTop = getPaddingTop();
        paddingRight = getPaddingRight();
        paddingBottom = getPaddingBottom();
    }

    private void initData() {
        paintSelected = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintSelected.setColor(colorSelected);
        paintUnSelected = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintUnSelected.setColor(colorUnSelected);

        // dotPoints = new PointF[dotCount];
        // for (int i = 0; i < dotCount; i++) {
        // float x = paddingLeft + (i * dotCenterDistance) + dotRadius;
        // float y = paddingTop + dotRadius;
        // dotPoints[i] = new PointF(x, y);
        // }

        refreshDotPoints();

        selectedViewParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        STICKY_BALL = new SelectedStickyBall(getContext());
    }

    private void refreshDotPoints() {
        dotPoints = new PointF[dotCount];
        for (int i = 0; i < dotCount; i++) {
            float x = paddingLeft + (i * dotCenterDistance) + dotRadius;
            float y = paddingTop + dotRadius;
            dotPoints[i] = new PointF(x, y);
        }
    }

    public void setCurrentItem(int position) {
        this.position = position;
        if (selectedView != null)
            selectedView.onSelected(position);
    }

    public void setSelectedView(ISelectedView selectedView) {
        this.selectedView = selectedView;
        if (selectedView != null) {

            addView(selectedView.getView(this), selectedViewParams);

            if (info == null)
                info = new DotIndicatorInfo();
            info.setColorSelected(colorSelected);
            info.setDotCenterDistance(dotCenterDistance);
            info.setDotCount(dotCount);
            info.setDotRadius(dotRadius);
            info.setDotPoints(dotPoints);
            info.setPosition(position);
            info.setPaddingLeft(paddingLeft);
            info.setPaddingTop(paddingTop);
            info.setPaddingRight(paddingRight);
            info.setPaddingBottom(paddingBottom);
            selectedView.onCreatedIndicator(info);
        }
    }

    public void setColorUnSelected(int colorUnSelected) {
        this.colorUnSelected = colorUnSelected;
        invalidate();
    }

    public void setColorSelected(int colorSelected) {
        this.colorSelected = colorSelected;
        if (info != null && selectedView != null) {
            info.setColorSelected(colorUnSelected);
            selectedView.onCreatedIndicator(info);
        }
        invalidate();
    }

    public void setDotCenterDistance(int dotCenterDistance) {
        this.dotCenterDistance = dotCenterDistance;
        if (info != null && selectedView != null) {
            info.setDotCenterDistance(dotCenterDistance);
            selectedView.onCreatedIndicator(info);
        }
        invalidate();
    }

    public void setDotRadius(int dotRadius) {
        this.dotRadius = dotRadius;
        if (info != null && selectedView != null) {
            info.setDotRadius(dotRadius);
            selectedView.onCreatedIndicator(info);
        }
        invalidate();
    }

    public void setDotCount(int dotCount) {
        this.dotCount = dotCount;
        refreshDotPoints();
        if (info != null && selectedView != null) {
            info.setDotPoints(dotPoints);
            info.setDotCount(dotCount);
            selectedView.onCreatedIndicator(info);
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (PointF pointF : dotPoints) {
            canvas.drawCircle(pointF.x, pointF.y, dotRadius, paintUnSelected);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = measureWidth(widthMeasureSpec);
        int measuredHeight = measureHeight(heightMeasureSpec);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    private int measureHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int result = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.AT_MOST)
            result = paddingTop + paddingBottom + dotRadius * 2;
        return result;
    }

    private int measureWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int result = MeasureSpec.getSize(widthMeasureSpec);
        if (mode == MeasureSpec.AT_MOST)
            result = paddingLeft + paddingRight + dotRadius * 2
                    + (dotCount - 1) * dotCenterDistance;
        return result;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setCurrentItem(position % dotCount);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
