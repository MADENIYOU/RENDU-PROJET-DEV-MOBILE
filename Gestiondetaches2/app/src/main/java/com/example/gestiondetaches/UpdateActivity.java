package com.example.gestiondetaches;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UpdateActivity extends AppCompatActivity {

    EditText task_title, task_description;
    Button update_button;

    String id, nom, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        task_title = findViewById(R.id.title_input2);
        task_description = findViewById(R.id.descrption_input2);
        Spinner spinner = findViewById(R.id.spinner2);

        String[] items = {"Todo", "Bug", "In Progress", "Done"};
        int[] colors = {R.drawable.circle_background, R.drawable.circle_background_red, R.drawable.circle_background_blue, R.drawable.circle_background_green};

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, items, colors);
        spinner.setAdapter(adapter);
        update_button = findViewById(R.id.update_button);
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaBaseDeDonnees myDB = new MaBaseDeDonnees(UpdateActivity.this);
                myDB.updateData(id, task_title.getText().toString().trim(),
                        task_description.getText().toString().trim(),
                        spinner.getSelectedItem().toString().trim());
            }
        });
        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(task_title.getText().toString());
        }


        FloatingActionButton close_button;
        close_button = findViewById(R.id.floatingCloseButton);
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    void getAndSetIntentData() {
        if(getIntent().hasExtra("Nom") && getIntent().hasExtra("id")) {
            //Récupération des données
            id = getIntent().getStringExtra("id");
            nom = getIntent().getStringExtra("Nom");
            description = getIntent().getStringExtra("Description");

            //Modification des données
            task_title.setText(nom);
            task_description.setText(description);
        } else {
            Toast.makeText(this, "Aucune donnee !", Toast.LENGTH_SHORT).show();
        }
    }


}