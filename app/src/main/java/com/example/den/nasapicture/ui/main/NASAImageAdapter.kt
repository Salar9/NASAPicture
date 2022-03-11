package com.example.den.nasapicture.ui.main

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.den.nasapicture.R
import com.example.den.nasapicture.databinding.NasaImageListItemBinding
import com.example.den.nasapicture.model.RoverPhotos
import com.squareup.picasso.Picasso

private const val TAG = "NASAImageAdapter"

class NASAImageAdapter(private val activityContext : FragmentActivity) : PagingDataAdapter<RoverPhotos.Photo, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<RoverPhotos.Photo>() {
            override fun areItemsTheSame(oldItem: RoverPhotos.Photo, newItem: RoverPhotos.Photo): Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: RoverPhotos.Photo, newItem: RoverPhotos.Photo): Boolean = oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //Log.i(TAG,"onBindViewHolder")
        (holder as? NASAImageViewHolder)?.bind(getItem(position)!!)
        holder.itemView.setOnClickListener {
            val photo = (holder as? NASAImageViewHolder)?.photo
            //Toast.makeText(activityContext,"Camera - $camera",Toast.LENGTH_SHORT).show()
            activityContext.supportFragmentManager.beginTransaction()
                .add(R.id.container,FullscreenImageFragment.newInstance(photo!!.img_src))
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NASAImageViewHolder{
        //Log.i(TAG,"onCreateViewHolder")
        val binding = DataBindingUtil.inflate<NasaImageListItemBinding>(LayoutInflater.from(parent.context), R.layout.nasa_image_list_item, parent,false)
        return NASAImageViewHolder(binding)
    }

//***************************************************************************
    class NASAImageViewHolder(val binding: NasaImageListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        lateinit var photo: RoverPhotos.Photo

        //, View.OnClickListener
//        init {
//            itemView.setOnClickListener(this)
//        }
//        override fun onClick(v: View) {
//            Log.i(TAG,"Selected: ${photo.img_src}")
//        }

        fun bind(photo: RoverPhotos.Photo) {
            this.photo = photo
            binding.apply {
                roverName.text = photo.rover.name
                cameraName.text = photo.camera.name
                earthDate.text = photo.earth_date
            }

            Picasso.get()
                .load(photo.img_src)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_404)
                .into(binding.imageView)
        }

    }
}