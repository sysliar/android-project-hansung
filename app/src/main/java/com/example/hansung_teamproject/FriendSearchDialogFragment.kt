package com.example.hansung_teamproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hansung_teamproject.databinding.FriendSearchBinding
import com.example.hansung_teamproject.databinding.SignupPopupBinding

class FriendSearchDialogFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = true
        return FriendSearchBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FriendSearchBinding.bind(view)

        val mList : ArrayList<String> = ArrayList()
        mList.add("사용자1")
        mList.add("사용자2")
        mList.add("사용자3")

        //recyclerView4는 FriendSearchListAdpater로 해야함, scorllView는 구현할 필요 없음
        val adapter = FriendListAdapter()
        adapter.setFriendList(mList)
        binding.recyclerView4.adapter = adapter
        binding.recyclerView4.layoutManager = LinearLayoutManager(context)
        binding.recyclerView4.setHasFixedSize(true)

    }
}