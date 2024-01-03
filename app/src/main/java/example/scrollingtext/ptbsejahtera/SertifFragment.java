package example.scrollingtext.ptbsejahtera;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SertifFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class SertifFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SertifFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SertifFragment newInstance(String param1, String param2) {
        SertifFragment fragment = new SertifFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private FloatingActionButton tambahSertifButton;
    public SertifFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sertif, container, false);

        // Inisialisasi Floating Action Button
        tambahSertifButton = view.findViewById(R.id.tambahsertif);

        // Menambahkan onClickListener pada Floating Action Button
        tambahSertifButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Aksi yang ingin dilakukan saat Floating Action Button ditekan
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