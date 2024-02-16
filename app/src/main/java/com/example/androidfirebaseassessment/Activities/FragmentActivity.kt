package com.example.androidfirebaseassessment.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidfirebaseassessment.Fragments.FirstFragment
import com.example.androidfirebaseassessment.R

class FragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        var fragment = FirstFragment()
        var manager = supportFragmentManager
        var transaction = manager.beginTransaction()
        transaction.add(R.id.fragment_container,fragment)
        transaction.commit()
    }
}