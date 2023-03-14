package com.cursosandroidant.sports

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.transition.Transition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.imageLoader
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.cursosandroidant.sports.databinding.ItemSportBinding
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

/****
 * Project: Sports
 * From: com.cursosandroidant.sports
 * Created by Alain Nicolás Tello on 29/09/21 at 12:32
 * All rights reserved 2021.
 *
 * All my Udemy Courses:
 * https://www.udemy.com/user/alain-nicolas-tello/
 * Web: www.alainnicolastello.com
 ***/
class SportListAdapter(private val listener: OnClickListener) :
    ListAdapter<Sport, RecyclerView.ViewHolder>(SportDiffCallback()) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_sport, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val sport = getItem(position)

        with(holder as ViewHolder){
            setListener(sport)

            binding.tvName.text = sport.name

            val request = ImageRequest.Builder(context)
                .data(sport.imgUrl)
                .crossfade(true)
                .transformations()
                .size(1280, 720)
                .target(onStart = {
                    binding.imgPhoto.setImageResource(R.drawable.ic_access_time)
                },
                onSuccess = {
                    binding.progressBar.visibility = View.GONE
                    binding.imgPhoto.scaleType = ImageView.ScaleType.CENTER_CROP
                    binding.imgPhoto.setImageDrawable(it)
                },
                onError = {
                    binding.progressBar.visibility = View.GONE
                    binding.imgPhoto.setImageResource(R.drawable.ic_error_outline)
                }).build()

            context.imageLoader.enqueue(request)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemSportBinding.bind(view)

        fun setListener(sport: Sport){
            binding.root.setOnClickListener { listener.onClick(sport) }
        }
    }

    class SportDiffCallback: DiffUtil.ItemCallback<Sport>(){
        override fun areItemsTheSame(oldItem: Sport, newItem: Sport): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Sport, newItem: Sport): Boolean = oldItem == newItem
    }
}