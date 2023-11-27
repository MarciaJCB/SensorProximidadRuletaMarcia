package com.example.ppt15sensorproximidadruleta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

//La implementación SensorEventListener debe proporcionar implementaciones para los métodos onSensorChanged y onAccuracyChanged
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    ConstraintLayout cl; //El ConstraintLayout que es el contenedor
    TextView texto; //El TextView que se utilizará para mostrar un mensaje por pantalla
    SensorManager sm; //Objeto que se utilizará para gestionar los sensores dl dispositivo
    Sensor sensor; //Obtiene el sensor de proximidad
    ImageView ruleta; //Objeto ImageView que representa la ruleta en la interfaz de usuario
    Random random; // Objeto para generar angulos aleatoreos para la rotación de la ruleta
    int angulo;
    boolean restart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ruleta=findViewById(R.id.ruleta);
        cl=findViewById(R.id.Caja);
        texto=findViewById(R.id.txt_texto);
        sm=(SensorManager)getSystemService(SENSOR_SERVICE);
        sensor=sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sm.registerListener((SensorEventListener) this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        random=new Random();
    }

    @Override
    public void onSensorChanged(SensorEvent event) { //El textView se actualiza con el valor actual de proximidad
        String texto1 = String.valueOf(event.values[0]); //El valor del sensor se encuentra en event.values[0]
        texto.setText(texto1);//Se converte en una cadena y se muestra en el TextView
        float valor = Float.parseFloat(texto1);
        if (valor == 0){//Si el valor es igual a 0 se genera el angulo aleatorio
            angulo = random.nextInt(3600) + 360; //Se aplica una animación de rotación a la ruleta durante un tiempo determinado 3600 milisegundos
            RotateAnimation rotate = new RotateAnimation(0,angulo,
            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
            RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            rotate.setFillAfter(true);
            rotate.setDuration(3600);
            rotate.setInterpolator(new AccelerateDecelerateInterpolator());
            ruleta.startAnimation(rotate);
            restart = false; //La propiedad restart se establece en false para evitar que la ruleta se reinicie si se mantiene la mano cerca del sensor
        }else{//Si el valor del sensor no es 0 se muestra un mensaje que debe acerca la mano para jugar
            texto.setText("Pasa tu mano para jugar");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) { //No realiza ninguna acción en este caso

    }
}