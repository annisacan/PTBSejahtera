package example.scrollingtext.ptbsejahtera;

import static android.graphics.Paint.Align.CENTER;
import static android.graphics.Paint.Align.LEFT;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CvFragment extends Fragment {


    final static int REQUEST_CODE = 1232;
    Button btnGeneratePDF;
    List<DataClassPress>dataPress;
    List<DataClassPeng>dataPeng;
    List<DataClassOrg>dataOrg;
    List<DataClassPel>dataPel;
    DatabaseReference databaseReferencePress, databaseReferencePeng, databaseReferenceOrg, databaseReferencePel;
    ValueEventListener eventListenerPress, eventListenerPeng, eventListenerOrg, eventListenerPel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cv, container, false);
        askPermission();
        Button btnGeneratePdf = view.findViewById(R.id.btnGeneratePdf);

        dataPress = new ArrayList<>();
        dataPeng = new ArrayList<>();
        dataOrg = new ArrayList<>();
        dataPel = new ArrayList<>();

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();

        AdapterSertifPress adapterPress = new AdapterSertifPress(getContext(), dataPress);
        AdapterSertifPeng adapterPeng = new AdapterSertifPeng(getContext(), dataPeng);
        AdapterSertifOrg adapterOrg = new AdapterSertifOrg(getContext(), dataOrg);
        AdapterSertifPel adapterPel = new AdapterSertifPel(getContext(), dataPel);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseReferencePress = FirebaseDatabase.getInstance().getReference("Data Sertifikat User").child(uid).child("Sertifikat Prestasi");
        databaseReferencePeng = FirebaseDatabase.getInstance().getReference("Data Sertifikat User").child(uid).child("Sertifikat Pengalaman");
        databaseReferenceOrg = FirebaseDatabase.getInstance().getReference("Data Sertifikat User").child(uid).child("Sertifikat Organisasi");
        databaseReferencePel = FirebaseDatabase.getInstance().getReference("Data Sertifikat User").child(uid).child("Sertifikat Pelatihan");

        eventListenerPress = databaseReferencePress.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataPress.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    DataClassPress dataClassPress = itemSnapshot.getValue(DataClassPress.class);
                    dataPress.add(dataClassPress);
                }
                adapterPress.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });

        eventListenerPeng = databaseReferencePeng.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataPeng.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    DataClassPeng dataClassPeng = itemSnapshot.getValue(DataClassPeng.class);
                    dataPeng.add(dataClassPeng);
                }
                adapterPeng.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });

        eventListenerOrg = databaseReferenceOrg.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataOrg.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    DataClassOrg dataClassOrg = itemSnapshot.getValue(DataClassOrg.class);
                    dataOrg.add(dataClassOrg);
                }
                adapterOrg.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });

        eventListenerPel = databaseReferencePel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataPel.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    DataClassPel dataClassPel = itemSnapshot.getValue(DataClassPel.class);
                    dataPel.add(dataClassPel);
                }
                adapterPel.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });

        btnGeneratePdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditTextFilled()) {
                    generatePdfFromData();
                } else {
                    // Tampilkan notifikasi jika ada EditText yang kosong
                    Toast.makeText(getContext(), "Harap lengkapi semua data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private boolean isEditTextFilled() {
        String namaLengkap = getEditTextValue(R.id.namacv);
        String pendidikanTerakhir = getEditTextValue(R.id.pendidikancv);
        String nomorTelepon = getEditTextValue(R.id.nomorcv);
        String alamatEmail = getEditTextValue(R.id.emailcv);

        // Cek apakah semua EditText sudah terisi
        return !namaLengkap.isEmpty() && !pendidikanTerakhir.isEmpty() && !nomorTelepon.isEmpty() && !alamatEmail.isEmpty();
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
    }


    private void drawDataToPdfOrg(Canvas canvas, DataClassOrg dataorg) {
        Paint myPaint = new Paint();
        Typeface poppinreg = ResourcesCompat.getFont(requireContext(), R.font.pr);

        myPaint.setTextSize(10f);
        canvas.drawText( "> " + dataorg.getDataPeriode(), 9, 254, myPaint);
        canvas.drawText( dataorg.getDataJabatan() + dataorg.getDataOrganisasi(), 17, 277, myPaint);
    }

    private void drawDataToPdfPel(Canvas canvas, DataClassPel datapel) {
        Paint myPaint = new Paint();

        myPaint.setTextSize(10f);
        canvas.drawText( "> " + datapel.getDataPeriode(), 205, 164, myPaint);
        canvas.drawText( datapel.getDataKegiatan() + datapel.getDataLembaga(), 213, 187, myPaint);
    }

    private void drawDataToPdfPeng(Canvas canvas, DataClassPeng datapeng) {
        Paint myPaint = new Paint();
        Typeface poppinbold = ResourcesCompat.getFont(requireContext(), R.font.pb);

        myPaint.setTextSize(10f);
        canvas.drawText( "> " + datapeng.getDataPeriode(), 421, 170, myPaint);
        canvas.drawText( datapeng.getDataKegiatan() + datapeng.getDataInstansi(), 428, 193, myPaint);
    }

    private void drawDataToPdfPress(Canvas canvas, DataClassPress datapres) {
        Paint myPaint = new Paint();

        myPaint.setTextSize(10f);
        canvas.drawText( "> " + datapres.getDataTanggal(), 421, 124, myPaint);
        canvas.drawText( datapres.getDataCapaian() + datapres.getDataKegiatan(), 428, 147, myPaint);
    }



    private String getEditTextValue(int editTextId) {
        EditText editText = getView().findViewById(editTextId);
        if (editText != null) {
            return editText.getText().toString();
        } else {
            return "";
        }
    }


    // Metode ini perlu diimplementasikan sesuai dengan kebutuhan PDF generation
    private void generatePdfFromData() {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {

            PdfDocument myPdfDocument = new PdfDocument();
            Paint myPaint = new Paint();
            Paint forLinePaint = new Paint();

            PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
            PdfDocument.Page mypage = myPdfDocument.startPage(myPageInfo1);
            Canvas canvas = mypage.getCanvas();
            Typeface poppinbold = ResourcesCompat.getFont(requireContext(), R.font.pb);
            Typeface poppinreg = ResourcesCompat.getFont(requireContext(), R.font.pr);

            String namaLengkap = getEditTextValue(R.id.namacv);
            String pendidikanTerakhir = getEditTextValue(R.id.pendidikancv);
            String nomorTelepon = getEditTextValue(R.id.nomorcv);
            String alamatEmail = getEditTextValue(R.id.emailcv);

            Drawable hpIcon = getResources().getDrawable(R.drawable.iconhpkecik); // Sesuaikan dengan nama file drawable Anda
            Drawable emailIcon = getResources().getDrawable(R.drawable.iconemailkecik); // Sesuaikan dengan nama file drawable Anda

            int left = 53; // Sesuaikan dengan posisi horizontal yang diinginkan
            int topHpIcon = 705; // Sesuaikan dengan posisi vertikal untuk hpIcon
            int topEmailIcon = 753; // Sesuaikan dengan posisi vertikal untuk emailIcon

            int iconSize = 15;

            hpIcon.setBounds(left, topHpIcon, left + iconSize, topHpIcon + iconSize);
            emailIcon.setBounds(left, topEmailIcon, left + iconSize, topEmailIcon + iconSize);


            hpIcon.draw(canvas);
            emailIcon.draw(canvas);

            myPaint.setTextSize(36f);
            myPaint.setColor(Color.rgb(0, 0, 0));
            myPaint.setTypeface(poppinbold);
            myPaint.setTextAlign(CENTER);
            canvas.drawText( namaLengkap, 298, 100, myPaint);
            myPaint.setTextSize(15f);
            myPaint.setTypeface(poppinreg);

            myPaint.setTextAlign(LEFT);
            canvas.drawText(nomorTelepon, 82, 720, myPaint);
            canvas.drawText(alamatEmail, 82, 765, myPaint);
            myPaint.setTextSize(10f);
            myPaint.setTypeface(poppinbold);
            canvas.drawText("Pendidikan Terakhir: " + pendidikanTerakhir, 70, 148, myPaint);
            canvas.drawText("Organisasi", 70,222, myPaint);
            canvas.drawText("Pelatihan",275,222, myPaint);
            canvas.drawText("Prestasi",480,222, myPaint);
            myPaint.setTextSize(15f);
            canvas.drawText("Kontak", 53,673, myPaint);



            for (DataClassOrg data : dataOrg) {
                // Gambar data jenis Org ke Canvas PDF
                drawDataToPdfOrg(canvas, data);
                myPaint.setTypeface(poppinreg);
                canvas.translate(0, 45); // Sesuaikan posisi setiap item
            }
            for (DataClassPel data : dataPel) {
                // Gambar data jenis Pel ke Canvas PDF
                drawDataToPdfPel(canvas, data);
                myPaint.setTypeface(poppinreg);
                canvas.translate(0, 45);; // Sesuaikan posisi setiap item
            }

            for (DataClassPress data : dataPress) {
                // Gambar data jenis Pel ke Canvas PDF
                drawDataToPdfPress(canvas, data);
                myPaint.setTypeface(poppinreg);
                canvas.translate(0, 45);
            }
            myPaint.setTextSize(10f);
            myPaint.setTypeface(poppinbold);
            canvas.drawText("Pengalaman", 470, 140, myPaint);

            for (DataClassPeng data : dataPeng) {
                // Gambar data jenis Peng ke Canvas PDF
                drawDataToPdfPeng(canvas, data);
                myPaint.setTypeface(poppinreg);
                canvas.translate(0, 45); // Sesuaikan posisi setiap item
            }



            myPdfDocument.finishPage(mypage);

            File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            String filename = "CVEdumingle.pdf";
            File file = new File(downloadDir, filename);

            // Cek apakah file sudah ada
            if (file.exists()) {
                int counter = 1;
                while (file.exists()) {
                    // Tambah nomor urut pada nama file
                    filename = "CVEdumingle(" + counter + ").pdf";
                    file = new File(downloadDir, filename);
                    counter++;
                }
            }

            try {
                FileOutputStream fos = new FileOutputStream(file);
                myPdfDocument.writeTo(fos);
                myPdfDocument.close();
                fos.close();
                Toast.makeText(getContext(), "CV telah dibuat dan disimpan di " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                Log.d("mylog", "Error while generate" + e.toString());
                throw new RuntimeException(e);
            } catch (IOException e) {
                Log.d("mylog", "Error while generating PDF: " + e.toString());
                throw new RuntimeException(e);
            }
        } else {
            Toast.makeText(getContext(), "Anda belum masuk. Silakan masuk terlebih dahulu.", Toast.LENGTH_SHORT).show();
            return;
        }

    }

}