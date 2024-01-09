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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Detail_SertifOrg extends AppCompatActivity {
    ImageView detailImgOrg, backDetail;
    TextView detailOrg, detailPer, detailJab, detailDiv;
    Button detailDelete, detailEdit;
    String key = "";
    String imgurl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sertif_org);

        detailImgOrg = findViewById(R.id.viewdetail);
        detailOrg = findViewById(R.id.data1);
        detailPer = findViewById(R.id.data2);
        detailJab = findViewById(R.id.data3);
        detailDiv = findViewById(R.id.data4);
        backDetail = findViewById(R.id.backDetail);
        detailDelete = findViewById(R.id.deleteOrg);
        detailEdit = findViewById(R.id.editOrg);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailOrg.setText(bundle.getString("Organisasi"));
            detailPer.setText(bundle.getString("Periode"));
            detailJab.setText(bundle.getString("Jabatan"));
            detailDiv.setText(bundle.getString("Divisi"));
            key = bundle.getString("Key");
            imgurl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailImgOrg);
        }

        detailDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Data Sertifikat User").child(uid).child("Sertifikat Organisasi");
                FirebaseStorage storage = FirebaseStorage.getInstance();

                StorageReference storageReference = storage.getReferenceFromUrl(imgurl);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        reference.child(key).removeValue();
                        Toast.makeText(Detail_SertifOrg.this, "Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                });
            }
        });

        backDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detail_SertifOrg.this, MainActivity.class);
                startActivity(intent);
            }
        });

        detailEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detail_SertifOrg.this, Edit_Org.class)
                        .putExtra("Organisasi", detailOrg.getText().toString())
                        .putExtra("Periode", detailPer.getText().toString())
                        .putExtra("Jabatan", detailJab.getText().toString())
                        .putExtra("Divisi", detailDiv.getText().toString())
                        .putExtra("Image", imgurl)
                        .putExtra("Key", key);
                startActivity(intent);
            }
        });
    }
}