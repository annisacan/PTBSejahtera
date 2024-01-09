package example.scrollingtext.ptbsejahtera;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditKalender extends AppCompatActivity {
    EditText updateTanggal, updateLokasi, updateAgenda;
    Button updateSave;
    ImageView updateBack;
    String Tanggal, Lokasi, Agenda;
    String key;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editkalender);

        updateSave = findViewById(R.id.btnsaveeditkalender);
        updateTanggal = findViewById(R.id.tanggalkalender);
        updateLokasi = findViewById(R.id.lokasikalender);
        updateAgenda = findViewById(R.id.agendakalender);
        updateBack = findViewById(R.id.backkalender);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            updateTanggal.setText(bundle.getString("Tanggal"));
            updateLokasi.setText(bundle.getString("Lokasi"));
            updateAgenda.setText(bundle.getString("Agenda"));
            key = bundle.getString("Key");
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("agenda").child(uid).child(key);

        updateTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        updateBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditKalender.this, KalenderFragment.class);
                startActivity(intent);
            }
        });
        updateSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
                Intent intent = new Intent(EditKalender.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (DatePicker view, int year, int month, int dayOfMonth) -> {
                    String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                    updateTanggal.setText(selectedDate);
                },
                2024, 0, 1);

        datePickerDialog.show();
    }
    public void updateData(){
        Tanggal = updateTanggal.getText().toString().trim();
        Lokasi = updateLokasi.getText().toString().trim();
        Agenda = updateAgenda.getText().toString().trim();

        DataClassKalender dataKal = new DataClassKalender(Tanggal, Lokasi, Agenda);

        databaseReference.setValue(dataKal).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(EditKalender.this, "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditKalender.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(EditKalender.this, "Failed to update", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditKalender.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
