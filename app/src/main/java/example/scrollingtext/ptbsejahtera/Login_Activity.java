package example.scrollingtext.ptbsejahtera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Activity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText loginemail, loginpass;
    private Button loginbtn, forgotbtn, regisbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth= FirebaseAuth.getInstance();
        loginemail= findViewById(R.id.inputusernamelogin);
        loginpass= findViewById(R.id.inputpasslogin);
        loginbtn= findViewById(R.id.masukTombol);
        forgotbtn= findViewById(R.id.forgotPasswordButton);
        regisbtn= findViewById(R.id.belumPunyaAkunButton);
        forgotbtn= findViewById(R.id.forgotPasswordButton);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= loginemail.getText().toString();
                String pass= loginpass.getText().toString();

                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    if (!pass.isEmpty()){
                        auth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(Login_Activity.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login_Activity.this, MainActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login_Activity.this, "Email atau Password anda salah", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        loginpass.setError("Password tidak boleh kosong");
                    }
                } else if (email.isEmpty()) {
                    loginemail.setError("Email tidak boleh kosong");
                }else {
                    loginemail.setError("Tolong masukkan email yang terdaftar");
                }
            }
        });
        regisbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Activity.this, Register_Activity.class);
                startActivity(intent);
            }
        });
        forgotbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(Login_Activity.this);
                View dialogView= getLayoutInflater().inflate(R.layout.activity_forgot, null);
                EditText emailbox= dialogView.findViewById(R.id.emailbox);

                builder.setView(dialogView);
                AlertDialog dialog= builder.create();

                dialogView.findViewById(R.id.btnReset).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email= emailbox.getText().toString();

                        if (TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                            Toast.makeText(Login_Activity.this, "Masukan emailmu", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(Login_Activity.this, "Periksa emailmu", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(Login_Activity.this, "Tidak dapat mengirim", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                dialogView.findViewById(R.id.btncancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                if (dialog.getWindow() != null){
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog.show();
            }
        });
    }
}