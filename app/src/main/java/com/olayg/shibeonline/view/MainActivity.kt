package com.olayg.shibeonline.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.olayg.shibeonline.adapter.ShibeAdapter
import com.olayg.shibeonline.databinding.ActivityMainBinding
import com.olayg.shibeonline.viewmodel.ShibeViewModel

/**
 * 1) Find a image loading library and use it to load the image in the adapter class
 * 2) Add a button to switch between LinearLayoutManager and GridLayoutManager
 */
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<ShibeViewModel>()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var spanCount: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()

        binding.btnSwitch.setOnClickListener() {
            spanCount = if (spanCount % 2 == 0) 1 else 2
            binding.rvImages.layoutManager = GridLayoutManager(this, spanCount)
        }

        viewModel.shibes.observe(this) {
            // Here is where your will get the result
            Log.d("MainActivity", "onCreate: $it")
            (binding.rvImages.adapter as ShibeAdapter).updateUrls(it)
        }
    }

    private fun initViews() = with(binding) {
        btnFetch.setOnClickListener {
            val count = binding.etCount.text?.toString()?.toIntOrNull()
            // Use this method to get the images
            viewModel.getImages(count ?: 1)
        }
        rvImages.adapter = ShibeAdapter()
        rvImages.layoutManager = GridLayoutManager(this@MainActivity, spanCount)
    }
}