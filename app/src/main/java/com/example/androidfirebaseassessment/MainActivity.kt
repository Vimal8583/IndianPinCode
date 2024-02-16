package com.example.androidfirebaseassessment

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidfirebaseassessment.adapter.PagerAdapter
import com.example.androidfirebaseassessment.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)

        var tableLayout = binding.tabLayout
        var viewPager = binding.viewPager

        viewPager.adapter = PagerAdapter(this)
        TabLayoutMediator(tableLayout,viewPager){ tab,index ->
            tab.text = when(index){
                0-> {"QUICK SEARCH"}
                1->{"SEARCH BY AREA"}
                2->{"SAVED RECORD"}
                else -> {throw Resources.NotFoundException("position not found")}
            }
        }.attach()
    }
}