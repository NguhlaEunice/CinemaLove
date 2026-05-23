package com.example.cinema2.utils
import com.example.cinema2.model.Film


import android.os.Handler
import android.os.Looper
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.concurrent.Executors

object TMDBHelper {
    private const val BASE = "https://api.themoviedb.org/3"
    private val handler = Handler(Looper.getMainLooper())

    interface Callback {
        fun onSuccess(films: List<Film>)
        fun onError(msg: String)
    }

    // Rechercher un film par titre
    fun rechercher(query: String, apiKey: String, cb: Callback) {
        Executors.newSingleThreadExecutor().execute {
            try {
                val url = "$BASE/search/movie?api_key=$apiKey" +
                        "&query=${URLEncoder.encode(query, "UTF-8")}" +
                        "&language=fr-FR"
                val films = parse(fetchUrl(url))
                handler.post { cb.onSuccess(films) }
            } catch (e: Exception) {
                handler.post { cb.onError(e.message ?: "Erreur") }
            }
        }
    }

    // Films romance populaires (genre 10749)
    fun filmsRomance(apiKey: String, cb: Callback) {
        Executors.newSingleThreadExecutor().execute {
            try {
                val url = "$BASE/discover/movie?api_key=$apiKey" +
                        "&with_genres=10749" +
                        "&language=fr-FR" +
                        "&sort_by=popularity.desc"
                val films = parse(fetchUrl(url))
                handler.post { cb.onSuccess(films) }
            } catch (e: Exception) {
                handler.post { cb.onError(e.message ?: "Erreur") }
            }
        }
    }

    private fun fetchUrl(urlStr: String): String {
        val conn = URL(urlStr).openConnection() as HttpURLConnection
        conn.connectTimeout = 10000
        conn.readTimeout = 10000
        return conn.inputStream.bufferedReader().readText()
    }

    private fun parse(json: String): List<Film> {
        val results = JSONObject(json).getJSONArray("results")
        return (0 until results.length()).map { i ->
            val o = results.getJSONObject(i)
            Film(
                tmdbId        = o.getInt("id"),
                titre         = o.optString("title", "Sans titre"),
                cheminAffiche = o.optString("poster_path", ""),
                noteTmdb      = o.optDouble("vote_average", 0.0),
                dateSortie    = o.optString("release_date", ""),
                synopsis      = o.optString("overview", "")
            )
        }
    }
}

