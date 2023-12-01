package example.scrollingtext.ptbsejahtera;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import de.hdodenhof.circleimageview.CircleImageView;

public class Home_Activity extends AppCompatActivity {

    private CircleImageView profileImage;
    private String profileImageUrl = "https://i.pinimg.com/564x/3f/17/e0/3f17e00b072cbb13c0ba1c3b5f84901c.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_profile);
        View view = MenuItemCompat.getActionView(menuItem);

        CircleImageView profileImage = view.findViewById(R.id.toolbar_profile_image);

        Glide.with(this)
                .load(profileImageUrl)
                .into(profileImage);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Home_Activity.this, "Profil tampil", Toast.LENGTH_SHORT).show();
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id== R.id.menu_profile){
            Toast.makeText(this, "Profil ditampilkan", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
