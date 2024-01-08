package example.scrollingtext.ptbsejahtera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import example.scrollingtext.ptbsejahtera.databinding.ActivityDetailkalenderBinding;

public class Detail_Kalender extends AppCompatActivity {

    TextView detailTanggal, detailLokasi, detailAgenda;
    ImageView detailBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailkalender);

        detailTanggal = findViewById(R.id.tanggalkalender);
        detailLokasi = findViewById(R.id.lokasikalender);
        detailAgenda = findViewById(R.id.agendakalender);
        detailBack = findViewById(R.id.backkalender);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailTanggal.setText(bundle.getString("Tanggal"));
            detailLokasi.setText(bundle.getString("Lokasi"));
            detailAgenda.setText(bundle.getString("Agenda"));
        }

        detailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detail_Kalender.this, KalenderFragment.class);
                startActivity(intent);
            }
        });
    }
}