package edu.csueb.android.zooDirectory_v2;

//imports
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.AlertDialog;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {

    //declarations
    public static String PACKAGE_NAME;
    RecyclerView recyclerView;
    AlertDialog.Builder builder;
    ArrayList<AnimalDetails> zooDirectory = new ArrayList<>();

    //populate images into an array
    int[] animalImages = {
        R.drawable.gorilla,
        R.drawable.lion,
        R.drawable.penguin,
        R.drawable.redpanda,
        R.drawable.zebra
    };

    //main function
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //housekeeping
        builder = new AlertDialog.Builder(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //save package name
        PACKAGE_NAME = getApplicationContext().getPackageName();
        PACKAGE_NAME = "package:" + PACKAGE_NAME;

        //populate arrays w/ animal information
        setUpAnimalDetails();

        //set up recyclerView
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        Adapter adapter = new Adapter(this, this, zooDirectory);
        recyclerView.setAdapter(adapter);
    }

    //inflate options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //actions based on selections from option menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int ItemID = item.getItemId();
        //if information selected, launch info action
        if (ItemID == R.id.action_info) {
            Intent intent = new Intent(this, InfoActivity.class);
            startActivity(intent);
        }
        //if uninstall selected, prompt user
        if (ItemID == R.id.action_uninstall) {
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.parse(PACKAGE_NAME));
            startActivity(intent);
        }
        return super.onContextItemSelected(item);
    }

    //populate strings of animal information into string arrays
    private void setUpAnimalDetails(){
        String[] animalNames = getResources().getStringArray(R.array.animal_names_full);
        String[] animalDescriptions = getResources().getStringArray(R.array.animal_descriptions_full);
        for (int i = 0; i <animalNames.length;i++) {
            zooDirectory.add(new AnimalDetails(animalNames[i], animalDescriptions[i], animalImages[i]));
        };
    };

    @Override
    public void onItemClick(int position) {
        //declarations
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        builder.setMessage(R.string.alert_message).setTitle(R.string.alert_name);

        //putting contents of selected animal into an intent
        intent.putExtra("NAME", zooDirectory.get(position).getAnimalName());
        intent.putExtra("DESCRIPTION", zooDirectory.get(position).getAnimalDescription());
        intent.putExtra("IMAGE", zooDirectory.get(position).getAnimalImage());

        //if the last animal in the directory is chosen, launch a dialogue menu
        if(position==(zooDirectory.size()-1)) {

            //if the user chooses yes, show the animal
            builder.setPositiveButton("Yes", (dialog, id) -> startActivity(intent));

            //if the user chooses no, do nothing
            builder.setNegativeButton("No", (dialog, id) -> dialog.dismiss());
            AlertDialog alert = builder.create();
            alert.show();
        }
        else { startActivity(intent);}
    }
}