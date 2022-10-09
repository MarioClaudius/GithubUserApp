package android.example.com.githubuserapp.detail

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.example.com.githubuserapp.adapter.ListUserAdapter
import android.example.com.githubuserapp.data.GithubUser
import android.example.com.githubuserapp.database.FavoriteUser
import android.example.com.githubuserapp.databinding.FragmentFollowBinding
import android.example.com.githubuserapp.favorite.FavoriteActivity
import android.example.com.githubuserapp.helper.ViewModelFactory
import android.example.com.githubuserapp.main.MainActivity
import android.example.com.githubuserapp.settingpreferences.SettingPreferences
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
//        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]
        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        val viewModelFactory = ViewModelFactory(this@FollowFragment.requireActivity().application, "", pref)
        viewModel = ViewModelProvider(this@FollowFragment.requireActivity(), viewModelFactory)[DetailViewModel::class.java]

        val githubUser : GithubUser? = requireActivity().intent.getParcelableExtra(MainActivity.EXTRA_DATA)
        val favoriteUser : FavoriteUser? = requireActivity().intent.getParcelableExtra(FavoriteActivity.EXTRA_FAVORITE)
        val username : String = if (githubUser != null) githubUser.login else favoriteUser?.username.toString()
        val tabTitle = arguments?.getString(TAB_TITLE)
        binding.rvFollower.layoutManager = LinearLayoutManager(activity)

        if (tabTitle == FOLLOWER) {
//            viewModel.displayFollowerList(githubUser.login)
            viewModel.displayFollowerList(username)
        } else if (tabTitle == FOLLOWING) {
//            viewModel.displayFollowingList(githubUser.login)
            viewModel.displayFollowingList(username)
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