package com.think.guoyh.ui.demo.view.fish;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FishDrawable2 extends Drawable {

    private Path mPath;
    private Paint mPaint;

    // 除鱼身外的所有透明度
    private final static int OTHER_ALPHA = 110;
    // 鱼身透明度
    private final static int BODY_ALPHA = 160;
    // 转弯更自然的重心（身体的中心点）
    private PointF middlePoint;
    // 鱼的主角度
    private float fishMainAngle = 90;

    public final static float HEAD_RADIUS = 50;

    // 鱼身长度
    private final static float BODY_LENGTH = 3.2f * HEAD_RADIUS;
    // ---------------鱼鳍-------------------
    // 寻找鱼鳍开始点的线长
    private final static float FIND_FINS_LENGTH = 0.9f * HEAD_RADIUS;
    // 鱼鳍的长度
    private final static float FINS_LENGTH = 1.3f * HEAD_RADIUS;
    // -------------鱼尾---------------
    // 尾部大圆的半径(圆心就是身体底部的中点)
    private final float BIG_CIRCLE_RADIUS = HEAD_RADIUS * 0.7f;
    // 尾部中圆的半径
    private final float MIDDLE_CIRCLE_RADIUS = BIG_CIRCLE_RADIUS * 0.6f;
    // 尾部小圆的半径
    private final float SMALL_CIRCLE_RADIUS = MIDDLE_CIRCLE_RADIUS * 0.4f;
    // --寻找尾部中圆圆心的线长
    private final float FIND_MIDDLE_CIRCLE_LENGTH = BIG_CIRCLE_RADIUS + MIDDLE_CIRCLE_RADIUS;
    // --寻找尾部小圆圆心的线长
    private final float FIND_SMALL_CIRCLE_LENGTH = MIDDLE_CIRCLE_RADIUS * (0.4f + 2.7f);
    // --寻找大三角形底边中心点的线长
    private final float FIND_TRIANGLE_LENGTH = MIDDLE_CIRCLE_RADIUS * 2.7f;

    private float currentValue = 0;

    public FishDrawable2() {
        init();
    }

    private void init() {
        mPath = new Path();// 路径
        mPaint = new Paint();// 画笔
        mPaint.setStyle(Paint.Style.FILL);// 画笔类型，填充
        mPaint.setARGB(OTHER_ALPHA, 244, 92, 71);// 设置颜色
        mPaint.setAntiAlias(true);// 抗锯齿
        mPaint.setDither(true);// 防抖

        middlePoint = new PointF(4.19f * HEAD_RADIUS, 4.19f * HEAD_RADIUS);

//        ValueAnimator valueAnimator = ValueAnimator.ofFloat(-20, 20);
//        valueAnimator.setDuration(100);
//        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
//        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
//        valueAnimator.setInterpolator(new LinearInterpolator());
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animator) {
//                currentValue = (float) animator.getAnimatedValue();
//                invalidateSelf();
//            }
//        });
//        valueAnimator.start();
    }

    // view ondraw
    @Override
    public void draw(@NonNull Canvas canvas) {
        float fishAngle = fishMainAngle+currentValue;

        // 绘制鱼头
        PointF headPoint = calculatPoint(middlePoint, BODY_LENGTH / 2, fishAngle);
        canvas.drawCircle(headPoint.x, headPoint.y, HEAD_RADIUS, mPaint);

        // 绘制右鱼鳍
        PointF rightFinsPoint = calculatPoint(headPoint, FIND_FINS_LENGTH, fishAngle - 110);
        makeFins(canvas, rightFinsPoint, fishAngle, true);

        // 绘制左鱼鳍
        PointF leftFinsPoint = calculatPoint(headPoint, FIND_FINS_LENGTH, fishAngle + 110);
        makeFins(canvas, leftFinsPoint, fishAngle, false);

        // 身体底部的中心点
        PointF bodyBottomCenterPoint = calculatPoint(headPoint, BODY_LENGTH, fishAngle - 180);
        // 画节肢1
        makeSegment(canvas, bodyBottomCenterPoint, BIG_CIRCLE_RADIUS, MIDDLE_CIRCLE_RADIUS,
                FIND_MIDDLE_CIRCLE_LENGTH, fishAngle, true);

        // 画节肢2
        PointF middleCircleCenterPoint = calculatPoint(bodyBottomCenterPoint,
                FIND_MIDDLE_CIRCLE_LENGTH, fishAngle - 180);
        makeSegment(canvas, middleCircleCenterPoint, MIDDLE_CIRCLE_RADIUS, SMALL_CIRCLE_RADIUS,
                FIND_SMALL_CIRCLE_LENGTH, fishAngle, false);

        // 画尾巴
        makeTriangle(canvas, middleCircleCenterPoint, FIND_TRIANGLE_LENGTH,
                BIG_CIRCLE_RADIUS, fishAngle);
        makeTriangle(canvas, middleCircleCenterPoint, FIND_TRIANGLE_LENGTH - 10,
                BIG_CIRCLE_RADIUS - 20, fishAngle);

        // 绘制鱼身
        makeBody(canvas, headPoint, bodyBottomCenterPoint, fishAngle);
    }

    private void makeBody(Canvas canvas, PointF headPoint, PointF bodyBottomCenterPoint, float fishAngle) {
        // 身体的四个点
        PointF topLeftPoint = calculatPoint(headPoint, HEAD_RADIUS, fishAngle + 90);
        PointF topRightPoint = calculatPoint(headPoint, HEAD_RADIUS, fishAngle - 90);
        PointF bottomLeftPoint = calculatPoint(bodyBottomCenterPoint, BIG_CIRCLE_RADIUS,
                fishAngle + 90);
        PointF bottomRightPoint = calculatPoint(bodyBottomCenterPoint, BIG_CIRCLE_RADIUS,
                fishAngle - 90);

        // 二阶贝塞尔曲线的控制点，决定鱼的胖瘦
        PointF controlLeft = calculatPoint(headPoint, BODY_LENGTH * 0.56f,
                fishAngle + 130);
        PointF controlRight = calculatPoint(headPoint, BODY_LENGTH * 0.56f,
                fishAngle - 130);

        // 画身体
        mPath.reset();
        mPath.moveTo(topLeftPoint.x, topLeftPoint.y);
        mPath.quadTo(controlLeft.x, controlLeft.y, bottomLeftPoint.x, bottomLeftPoint.y);
        mPath.lineTo(bottomRightPoint.x, bottomRightPoint.y);
        mPath.quadTo(controlRight.x, controlRight.y, topRightPoint.x, topRightPoint.y);
        mPaint.setAlpha(BODY_ALPHA);
        canvas.drawPath(mPath, mPaint);
    }

    private void makeTriangle(Canvas canvas, PointF startPoint,
                              float findCenterLength, float findEdgeLength, float fishAngle) {
        // 三角形底边的中心点
        PointF centerPoint = calculatPoint(startPoint, findCenterLength, fishAngle - 180);
        // 三角形底边的两点
        PointF leftPoint = calculatPoint(centerPoint, findEdgeLength, fishAngle + 90);
        PointF rightPoint = calculatPoint(centerPoint, findEdgeLength, fishAngle - 90);

        // 绘制三角形
        mPath.reset();
        mPath.moveTo(startPoint.x, startPoint.y);
        mPath.lineTo(leftPoint.x, leftPoint.y);
        mPath.lineTo(rightPoint.x, rightPoint.y);
        canvas.drawPath(mPath, mPaint);
    }

    private void makeSegment(Canvas canvas, PointF bottomCenterPoint, float bigRadius,
                             float smallRadius, float findSmallCircleLength, float fishAngle,
                             boolean hasBigCircle) {
        // 梯形上底的中心点(中等大的圆的圆心)
        PointF upperCenterPoint = calculatPoint(bottomCenterPoint, findSmallCircleLength,
                fishAngle - 180);
        // 梯形的四个点
        PointF bottomLeftPoint = calculatPoint(bottomCenterPoint, bigRadius, fishAngle + 90);
        PointF bottomRightPoint = calculatPoint(bottomCenterPoint, bigRadius, fishAngle - 90);
        PointF upperLeftPoint = calculatPoint(upperCenterPoint, smallRadius, fishAngle + 90);
        PointF upperRightPoint = calculatPoint(upperCenterPoint, smallRadius, fishAngle - 90);
        if (hasBigCircle) {
            // 画大圆
            canvas.drawCircle(bottomCenterPoint.x, bottomCenterPoint.y, bigRadius, mPaint);
        }
        // 画小圆
        canvas.drawCircle(upperCenterPoint.x, upperCenterPoint.y, smallRadius, mPaint);
        // 画梯形
        mPath.reset();
        mPath.moveTo(bottomLeftPoint.x, bottomLeftPoint.y);
        mPath.lineTo(upperLeftPoint.x, upperLeftPoint.y);
        mPath.lineTo(upperRightPoint.x, upperRightPoint.y);
        mPath.lineTo(bottomRightPoint.x, bottomRightPoint.y);
        canvas.drawPath(mPath, mPaint);
    }

    private void makeFins(Canvas canvas, PointF startPoint, float fishAngle, boolean isRightFins) {
        float controlAngle = 115;

        PointF endPoint = calculatPoint(startPoint, FINS_LENGTH, fishAngle - 180);

        PointF controlPoint = calculatPoint(startPoint, 1.8f * FINS_LENGTH,
                isRightFins ? fishAngle - controlAngle : fishAngle + controlAngle);

        mPath.reset();

        mPath.moveTo(startPoint.x, startPoint.y);
        mPath.quadTo(controlPoint.x, controlPoint.y, endPoint.x, endPoint.y);
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * @param startPoint 起始点
     * @param length     两点的距离
     * @param angle      两点连线与x轴的夹角
     * @return
     */
    public static PointF calculatPoint(PointF startPoint, float length, float angle) {
        // cosα * c =
        float deltaX = (float) (Math.cos(Math.toRadians(angle)) * length);
        float deltaY = (float) (Math.sin(Math.toRadians(angle - 180)) * length);
        return new PointF(startPoint.x + deltaX, startPoint.y + deltaY);
    }

    @Override
    public void setAlpha(int i) {
        mPaint.setAlpha(i);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicHeight() {
        return (int) (8.38f * HEAD_RADIUS);
    }

    @Override
    public int getIntrinsicWidth() {
        return (int) (8.38f * HEAD_RADIUS);
    }
}
