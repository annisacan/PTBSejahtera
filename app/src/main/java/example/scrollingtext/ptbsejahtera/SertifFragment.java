package example.scrollingtext.ptbsejahtera;

import android.content.Context;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class SertifFragment extends Fragment {


    private FloatingActionButton tambahSertifButton;
    RecyclerView recyclerViewPress;
    RecyclerView recyclerViewPeng;
    RecyclerView recyclerViewOrg;
    RecyclerView recyclerViewPel;
    List<DataClassPress>dataPress;
    List<DataClassPeng>dataPeng;
    List<DataClassOrg>dataOrg;
    List<DataClassPel>dataPel;
    DatabaseReference databaseReferencePress, databaseReferencePeng, databaseReferenceOrg, databaseReferencePel;
    ValueEventListener eventListenerPress, eventListenerPeng, eventListenerOrg, eventListenerPel;
    public SertifFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sertif, container, false);

        recyclerViewPress = view.findViewById(R.id.recyclersertifPress);
        recyclerViewPeng = view.findViewById(R.id.recyclersertifPeng);
        recyclerViewOrg = view.findViewById(R.id.recyclersertifOrg);
        recyclerViewPel = view.findViewById(R.id.recyclersertifPel);

        GridLayoutManager gridLayoutManagerPress = new GridLayoutManager(requireContext(),1);
        GridLayoutManager gridLayoutManagerPeng = new GridLayoutManager(requireContext(),1);
        GridLayoutManager gridLayoutManagerOrg = new GridLayoutManager(requireContext(),1);
        GridLayoutManager gridLayoutManagerPel = new GridLayoutManager(requireContext(),1);

        recyclerViewPress.setLayoutManager(gridLayoutManagerPress);
        recyclerViewPeng.setLayoutManager(gridLayoutManagerPeng);
        recyclerViewOrg.setLayoutManager(gridLayoutManagerOrg);
        recyclerViewPel.setLayoutManager(gridLayoutManagerPel);

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

        recyclerViewPress.setAdapter(adapterPress);
        recyclerViewPeng.setAdapter(adapterPeng);
        recyclerViewOrg.setAdapter(adapterOrg);
        recyclerViewPel.setAdapter(adapterPel);

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
                    dataClassPress.setKey(itemSnapshot.getKey());
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
                    dataClassPeng.setKey(itemSnapshot.getKey());
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
                    dataClassOrg.setKey(itemSnapshot.getKey());
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
                    dataClassPel.setKey(itemSnapshot.getKey());
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

        tambahSertifButton = view.findViewById(R.id.tambahsertif);
        tambahSertifButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToTipeSertifikat();
            }
        });

        return view;
    }
    private void navigateToTipeSertifikat() {
        Intent intent = new Intent(getActivity(), Tipe_Sertif.class);
        startActivity(intent);
    }
}