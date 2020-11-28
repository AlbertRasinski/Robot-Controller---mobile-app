package albert.rasinski;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DrawCamera extends androidx.appcompat.widget.AppCompatImageView{
    public Bitmap bitmap;
    private boolean readyToSend;
    private int heightPx;
    private int widthPx;
    final double ratio = 960.0 / 540.0;

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
        readyToSend = false;
    }

    public boolean readyToDraw(){
        if (readyToSend){
            readyToSend = false;
            return true;
        }else{
            return false;
        }
    }

    public void setReadiness(){
        readyToSend = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        bitmap = Bitmap.createScaledBitmap(bitmap,(int)(ratio * (double) widthPx), widthPx, true);
        canvas.drawBitmap(bitmap,0,0,null);
    }
}