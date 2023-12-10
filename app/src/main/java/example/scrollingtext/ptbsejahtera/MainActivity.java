package example.scrollingtext.ptbsejahtera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import example.scrollingtext.ptbsejahtera.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    private int selectedTab = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout homelayout = findViewById(R.id.navLayoutHome);
        final LinearLayout kalenderlayout = findViewById(R.id.navLayoutKalender);
        final LinearLayout searchlayout = findViewById(R.id.navLayoutSearch);

        final ImageView homeimage = findViewById(R.id.homenavimage);
        final ImageView kalenderimage = findViewById(R.id.kalendernavimage);
        final ImageView searchimage = findViewById(R.id.searchnavimage);

        final TextView hometxt = findViewById(R.id.homenavtxt);
        final TextView kalendertxt = findViewById(R.id.kalendernavtxt);
        final TextView searchtxt = findViewById(R.id.searchnavtxt);

        //buat home fragment sebagai default
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.frame_layout_main, HomeFragment.class, null)
                .commit();

        homelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //cek tab nya udah kepilih ato belum
                if (selectedTab != 1) {

                    // set home fragment
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.frame_layout_main, HomeFragment.class, null)
                            .commit();

                    //tab yang gk dipilih
                    kalendertxt.setVisibility(View.GONE);
                    searchtxt.setVisibility(View.GONE);

                    kalenderimage.setImageResource(R.drawable.vektorkalender);
                    searchimage.setImageResource(R.drawable.baseline_search_24);

                    kalenderlayout.setBackgroundColor(getResources().getColor((android.R.color.transparent)));
                    searchlayout.setBackgroundColor(getResources().getColor((android.R.color.transparent)));

                    //tab yang dipilih
                    hometxt.setVisibility(View.VISIBLE);
                    homeimage.setImageResource(R.drawable.vektorhome_active);
                    homelayout.setBackgroundResource(R.drawable.round_belakanghome);

                    //animasih
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    homelayout.startAnimation(scaleAnimation);

                    //buat tab home di select
                    selectedTab = 1;
                }
            }
        });

        kalenderlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTab != 2) {

                    //set kalender fragment
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.frame_layout_main, KalenderFragment.class, null)
                            .commit();

                    //tab yang gk dipilih
                    hometxt.setVisibility(View.GONE);
                    searchtxt.setVisibility(View.GONE);

                    homeimage.setImageResource(R.drawable.vektorhome);
                    searchimage.setImageResource(R.drawable.baseline_search_24);

                    homelayout.setBackgroundColor(getResources().getColor((android.R.color.transparent)));
                    searchlayout.setBackgroundColor(getResources().getColor((android.R.color.transparent)));

                    //tab yang dipilih
                    kalendertxt.setVisibility(View.VISIBLE);
                    kalenderimage.setImageResource(R.drawable.vektorkalender_active);
                    kalenderlayout.setBackgroundResource(R.drawable.round_belakanghome);

                    //animasih
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    kalenderlayout.startAnimation(scaleAnimation);

                    //buat tab kalender di select
                    selectedTab = 2;
                }
            }
        });

        searchlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTab != 3) {

                    //set search fragment
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.frame_layout_main, SearchFragment.class, null)
                            .commit();

                    //tab yang gk dipilih
                    hometxt.setVisibility(View.GONE);
                    kalendertxt.setVisibility(View.GONE);

                    homeimage.setImageResource(R.drawable.vektorhome);
                    kalenderimage.setImageResource(R.drawable.vektorkalender);

                    homelayout.setBackgroundColor(getResources().getColor((android.R.color.transparent)));
                    kalenderlayout.setBackgroundColor(getResources().getColor((android.R.color.transparent)));

                    //tab yang dipilih
                    searchtxt.setVisibility(View.VISIBLE);
                    searchimage.setImageResource(R.drawable.search_active);
                    searchlayout.setBackgroundResource(R.drawable.round_belakanghome);

                    //animasih
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    searchlayout.startAnimation(scaleAnimation);

                    //buat tab search di select
                    selectedTab = 3;
                }
            }
        });
    }
}