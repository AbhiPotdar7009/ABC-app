package com.example.abc_app.ui.activity

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.abc_app.R
import com.example.abc_app.databinding.ActivityMainBinding
import com.example.abc_app.databinding.BottomSheetStatsBinding
import com.example.abc_app.ui.adapter.CarouselAdapter
import com.example.abc_app.ui.adapter.LabelAdapter
import com.example.abc_app.ui.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var labelAdapter: LabelAdapter
    private lateinit var binding: ActivityMainBinding

    private val carouselData = listOf(
        R.drawable.apple, R.drawable.banana, R.drawable.cherry
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Carousel
        val carouselAdapter = CarouselAdapter(carouselData)
        binding.carouselViewPager.adapter = carouselAdapter
        TabLayoutMediator(binding.carouselIndicator, binding.carouselViewPager) { tabLayout , _ ->
            tabLayout.setCustomView(R.layout.custom_tab_dot)
        }.attach()

        // Setup RecyclerView
        labelAdapter = LabelAdapter(emptyList())
        binding.listRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.listRecyclerView.adapter = labelAdapter

        // Observe Data
        viewModel.items.observe(this, Observer { newList ->
            labelAdapter.update(newList)
        })

        // Carousel page changes -> update list
        binding.carouselViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                viewModel.updateCarousel(position)
                binding.searchBar.text.clear()
            }
        })

        // Search functionality
        binding.searchBar.addTextChangedListener {
            viewModel.search(it.toString())
        }

        //Show bottom sheet
        binding.fab.setOnClickListener {
            showStatsBottomSheet()
        }
    }

    private fun showStatsBottomSheet() {
        val dialog = BottomSheetDialog(this)

        val binding = BottomSheetStatsBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

        val labelList = labelAdapter.labels
        val topChars = viewModel.top3(labelList)

        binding.statsText.text = buildString {
            append("List 1 (${labelList.size} items)\n")
            topChars.forEach { (char, count) ->
                append("$char = $count\n")
            }
        }

        dialog.show()
    }

}

