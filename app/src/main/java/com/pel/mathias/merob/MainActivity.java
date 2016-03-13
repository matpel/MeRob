package com.pel.mathias.merob;

import android.app.Activity;
import android.net.wifi.p2p.WifiP2pDevice;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Collection;

public class MainActivity extends Activity {

    Button createAP_button,auto_button;;
    ImageButton forward_button,backward_button,right_button,left_button,stop_button;
    TextView textssid,textpass,textip;
    CreateAP createAP_thread;
    boolean createdAP=false;
    Collection<WifiP2pDevice> deviceList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createAP_button=(Button) findViewById(R.id.createGroup);
        forward_button=(ImageButton) findViewById(R.id.buttonF);
        backward_button=(ImageButton) findViewById(R.id.buttonB);
        right_button=(ImageButton) findViewById(R.id.buttonR);
        left_button=(ImageButton) findViewById(R.id.buttonL);
        stop_button=(ImageButton)findViewById(R.id.buttonS);
        auto_button=(Button) findViewById(R.id.autoMode);

        forward_button.setVisibility(View.GONE);
        backward_button.setVisibility(View.GONE);
        left_button.setVisibility(View.GONE);
        right_button.setVisibility(View.GONE);
        stop_button.setVisibility(View.GONE);
        auto_button.setVisibility(View.GONE);

        textpass=(TextView) findViewById(R.id.pass);
        textssid=(TextView) findViewById(R.id.ssid);
        textip=(TextView) findViewById(R.id.ip);

        createAP_thread = new CreateAP(this, textssid, textpass, textip);
        createAP_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createdAP = true;
                createAP_thread.start();
            }
        });
        Thread thread_in=new Thread(new Runnable() {
            @Override
            public void run() {
                boolean test_connec=false;
                while(!test_connec) {
                    try {
                        int server_port = 8888;
                        byte[] message = new byte[1500];
                        DatagramPacket input = new DatagramPacket(message, message.length);
                        DatagramSocket s_in = new DatagramSocket(server_port);
                        s_in.receive(input);
                        s_in.close();

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
                                backward_button.setVisibility(View.VISIBLE);
                                forward_button.setVisibility(View.VISIBLE);
                                right_button.setVisibility(View.VISIBLE);
                                left_button.setVisibility(View.VISIBLE);
                                stop_button.setVisibility(View.VISIBLE);
                                auto_button.setVisibility(View.VISIBLE);
                            }
                        });
                        test_connec = true;

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread_in.start();

        forward_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread_out = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            byte[] message = "movef".getBytes();
                            DatagramPacket output = new DatagramPacket(message, message.length);
                            DatagramSocket s_out = new DatagramSocket(8000);
                            output.setPort(8000);
                            output.setAddress(InetAddress.getByName("192.168.49.215"));
                            s_out.send(output);
                            s_out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread_out.start();
            }
        });
        backward_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread_out = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            byte[] message = "moveb".getBytes();
                            DatagramPacket output = new DatagramPacket(message, message.length);
                            DatagramSocket s_out = new DatagramSocket(8000);
                            output.setPort(8000);
                            output.setAddress(InetAddress.getByName("192.168.49.215"));
                            s_out.send(output);
                            s_out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread_out.start();
            }
        });
        right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread_out = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            byte[] message = "mover".getBytes();
                            DatagramPacket output = new DatagramPacket(message, message.length);
                            DatagramSocket s_out = new DatagramSocket(8000);
                            output.setPort(8000);
                            output.setAddress(InetAddress.getByName("192.168.49.215"));
                            s_out.send(output);
                            s_out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread_out.start();
            }
        });
        left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread_out = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            byte[] message = "movel".getBytes();
                            DatagramPacket output = new DatagramPacket(message, message.length);
                            DatagramSocket s_out = new DatagramSocket(8000);
                            output.setPort(8000);
                            output.setAddress(InetAddress.getByName("192.168.49.215"));
                            s_out.send(output);
                            s_out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread_out.start();
            }
        });
       stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread_out = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            byte[] message = "moves".getBytes();
                            DatagramPacket output = new DatagramPacket(message, message.length);
                            DatagramSocket s_out = new DatagramSocket(8000);
                            output.setPort(8000);
                            output.setAddress(InetAddress.getByName("192.168.49.215"));
                            s_out.send(output);
                            s_out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread_out.start();
            }
        });

       auto_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread_out = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            byte[] message = "movea".getBytes();
                            DatagramPacket output = new DatagramPacket(message, message.length);
                            DatagramSocket s_out = new DatagramSocket(8000);
                            output.setPort(8000);
                            output.setAddress(InetAddress.getByName("192.168.49.215"));
                            s_out.send(output);
                            s_out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread_out.start();
            }
        });
    }
    /* register the broadcast receiver with the intent values to be matched */
    @Override
    protected void onResume() {
        super.onResume();
        if(createdAP)
            createAP_thread.resume_connect();
    }
    /* unregistr the broadcast receiver */
    @Override
    protected void onPause() {
        super.onPause();
        if(createdAP)
            createAP_thread.pause_connect();
    }

}
