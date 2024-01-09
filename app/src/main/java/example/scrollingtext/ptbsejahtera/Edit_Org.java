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
import android.media.Image;
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

public class Edit_Org extends AppCompatActivity {
    ImageView editImg;
    TextView editOrg, editPer, editJab, editDiv;
    Button saveEditBtn;
    String Org, Per, Jab, Div;
    String imageUrl;
    String key, oldImgUrl;
    Uri uri;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_org);

        saveEditBtn = findViewById(R.id.editBtnSaveOrg);
        editOrg = findViewById(R.id.editOrg);
        editPer = findViewById(R.id.editPeriodOrg);
        editJab = findViewById(R.id.editJabatanOrg);
        editDiv = findViewById(R.id.editDivOrg);
        editImg = findViewById(R.id.editImgOrg);
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
                            Toast.makeText(Edit_Org.this, "Tidak ada Gambar yang dipilih", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            Glide.with(Edit_Org.this).load(bundle.getString("Image")).into(editImg);
            editOrg.setText(bundle.getString("Organisasi"));
            editPer.setText(bundle.getString("Periode"));
            editJab.setText(bundle.getString("Jabatan"));
            editDiv.setText(bundle.getString("Divisi"));
            key = bundle.getString("Key");
            oldImgUrl = bundle.getString("Image");
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("Data Sertifikat User").child(uid).child("Sertifikat Organisasi").child(key);
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
                Intent intent = new Intent(Edit_Org.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void saveData(){
     storageReference = FirebaseStorage.getInstance().getReference().child("Gambar Sertifikat Organisasi").child(uri.getLastPathSegment());
        AlertDialog.Builder builder = new AlertDialog.Builder(Edit_Org.this);
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
        Org = editOrg.getText().toString().trim();
        Per = editPer.getText().toString().trim();
        Jab = editJab.getText().toString().trim();
        Div = editDiv.getText().toString().trim();

        DataClassOrg dataOrg = new DataClassOrg(Org, Per, Jab, Div, imageUrl);

        databaseReference.setValue(dataOrg).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(oldImgUrl);
                    reference.delete();
                    Toast.makeText(Edit_Org.this, "Updated", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Edit_Org.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}