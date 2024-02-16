package com.example.androidfirebaseassessment.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidfirebaseassessment.R
import com.example.androidfirebaseassessment.adapter.SetDataAdapter
import com.example.androidfirebaseassessment.databinding.FragmentSecondBinding
import com.example.androidfirebaseassessment.model.Address
import com.example.androidfirebaseassessment.model.State
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class SecondFragment : Fragment() {
    private lateinit var mRef: DatabaseReference
    var stateList = mutableListOf<State>()
    private lateinit  var mAdapter: SetDataAdapter
    private var addressList = mutableListOf<Address>()
    private lateinit var binding: FragmentSecondBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater,container,false)
        mRef = Firebase.database.reference
        mAdapter = SetDataAdapter(requireContext(), addressList,mRef, false){ itemId ->
        }
        binding.recycleview.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleview.adapter = mAdapter
       return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRef = Firebase.database.reference
        mRef.child("state").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                stateList.clear()
                for(snap in snapshot.children){
                    var state= snap.getValue(State::class.java)
                    state?.let {
                        stateList.add(it)
                    }
                }
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, stateList)
                binding.autoState.setAdapter(adapter)
                binding.autoState.onItemSelectedListener= object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        var state = stateList[position]
                        loadDistrictList(state)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
        binding.search.setOnClickListener {
            val selectedDistrict = binding.autoDistrict.selectedItem.toString()
            val selectedCity = binding.autoCity.selectedItem.toString()
            val filteredList = addressList.filter { it.district == selectedDistrict || it.city == selectedCity }

            mAdapter.updateList(filteredList,true)
        }
        binding.clear.setOnClickListener {
            binding.autoState.setSelection(0)
            binding.autoDistrict.setSelection(0)
            binding.autoCity.setSelection(0)

            // Clear adapter data
            mAdapter.UpdateList(emptyList(), false)
        }
    }



    private fun loadDistrictList(state: State) {
        var districts = state.districts as List<String>
        var districtAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,districts)
        binding.autoDistrict.setAdapter(districtAdapter)
        districtAdapter.notifyDataSetChanged()

        binding.autoDistrict.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var district = districts[position]
                Log.d("SelectedDistrict", district)
                loadCity(district)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun loadCity(district: String) {
        addressList.clear()
        mRef.child("address").orderByChild("district").equalTo(district).addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val uniqueCities:MutableSet<String> = mutableSetOf<String>()
                    for(snap in snapshot.children){
                        var address = snap.getValue(Address::class.java)
                        address?.let {
                            if (uniqueCities.add(it.city!!))
                                addressList.add(it)
                        }
                    }

                    var adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, addressList.map { it.city })
                    binding.autoCity.adapter = adapter

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("TAG", "onCancelled: ")
                }

            })

    }


    private fun SetDataAdapter.UpdateList(mutableList: List<Address>,displayData: Boolean = true) {
        this.addresslist = mutableList.toMutableList()
        this.displayData=displayData
        for (location in mutableList) {
            fetchLikeStatusFromFirebase(location)
        }

        notifyDataSetChanged()
    }

}