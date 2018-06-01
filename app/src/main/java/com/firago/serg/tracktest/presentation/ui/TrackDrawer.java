package com.firago.serg.tracktest.presentation.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.firago.serg.tracktest.util.Coordinate;

import java.util.List;

public class TrackDrawer extends View {

    private static final String TAG = "TrackDrawer";
    private static final float pointInMeter = 10f;
    private static final float SENSITIVITY = 1f;
    public static final String KEY_SUPER_STATE = "com.firago.serg.tracktest.presentation.ui.superState";
    public static final String KEY_POSX = "com.firago.serg.tracktest.presentation.ui.POSX";
    public static final String KEY_POSY = "com.firago.serg.tracktest.presentation.ui.POSY";
    private final Paint paint;
    private final Path drawPath;

    private Path path;
    private Paint paintPath;
    private Paint paintRadar;

    private float X0 = 250;
    private float Y0 = 250;
    private float touchX;
    private float touchY;
    private float posX;
    private float posY;
    Matrix matrix = new Matrix();
    private float currentMarkerDiametr = 30F;
    private float radarDiameter = 400F;

    public TrackDrawer(Context context, AttributeSet attributes) {
        super(context, attributes);
        paint = new Paint();
        paint.setColor(0xFF80FF80);
        paint.setStyle(Paint.Style.FILL);

        paintPath = new Paint();
        paintPath.setColor(Color.BLACK);
        paintPath.setStrokeWidth(3);
        paintPath.setStyle(Paint.Style.STROKE);

        paintRadar = new Paint();
        paintRadar.setColor(0x4000FF00);
        paintRadar.setStyle(Paint.Style.FILL);

        path = new Path();
        drawPath = new Path();
    }

    public void setData(List<Coordinate> data) {
        path.reset();
        path.moveTo(X0, Y0);

        for (Coordinate coordinate : data) {

            Log.d(TAG, "setData: coordinate " + coordinate.getX() + " " + coordinate.getY());
            float x = (float) coordinate.getX() * pointInMeter;
            float y = (float) coordinate.getY() * pointInMeter;
            path.lineTo(X0 + x, Y0 + y);
            Log.d(TAG, "setData: line " + x + " " + y);
        }

        invalidate();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawOvalInCenter(canvas, paintRadar, X0, Y0, radarDiameter);

        drawOvalInCenter(canvas, paint, X0, Y0, currentMarkerDiametr);


        drawPath.reset();
        path.transform(matrix, drawPath);
        canvas.drawPath(drawPath, paintPath);


    }

    private void drawOvalInCenter(Canvas canvas, Paint ovalPaint, float x, float y, float diameter) {
        drawPath.reset();
        RectF oval = new RectF(x - diameter/2, y - diameter/2, x+diameter/2, y+diameter/2);
        drawPath.addOval(oval, Path.Direction.CW);
        drawPath.transform(matrix);
        canvas.drawPath(drawPath, ovalPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        X0 = w / 2;
        Y0 = h / 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                int actionIndex = event.getActionIndex();
                float x = event.getX(actionIndex);
                float y = event.getY(actionIndex);

                touchX = x;
                touchY = y;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int actionIndex = event.getActionIndex();
                float x = event.getX(actionIndex);
                float y = event.getY(actionIndex);
                posX += x - touchX;
                posY += y - touchY;

                resetMatrix();

                invalidate();
                touchX = x;
                touchY = y;
            }

        }

        return true;
    }

    private void resetMatrix() {
        matrix.setTranslate(posX/SENSITIVITY, posY/SENSITIVITY);
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_SUPER_STATE, super.onSaveInstanceState());
        bundle.putFloat(KEY_POSX, posX);
        bundle.putFloat(KEY_POSY, posY);
        Log.d(TAG, "onSaveInstanceState: save");
        return  bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle){
            Bundle bundle = (Bundle) state;
            posX = bundle.getFloat(KEY_POSX);
            posY = bundle.getFloat(KEY_POSY);
            resetMatrix();
            state = bundle.getParcelable(KEY_SUPER_STATE);
            Log.d(TAG, "onRestoreInstanceState: restore");
        }
        super.onRestoreInstanceState(state);
    }
}
