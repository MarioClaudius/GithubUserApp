package android.example.com.githubuserapp.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.example.com.githubuserapp.R
import android.example.com.githubuserapp.adapter.ListUserAdapter
import android.example.com.githubuserapp.databinding.FragmentFollowBinding
import androidx.recyclerview.widget.LinearLayoutManager

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFollower.layoutManager = LinearLayoutManager(activity)
//        val adapter = ListUserAdapter()
    }
}