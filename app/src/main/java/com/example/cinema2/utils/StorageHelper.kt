package com.example.cinema2.utils
import android.content.Context
import com.example.cinema2.model.Film
import org.json.JSONArray
import org.json.JSONObject
import java.io.FileNotFoundException
object StorageHelper {
    private const val FICHIER = "moncineromance.json"
    fun sauvegarder(ctx: Context, films: List<Film>) {
        try {
            val array = JSONArray()
            films.forEach { f ->
                val o = JSONObject()
                o.put("tmdbId", f.tmdbId)
                o.put("titre", f.titre)
                o.put("affiche", f.cheminAffiche)
                o.put("synopsis", f.synopsis)
                o.put("noteTmdb", f.noteTmdb)
                o.put("dateSortie", f.dateSortie)
                o.put("statut", f.statut)
                o.put("notePerso", f.notePersonnelle)
                o.put("avis", f.avisPersonnel)
                o.put("dateVision", f.dateVisionnage)
                o.put("dateAjout", f.dateAjout)
                array.put(o)
            }
            ctx.openFileOutput(FICHIER, Context.MODE_PRIVATE)
                .use { it.write(array.toString().toByteArray()) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun charger(ctx: Context): MutableList<Film> {
        val liste = mutableListOf<Film>()
        try {
            val json = ctx.openFileInput(FICHIER)
                .bufferedReader().readText()
            val array = JSONArray(json)
            for (i in 0 until array.length()) {
                val o = array.getJSONObject(i)
                liste.add(
                    Film(
                        tmdbId = o.getInt("tmdbId"),
                        titre = o.getString("titre"),
                        cheminAffiche = o.getString("affiche"),
                        synopsis = o.getString("synopsis"),
                        noteTmdb = o.getDouble("noteTmdb"),
                        dateSortie = o.getString("dateSortie"),
                        statut = o.getString("statut"),
                        notePersonnelle = o.getDouble("notePerso").toFloat(),
                        avisPersonnel = o.getString("avis"),
                        dateVisionnage = o.getString("dateVision")
                    )
                )
            }
        } catch (e: FileNotFoundException) {
            // Normal la première fois
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return liste
    }

    fun ajouter(ctx: Context, film: Film) {
        val liste = charger(ctx)
        liste.removeIf { it.tmdbId == film.tmdbId }
        liste.add(film)
        sauvegarder(ctx, liste)
    }

    fun supprimer(ctx: Context, tmdbId: Int) {
        val liste = charger(ctx)
        liste.removeIf { it.tmdbId == tmdbId }
        sauvegarder(ctx, liste)
    }
}