package com.batikfy.batikfy.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.batikfy.batikfy.BuildConfig
import com.batikfy.batikfy.data.Result
import com.batikfy.batikfy.databinding.FragmentExploreArticleBinding
import com.batikfy.batikfy.utils.ViewModelFactory

class ExploreArticleFragment : Fragment() {

    private var _binding: FragmentExploreArticleBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentExploreArticleBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory =
            ViewModelFactory.getInstance(requireActivity(), BuildConfig.BASE_URL)
        val exploreArticleViewModel: ExploreArticleViewModel by viewModels {
            factory
        }

        exploreArticleViewModel.getAllArticleData().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Success -> {
                        val layoutManager = LinearLayoutManager(requireActivity())
                        binding?.rvExploreArticle?.layoutManager = layoutManager
                        val itemDecoration =
                            DividerItemDecoration(requireActivity(), layoutManager.orientation)
                        binding?.rvExploreArticle?.addItemDecoration(itemDecoration)
                        val adapter = ExploreArticleAdapter(result.data.data.blogs)
                        binding?.rvExploreArticle?.adapter = adapter

                        binding?.loading?.visibility = View.GONE
                    }
                    is Result.Error -> {
                        binding?.loading?.visibility = View.GONE
                        Toast.makeText(
                            requireActivity(),
                            "Terjadi kesalahan" + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is Result.Loading -> {
                        binding?.loading?.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}