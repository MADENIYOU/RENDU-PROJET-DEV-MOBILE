package com.example.gestiondetaches;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MaBaseDeDonnees extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "TodoApp.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "ma_todolist";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITRE = "tache";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_STATUT = "statut";
    MaBaseDeDonnees(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String requete = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITRE + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT," +
                COLUMN_STATUT + " TEXT)";

        db.execSQL(requete);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void ajouterTache(String titre, String details, String statut) {
            SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contenu = new ContentValues();

        contenu.put(COLUMN_TITRE, titre);
        contenu.put(COLUMN_DESCRIPTION, details);
        contenu.put(COLUMN_STATUT, statut);
        long resultat = db.insert(TABLE_NAME, null, contenu);

        if (resultat == -1) {
            Toast.makeText(context, "Insertion échouée !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Insertion réussie !", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor affichageDonnees() {
        String requete = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor curseur = null;

        if (db != null) {
            curseur = db.rawQuery(requete, null);
        }
        return curseur;
    }

    Cursor filtrerTaches(String statut) {
        String requete = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_STATUT + " = '" + statut + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(requete, null);
        }

        return  cursor;
    }

    void updateData(String row_id, String titre, String details, String statut) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITRE, titre);
        cv.put(COLUMN_DESCRIPTION, details);
        cv.put(COLUMN_STATUT, statut);

        long result = db.update(TABLE_NAME, cv, "id=?", new String[]{row_id});

        if(result == -1) {
            Toast.makeText(context, "Modification échouée !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Modification réussie !", Toast.LENGTH_SHORT).show();
        }
    }
}
