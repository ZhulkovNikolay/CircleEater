package com.squirrel.nzhulkov.squirrel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class CanvasView extends View implements ICanvsView {
    private static int height;
    private static int width;
    private GameManager gameManager;
    private Paint paint;
    private Canvas canvas;
    private Toast toast;

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initWidthAndHeight(context);
        initPaint();
        gameManager = new GameManager(this, width, height);
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
    }

    // определяем размер экрана телефона
    private void initWidthAndHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        width = point.x; // координата правой нижней точки
        height = point.y;
    }

    // когда CanvasView будет отображаться на экране, будет вызван метод onDraw
    // все что мы в нем напишем, отобразится на экране
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        gameManager.onDraw();
    }

    // мы не хотим чтобы ГеймМенеджер рисовал сразу на вьюшке
    // поэтому мы имплементируем интерфейс
    // рисуем на канвасе круг

    @Override
    public void drawCircle(SimpleCircle circle) {
        paint.setColor(circle.getColor());
        canvas.drawCircle(circle.getX(), circle.getY(), circle.getRadius(), paint);
    }

    @Override
    public void redraw() {
        invalidate();
    }

    @Override
    public void showMessage(String text) {
        if (toast != null) {
            toast.cancel();
        }
        //всплывающее сообщение
        toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        // двигается ли по экрану
        // тогда вызываем метод у геймМенеджера
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            gameManager.onTouchEvent(x, y);
        }
        // когда гем менеджер все пересчитал, нам нужно чтобы Вью себя перерисовала
        invalidate();
        return true;
    }

  //  TODO запретить повороты, сделать разные радиусы
  //  public static int recalculateRadius(int radius) {
  //      return radius * 768 / width < height ? width : height;
  //  }
}
