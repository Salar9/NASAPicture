package com.example.den.nasapicture.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.den.nasapicture.databinding.MainFragmentBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

private const val TAG = "MainFragment"

class MainFragment : Fragment() {
    private lateinit var pagingAdapter: NASAImageAdapter
    private lateinit var binding: MainFragmentBinding

    private val nasaViewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    //lateinit var nasaViewModel: MainViewModel

    companion object {
        fun newInstance() = MainFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        //nasaViewModel = defaultViewModelProviderFactory.create(MainViewModel::class.java)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagingAdapter = NASAImageAdapter(requireActivity())


        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = pagingAdapter
        }

        fetchNASAImages()
/*
        val repo = NASAReporitory.getInstance()
        runBlocking {
            repo.test().photos.forEach {
                Log.i(TAG,"ID - ${it.id}\tRover - ${it.rover.name}\tCamera ${it.camera.full_name}")
            }
        }
 */
    }
    private fun fetchNASAImages() {
        lifecycleScope.launch {
            nasaViewModel.fetchNASAImages().distinctUntilChanged().collectLatest {
                pagingAdapter.submitData(it)
            }
        }
    }
}