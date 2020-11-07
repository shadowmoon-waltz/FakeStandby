package android.jonas.fakestandby.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import android.jonas.fakestandby.service.AccessibilityOverlayService;

public class OverlayView extends View {
    private float yBorder = 0;

    private boolean hiding = false;
    private float hidingVelocity = 40;

    private boolean falling = false;

    private OnHideFinishedListener onHideFinishedListener = null;

    private final int imhereSize = 10;
    private final int imhereColor = 0xffffffff;
    
    public OverlayView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        float h = height-yBorder;
        Paint ip = new Paint();
        canvas.drawRect(0,0, (float) width, h, ip);

        ip.setColor(imhereColor);
        canvas.drawRect(width/2-imhereSize/2, h/2-imhereSize/2, width/2+imhereSize/2, h/2+imhereSize/2, ip);
        
        if (hiding) {
            if (yBorder > height) {
                hiding = false;
                yBorder = 0;
                onHideFinishedListener.onHideFinished();
                return;
            }

            yBorder += hidingVelocity;
            invalidate();
            return;
        }

        if (falling) {
            if (yBorder < 0) {
                AccessibilityOverlayService.state = Constants.Overlay.State.VISIBLE;

                falling = false;
                yBorder = 0;
                invalidate();
                return;
            }else {
                yBorder -= 40;
            }
            invalidate();
            return;
        }
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public void setyBorder(float Y) {
        this.yBorder = Y;
        this.hiding = false;
        this.falling = false;
        invalidate();
    }

    public void setHiding(boolean hiding) {
        this.falling = false;

        this.hiding = hiding;
        invalidate();
    }

    public void setFalling(boolean falling) {
        this.hiding = false;

        this.falling = falling;
        invalidate();
    }

    public void setHidingVelocity(float velocity) {
        this.hidingVelocity = velocity;
    }

    public void setOnHideFinishedListener(OnHideFinishedListener onHideFinishedListener) {
        this.onHideFinishedListener = onHideFinishedListener;
    }

}
