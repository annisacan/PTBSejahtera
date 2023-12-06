package example.scrollingtext.ptbsejahtera;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class UploadActivityPres extends AppCompatActivity {

    ImageView upimage;
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
        savebtn= findViewById(R.id.btnsavepres);

    }
}