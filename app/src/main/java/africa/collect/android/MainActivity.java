package africa.collect.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.util.Random;
import java.util.UUID;

import africa.collect.android.Listeners.OnClose;
import africa.collect.android.Listeners.OnFailed;
import africa.collect.android.Listeners.OnSuccess;
import africa.collect.android.Utils.Enviroment;


public class MainActivity extends AppCompatActivity  {
    Button initSdk;

    TextView status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSdk= findViewById(R.id.initSdk);

        status = findViewById(R.id.check_status);



        //Initialize SDK on Button Click
        initSdk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status.setText("");

//                CollectWidget
                new CollectWidget()
                        .CollectCheckout(
                                "john.doe@examlple.com",
                                "John",
                                "Doe",
                                generateRef(),
                                10000, "NGN",
                                "",
                                Enviroment.SANDBOX,
                                "pk_sandbox_8rfml1p45kjxjgd44dl9"
                                )
                        .build(MainActivity.this, new OnClose() {
                            @Override
                            public void OnClose() {
                                //Modal Closed
                            }
                        }, new OnFailed() {
                            @Override
                            public void OnFailed(String error) {
                                //An Error has Occurred
                                status.setText(error);

                            }
                        }, new OnSuccess() {
                            @Override
                            public void OnSuccess(String reference, int amount) {
                                status.setText("Amount Paid:" + " "+ amount +"\n\n"+"Reference:"+" "+reference);
                            }
                        });
            }
        });


    }

    public String generateRef(){
        String uuid = String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16));
        return uuid.substring(uuid.length() - 15);
    }
}