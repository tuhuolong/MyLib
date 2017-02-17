
package app.lib.commonui.stickyball.utils;

import android.graphics.PointF;

public class GeometryUtil {

    /**
     * As meaning of method name. 获得两点之间的距离
     *
     * @param p0
     * @param p1
     * @return
     */
    public static float getDistanceBetween2Points(PointF p0, PointF p1) {
        float distance = (float) Math.sqrt(Math.pow(p0.y - p1.y, 2) + Math.pow(p0.x - p1.x, 2));
        return distance;
    }

    /**
     * Get middle point between p1 and p2. 获得两点连线的中点
     *
     * @param p1
     * @param p2
     * @return
     */
    public static PointF getControlPoint(PointF p1, PointF p2) {
        return new PointF((p1.x + p2.x) / 2.0f, (p1.y + p2.y) / 2.0f);
    }

    /**
     * Get control point between o1 and o2. 获得控制点（默认控制点是两个圆间距的中点）
     *
     * @param o1 移动点
     * @param o2 原点
     * @return
     */
    public static PointF getControlPoint(PointF o1, PointF o2, float r1, float r2) {
        float l = getDistanceBetween2Points(o1, o2);
        float d = l - r1 - r2;
        float k = (r2 + d / 2f) / l;
        return new PointF((o1.x - o2.x) * k + o2.x, (o1.y - o2.y) * k + o2.y);
    }

    /**
     * Get point between p1 and p2 by percent. 根据百分比获取两点之间的某个点坐标
     *
     * @param p1
     * @param p2
     * @param percent
     * @return
     */
    public static PointF getPointByPercent(PointF p1, PointF p2, float percent) {
        return new PointF(evaluateValue(percent, p1.x, p2.x), evaluateValue(percent, p1.y, p2.y));
    }

    /**
     * 根据分度值，计算从start到end中，fraction位置的值。fraction范围为0 -> 1
     *
     * @param fraction
     * @param start
     * @param end
     * @return
     */
    public static float evaluateValue(float fraction, Number start, Number end) {
        return start.floatValue() + (end.floatValue() - start.floatValue()) * fraction;
    }

    /**
     * 获取四个锚点
     *
     * @param o1 移动点
     * @param o2 原点
     * @param r1 移动点圆形半径
     * @param r2 原点圆形半径
     * @param k 两点圆心连线的斜率
     * @return
     */
    public static PointF[] getIntersectionPoints(PointF o1, PointF o2, float r1, float r2,
            double k) {
        PointF p1 = new PointF();
        PointF p2 = new PointF();
        PointF p3 = new PointF();
        PointF p4 = new PointF();
        float alpha, beta, theta;
        float d;
        theta = (float) Math.atan(k);
        d = getDistanceBetween2Points(o1, o2) - r1 - r2;
        alpha = (float) Math.asin(r1 / (r1 + d / 2));
        beta = (float) Math.acos(r2 / (r2 + d / 2));
        float dX1, dY1, dX2, dY2;
        if (!Double.isNaN(k) && !Double.isInfinite(k)) {
            dX1 = (float) (r1 * Math.cos(Math.PI / 2 - alpha - theta));
            dY1 = (float) (r1 * Math.sin(Math.PI / 2 - alpha - theta));
            p1.set(o1.x - dX1, o1.y + dY1);
        } else {
            dX1 = (float) (r1 * Math.cos(alpha));
            dY1 = (float) (r1 * Math.sin(alpha));
            if (o1.y - o2.y > 0)
                p1.set(o1.x - dX1, o1.y - dY1);
            else
                p1.set(o1.x - dX1, o1.y + dY1);

        }
        if (!Double.isNaN(k) && !Double.isInfinite(k)) {
            dX2 = (float) (r2 * Math.cos(beta + theta));
            dY2 = (float) (r2 * Math.sin(beta + theta));
            p2.set(o2.x + dX2, o2.y + dY2);
        } else {
            dX2 = (float) (r2 * Math.sin(beta));
            dY2 = (float) (r2 * Math.cos(beta));
            if (o1.y - o2.y > 0)
                p2.set(o2.x - dX2, o2.y + dY2);
            else
                p2.set(o2.x - dX2, o2.y - dY2);

        }
        // 以o2为原点的p1、p2相对坐标值
        float[] p1XY = {
                p1.x - o2.x, p1.y - o2.y
        };
        float[] p2XY = {
                p2.x - o2.x, p2.y - o2.y
        };
        double a = k, b = -1;
        // 以o2为原点的p3、p4的相对坐标
        float p3X, p3Y, p4X, p4Y;
        if (!Double.isNaN(k) && !Double.isInfinite(k)) {
            p3X = (float) (p1XY[0]
                    - 2 * a * (a * p1XY[0] + b * p1XY[1]) / (Math.pow(a, 2) + Math.pow(b, 2)));
            p3Y = (float) (p1XY[1]
                    - 2 * b * (a * p1XY[0] + b * p1XY[1]) / (Math.pow(a, 2) + Math.pow(b, 2)));
            p4X = (float) (p2XY[0]
                    - 2 * a * (a * p2XY[0] + b * p2XY[1]) / (Math.pow(a, 2) + Math.pow(b, 2)));
            p4Y = (float) (p2XY[1]
                    - 2 * b * (a * p2XY[0] + b * p2XY[1]) / (Math.pow(a, 2) + Math.pow(b, 2)));
        } else {
            p3X = -p1XY[0];
            p3Y = p1XY[1];
            p4X = -p2XY[0];
            p4Y = p2XY[1];
        }
        p3.set(o2.x + p3X, o2.y + p3Y);
        p4.set(o2.x + p4X, o2.y + p4Y);
        PointF[] points = {
                p1, p2, p3, p4
        };
        return points;
    }
}
