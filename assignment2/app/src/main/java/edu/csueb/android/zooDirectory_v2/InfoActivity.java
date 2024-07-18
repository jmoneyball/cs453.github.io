package edu.csueb.android.zooDirectory_v2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class InfoActivity extends AppCompatActivity {
    @Override
        @SuppressLint("InflateParams")

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);

            LayoutInflater inflater = LayoutInflater.from(this);
            View view = inflater.inflate(R.layout.activity_info, null);
            setContentView(view);
        }

        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:888-888-8888"));
            startActivity(intent);
        }
}

