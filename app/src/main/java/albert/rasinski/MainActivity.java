package albert.rasinski;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    private View decorView;
    private int heightPx;
    private int widthPx;
    private Joystick joystick;
    private DrawCamera drawCamera;
    private IpPort ipPort;
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener((new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0){
                    decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                }
            }
        }));

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        joystick = (Joystick) findViewById(R.id.joystickView);
        drawCamera = findViewById(R.id.cameraView);

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point realSize = new Point();
        display.getRealSize(realSize);
        heightPx = realSize.x;
        widthPx = realSize.y;

        ipPort = new IpPort(this);
        Bundle extras = getIntent().getExtras();

        if (extras != null){
            ipPort.setIp(extras.getString("ip"));
            ipPort.setPort(extras.getInt("port"));
            ipPort.save(this);
        }

    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        final View view = getWindow().getDecorView();
        view.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int cursorX = (int) event.getX();
        int cursorY = (int) event.getY();

        joystick.useJoystick(cursorX, cursorY, heightPx, widthPx);

        if (event.getAction() == MotionEvent.ACTION_UP){
            joystick.resetJoystick();
        }

        joystick.postInvalidate();

        return true;
    }

    public void menuButton(View view){
        Intent intent = new Intent(this, Menu.class);
        intent.putExtra("ip", ipPort.getIp());
        intent.putExtra("port", ipPort.getPort());
        startActivity(intent);
    }

    public void connectButton(View view){
        Thread threadClient = new Thread(client = new Client(joystick, drawCamera, ipPort.getIp(), ipPort.getPort()));
        threadClient.start();

        Thread threadDrawing = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if (drawCamera.readyToDraw()){
                        drawCamera.postInvalidate();
                    }
                }
            }
        });
        threadDrawing.start();
    }
}