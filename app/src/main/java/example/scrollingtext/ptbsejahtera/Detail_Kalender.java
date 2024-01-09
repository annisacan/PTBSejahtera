package example.scrollingtext.ptbsejahtera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import example.scrollingtext.ptbsejahtera.databinding.ActivityDetailkalenderBinding;

public class Detail_Kalender extends AppCompatActivity {

    TextView detailTanggal, detailLokasi, detailAgenda;
    ImageView detailBack;
    Button btnDelete, btnEdit;
    String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailkalender);

        detailTanggal = findViewById(R.id.tanggalkalender);
        detailLokasi = findViewById(R.id.lokasikalender);
        detailAgenda = findViewById(R.id.agendakalender);
        btnDelete = findViewById(R.id.btndeletekalender);
        btnEdit = findViewById(R.id.btneditkalender);
        detailBack = findViewById(R.id.backkalender);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailTanggal.setText(bundle.getString("Tanggal"));
            detailLokasi.setText(bundle.getString("Lokasi"));
            detailAgenda.setText(bundle.getString("Agenda"));
            key = bundle.getString("Key");
        }

        detailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detail_Kalender.this, KalenderFragment.class);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("agenda").child(uid);
                reference.child(key).removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Jika penghapusan berhasil
                                Toast.makeText(Detail_Kalender.this, "Agenda dihapus", Toast.LENGTH_SHORT).show();
                                // Lakukan tindakan setelah penghapusan berhasil di sini
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Jika terjadi kegagalan saat menghapus data
                                Toast.makeText(Detail_Kalender.this, "Gagal menghapus agenda", Toast.LENGTH_SHORT).show();
                                Log.e("Detail_Kalender", "Error deleting agenda: " + e.getMessage());
                            }
                        });
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detail_Kalender.this, EditKalender.class)
                        .putExtra("Tanggal", detailTanggal.getText().toString())
                        .putExtra("Lokasi", detailLokasi.getText().toString())
                        .putExtra("Agenda", detailAgenda.getText().toString())
                        .putExtra("Key", key);
                startActivity(intent);
            }
        });

    }
}