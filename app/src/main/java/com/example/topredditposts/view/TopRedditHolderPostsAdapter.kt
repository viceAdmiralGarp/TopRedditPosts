package com.example.topredditposts.view

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.topredditposts.R
import com.example.topredditposts.databinding.ListItemBinding
import com.example.topredditposts.dto.PostDto
import com.example.topredditposts.img.ImageLoader
import com.squareup.picasso.Picasso


class TopRedditHolderPostsAdapter(private val activity: Context,
                                  private val onLoadNextPage: (() -> Unit)? = null) :
    ListAdapter<PostDto, TopRedditHolderPostsAdapter.Holder>(Comparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if (currentList.size - 5 == position) {
            onLoadNextPage?.invoke()
        }
        holder.bind(getItem(position))
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemBinding.bind(view)

        fun bind(postDto: PostDto) {
            with(binding) {
                authorName.text = postDto.authorFullName
                comments.text = postDto.numComments.toString()
                timeCreated.text = postDto.created.toString()

                Picasso.get()
                    .load(postDto.url)
                    .into(imageView)

                imageView.setOnClickListener {
                    showFullScreenImage(postDto.url)
                }
            }
        }

        private fun showFullScreenImage(imageUrl: String) {
            val dialog = Dialog(activity)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.activity_full_screen_image)

            val fullScreenImageView = dialog.findViewById<ImageView>(R.id.full_screen)
            Picasso.get().load(imageUrl).into(fullScreenImageView)

            val downloadButton = dialog.findViewById<Button>(R.id.download_button)
            downloadButton.setOnClickListener {
                downloadImage(imageUrl)
            }

            dialog.show()
        }

        private fun downloadImage(imageUrl: String) {
            ImageLoader.downloadImage(imageUrl, activity)
            Toast.makeText(activity, "Image downloaded", Toast.LENGTH_SHORT).show()
        }
    }


    class Comparator : DiffUtil.ItemCallback<PostDto>() {
        override fun areItemsTheSame(oldItem: PostDto, newItem: PostDto): Boolean {
            return oldItem.created == newItem.created
        }

        override fun areContentsTheSame(oldItem: PostDto, newItem: PostDto): Boolean {
            return oldItem == newItem
        }
    }
}