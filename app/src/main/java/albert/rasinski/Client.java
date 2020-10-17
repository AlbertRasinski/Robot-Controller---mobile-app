package albert.rasinski;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

class Client extends AsyncTask<Void, Void, Void> {


    private Joystick joystick;
    private boolean running = true;
    private String ip;
    private int port;
    private DrawCamera drawCamera;

    Client(Joystick joystick, DrawCamera drawCamera, String ip, int port){
        this.joystick = joystick;
        this.drawCamera = drawCamera;
        this.ip = ip;
        this.port = port;
    }

    @Override
    protected Void doInBackground(Void... Void) {
        Socket socket = null;

        InputStream inputStream = null;
        BufferedInputStream bufferedInputStream = null;

        try {
            socket = new Socket(ip, port);
            inputStream = socket.getInputStream();
            bufferedInputStream = new BufferedInputStream(inputStream);

            //while(running){
                byte[] numberOfBytesCamera = new byte[4];
                bufferedInputStream.read(numberOfBytesCamera,0,4);
                int numBytesCamera = ByteBuffer.wrap(numberOfBytesCamera).asIntBuffer().get();

                byte[] imageArrayOfBytes = new byte[numBytesCamera];
                bufferedInputStream.read(imageArrayOfBytes,0, numBytesCamera);



                drawCamera.bitmap = BitmapFactory.decodeByteArray(imageArrayOfBytes,0, numBytesCamera);

                //}
                //Thread.sleep(100);
                    //while(true){
                    /*bufferedOutputStream.write(joystick.getMotorLeft());
                    bufferedOutputStream.write(joystick.getMotorRight());
                    bufferedOutputStream.flush();*/
            //}
        } catch (IOException e) {
            e.printStackTrace();
            try {
                if (socket != null){
                    socket.close();
                }
                if (inputStream != null){
                    inputStream.close();
                }
                /*if (bufferedInputStream != null){
                    bufferedInputStream.close();
                }*/

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onCancelled() {
        running = false;

        super.onCancelled();
    }
}
