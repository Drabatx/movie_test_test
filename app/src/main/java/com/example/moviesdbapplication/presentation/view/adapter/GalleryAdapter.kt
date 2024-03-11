package com.example.moviesdbapplication.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesdbapplication.R
import com.example.moviesdbapplication.domain.model.ImageResponse
import com.squareup.picasso.Picasso

class GalleryAdapter(private val context: Context, private var imageList: List<ImageResponse>) :
    RecyclerView.Adapter<GalleryAdapter.ImageViewHolder>() {

    fun updateImages(imageList: List<ImageResponse>) {
        this.imageList = imageList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_gallery, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.render(imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun render(item: ImageResponse) {
            Picasso.get()
                .load(item.url)
                .placeholder(R.drawable.drawable_placeholder)
                .error(R.drawable.drawable_error)
                .into(imageView)
        }
    }
}