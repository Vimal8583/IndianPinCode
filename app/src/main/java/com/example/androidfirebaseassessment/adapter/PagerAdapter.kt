package com.example.androidfirebaseassessment.adapter

import android.content.Context
import android.content.res.Resources
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.androidfirebaseassessment.Activities.FragmentActivity
import com.example.androidfirebaseassessment.Fragments.FirstFragment
import com.example.androidfirebaseassessment.Fragments.SecondFragment
import com.example.androidfirebaseassessment.Fragments.ThirdFragment

class PagerAdapter(fragmentActivity: androidx.fragment.app.FragmentActivity):FragmentStateAdapter(fragmentActivity){
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
            return when(position){
                0-> {FirstFragment()}
                1-> {SecondFragment()}
                2-> {ThirdFragment()}
                else -> {throw Resources.NotFoundException("position not found")}
            }
    }

}



