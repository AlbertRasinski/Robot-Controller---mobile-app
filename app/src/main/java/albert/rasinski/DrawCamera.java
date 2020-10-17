package albert.rasinski;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.Image;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

public class DrawCamera extends View {
    public static Bitmap bitmap;
    private int heightPx;
    private int widthPx;
    private double ratio;

    public DrawCamera(Context context) {
        super(context);
        init(context);
    }

    public DrawCamera(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DrawCamera(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public DrawCamera(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point realSize = new Point();
        display.getRealSize(realSize);
        heightPx = realSize.x;
        widthPx = realSize.y;

        Bitmap tmpBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ccc);
        int widthImage = tmpBitmap.getWidth();
        int heightImage = tmpBitmap.getHeight();
        ratio = (float)(heightImage) / (float)(widthImage);
        Log.d("halo",""+ratio);
        bitmap = Bitmap.createScaledBitmap(tmpBitmap,(int)(ratio * (float)heightPx), widthPx, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("testb", "want to draw");
        Log.d("testb", "" + bitmap.getByteCount());
        canvas.drawBitmap(bitmap,0,0,null);
        /*if (bitmap != null){
            Log.d("testb", "start draw");

            Log.d("testb", "stop draw");
        }else{
            Log.d("testb", "jest pusty");
        }*/
    }
}
