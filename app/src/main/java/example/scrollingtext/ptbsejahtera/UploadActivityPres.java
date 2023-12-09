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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadActivityPres extends AppCompatActivity {

    ImageView upimage, back;
    Button savebtn;
    EditText inkegiatan, intgl, incapaian, inskala;
    String imageurl;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadprestasi);

        upimage= findViewById(R.id.upload1);
        inkegiatan= findViewById(R.id.kegiatanpres);
        intgl= findViewById(R.id.tglpres);
        incapaian= findViewById(R.id.capaianpres);
        inskala= findViewById(R.id.skalapres);
        back= findViewById(R.id.back1);
        savebtn= findViewById(R.id.btnsavepres);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadActivityPres.this, Tipe_Sertif.class);
                startActivity(intent);
            }
        });

        ActivityResultLauncher<Intent>activityResultLauncher= registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            upimage.setImageURI(uri);
                        } else {
                            Toast.makeText(UploadActivityPres.this, "Gambar tidak ada", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        upimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }
    public void saveData(){
        StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("Gambar Sertifikat Prestasi").child(uri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(UploadActivityPres.this);
        builder.setCancelable(false);
        builder.setView(R.layout.saving_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri>uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlimage = uriTask.getResult();
                imageurl = urlimage.toString();
                uploadData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }
    public void uploadData(){
        String kegiatan = inkegiatan.getText().toString();
        String tgl = intgl.getText().toString();
        String capaian = incapaian.getText().toString();
        String skala = inskala.getText().toString();

        DataClassPress dataClass = new DataClassPress(kegiatan,tgl,capaian,skala,imageurl);

        FirebaseDatabase.getInstance().getReference("Deskripsi Prestasi").child(kegiatan).setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(UploadActivityPres.this, "Saved", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadActivityPres.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}