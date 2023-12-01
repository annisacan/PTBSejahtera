package example.scrollingtext.ptbsejahtera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Landing extends AppCompatActivity {

    private Button btnregis, btnlogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        btnregis= findViewById(R.id.butdaflan);
        btnlogin= findViewById(R.id.butmaslan);

        btnregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Landing.this, Register_Activity.class);
                startActivity(intent);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Landing.this, Login_Activity.class);
                startActivity(intent);
            }
        });
    }
}