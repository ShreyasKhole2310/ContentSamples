package com.example.diagnaltest.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.diagnaltest.R
import com.example.diagnaltest.data.Content

class ContentListRecyclerAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Content, RecyclerView.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ContentItemViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.content_list_item, parent,false
        ))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ContentItemViewHolder
        val content = getItem(position)
        holder.contentNameText.text = content?.name
        Glide.with(holder.itemView.context)
            .load("file:///android_asset/${content.posterImage}")
            .into(holder.posterImage)
        holder.bind(content, onClickListener)
    }

    internal class ContentItemViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val posterImage: AppCompatImageView = itemView.findViewById(R.id.poster_image)
        val contentNameText: AppCompatTextView = itemView.findViewById(R.id.content_name_text)
        fun bind(content: Content,onClickListener: OnClickListener) {
            itemView.setOnClickListener{onClickListener.onClick(content, itemView) }
        }
    }

    class OnClickListener(val clickListener: (item: Content, view: View) -> Unit) {
        fun onClick(item: Content, view: View) = clickListener(item, view)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Content>() {
        override fun areItemsTheSame(oldContent: Content, newContent: Content): Boolean {
            return oldContent === newContent
        }

        override fun areContentsTheSame(oldContent: Content, newContent: Content): Boolean {
            return oldContent.name == newContent.name
        }
    }
}