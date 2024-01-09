package example.scrollingtext.ptbsejahtera;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bumptech.glide.disklrucache.DiskLruCache;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class KalenderFragment extends Fragment {

    private Button button_tambah;
    RecyclerView recyclerView;
    List<DataClassKalender> dataKal;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;

    public KalenderFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_kalender, container, false);

        recyclerView = view.findViewById(R.id.recyclerkalender);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        dataKal = new ArrayList<>();

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();

        AdapterKalender adapterKalender = new AdapterKalender(getContext(), dataKal);
        recyclerView.setAdapter(adapterKalender);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("agenda").child(uid);

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataKal.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    DataClassKalender dataClassKalender = itemSnapshot.getValue(DataClassKalender.class);
                    dataClassKalender.setKey(itemSnapshot.getKey());
                    dataKal.add(dataClassKalender);
                }
                adapterKalender.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { dialog.dismiss();}
        });

        // Inisialisasi button dari layout fragment_kalender
        button_tambah = view.findViewById(R.id.btntambahkalender); // Sesuaikan dengan ID button Anda

        // Set listener atau lakukan operasi lainnya jika diperlukan
        button_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Buat Intent untuk memulai Activity baru
                navigateToActivityKalender();
            }
        });

        return view;
    }

    private void navigateToActivityKalender(){
        Intent intent = new Intent(getActivity(), Kalender.class);
        startActivity(intent);
    }

}