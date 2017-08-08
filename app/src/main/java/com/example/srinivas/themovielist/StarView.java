package com.example.srinivas.themovielist;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Srinivas on 8/8/2017.
 */

public class StarView extends View {
    Paint paint;

    Path path;
    private Paint paint1;

    public StarView(Context context) {
        super(context);
        init();
    }

    public StarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void init(){

         paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);

        paint1 = new Paint();
        paint1.setColor(Color.BLACK);

         path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        float mid = getWidth() / 3;
        float min = Math.min(getWidth(), getHeight());
        float fat = min / 17;
        float half = min / 2;
        float rad = half - 2f*fat;
        mid = mid - half;

        paint.setStrokeWidth(fat/6f);
        paint.setStyle(Paint.Style.STROKE);

        //canvas.drawCircle(mid + half, half, rad, paint);

        path.reset();
        paint1.setStrokeWidth(fat/6f);
        paint1.setStyle(Paint.Style.STROKE);


        // top left
        path.moveTo(mid + half * 0.5f, half * 0.84f);
        // top right
        path.lineTo(mid + half * 1.5f, half * 0.84f);
        // bottom left
        path.lineTo(mid + half * 0.68f, half * 1.45f);
        // top tip
        path.lineTo(mid + half * 1.0f, half * 0.5f);
        // bottom right
        path.lineTo(mid + half * 1.32f, half * 1.45f);
        // top left
        path.lineTo(mid + half * 0.5f, half * 0.84f);

        path.close();
        canvas.drawPath(path, paint1);
        paint.setTextSize(40);
        canvas.drawText("Mark as Favourite",mid + half * 0.3f, half * 1.7f,paint);

        super.onDraw(canvas);

    }
}
