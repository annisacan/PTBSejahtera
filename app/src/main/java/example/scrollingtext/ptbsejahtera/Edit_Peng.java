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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

public class Edit_Peng extends AppCompatActivity {
    ImageView editImg;
    TextView editKeg, editIns, editPer, editPos;
    Button saveEditBtn;
    String Kegiatan, Instansi, Periode, Posisi;
    String imageUrl;
    String key, oldImgUrl;
    Uri uri;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_peng);

        saveEditBtn = findViewById(R.id.editBtnSavePeng);
        editKeg = findViewById(R.id.editKegiatanPeng);
        editIns = findViewById(R.id.editInstansiPeng);
        editPer = findViewById(R.id.editPeriodePeng);
        editPos = findViewById(R.id.editPosisiPeng);
        editImg = findViewById(R.id.editImgPeng);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            editImg.setImageURI(uri);
                        } else {
                            Toast.makeText(Edit_Peng.this, "Tidak ada Gambar yang dipilih", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            Glide.with(Edit_Peng.this).load(bundle.getString("ImagePeng")).into(editImg);
            editKeg.setText(bundle.getString("KegiatanPeng"));
            editIns.setText(bundle.getString("InstansiPeng"));
            editPer.setText(bundle.getString("PeriodePeng"));
            editPos.setText(bundle.getString("PosisiPeng"));
            key = bundle.getString("Key");
            oldImgUrl = bundle.getString("ImagePeng");
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("Data Sertifikat User").child(uid).child("Sertifikat Pengalaman").child(key);
        editImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        saveEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Intent intent = new Intent(Edit_Peng.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void saveData(){
        storageReference = FirebaseStorage.getInstance().getReference().child("Gambar Sertifikat Pengalaman").child(uri.getLastPathSegment());
        AlertDialog.Builder builder = new AlertDialog.Builder(Edit_Peng.this);
        builder.setCancelable(false);
        builder.setView(R.layout.saving_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageUrl = urlImage.toString();
                updateData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }
    public void updateData(){
        Kegiatan = editKeg.getText().toString().trim();
        Instansi = editIns.getText().toString().trim();
        Periode = editPer.getText().toString().trim();
        Posisi = editPos.getText().toString().trim();

        DataClassPeng dataPeng = new DataClassPeng(Kegiatan, Instansi, Periode, Posisi, imageUrl);

        databaseReference.setValue(dataPeng).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(oldImgUrl);
                    reference.delete();
                    Toast.makeText(Edit_Peng.this, "Updated", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Edit_Peng.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}