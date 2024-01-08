package example.scrollingtext.ptbsejahtera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Detail_Sertif_Pel extends AppCompatActivity {
    ImageView detailImgPel, backDetailPel;
    TextView detailkegPel, detailLemPel, detailPerPel, detailSkalPel;
    Button detailDelete;
    String key = "";
    String imgurl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sertif_pel);

        detailImgPel = findViewById(R.id.viewdetailPel);
        detailkegPel= findViewById(R.id.data1Pel);
        detailLemPel = findViewById(R.id.data2Pel);
        detailPerPel = findViewById(R.id.data3Pel);
        detailSkalPel = findViewById(R.id.data4Pel);
        backDetailPel = findViewById(R.id.backDetailPel);
        detailDelete = findViewById(R.id.deletePel);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailkegPel.setText(bundle.getString("KegiatanPel"));
            detailLemPel.setText(bundle.getString("LembagaPel"));
            detailPerPel.setText(bundle.getString("PeriodePel"));
            detailSkalPel.setText(bundle.getString("SkalaPel"));
            key = bundle.getString("Key");
            imgurl = bundle.getString("ImagePel");
            Glide.with(this).load(bundle.getString("ImagePel")).into(detailImgPel);
        }
        detailDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Data Sertifikat User").child(uid).child("Sertifikat Pelatihan");
                FirebaseStorage storage = FirebaseStorage.getInstance();

                StorageReference storageReference = storage.getReferenceFromUrl(imgurl);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        reference.child(key).removeValue();
                        Toast.makeText(Detail_Sertif_Pel.this, "Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                });
            }
        });

        backDetailPel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detail_Sertif_Pel.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}