package com.example.gestiondetaches;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;


    MaBaseDeDonnees myDB;
    ArrayList<String> tache_id, tache_titre, tache_statut, tache_description;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Edge-to-edge setup
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });




        myDB = new MaBaseDeDonnees(MainActivity.this);

        tache_id = new ArrayList<>();
        tache_titre = new ArrayList<>();
        tache_description = new ArrayList<>();
        tache_statut = new ArrayList<>();

         affichageDonnes();


        customAdapter = new CustomAdapter(MainActivity.this, this, tache_id,tache_statut, tache_titre);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        } else if (requestCode == 2) {
            recreate();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.filtrer) {
            afficherDialogFiltrage();

        }
        return super.onOptionsItemSelected(item);
    }

    void affichageDonnes() {
        Cursor curseur = myDB.affichageDonnees();

        if (curseur.getCount() == 0) {
            Toast.makeText(this, "Aucune tâche", Toast.LENGTH_SHORT).show();
        } else {
            while (curseur.moveToNext()) {
                tache_id.add(curseur.getString(0));
                tache_titre.add(curseur.getString(1));
                //tache_description.add(curseur.getString(2));
                tache_statut.add(curseur.getString(3));
            }
        }
    }

    private void afficherDialogFiltrage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_filter, null);
        builder.setView(dialogView);

        // Récupérer les éléments du layout du dialogue
        RadioGroup radioGroup = dialogView.findViewById(R.id.radioGroup);
        Button btnApply = dialogView.findViewById(R.id.btn_apply);

        AlertDialog dialog = builder.create();

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    RadioButton selectedRadioButton = dialogView.findViewById(selectedId);
                    String optionText = selectedRadioButton.getText().toString();
                    Cursor filtered = myDB.filtrerTaches(optionText);
                    afficherDonnesFiltrees(filtered);
                } else {
                    Toast.makeText(MainActivity.this, "Aucune option sélectionnée", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss(); // Fermer le dialogue après le filtrage
            }
        });

        dialog.show();
    }

    private void afficherDonnesFiltrees(Cursor cursor) {
        tache_id.clear();
        tache_titre.clear();
        tache_statut.clear();

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                tache_id.add(cursor.getString(0));
                tache_titre.add(cursor.getString(1));
                tache_statut.add(cursor.getString(3));
            }
            cursor.close(); // Fermer le curseur après avoir utilisé les données
        }

        // Mettre à jour le RecyclerView avec les données filtrées
        customAdapter.notifyDataSetChanged();
    }



}
