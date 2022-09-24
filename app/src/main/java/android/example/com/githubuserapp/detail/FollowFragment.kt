package android.example.com.githubuserapp.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.example.com.githubuserapp.adapter.ListUserAdapter
import android.example.com.githubuserapp.data.GithubUser
import android.example.com.githubuserapp.databinding.FragmentFollowBinding
import android.example.com.githubuserapp.main.MainActivity
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]

        val githubUser : GithubUser = requireActivity().intent.getParcelableExtra(MainActivity.EXTRA_DATA)!!
        val tabTitle = arguments?.getString(TAB_TITLE)
        binding.rvFollower.layoutManager = LinearLayoutManager(activity)

        if (tabTitle == FOLLOWER) {
            viewModel.displayFollowerList(githubUser.login)
        } else if (tabTitle == FOLLOWING) {
            viewModel.displayFollowingList(githubUser.login)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.followList.observe(viewLifecycleOwner) {
            val adapter = ListUserAdapter(it)
            binding.apply {
                rvFollower.adapter = adapter
            }
        }

        viewModel.isError.observe(viewLifecycleOwner) {
            Toast.makeText(context, "Data not found!", Toast.LENGTH_SHORT).show()
            viewModel.doneToastErrorInput()
        }

        return binding.root
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val TAB_TITLE = "tab_title"
        const val FOLLOWER = "follower"
        const val FOLLOWING = "following"
    }
}