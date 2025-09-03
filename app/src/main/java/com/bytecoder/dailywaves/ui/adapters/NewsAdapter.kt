package com.bytecoder.dailywaves.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bytecoder.dailywaves.data.model.Article
import com.bytecoder.dailywaves.databinding.ItemNewsBinding

class NewsAdapter(
    private val onClick: (Article) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsVH>() {

    private val items = mutableListOf<Article>()

    fun submitList(list: List<Article>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    inner class NewsVH(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsVH {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsVH(binding)
    }

    override fun onBindViewHolder(holder: NewsVH, position: Int) {
        val item = items[position]
        with(holder.binding) {
            tvTitle.text = item.title ?: ""
            tvSubtitle.text = item.description ?: item.content ?: ""
            Glide.with(ivThumb).load(item.urlToImage).centerCrop().into(ivThumb)
            root.setOnClickListener { onClick(item) }
        }
    }

    override fun getItemCount(): Int = items.size
}
