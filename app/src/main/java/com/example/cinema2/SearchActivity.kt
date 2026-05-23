package com.example.cinema2
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema2.adapter.SearchAdapter
import com.example.cinema2.model.Film
import com.example.cinema2.utils.TMDBHelper
class SearchActivity : AppCompatActivity() {
    private lateinit var adapter: SearchAdapter
    private val resultats = mutableListOf<Film>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val apiKey = BuildConfig.TMDB_KEY
        val progress = findViewById<ProgressBar>(R.id.progress_bar)
        val rv = findViewById<RecyclerView>(R.id.recycler_search)
        rv.layoutManager = LinearLayoutManager(this)
        adapter = SearchAdapter(resultats) { film ->
            val i = Intent(this, DetailActivity::class.java)
            i.putExtra("film_id", film.tmdbId)
            i.putExtra("film_titre", film.titre)
            i.putExtra("film_affiche", film.cheminAffiche)
            i.putExtra("film_synopsis",film.synopsis)
            i.putExtra("film_note", film.noteTmdb)
            i.putExtra("film_date", film.dateSortie)
            startActivity(i)
        }
        rv.adapter = adapter
// Charger films romance populaires au démarrage
        TMDBHelper.filmsRomance(apiKey, object: TMDBHelper.Callback {
            override fun onSuccess(films: List<Film>) {
                resultats.clear()
                resultats.addAll(films)
                adapter.notifyDataSetChanged()
            }
            override fun onError(msg: String) {
                Toast.makeText(this@SearchActivity,
                    "Pas de connexion internet",
                    Toast.LENGTH_SHORT).show()
            }
        })
        val sv = findViewById<SearchView>(R.id.search_view)
        sv.setOnQueryTextListener(
            object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(q: String): Boolean {
                    progress.visibility = View.VISIBLE
                    TMDBHelper.rechercher(q, apiKey,
                        object: TMDBHelper.Callback {
                            override fun onSuccess(films: List<Film>) {
                                progress.visibility = View.GONE
                                resultats.clear()
                                resultats.addAll(films)
                                adapter.notifyDataSetChanged()
                                if (films.isEmpty())
                                    Toast.makeText(this@SearchActivity,
                                        "Aucun r&#233;sultat",
                                        Toast.LENGTH_SHORT).show()
                            }
                            override fun onError(msg: String) {
                                progress.visibility = View.GONE
                                Toast.makeText(this@SearchActivity,"Erreur r&#233;seau",
                                    Toast.LENGTH_LONG).show()
                            }
                        })
                    return true
                }
                override fun onQueryTextChange(q: String) = false
            })
    }
    override fun onPause() { super.onPause() }
    override fun onResume() { super.onResume() }
}