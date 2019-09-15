package supimi.github.capteur;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {


    private Button start_btn;
    private Button stop_btn;
    private TextView out_x;
    private TextView out_y;
    private TextView out_z;
    int i = 0;

    private float x_reading = 0;
    private float y_reading = 0;
    private float z_reading = 0;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.start_btn = findViewById(R.id.btn_start);
        this.stop_btn = findViewById(R.id.btn_stop);
        this.out_x = findViewById(R.id.out_x);
        this.out_y = findViewById(R.id.out_y);
        this.out_z = findViewById(R.id.out_z);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!=null){
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }else{

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void readSensorOutput(View view) {
        System.out.println("Start..................");
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop(View view) {
        System.out.println("Stop..................");
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        displayCurrentValue();

        this.x_reading = sensorEvent.values[0];
        this.y_reading = sensorEvent.values[1];
        this.z_reading = sensorEvent.values[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void displayCurrentValue() {
        out_x.setText(Float.toString(x_reading));
        out_y.setText(Float.toString(y_reading));
        out_z.setText(Float.toString(z_reading));
    }
}
