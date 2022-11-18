package com.example.hansung_teamproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hansung_teamproject.databinding.FriendSearchBinding

class FriendSearchDialogFragment : DialogFragment() {
    lateinit var searchString: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var bundle = arguments
        searchString = bundle!!.getString("searchString").toString()
        isCancelable = true
        return FriendSearchBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FriendSearchBinding.bind(view)
        val adapter = FriendSearchListAdapter()
        
        binding.recyclerView4.adapter = adapter
        binding.recyclerView4.layoutManager = LinearLayoutManager(context)
        binding.recyclerView4.setHasFixedSize(true)
        adapter.search(searchString)
    }
}