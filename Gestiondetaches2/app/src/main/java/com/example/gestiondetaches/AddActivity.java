package com.example.gestiondetaches;

import static com.example.gestiondetaches.R.layout.activity_add;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    EditText title_input, description_input;
    Button button_ajout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Spinner spinner = findViewById(R.id.spinner);

        String[] items = {"Todo", "Bug", "In Progress", "Done"};
        int[] colors = {R.drawable.circle_background, R.drawable.circle_background_red, R.drawable.circle_background_blue, R.drawable.circle_background_green};

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, items, colors);
        spinner.setAdapter(adapter);

        title_input = findViewById(R.id.title_input);
        description_input = findViewById(R.id.descrption_input);
        button_ajout = findViewById(R.id.button_ajout);
        button_ajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaBaseDeDonnees myDB = new MaBaseDeDonnees(AddActivity.this);
                myDB.ajouterTache(title_input.getText().toString().trim(),
                        description_input.getText().toString().trim(),
                        spinner.getSelectedItem().toString().trim());
            }
        });

        FloatingActionButton close_button;
        close_button = findViewById(R.id.floatingCloseButton);
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

