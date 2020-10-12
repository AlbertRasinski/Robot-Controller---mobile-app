package albert.rasinski;

import android.os.AsyncTask;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class Client extends AsyncTask<Void, Void, Void> {
    private Joystick joystick;
    private boolean running = true;
    private String ip;
    private int port;

    Client(Joystick joystick, String ip, int port){
        this.joystick = joystick;
        this.ip = ip;
        this.port = port;
    }

    @Override
    protected Void doInBackground(Void... Void) {
        Socket socket = null;
        DataInputStream dataInputStream = null;
        DataOutputStream dataOutputStream = null;
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;

        try {
            socket = new Socket(ip,port);

            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            bufferedInputStream = new BufferedInputStream(dataInputStream);
            bufferedOutputStream = new BufferedOutputStream(dataOutputStream);


            while(running){
                bufferedOutputStream.write(joystick.getMotorLeft());
                bufferedOutputStream.write(joystick.getMotorRight());
                bufferedOutputStream.flush();
                Thread.sleep(1000);

            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            try {
                if (socket != null) {
                    socket.close();
                }
                if (dataInputStream != null) {
                    dataInputStream.close();
                }
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
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
