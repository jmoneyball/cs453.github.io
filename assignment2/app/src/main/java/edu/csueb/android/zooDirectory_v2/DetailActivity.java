package edu.csueb.android.zooDirectory_v2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_detail);

        //update variables based on animal passed to activity
        String name = getIntent().getStringExtra("NAME");
        String description = getIntent().getStringExtra("DESCRIPTION");
        int image = getIntent().getIntExtra("IMAGE",0);

        //create and populate views
        TextView nameTextView = findViewById(R.id.animalName);
        TextView descriptionTextView = findViewById(R.id.animalDesc);
        ImageView imageView = findViewById(R.id.animalImage);
        nameTextView.setText(name);
        descriptionTextView.setText(description);
        imageView.setImageResource(image);
    }
}