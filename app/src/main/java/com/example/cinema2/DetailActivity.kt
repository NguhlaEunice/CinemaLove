package com.example.cinema2
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cinema2.model.Film
import com.example.cinema2.utils.StorageHelper
import java.util.Calendar
class DetailActivity : AppCompatActivity() {
    private var dateVisionnage = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val filmId = intent.getIntExtra("film_id", 0)
        val titre = intent.getStringExtra("film_titre") ?: ""
        val affiche = intent.getStringExtra("film_affiche") ?: ""
        val synopsis = intent.getStringExtra("film_synopsis") ?: ""
        val note = intent.getDoubleExtra("film_note", 0.0)
        val date = intent.getStringExtra("film_date") ?: ""
        findViewById<TextView>(R.id.tv_titre_detail).text = titre
        findViewById<TextView>(R.id.tv_date_detail).text = date
        findViewById<TextView>(R.id.tv_synopsis).text = synopsis
        findViewById<RatingBar>(R.id.rb_note_tmdb).rating = (note / 2).toFloat()
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500$affiche")
            .into(findViewById(R.id.img_affiche_detail))
        val spinner = findViewById<Spinner>(R.id.spinner_statut)
        ArrayAdapter.createFromResource(
            this,
            R.array.statuts_array,
            android.R.layout.simple_spinner_item
        ).also {
            it.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
            )
            spinner.adapter = it
        }
        val tvDate = findViewById<TextView>(R.id.tv_date_selectionnee)
        findViewById<android.widget.Button>(
            R.id.btn_date_visionnage
        ).setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(
                this, { _, y, m, d ->
                    dateVisionnage = "$d/${m + 1}/$y"
                    tvDate.text = dateVisionnage
                }, cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        val rbPerso = findViewById<RatingBar>(R.id.rb_note_perso)
        val etAvis = findViewById<EditText>(R.id.et_avis)
        findViewById<android.widget.Button>(
            R.id.btn_ajouter
        ).setOnClickListener {
            val film = Film(
                tmdbId = filmId,
                titre = titre,
                cheminAffiche = affiche,
                noteTmdb = note,
                dateSortie = date,
                synopsis = synopsis,
                statut = spinner.selectedItem.toString(), notePersonnelle = rbPerso.rating,
                avisPersonnel = etAvis.text.toString(),
                dateVisionnage = dateVisionnage
            )
            StorageHelper.ajouter(this, film)
            Toast.makeText(
                this,
                "Ajout&#233; &#224; ta collection ! &#10084;",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onSaveInstanceState(out: Bundle) {
        super.onSaveInstanceState(out)
        out.putString("date_vision", dateVisionnage)
    }

    override fun onRestoreInstanceState(saved: Bundle) {
        super.onRestoreInstanceState(saved)
        dateVisionnage = saved.getString("date_vision", "")!!
    }
}