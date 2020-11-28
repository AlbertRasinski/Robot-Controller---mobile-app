package albert.rasinski;

import android.graphics.BitmapFactory;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

class Client implements Runnable{
    private Joystick joystick;
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
    public void run() {
        Socket socket = null;
        InputStream inputStream = null;

        DataOutputStream dataOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;

        try {
            socket = new Socket(ip, port);
            inputStream = socket.getInputStream();

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            bufferedOutputStream = new BufferedOutputStream(dataOutputStream);

            while(true) {
                bufferedOutputStream.write(joystick.getMotorLeft());
                bufferedOutputStream.write(joystick.getMotorRight());
                bufferedOutputStream.flush();

                byte[] numberOfBytesArr = new byte[4];
                for (int i = 0; i < 4; ++i){
                    numberOfBytesArr[i] = (byte)inputStream.read();
                }
                int numberOfBytes = ByteBuffer.wrap(numberOfBytesArr).asIntBuffer().get();

                byte[] cameraByteArray = new byte[numberOfBytes];
                for (int j = 0; j < numberOfBytes; ++j){
                    cameraByteArray[j] = (byte)inputStream.read();
                }

                drawCamera.bitmap = BitmapFactory.decodeByteArray(cameraByteArray, 0, numberOfBytes);
                drawCamera.setReadiness();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null){
                    socket.close();
                }
                if (inputStream != null){
                    inputStream.close();
                }
                if (dataOutputStream != null){
                    dataOutputStream.close();
                }
                if (bufferedOutputStream != null){
                    bufferedOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}