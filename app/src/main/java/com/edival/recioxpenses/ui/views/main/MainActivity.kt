package com.edival.recioxpenses.ui.views.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.edival.recioxpenses.R
import com.edival.recioxpenses.databinding.ActivityMainBinding
import com.edival.recioxpenses.ui.views.main.adapters.ViewPagerAdapter
import com.edival.recioxpenses.ui.views.record.RecordFragment
import com.edival.recioxpenses.ui.views.today.TodayFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupTabLayout()
    }

    private fun setupTabLayout() {
        binding.viewPager.adapter = ViewPagerAdapter(
            listOf(TodayFragment(), RecordFragment()), supportFragmentManager, lifecycle
        )
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_today)
                    tab.text = getString(R.string.menu_today)
                }

                1 -> {
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.outline_history)
                    tab.text = getString(R.string.menu_record)
                }
            }
        }.attach()
    }
}