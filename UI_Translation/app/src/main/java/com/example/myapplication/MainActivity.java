package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    Button start_stop;
    AlertDialog alert;
    boolean started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start_stop = findViewById(R.id.start_stop);
        //This is our start button which will be changed to 'Stop' once the button has selected the button
        if (isMyServiceRunning(FloatingWindow.class)){
            started = true;
            start_stop.setText("Stop");
        }

        start_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_stop();
            }
        });

    }
//We will check the permission
    public void start_stop() {
        if (checkPermission()) {
            if (started) {
                stopService(new Intent(MainActivity.this, FloatingWindow.class));
                start_stop.setText("Start");
                started = false;
            } else {
                startService(new Intent(MainActivity.this, FloatingWindow.class));
                start_stop.setText("Stop");
                started = true;

            }
        }else {
            reqPermission();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK) {
            if (checkPermission()) {
                start_stop();
            } else {
                reqPermission();
            }
        }
    }


    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                reqPermission();
                return false;
            }
            else {
                return true;
            }
        }else{
            return true;
        }

    }

    private void reqPermission(){
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Screen overlay detected");
        alertBuilder.setMessage("Enable 'Draw over other apps' in your system setting.");
        alertBuilder.setPositiveButton("OPEN SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent,RESULT_OK);
            }
        });

        alert = alertBuilder.create();
        alert.show();


    }


    @Override
    protected void onRestart() {
        super.onRestart();
        alert.dismiss();
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
