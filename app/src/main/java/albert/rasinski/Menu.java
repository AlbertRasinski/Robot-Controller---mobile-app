package albert.rasinski;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class Menu extends AppCompatActivity {
    private View decorView;
    private String ip;
    private int port;
    private EditText setTextIP;
    private EditText setTextPort;
    private ImageButton backToMainActivityButton;
    private Button connectButtonMenu;

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

        backToMainActivityButton = findViewById(R.id.backButton);
        backToMainActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivityOnClick(v);
            }
        });

        connectButtonMenu = findViewById(R.id.connectMenu);
        connectButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectMenuOnClick(v);
            }
        });
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

    public void mainActivityOnClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("ip", ip);
        intent.putExtra("port", port);
        startActivity(intent);
    }

    public void connectMenuOnClick(View view){
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

        startActivity(intent);
    }
}