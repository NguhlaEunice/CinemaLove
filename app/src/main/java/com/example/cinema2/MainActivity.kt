package com.example.cinema2
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema2.adapter.FilmAdapter
import com.example.cinema2.model.Film
import com.example.cinema2.utils.StorageHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
class MainActivity : AppCompatActivity() {
    private lateinit var adapter: FilmAdapter
    private val collection = mutableListOf<Film>()
    private var filmSelectionne: Film? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val rv = findViewById<RecyclerView>(R.id.recycler_collection)
        rv.layoutManager = LinearLayoutManager(this)
        adapter = FilmAdapter(
            collection,
            onClick = { film ->
                val i = Intent(this, DetailActivity::class.java)
                i.putExtra("film_id", film.tmdbId)
                i.putExtra("film_titre", film.titre)
                i.putExtra("film_affiche", film.cheminAffiche)
                i.putExtra("film_synopsis", film.synopsis)
                i.putExtra("film_note", film.noteTmdb)
                i.putExtra("film_date", film.dateSortie)
                startActivity(i)
            },
            onLongClick = { film, view ->
                filmSelectionne = film
                registerForContextMenu(view)
                openContextMenu(view)
            }
        )
        rv.adapter = adapter
        findViewById<FloatingActionButton>(R.id.fab_search)
            .setOnClickListener {
                startActivity(
                    Intent(
                        this,
                        SearchActivity::class.java
                    )
                )
            }
    }

    override fun onResume() {
        super.onResume()
        collection.clear()
        collection.addAll(StorageHelper.charger(this))
        adapter.notifyDataSetChanged()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onSaveInstanceState(out: Bundle) {
        super.onSaveInstanceState(out)
    }

    override fun onRestoreInstanceState(saved: Bundle) {
        super.onRestoreInstanceState(saved)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View, info: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.menu_context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_supprimer) {
            filmSelectionne?.let { f ->
                AlertDialog.Builder(this)
                    .setTitle("Supprimer")
                    .setMessage("Supprimer ${f.titre} ?")
                    .setPositiveButton("Oui") { _, _ ->
                        StorageHelper.supprimer(this, f.tmdbId)
                        collection.clear()
                        collection.addAll(StorageHelper.charger(this))
                        adapter.notifyDataSetChanged()
                        Toast.makeText(
                            this, "Supprim&#233;",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .setNegativeButton("Non", null).show()
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_parametres) {
            startActivity(
                Intent(
                    this,
                    SettingsActivity::class.java
                )
            )
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}