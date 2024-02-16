package com.example.androidfirebaseassessment.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidfirebaseassessment.R
import com.example.androidfirebaseassessment.adapter.SetDataAdapter
import com.example.androidfirebaseassessment.databinding.FragmentThirdBinding
import com.example.androidfirebaseassessment.model.Address
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class ThirdFragment : Fragment() {
    private lateinit var adapter: SetDataAdapter
    private var addresslist= mutableListOf<Address>()
    private lateinit var mRef: DatabaseReference
    private lateinit var binding : FragmentThirdBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = FragmentThirdBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRef= Firebase.database.reference
        adapter = SetDataAdapter(requireContext(),addresslist, mRef,true){ itemId ->

        }
        binding.recycleview.layoutManager= LinearLayoutManager(context)
        binding.recycleview.adapter = adapter

        adapter.fetchAllLikeStatusFromFirebase()
        for (location in addresslist) {
            adapter.fetchLikeStatusFromFirebase(location)
        }

        adapter.notifyDataSetChanged()

    }
}