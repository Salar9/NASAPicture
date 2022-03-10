package com.example.den.nasapicture.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.den.nasapicture.R
import com.example.den.nasapicture.databinding.NasaImageListItemBinding
import com.example.den.nasapicture.model.RoverPhotos
import com.squareup.picasso.Picasso

private const val TAG = "NASAImageAdapter"

class NASAImageAdapter : PagingDataAdapter<RoverPhotos.Photo, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<RoverPhotos.Photo>() {
            override fun areItemsTheSame(oldItem: RoverPhotos.Photo, newItem: RoverPhotos.Photo): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: RoverPhotos.Photo, newItem: RoverPhotos.Photo): Boolean =
                oldItem == newItem
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.i(TAG,"onBindViewHolder")
        (holder as? NASAImageViewHolder)?.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NASAImageViewHolder{
        Log.i(TAG,"onCreateViewHolder")
        val binding = DataBindingUtil.inflate<NasaImageListItemBinding>(LayoutInflater.from(parent.context), R.layout.nasa_image_list_item, parent,false)
        return NASAImageViewHolder(binding)
    }

//***************************************************************************
    class NASAImageViewHolder(val binding: NasaImageListItemBinding) : RecyclerView.ViewHolder(binding.root) {

//        companion object {
//            //get instance of the DoggoImageViewHolder
//            fun getInstance(parent: ViewGroup): NASAImageViewHolder {
//                val inflater = LayoutInflater.from(parent.context)
//                val view = inflater.inflate(R.layout.nasa_image_list_item, parent, false)
//                return NASAImageViewHolder(view)
//            }
//        }

        fun bind(photo: RoverPhotos.Photo) {
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