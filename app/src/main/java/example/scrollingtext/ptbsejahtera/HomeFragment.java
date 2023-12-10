package example.scrollingtext.ptbsejahtera;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link HomeFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class HomeFragment extends Fragment {

    private TextView tabSertif, tabCV;
    private int selectedTabfragnomor = 1;
    //cuman punya 2 jadi nilai antara 1-2
    //deafult ke 1 karna tab deafult nya tab sertif

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tabSertif = view.findViewById(R.id.tabSertif);
        tabCV = view.findViewById(R.id.tabCV);

        //default tab
        selectTabfrag(selectedTabfragnomor);

        tabSertif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTabfragnomor = 1;
                selectTabfrag(selectedTabfragnomor);
            }
        });

        tabCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTabfragnomor = 2;
                selectTabfrag(selectedTabfragnomor);
            }
        });

        return view;
    }

    private void selectTabfrag (int tabNomor){
        Fragment fragment;

        if (tabNomor == 1) {
            // If tab 1 is selected, show SertifFragment
            tabSertif.setBackgroundResource(R.drawable.round_back_putihh);
            tabCV.setBackgroundResource(R.drawable.round_back_transparantputihh);
            fragment = new SertifFragment();
        } else {
            // If tab 2 is selected, show CVFragment
            tabSertif.setBackgroundResource(R.drawable.round_back_transparantputihh);
            tabCV.setBackgroundResource(R.drawable.round_back_putihh);
            fragment = new CvFragment();
        }
        Animation slideIn = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_from_right);
        Animation slideOut = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_to_left);
        Animation slideInReverse = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_from_left);
        Animation slideOutReverse = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_to_right);

        replaceFragmentWithAnimation(fragment, slideIn, slideInReverse,slideOut , slideOutReverse);
    }

    private void replaceFragmentWithAnimation(Fragment fragment, Animation slideIn, Animation slideOut,
                                              Animation slideInReverse, Animation slideOutReverse) {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();

        // Set custom animations
        transaction.setCustomAnimations(
                R.anim.slide_down,
                0,
                R.anim.slide_down_reverse,
                0
        );

        transaction.replace(R.id.fragmendihome, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}