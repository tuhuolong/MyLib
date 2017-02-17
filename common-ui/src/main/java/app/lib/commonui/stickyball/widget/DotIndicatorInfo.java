
package app.lib.commonui.stickyball.widget;

import android.graphics.PointF;

public class DotIndicatorInfo {
    private int colorSelected;
    private int dotCenterDistance;// the distance of the dots centers. px
    private int dotRadius;// the radius of the dots(circle). px
    private int dotCount;

    private int paddingLeft, paddingTop, paddingRight, paddingBottom;

    private PointF[] dotPoints;
    private int position;

    public int getColorSelected() {
        return colorSelected;
    }

    public void setColorSelected(int colorSelected) {
        this.colorSelected = colorSelected;
    }

    public int getDotCenterDistance() {
        return dotCenterDistance;
    }

    public void setDotCenterDistance(int dotCenterDistance) {
        this.dotCenterDistance = dotCenterDistance;
    }

    public int getDotRadius() {
        return dotRadius;
    }

    public void setDotRadius(int dotRadius) {
        this.dotRadius = dotRadius;
    }

    public int getDotCount() {
        return dotCount;
    }

    public void setDotCount(int dotCount) {
        this.dotCount = dotCount;
    }

    public int getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public int getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    public int getPaddingRight() {
        return paddingRight;
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
    }

    public int getPaddingBottom() {
        return paddingBottom;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    public PointF[] getDotPoints() {
        return dotPoints;
    }

    public void setDotPoints(PointF[] dotPoints) {
        this.dotPoints = dotPoints;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
