package com.example.cinema2.adapter
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema2.R
import com.example.cinema2.model.Film
class FilmAdapter(
    private var films: MutableList<Film>,
    private val onClick: (Film) -> Unit,
    private val onLongClick: (Film, View) -> Unit
) : RecyclerView.Adapter<FilmAdapter.VH>() {
    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        val affiche: ImageView = v.findViewById(R.id.img_affiche)
        val titre: TextView = v.findViewById(R.id.tv_titre)
        val note: RatingBar = v.findViewById(R.id.rb_note)
        val statut: TextView = v.findViewById(R.id.tv_statut)
    }

    override fun onCreateViewHolder(p: ViewGroup, t: Int): VH {
        val v = LayoutInflater.from(p.context)
            .inflate(R.layout.item_film, p, false)
        return VH(v)
    }

    override fun onBindViewHolder(h: VH, pos: Int) {
        val f = films[pos]
        h.titre.text = f.titre
        h.note.rating = f.notePersonnelle
        h.statut.text = f.statut.replace("_", " ")
        Glide.with(h.itemView)
            .load("https://image.tmdb.org/t/p/w200${f.cheminAffiche}")
            .into(h.affiche)
        h.itemView.setOnClickListener { onClick(f) }
        h.itemView.setOnLongClickListener { onLongClick(f, it); true }
    }

    override fun getItemCount() = films.size
    fun update(newList: MutableList<Film>) {
        films = newList
        notifyDataSetChanged()
    }
}