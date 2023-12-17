package example.scrollingtext.ptbsejahtera;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeFragment extends Fragment {

    private TextView tabSertif, tabCV;
    private int selectedTabfragnomor = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tabSertif = view.findViewById(R.id.tabSertif);
        tabCV = view.findViewById(R.id.tabCV);

        // Default tab
        selectTabfrag(selectedTabfragnomor);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragmendihome, new SertifFragment())
                .commit();

        tabSertif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTabfragnomor = 1;
                selectTabfrag(selectedTabfragnomor);
                getChildFragmentManager().beginTransaction()
                        .replace(R.id.fragmendihome, new SertifFragment())
                        .commit();
            }
        });

        tabCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTabfragnomor = 2;
                selectTabfrag(selectedTabfragnomor);
                getChildFragmentManager().beginTransaction()
                        .replace(R.id.fragmendihome, new CvFragment())
                        .commit();
            }
        });

        return view;
    }

    private void selectTabfrag(int tabNomor) {
        if (tabNomor == 1) {
            // If tab 1 is selected, show SertifFragment
            tabCV.setBackgroundResource(R.drawable.round_back_putihh);
            tabSertif.setBackgroundResource(R.drawable.round_back_transparantputihh);

        } else {
            // If tab 2 is selected, show CVFragment
            tabCV.setBackgroundResource(R.drawable.round_back_transparantputihh);
            tabSertif.setBackgroundResource(R.drawable.round_back_putihh);
        }
    }
}
