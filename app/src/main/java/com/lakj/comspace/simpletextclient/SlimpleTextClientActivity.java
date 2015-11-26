package com.lakj.comspace.simpletextclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.net.wifi.*;
import android.content.Context;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;
import android.view.KeyEvent;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * This is a simple Android mobile client
 * This application read any string massage typed on the text field and 
 * send it to the server when the Send button is pressed
 * Author by Lak J Comspace
 *
 */
public class SlimpleTextClientActivity extends Activity {

	private Socket client;
	private PrintWriter printwriter;
	//private EditText ssidText;
   // private EditText passwordText;
   // private EditText btnIdText;
	private ImageButton buttonon;
    private ImageButton buttonoff;
	private String on="1";
    private String off="0";
   private String password="";
   private String btnid="";
    private String fullmsg;
    private String fullmsgoff;
    private WifiManager myWifiManager;
    private boolean wasEnabled;
    String networkSSID1 = "DbuttonApp";
    String networkSSID2 = "DbuttonApp1";
    ToggleButton tButton;


   // String networkPass = "pass";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slimple_text_client);
        myWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        boolean wasEnabled = myWifiManager.isWifiEnabled();
        myWifiManager.setWifiEnabled(true);
        Toast.makeText(getApplicationContext()," Turning On Wifi...", Toast.LENGTH_LONG).show();
        while(!myWifiManager.isWifiEnabled())
        {
            //display some message while wifi is enabling
        }
        if(myWifiManager.isWifiEnabled()){
            Toast.makeText(getApplicationContext()," WiFi is On", Toast.LENGTH_LONG).show();
        }else{Toast.makeText(getApplicationContext(),"Wifi is Off", Toast.LENGTH_LONG).show();}

        wasEnabled=true;

/*
        if(myWifiManager.isWifiEnabled()){
            if(myWifiManager.startScan()){
// List available APs
                List<ScanResult> scans = myWifiManager.getScanResults();
                if(scans != null && !scans.isEmpty()){
                    for (ScanResult scan : scans) {
                        int level = WifiManager.calculateSignalLevel(scan.level, 20);

                    System.out.println(scan.SSID);
                    System.out.println(level);
                    }
                }
            }
        }
 */
        //connecting to a SSID

/*
        tButton = (ToggleButton) findViewById(R.id.toggleButton1);
        tButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){

                    fullmsg="#"+on+"&"+password+"*"+btnid+"$";



                    System.out.println(fullmsg);

                    SendMessage sendMessageTask = new SendMessage();
                    sendMessageTask.execute();
                    Toast.makeText(getApplicationContext(),"Successfully Configured", Toast.LENGTH_LONG).show();

                }else{
                    fullmsg="#"+off+"&"+password+"*"+btnid+"$";



                    System.out.println(fullmsg);

                    SendMessage sendMessageTask = new SendMessage();
                    sendMessageTask.execute();
                    Toast.makeText(getApplicationContext(),"Successfully Configured", Toast.LENGTH_LONG).show();
                }

            }
        });
   */
        final ToggleButton toggle1 = (ToggleButton) findViewById(R.id.toggleButton2);
        final ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton);

        toggle1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    toggle.setChecked(false);
                    WifiConfiguration conf = new WifiConfiguration();
                    conf.SSID = "\"" + networkSSID1 + "\"";   // Please note the quotes. String should contain ssid in quotes
                    conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                    myWifiManager.addNetwork(conf);
                    List<WifiConfiguration> list =  myWifiManager.getConfiguredNetworks();
                    for( WifiConfiguration i : list ) {
                        if(i.SSID != null && i.SSID.equals("\"" + networkSSID1 + "\"")) {
                            System.out.println(i.SSID);
                            myWifiManager.disconnect();
                            myWifiManager.enableNetwork(i.networkId, true);
                            myWifiManager.reconnect();
                            break;
                        }
                        System.out.println(i.SSID);
                        // Toast.makeText(getApplicationContext(),"Please Wait... Connecting to the D-Button", Toast.LENGTH_LONG).show();

                    }

                    Toast.makeText(getApplicationContext(),"Please Wait... Connecting to the Device", Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "Successfully Connected", Toast.LENGTH_LONG).show();

                } else {
                    // The toggle is disabled
                }
            }
        });


        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    toggle1.setChecked(false);
                    WifiConfiguration conf = new WifiConfiguration();
                    conf.SSID = "\"" + networkSSID2 + "\"";   // Please note the quotes. String should contain ssid in quotes
                    conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                    myWifiManager.addNetwork(conf);
                    List<WifiConfiguration> list =  myWifiManager.getConfiguredNetworks();
                    for( WifiConfiguration i : list ) {
                        if(i.SSID != null && i.SSID.equals("\"" + networkSSID2 + "\"")) {
                            System.out.println(i.SSID);
                            myWifiManager.disconnect();
                            myWifiManager.enableNetwork(i.networkId, true);
                            myWifiManager.reconnect();
                            break;
                        }
                        System.out.println(i.SSID);
                        // Toast.makeText(getApplicationContext(),"Please Wait... Connecting to the D-Button", Toast.LENGTH_LONG).show();

                    }

                    Toast.makeText(getApplicationContext(),"Please Wait... Connecting to the Device", Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "Successfully Connected", Toast.LENGTH_LONG).show();

                } else {
                    // The toggle is disabled
                }
            }
        });







        //connecting to a SSID

           // ssidText = (EditText) findViewById(R.id.ssidText); // reference to the text field
           // passwordText = (EditText) findViewById(R.id.passwordText);
           // btnIdText = (EditText) findViewById(R.id.btnIdText);
            buttonon = (ImageButton) findViewById(R.id.buttonon); // reference to the send button
            buttonoff = (ImageButton) findViewById(R.id.buttonoff);
            // Button press event listener
            buttonon.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {



                    fullmsg="#"+on+"&"+password+"*"+btnid+"$";



                    System.out.println(fullmsg);

                    SendMessage sendMessageTask = new SendMessage();
                    sendMessageTask.execute();
                    Toast.makeText(getApplicationContext(),"Successfully Configured", Toast.LENGTH_LONG).show();

                }
            });
        buttonoff.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {



                fullmsg="#"+off+"&"+password+"*"+btnid+"$";



                System.out.println(fullmsg);

                SendMessage sendMessageTask = new SendMessage();
                sendMessageTask.execute();
                Toast.makeText(getApplicationContext(),"Successfully Configured", Toast.LENGTH_LONG).show();

            }
        });




    }

	private class SendMessage extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {

				client = new Socket("192.168.4.1", 1336); // connect to the server
				printwriter = new PrintWriter(client.getOutputStream(), true);
				printwriter.write(fullmsg); // write the message to output stream
				printwriter.flush();
				printwriter.close();
				client.close(); // closing the connection

			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.slimple_text_client, menu);

        return true;
	}



    public boolean onKeyDown(int keyCode, KeyEvent event){ if(keyCode == KeyEvent.KEYCODE_BACK)
    {
        myWifiManager.setWifiEnabled(wasEnabled);
        System.exit(0);
    }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

}
