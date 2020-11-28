package albert.rasinski;

import android.content.Context;
import android.content.SharedPreferences;

public class IpPort{
    private String ip;
    private int port;

    private final String SHARED_PREFS = "saved_ip_port_data";
    private static final String IP_NAME = "ip";
    private final String PORT_NAME = "port";

    public IpPort(Context context){
        load(context);
    }

    public void save(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(IP_NAME, ip);
        editor.putInt(PORT_NAME, port);
        editor.commit();
    }

    public void load(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);

        ip = sharedPreferences.getString(IP_NAME, "No data");
        port = sharedPreferences.getInt(PORT_NAME,4831);
    }

    public String getIp(){
        return ip;
    }
    public int getPort(){
        return port;
    }

    public void setIp(String ip){
        this.ip = ip;
    }
    public void setPort(int port){
        this.port = port;
    }
};

