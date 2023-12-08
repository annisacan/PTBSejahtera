package example.scrollingtext.ptbsejahtera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Tipe_Sertif extends AppCompatActivity {

    private ImageView btnpres, btnpeng, btnorg, btnpel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipesertif);

        btnpres = findViewById(R.id.btnpres);
        btnpeng = findViewById(R.id.btnpeng);
        btnorg = findViewById(R.id.btnorg);
        btnpel = findViewById(R.id.btnpel);

        btnpres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tipe_Sertif.this, UploadActivityPres.class);
                startActivity(intent);
            }
        });

        btnpeng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(Tipe_Sertif.this, UploadActivityPeng.class);
                startActivity(intent);
            }
        });
    }
}