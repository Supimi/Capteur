package supimi.github.capteur;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {


    private Button start_btn;
    private Button stop_btn;
    private TextView out_x;
    private TextView out_y;
    private TextView out_z;

    private double delta_x, delta_y, delta_z;
    private double x_reading = 0;
    private double y_reading = 0;
    private double z_reading = 0;

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
        this.set_initial_message();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        } else {
            System.out.println("Sensor not found!!");
            displayError();
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
        if(accelerometer==null){
           displayError();
        }
        this.clean_readings();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop(View view) {
        System.out.println("Stop..................");
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        //reading will change if sensor output changed by 1 decimal places
        this.delta_x = Math.round(Math.abs(x_reading - sensorEvent.values[0]) * 10.0) / 10.0;
        this.delta_y = Math.round(Math.abs(y_reading - sensorEvent.values[1]) * 10.0) / 10.0;
        this.delta_z = Math.round(Math.abs(z_reading - sensorEvent.values[2]) * 10.0) / 10.0;


        if (delta_x > 0.0) {
            this.x_reading = sensorEvent.values[0];
        }
        if (delta_y > 0.0) {
            this.y_reading = sensorEvent.values[1];
        }

        if (delta_z > 0.0) {
            this.z_reading = sensorEvent.values[2];
        }

        this.displayCurrentValue();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void displayCurrentValue() {
        out_x.setText(Double.toString(x_reading));
        out_y.setText(Double.toString(y_reading));
        out_z.setText(Double.toString(z_reading));
    }

    public void displayError() {
        Context context = getApplicationContext();
        CharSequence err_msg = "Sensor not found!";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, err_msg, duration);
        toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 900);
        toast.show();
    }

    public void set_initial_message() {
        String init_msg = "No readings yet...";
        out_x.setText(init_msg);
        out_y.setText(init_msg);
        out_z.setText(init_msg);
    }

    public void clean_readings() {
        String init_reading = "0.0";
        out_x.setText(init_reading);
        out_y.setText(init_reading);
        out_z.setText(init_reading);
    }
}
