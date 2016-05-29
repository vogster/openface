package com.unlogicon.openface.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.unlogicon.openface.App;
import com.unlogicon.openface.model.Bboxes;
import com.unlogicon.openface.model.Detect;

import java.util.ArrayList;

/**
 * Created by Nik on 08.05.2016.
 */
public class DrawView extends ImageView {

    private Context context;
    private Detect detect;

    private float bitmapHeight;
    private float bitmapWidth;
    private float serverHeight;
    private float serverWidth;
    private float viewHeight;
    private float viewWidth;

    private ArrayList<Rect> rects = new ArrayList<Rect>();

    private OnRectClickListener onRectClickListener;


    public DrawView(Context context) {
        super(context);
        this.context = context;

    }

    DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    DrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setDetect(Detect detect, View view, Context context){
        this.detect = detect;
        this.context = context;
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        this.bitmapWidth = ((App) context.getApplicationContext()).getSharedPreferencesHelper().getServerWidth();
        this.bitmapHeight = ((App) context.getApplicationContext()).getSharedPreferencesHelper().getServerHeight();
        this.serverHeight = ((App) context.getApplicationContext()).getSharedPreferencesHelper().getServerHeight();
        this.serverWidth = ((App) context.getApplicationContext()).getSharedPreferencesHelper().getServerWidth();
        this.viewWidth = ((App) context.getApplicationContext()).getSharedPreferencesHelper().getServerWidth();
        this.viewHeight = ((App) context.getApplicationContext()).getSharedPreferencesHelper().getServerHeight();

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);

        for (Bboxes bboxes : detect.getBboxes()){
            canvas.drawRect(countX((float) bboxes.getX1()), countY((float) bboxes.getY1()) , countX((float) bboxes.getX2()), countY((float) bboxes.getY2()), paint);
            rects.add(new Rect((int) countX((float) bboxes.getX1()), (int) countY((float) bboxes.getY1()) , (int) countX((float) bboxes.getX2()),(int) countY((float) bboxes.getY2())));
        }
    }

    private float countY(float paramFloat)
    {
        return paramFloat / this.serverHeight * this.viewHeight;
    }


    private float countX(float paramFloat)
    {
        float f = countActualWidth(this.viewWidth, this.bitmapWidth, this.viewHeight, this.bitmapHeight);
        return f * (paramFloat / this.serverWidth * this.viewWidth / this.viewWidth) + (this.viewWidth - f) / 2.0F;
    }

    public static float countActualWidth(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
        if (paramFloat3 * paramFloat2 <= paramFloat1 * paramFloat4) {
            return paramFloat2 * paramFloat3 / paramFloat4;
        }
        return paramFloat1;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float x = event.getX();
        float y = event.getY();
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                for (int i = 0; i < detect.getBboxes().size(); i++){
                    if (rects.get(i).contains((int)x, (int)y)){
                        if (onRectClickListener != null) {
                            onRectClickListener.onClickRect(i);
                        }
                        break;
                    }
                }
                return true;
        }
        return false;
    }

    public interface OnRectClickListener{
        void onClickRect(int bbox_position);
    }

    public void setRectClickListener(OnRectClickListener onRectClickListener){
        this.onRectClickListener = onRectClickListener;
    }

}