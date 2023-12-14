package com.dicoding.submissionakhir.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.submissionakhir.R
import com.dicoding.submissionakhir.adapter.FollowAdapter
import com.dicoding.submissionakhir.factory.ViewModelFactory
import com.dicoding.submissionakhir.response.ItemsItem


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class FollowFragment : Fragment() {
    private lateinit var rvFollow: RecyclerView


    private var position: Int? = null
    private var username: String? = null


    private val detailViewModel by viewModels<DetailUserViewModel>{
        ViewModelFactory.getInstance(requireActivity())
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_PARAM1)
            username = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvFollow = view.findViewById(R.id.rvFollow)

        if (position == 1){
            detailViewModel.getFollower(username.toString())
            detailViewModel.listFollower.observe(viewLifecycleOwner){
                setReviewData(it)
            }
        } else {
            detailViewModel.getFollowing(username.toString())
            detailViewModel.listFollowing.observe(viewLifecycleOwner){
                setReviewData(it)
            }
        }

        detailViewModel.isLoading.observe(requireActivity()) {
            detailViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                view.findViewById<ProgressBar>(R.id.progressBar).visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }

    private fun setReviewData(consumerReviews: List<ItemsItem?>) {
        val adapter = FollowAdapter()
        adapter.submitList(consumerReviews)
        rvFollow.layoutManager = LinearLayoutManager(requireActivity())
        rvFollow.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int, param2: String) =
            FollowFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}