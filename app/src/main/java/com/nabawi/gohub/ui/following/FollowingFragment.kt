package com.nabawi.gohub.ui.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nabawi.gohub.R
import com.nabawi.gohub.data.model.UserEntity
import com.nabawi.gohub.data.source.Resource
import com.nabawi.gohub.databinding.FollowerFragmentBinding
import com.nabawi.gohub.ui.adapters.UserAdapter
import com.nabawi.gohub.utils.StateCallback

class FollowingFragment : Fragment(), StateCallback<List<UserEntity>> {

    companion object {
        private const val KEY_BUNDLE = "USERNAME"

        fun getInstance(username: String): Fragment {
            return FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_BUNDLE, username)
                }
            }
        }
    }

    private val followingBinding: FollowerFragmentBinding by viewBinding()
    private val viewModel: FollowingViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(KEY_BUNDLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.following_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        userAdapter = UserAdapter()
        followingBinding.rvListUserFollower.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        viewModel.getUserFollowing(username.toString()).observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Error -> onFailed(it.message)
                is Resource.Loading -> onLoading()
                is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
            }
        })
    }

    override fun onSuccess(data: List<UserEntity>) {
        userAdapter.setAllData(data)

        followingBinding.apply {
            tvResponPesan.visibility = invisible
            followProgressbar.visibility = invisible
            rvListUserFollower.visibility = visible
        }
    }

    override fun onLoading() {
        followingBinding.apply {
            tvResponPesan.visibility = invisible
            followProgressbar.visibility = visible
            rvListUserFollower.visibility = invisible
        }
    }

    override fun onFailed(message: String?) {
        followingBinding.apply {
            if (message == null) {
                tvResponPesan.text = resources.getString(R.string.following_tidak_ditemukan, username)
                tvResponPesan.visibility = visible
            } else {
                tvResponPesan.text = message
                tvResponPesan.visibility = visible
            }
            followProgressbar.visibility = invisible
            rvListUserFollower.visibility = invisible
        }
    }

}