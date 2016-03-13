package com.pel.mathias.merob;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.pel.mathias.merob.MainActivity;
import com.pel.mathias.merob.WiFiDirectBroadcastReceiver;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;

/**
 * Created by Mathias on 18/12/2015.
 */
public class CreateAP extends Thread{

    private String SSID;
    private  String passphrase;
    private  InetAddress IP_adress;
    private  WifiP2pManager mManager;
    private  WifiP2pManager.Channel mChannel;
    private WiFiDirectBroadcastReceiver mReceiver;
    private IntentFilter mIntentFilter;
    private Collection<WifiP2pDevice> deviceList;

    private MainActivity main;
    private TextView textssid,textpass,textip;

    CreateAP(MainActivity main, TextView textssid, TextView textpass,TextView textip){
        this.main=main;
        this.textssid=textssid;
        this.textpass=textpass;
        this.textip=textip;
    }

    @Override
    public void run() {
        super.run();
        mManager = (WifiP2pManager) main.getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(main, main.getMainLooper(), null);
        mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, main);

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        mManager.createGroup(mChannel, null);
        mManager.requestGroupInfo(mChannel, new WifiP2pManager.GroupInfoListener() {
            @Override
            public void onGroupInfoAvailable(WifiP2pGroup group) {
                SSID = group.getNetworkName();
                passphrase = group.getPassphrase();
                Log.d("test group", "ssid:" + SSID + " passphrase:" + passphrase);
                main.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textssid.setText("SSID: " + SSID);
                        textpass.setText("Passphrase: " + passphrase);
                    }
                });
            }
        });
        mManager.requestConnectionInfo(mChannel, new WifiP2pManager.ConnectionInfoListener() {
            @Override
            public void onConnectionInfoAvailable(WifiP2pInfo info) {
                IP_adress = info.groupOwnerAddress;
                main.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textip.setText("IP: " + IP_adress.toString());
                    }
                });
            }
        });

        mManager.discoverPeers(mChannel, null);

        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        deviceList=mReceiver.getDeviceList();

    }


    public Collection<WifiP2pDevice> getDeviceList() {
        return deviceList;
    }

    public void resume_connect() {
        main.registerReceiver(mReceiver, mIntentFilter);
    }
    public  void  pause_connect(){
        main.unregisterReceiver(mReceiver);
    }
}
