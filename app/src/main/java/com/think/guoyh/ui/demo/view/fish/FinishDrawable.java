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

import com.base.gyh.baselib.utils.mylog.Logger;

/**
 * @auther Guoyh
 * @Date 2020/9/29
 **/
public class FinishDrawable extends Drawable {
    private Path mPath;
    private Paint mPaint;
    private Paint mTestPaint;

    //除了鱼之外的透明度
    private final static int otherAlpha = 110;
    //鱼透明度
    private final static int finsh = 160;
    //身体的重心点
    private PointF middlePoint;
    //鱼的主角度
    private float fishMainAngle = 90;


    //鱼头半径
    private final static float heardRadios = 90;

    //鱼身长度
    private final static float fish_body_length = 3.2f * heardRadios;

    //寻找 鱼鳍 开始点的线长
    private final static float find_fins_length = 0.9f * heardRadios;
    //鱼鳍的长度
    private final static float fins_length = 1.3f * heardRadios;

    //尾部大圆半径
    private final static float big_circle_radios = 0.7f * heardRadios;
    //尾部中圆半径
    private final static float middle_circle_radios = 0.5f * heardRadios;
    //尾部小圆半径
    private final static float small_circle_radios = 0.3f * heardRadios;
    //寻找尾部中圆圆心 的线长
    private final static float find_middle_circle_length = big_circle_radios + middle_circle_radios;
    //寻找尾部小圆圆心 的线长
    private final static float find_small_circle_length = middle_circle_radios * (0.4f + 2.7f);
    //寻找大三角形底边中心点的线长
    private final static float find_triangle_length = middle_circle_radios * 2.7f;


    public FinishDrawable() {
        init();
    }

    private void init() {
        mPath = new Path();//路径
        mTestPaint = new Paint();
        mPaint = new Paint();//画笔
        mPaint.setStyle(Paint.Style.FILL);//画笔类型 填充
        mTestPaint.setStyle(Paint.Style.FILL);//画笔类型 填充
        mPaint.setARGB(otherAlpha, 244, 92, 71);// 设置颜色
        mTestPaint.setARGB(255, 255, 255, 255);
        mPaint.setAntiAlias(true);//抗锯齿
        mTestPaint.setAntiAlias(true);//抗锯齿
        mPaint.setDither(true);//防抖
        mTestPaint.setDither(true);//防抖
        middlePoint = new PointF(4.19f * heardRadios, 4.19f * heardRadios);
    }


    @Override
    public void draw(@NonNull Canvas canvas) {
        float fishAngle = fishMainAngle;
        Logger.dd(middlePoint.x, middlePoint.y);

        testCicle(canvas, middlePoint.x, middlePoint.y);
        //画鱼头
        PointF heardPonitf = calculatPoint(middlePoint, fish_body_length / 2, fishAngle);
        testCicle(canvas, heardPonitf.x, heardPonitf.y);
        canvas.drawCircle(heardPonitf.x, heardPonitf.y, heardRadios, mPaint);

        Logger.dd(fishAngle, heardRadios);

        //画 右  鱼鳍
        PointF rightFinsPointF = calculatPoint(heardPonitf, find_fins_length, fishAngle - 110);
        makeFins(canvas, rightFinsPointF, fishAngle, true);
        testCicle(canvas, rightFinsPointF.x, rightFinsPointF.y);

        //画 左  鱼鳍
        PointF leftFinsPointF = calculatPoint(heardPonitf, find_fins_length, fishAngle + 110);
        makeFins(canvas, leftFinsPointF, fishAngle, false);
        testCicle(canvas, leftFinsPointF.x, leftFinsPointF.y);


        drawBody(canvas,heardPonitf,fishAngle);

        drawJiezhi1(canvas, fishAngle, heardPonitf);


//        //画 节支 2
//        PointF middleCircleCenterPoint = calculatPoint(bodyBottomCenterPoint, find_middle_circle_length, fishAngle - 180);
//        testCicle(canvas,middleCircleCenterPoint.x,middleCircleCenterPoint.y);
//        makeSegment(canvas,middleCircleCenterPoint,middle_circle_radios,small_circle_radios,find_small_circle_length,fishAngle,false);

    }

    private void drawBody(Canvas canvas,PointF heardPoint,float fishAngle) {
        float controlAngle = 115;
        PointF pintTopA = calculatPoint(heardPoint, fish_body_length, fishAngle - 180);


        PointF bodyTopLeft = calculatPoint(heardPoint, heardRadios, fishAngle + 90);
        PointF bodyTopRight = calculatPoint(heardPoint, heardRadios, fishAngle - 90);
        PointF controlLeftPoint = calculatPoint(heardPoint, fish_body_length, fishAngle + controlAngle);
        PointF controlRightPoint = calculatPoint(heardPoint, fish_body_length, fishAngle - controlAngle);

        PointF bodyBotLeft = calculatPoint(pintTopA, big_circle_radios,
                fishAngle + 90);
        PointF bodyBotRight = calculatPoint(pintTopA, big_circle_radios,
                fishAngle - 90);


        mPath.reset();
        mPath.moveTo(bodyTopLeft.x, bodyTopLeft.y);
        mPath.quadTo(controlLeftPoint.x, controlLeftPoint.y, bodyBotLeft.x, bodyBotLeft.y);
        mPath.lineTo(bodyBotRight.x, bodyBotRight.y);
        mPath.quadTo(controlRightPoint.x, controlRightPoint.y, bodyTopRight.x, bodyTopRight.y);
        canvas.drawPath(mPath, mPaint);
    }

