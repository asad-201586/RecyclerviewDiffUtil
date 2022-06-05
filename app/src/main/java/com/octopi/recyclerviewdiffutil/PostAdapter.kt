package com.octopi.recyclerviewdiffutil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.octopi.recyclerviewdiffutil.model.PostResponse
import timber.log.Timber

class PostAdapter(
    private val itemClickListener: ItemClickListener
    ): ListAdapter<PostResponse.PostResponseItem,PostAdapter.PostViewHolder>(MyDiffUtil()) {

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val idTV: TextView = itemView.findViewById(R.id.text_id)
        private val titleTV: TextView = itemView.findViewById(R.id.text_title)
        private val bodyTV: TextView = itemView.findViewById(R.id.text_body)
        private val cardView: CardView = itemView.findViewById(R.id.card_view)
        private val deleteImg: ImageView = itemView.findViewById(R.id.delete_item)

        fun bind(item: PostResponse.PostResponseItem) {
            idTV.text = item.id.toString()
            titleTV.text = item.title
            bodyTV.text = item.body
        }

        fun itemClick(position: Int,item: PostResponse.PostResponseItem,itemClickListener: ItemClickListener) {
            itemView.setOnClickListener {
                itemClickListener.itemClicked(position,item)
            }
        }

        fun deleteItem(position: Int,itemClickListener: ItemClickListener) {
            deleteImg.setOnClickListener {
                itemClickListener.deleteItem(position)
            }
        }

        fun setSelection(item: PostResponse.PostResponseItem) {
            if (item.isSelected) {
                cardView.setCardBackgroundColor((cardView.context).getColor(R.color.back_color))
            }
            else cardView.setCardBackgroundColor((cardView.context).getColor(R.color.white))
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post,parent,false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemClick(position,item,itemClickListener)
        holder.deleteItem(position,itemClickListener)
        holder.setSelection(item)
        Timber.i("Called : $position")
    }


    class MyDiffUtil : DiffUtil.ItemCallback<PostResponse.PostResponseItem>() {
        override fun areItemsTheSame(
            oldItem: PostResponse.PostResponseItem,
            newItem: PostResponse.PostResponseItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PostResponse.PostResponseItem,
            newItem: PostResponse.PostResponseItem): Boolean {
            return oldItem.isSelected == newItem.isSelected && oldItem == newItem
        }
    }

    interface ItemClickListener {
        fun itemClicked(position: Int,item: PostResponse.PostResponseItem)
        fun deleteItem(position: Int)
    }
}