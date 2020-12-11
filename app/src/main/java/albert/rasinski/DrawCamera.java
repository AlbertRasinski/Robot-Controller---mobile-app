package albert.rasinski;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

//androidx.appcompat.widget.AppCompatImageView
public class DrawCamera extends AppCompatImageView implements Runnable{
    public Bitmap bitmap;
    private boolean readyToDraw;
    private int heightPx;
    private int widthPx;
    private final double ratio = 960.0 / 540.0;

    public DrawCamera(@NonNull Context context) {
        super(context);
        init(context);
    }

    public DrawCamera(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DrawCamera(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point realSize = new Point();
        display.getRealSize(realSize);
        heightPx = realSize.x;
        widthPx = realSize.y;
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        bitmap = Bitmap.createBitmap(widthPx, heightPx, config);
        readyToDraw = false;
    }

    public void setReadiness(){
        readyToDraw = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        bitmap = Bitmap.createScaledBitmap(bitmap,(int)(ratio * (double) widthPx), widthPx, true);
        canvas.drawBitmap(bitmap,0,0,null);
    }

    @Override
    public void run() {
        while(true){
            if (readyToDraw){
                readyToDraw = false;
                postInvalidate();
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}