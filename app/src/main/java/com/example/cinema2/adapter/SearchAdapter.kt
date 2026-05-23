package com.example.cinema2.adapter
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema2.R
import com.example.cinema2.model.Film
class SearchAdapter(
    private var films: MutableList<Film>,
    private val onClick: (Film) -> Unit
) : RecyclerView.Adapter<SearchAdapter.VH>() {
    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        val affiche: ImageView = v.findViewById(R.id.img_search)
        val titre: TextView = v.findViewById(R.id.tv_search_titre)
        val date: TextView = v.findViewById(R.id.tv_search_date)
    }
    override fun onCreateViewHolder(p: ViewGroup, t: Int): VH {
        val v = LayoutInflater.from(p.context)
            .inflate(R.layout.item_search, p, false)
        return VH(v)
    }
    override fun onBindViewHolder(h: VH, pos: Int) {
        val f = films[pos]
        h.titre.text = f.titre
        h.date.text = f.dateSortie
        Glide.with(h.itemView)
            .load("https://image.tmdb.org/t/p/w200${f.cheminAffiche}")
            .into(h.affiche)
        h.itemView.setOnClickListener { onClick(f) }
    }
    override fun getItemCount() = films.size
    fun update(newList: MutableList<Film>) {
        films = newList
        notifyDataSetChanged()
    }
}
