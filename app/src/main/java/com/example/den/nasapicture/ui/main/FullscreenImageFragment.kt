package com.example.den.nasapicture.ui.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.den.nasapicture.R
import com.example.den.nasapicture.databinding.FragmentFullscreenImageBinding
import com.ortiz.touchview.TouchImageView
import com.squareup.picasso.Picasso


private const val ARG_URI = "nasa_image_url"

class FullscreenImageFragment : Fragment() {
    private lateinit var url: String
    private lateinit var binding: FragmentFullscreenImageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        url = arguments?.getString(ARG_URI) ?: ""    //извлечение параметров
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFullscreenImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Picasso.get()
            .load(url)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error_404)
            .into(binding.touchImage)
    }


    companion object {
        fun newInstance(url: String): FullscreenImageFragment {  //создание инстанса и передача ему параметров
            return FullscreenImageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_URI, url)
                }
            }
        }

    }


}