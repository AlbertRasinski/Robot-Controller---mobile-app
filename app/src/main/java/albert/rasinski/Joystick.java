package albert.rasinski;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.RequiresApi;

public class Joystick extends View{
    class Position{
        int x;
        int y;

        public Position(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    private final int CONST_COORDINATION = 435;
    private final  Position POSITION_JS_BACKGROUND = new Position(CONST_COORDINATION, CONST_COORDINATION);
    private final int RADIUS_JS_BACKGROUND = 300;
    private final int RADIUS_JS_CONTROLLER_OUTER = 90;
    private final int RADIUS_JS_CONTROLLER_INNER = 65;

    private Position positionJsController;
    private Paint paintJsBackground;
    private Paint paintJsBackground_CONTOUR;
    private Paint paintJsController_INNER;
    private Paint paintMotor;

    private int radius;
    private int angleDegree;

    private int motorLeft;
    private int motorRight;

    public Joystick(Context context) {
        super(context);
        init(context);
    }

    public Joystick(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Joystick(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        positionJsController = new Position(CONST_COORDINATION, CONST_COORDINATION);
        paintJsBackground = new Paint();
        paintJsBackground.setColor(Color.argb(100,100,100,100));
        paintJsController_INNER = new Paint();
        paintJsController_INNER.setColor(Color.argb(160,255,255,255));
        paintJsBackground_CONTOUR = new Paint();
        paintJsBackground_CONTOUR.setColor(Color.argb(120,0,0,0));
        paintJsBackground_CONTOUR.setStyle(Paint.Style.STROKE);
        paintJsBackground_CONTOUR.setStrokeWidth(6);
        paintMotor = new Paint();
        paintMotor.setColor(Color.argb(160,250,10,10));

        radius = 0;
        angleDegree = 90;

        motorLeft = 100;
        motorRight = 100;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(0,getHeight());
        canvas.scale(1,-1);
        canvas.drawCircle(POSITION_JS_BACKGROUND.x,POSITION_JS_BACKGROUND.y,RADIUS_JS_BACKGROUND,paintJsBackground);
        canvas.drawOval(POSITION_JS_BACKGROUND.x - RADIUS_JS_BACKGROUND,POSITION_JS_BACKGROUND.y - RADIUS_JS_BACKGROUND,POSITION_JS_BACKGROUND.x + RADIUS_JS_BACKGROUND,POSITION_JS_BACKGROUND.y + RADIUS_JS_BACKGROUND, paintJsBackground_CONTOUR);
        canvas.drawOval(POSITION_JS_BACKGROUND.x - RADIUS_JS_BACKGROUND * 3 / 4,POSITION_JS_BACKGROUND.y - RADIUS_JS_BACKGROUND * 3 / 4,POSITION_JS_BACKGROUND.x + RADIUS_JS_BACKGROUND * 3 / 4,POSITION_JS_BACKGROUND.y + RADIUS_JS_BACKGROUND * 3 / 4, paintJsBackground_CONTOUR);
        canvas.drawOval(POSITION_JS_BACKGROUND.x - RADIUS_JS_BACKGROUND * 2 / 4,POSITION_JS_BACKGROUND.y - RADIUS_JS_BACKGROUND * 2 / 4,POSITION_JS_BACKGROUND.x + RADIUS_JS_BACKGROUND * 2 / 4,POSITION_JS_BACKGROUND.y + RADIUS_JS_BACKGROUND * 2 / 4, paintJsBackground_CONTOUR);
        canvas.drawOval(POSITION_JS_BACKGROUND.x - RADIUS_JS_BACKGROUND * 1 / 4,POSITION_JS_BACKGROUND.y - RADIUS_JS_BACKGROUND * 1 / 4,POSITION_JS_BACKGROUND.x + RADIUS_JS_BACKGROUND * 1 / 4,POSITION_JS_BACKGROUND.y + RADIUS_JS_BACKGROUND * 1 / 4, paintJsBackground_CONTOUR);
        canvas.drawLine(POSITION_JS_BACKGROUND.x - RADIUS_JS_BACKGROUND,POSITION_JS_BACKGROUND.y,POSITION_JS_BACKGROUND.x + RADIUS_JS_BACKGROUND,POSITION_JS_BACKGROUND.y, paintJsBackground_CONTOUR);
        canvas.drawLine(POSITION_JS_BACKGROUND.x,POSITION_JS_BACKGROUND.y - RADIUS_JS_BACKGROUND,POSITION_JS_BACKGROUND.x,POSITION_JS_BACKGROUND.y + RADIUS_JS_BACKGROUND, paintJsBackground_CONTOUR);
        canvas.drawLine(POSITION_JS_BACKGROUND.x - (int) ((float) RADIUS_JS_BACKGROUND * Math.sqrt(2) / 2),POSITION_JS_BACKGROUND.y - (int) ((float) RADIUS_JS_BACKGROUND * Math.sqrt(2) / 2),POSITION_JS_BACKGROUND.x + (int) ((float) RADIUS_JS_BACKGROUND * Math.sqrt(2) / 2),POSITION_JS_BACKGROUND.y + (int) ((float) RADIUS_JS_BACKGROUND * Math.sqrt(2) / 2), paintJsBackground_CONTOUR);
        canvas.drawLine(POSITION_JS_BACKGROUND.x - (int) ((float) RADIUS_JS_BACKGROUND * Math.sqrt(2) / 2),POSITION_JS_BACKGROUND.y + (int) ((float) RADIUS_JS_BACKGROUND * Math.sqrt(2) / 2),POSITION_JS_BACKGROUND.x + (int) ((float) RADIUS_JS_BACKGROUND * Math.sqrt(2) / 2),POSITION_JS_BACKGROUND.y - (int) ((float) RADIUS_JS_BACKGROUND * Math.sqrt(2) / 2), paintJsBackground_CONTOUR);
        canvas.drawCircle(positionJsController.x,positionJsController.y,RADIUS_JS_CONTROLLER_OUTER,paintJsController_INNER);
        canvas.drawOval(positionJsController.x - RADIUS_JS_CONTROLLER_INNER,positionJsController.y - RADIUS_JS_CONTROLLER_INNER,positionJsController.x + RADIUS_JS_CONTROLLER_INNER,positionJsController.y + RADIUS_JS_CONTROLLER_INNER, paintJsBackground_CONTOUR);
        canvas.drawOval(positionJsController.x - RADIUS_JS_CONTROLLER_OUTER,positionJsController.y - RADIUS_JS_CONTROLLER_OUTER,positionJsController.x + RADIUS_JS_CONTROLLER_OUTER,positionJsController.y + RADIUS_JS_CONTROLLER_OUTER, paintJsBackground_CONTOUR);
        canvas.drawRect(CONST_COORDINATION - 200,CONST_COORDINATION + RADIUS_JS_BACKGROUND + 50,CONST_COORDINATION - 70, CONST_COORDINATION + RADIUS_JS_BACKGROUND + 250,paintJsBackground);
        canvas.drawRect(CONST_COORDINATION + 70,CONST_COORDINATION + RADIUS_JS_BACKGROUND + 50,CONST_COORDINATION + 200, CONST_COORDINATION + RADIUS_JS_BACKGROUND + 250,paintJsBackground);
        canvas.drawRect(CONST_COORDINATION + 70, CONST_COORDINATION + RADIUS_JS_BACKGROUND + 50 + motorRight, CONST_COORDINATION + 200,CONST_COORDINATION + RADIUS_JS_BACKGROUND + 150,paintMotor);
        canvas.drawRect(CONST_COORDINATION - 200, CONST_COORDINATION + RADIUS_JS_BACKGROUND + 50 + motorLeft, CONST_COORDINATION - 70,CONST_COORDINATION + RADIUS_JS_BACKGROUND + 150,paintMotor);
        canvas.drawLine(CONST_COORDINATION - 200, CONST_COORDINATION + RADIUS_JS_BACKGROUND + 150 , CONST_COORDINATION - 70, CONST_COORDINATION + RADIUS_JS_BACKGROUND + 150, paintJsBackground_CONTOUR);
        canvas.drawLine(CONST_COORDINATION + 70, CONST_COORDINATION + RADIUS_JS_BACKGROUND + 150 , CONST_COORDINATION + 200, CONST_COORDINATION + RADIUS_JS_BACKGROUND + 150, paintJsBackground_CONTOUR);
    }

    public void resetJoystick(){
        positionJsController.x = CONST_COORDINATION;
        positionJsController.y = CONST_COORDINATION;

        radius = 0;
        angleDegree = 90;

        motorLeft = 100;
        motorRight = 100;
    }

    public void useJoystick(int cursorX, int cursorY, int heightPx, int widthPx){
        int x = heightPx - cursorX - CONST_COORDINATION;
        int y = widthPx - cursorY - CONST_COORDINATION;
        int distance = (int)Math.sqrt(x * x + y * y);
        if (distance < 1.13 * RADIUS_JS_BACKGROUND){
            if (distance > RADIUS_JS_BACKGROUND){
                distance = RADIUS_JS_BACKGROUND;
            }
            positionJsController.x = CONST_COORDINATION - x;
            positionJsController.y = CONST_COORDINATION + y;

            radius = distance * 100 / RADIUS_JS_BACKGROUND;
            angleDegree = (int) ((Math.toDegrees( Math.atan2((double)x, (double)y) ) + 450.0) % 360.0);

            if (angleDegree <= 90){
                motorLeft = 100 + radius;
                motorRight = 100 + radius * angleDegree / 90;
            }else if (angleDegree <= 180){
                motorLeft = 100 + radius - radius * (angleDegree - 90) / 90;
                motorRight = 100 + radius;
            }else if (angleDegree <= 270){
                motorLeft = 100 - radius * (angleDegree - 180) / 90;
                motorRight = 100 - radius;
            }else{
                motorLeft = 100 - radius;
                motorRight = 100 - radius + radius * (angleDegree - 270) / 90;
            }
        }else{
            resetJoystick();
        }
    }

    public int getMotorLeft(){
        return motorLeft;
    }

    public int getMotorRight(){
        return motorRight;
    }
}