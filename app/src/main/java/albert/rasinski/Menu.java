package albert.rasinski;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Menu extends AppCompatActivity {
    private View decorView;
    private String ip;
    private int port;
    private EditText setTextIP;
    private EditText setTextPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

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

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            ip = extras.getString("ip");
            port = extras.getInt("port");
        }
        setTextIP = (EditText) findViewById(R.id.set_ip);
        setTextIP.setHint(ip);
        setTextPort = (EditText) findViewById(R.id.set_port);
        setTextPort.setHint("" + port);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
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

    public void mainActivityButton(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("ip", ip);
        intent.putExtra("port", port);
        intent.putExtra("onOffClient",false);
        startActivity(intent);
    }

    public void connectClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        if (setTextIP.getText().toString().matches("")){
            intent.putExtra("ip", ip);
        }else{
            intent.putExtra("ip", setTextIP.getText().toString());
        }
        if (setTextPort.getText().toString().matches("")){
            intent.putExtra("port", port);
        }else{
            intent.putExtra("port", Integer.parseInt(setTextPort.getText().toString()));
        }

        intent.putExtra("onOffClient",true);
        startActivity(intent);
    }
}