    private void drawJiezhi1(Canvas canvas, float fishAngle, PointF heardPonitf) {
        //画 节支 1
        PointF pintTopA = calculatPoint(heardPonitf, fish_body_length, fishAngle - 180);
        testCicle(canvas,pintTopA);
        PointF pintBotoA = calculatPoint(pintTopA, (big_circle_radios+middle_circle_radios), fishAngle - 180);
        testCicle(canvas,pintBotoA);
        PointF pintTopLeftA = calculatPoint(pintTopA,big_circle_radios,fishAngle+90);
        PointF pintTopRightA = calculatPoint(pintTopA,big_circle_radios,fishAngle-90);
        PointF pintBotomLeftA = calculatPoint(pintBotoA,small_circle_radios,fishAngle+90);
        PointF pintBotomRightA = calculatPoint(pintBotoA,small_circle_radios,fishAngle-90);

        testCicle(canvas,pintTopLeftA);
        testCicle(canvas,pintTopRightA);
        testCicle(canvas,pintBotomLeftA);
        testCicle(canvas,pintBotomRightA);

        canvas.drawCircle(pintTopA.x, pintTopA.y, big_circle_radios, mPaint);
        canvas.drawCircle(pintBotoA.x, pintBotoA.y, middle_circle_radios, mPaint);

        mPath.reset();
        mPath.moveTo(pintTopLeftA.x, pintTopLeftA.y);
        mPath.lineTo(pintTopRightA.x, pintTopRightA.y);
        mPath.lineTo(pintBotomRightA.x, pintBotomRightA.y);
        mPath.lineTo(pintBotomLeftA.x, pintBotomLeftA.y);
        canvas.drawPath(mPath, mPaint);




    }

    private void makeSegment(Canvas canvas, PointF bottomCenterPoint, float bigRadius, float smallRadius, float findSmallCircleLength,
                             float fishAngle, boolean hasBigCircle) {
        //梯形上底的中心点（中等大的圆的圆形）
        PointF upperCenterPoint = calculatPoint(bottomCenterPoint, findSmallCircleLength, fishAngle - 180);
        testCicle(canvas, upperCenterPoint.x, upperCenterPoint.y);

        // 梯形的四个点
        PointF bottomLeftPoint = calculatPoint(bottomCenterPoint, bigRadius, fishAngle + 90);
        PointF bottomRightPoint = calculatPoint(bottomCenterPoint, bigRadius, fishAngle - 90);
        PointF upperLeftPoint = calculatPoint(upperCenterPoint, smallRadius, fishAngle + 90);
        PointF upperRightPoint = calculatPoint(upperCenterPoint, smallRadius, fishAngle - 90);
        testCicle(canvas, bottomLeftPoint.x, bottomLeftPoint.y);
        testCicle(canvas, bottomRightPoint.x, bottomRightPoint.y);
        testCicle(canvas, upperLeftPoint.x, upperLeftPoint.y);
        testCicle(canvas, upperRightPoint.x, upperRightPoint.y);

        if (hasBigCircle) {
            canvas.drawCircle(bottomCenterPoint.x, bottomCenterPoint.y, bigRadius, mPaint);
        }

        canvas.drawCircle(upperCenterPoint.x, upperCenterPoint.y, smallRadius, mPaint);

        mPath.reset();
        mPath.moveTo(bottomLeftPoint.x, bottomLeftPoint.y);
        mPath.lineTo(upperLeftPoint.x, upperLeftPoint.y);
        mPath.lineTo(upperRightPoint.x, upperRightPoint.y);
        mPath.lineTo(bottomRightPoint.x, bottomRightPoint.y);
        canvas.drawPath(mPath, mPaint);
    }


    //画鱼鳍
    private void makeFins(Canvas canvas, PointF startPoint, float fishAngle, boolean isRight) {
        float controlAngle = 115;

        PointF endPoint = calculatPoint(startPoint, fins_length, fishAngle - 180);

        PointF controlPoint = calculatPoint(startPoint, 1.8f * fins_length,
                isRight ? fishAngle - controlAngle : fishAngle + controlAngle);

        mPath.reset();
        mPath.moveTo(startPoint.x, startPoint.y);
        mPath.quadTo(controlPoint.x, controlPoint.y, endPoint.x, endPoint.y);
        canvas.drawPath(mPath, mPaint);
    }


    /**
     * @param startPoint 起始点
     * @param length     两点长度
     * @param angle      与x轴角度
     * @return
     */
    private PointF calculatPoint(PointF startPoint, float length, float angle) {
        float delax = (float) Math.cos(Math.toRadians(angle)) * length;
        float delay = (float) Math.sin(Math.toRadians(angle - 180)) * length;
        return new PointF(startPoint.x + delax, startPoint.y + delay);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
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

        return (int) (8.38f * heardRadios);
    }

    @Override
    public int getIntrinsicWidth() {
        return (int) (8.38f * heardRadios);
    }

    //画点 帮助理解
    private void testCicle(Canvas canvas, float x, float y) {
        Logger.dd(x, y);
        canvas.drawCircle(x, y, 5, mTestPaint);
    }

    //画点 帮助理解
    private void testCicle(Canvas canvas, PointF pointF) {
        testCicle(canvas,pointF.x, pointF.y);
    }
}
