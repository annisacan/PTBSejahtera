package example.scrollingtext.ptbsejahtera;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadActivityPres extends AppCompatActivity {

    ImageView upimage, back;
    EditText inkegiatan, intgl, incapaian, inskala;
    Button savebtn;
    Uri uri;
    String imageurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadprestasi);

        upimage = findViewById(R.id.upload1);
        inkegiatan = findViewById(R.id.kegiatanpres);
        intgl = findViewById(R.id.tglpres);
        incapaian = findViewById(R.id.capaianpres);
        inskala = findViewById(R.id.skalapres);
        back= findViewById(R.id.back1);
        savebtn = findViewById(R.id.btnsavepres);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadActivityPres.this, Tipe_Sertif.class);
                startActivity(intent);
            }
        });

        upimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    public void pickImage() {
        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        photoPicker.setType("image/*");
        activityResultLauncher.launch(photoPicker);
    }

     ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        uri = data.getData();
                        upimage.setImageURI(uri);
                    } else {
                        Toast.makeText(UploadActivityPres.this, "Gambar tidak ada", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    public void saveData() {
        if (uri == null) {
            Toast.makeText(this, "Tolong masukkan gambar", Toast.LENGTH_SHORT).show();
            return;
        }
        String kegiatan = inkegiatan.getText().toString();
        String tgl = intgl.getText().toString();
        String capaian = incapaian.getText().toString();
        String skala = inskala.getText().toString();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Gambar Sertifikat Prestasi").child(uid).child(uri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(R.layout.saving_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri urlimage) {
                        imageurl = urlimage.toString();
                        uploadData(kegiatan, tgl, capaian, skala, uid);
                        dialog.dismiss();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                Toast.makeText(UploadActivityPres.this, "Gagal mengupload gambar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void uploadData(String kegiatan, String tgl, String capaian, String skala, String uid) {
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Data Sertifikat User").child(uid);
        DatabaseReference dataReference = userReference.child("Sertifikat Prestasi").child(kegiatan);

        DataClassPress dataClassPress = new DataClassPress(kegiatan, tgl, capaian, skala, imageurl);

        dataReference.setValue(dataClassPress).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(UploadActivityPres.this, "Tersimpan", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(UploadActivityPres.this, "Gagal Menyimpan", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
