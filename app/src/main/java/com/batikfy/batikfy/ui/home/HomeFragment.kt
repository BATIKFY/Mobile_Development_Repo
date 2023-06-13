package com.batikfy.batikfy.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.batikfy.batikfy.MainActivity
import com.batikfy.batikfy.R
import com.batikfy.batikfy.data.Result
import com.batikfy.batikfy.databinding.FragmentHomeBinding
import com.batikfy.batikfy.utils.ViewModelFactory

class HomeFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.banner.setOnClickListener(this)
        binding.readMoreBtnBatik.setOnClickListener(this)
        binding.readMoreBtnArticle.setOnClickListener(this)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val homeViewModel: HomeViewModel by viewModels {
            factory
        }

        homeViewModel.getAllBatikData().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Success -> {
                        val selected4Data = result.data.data.batiks.shuffled().take(4)

                        // Init RecyclerView and Adapter
                        val gridBatikAdapter = GridBatikAdapter(selected4Data)

                        binding.rvHomeBatik.apply {
                            layoutManager = GridLayoutManager(requireContext(), 2)
                            adapter = gridBatikAdapter
                        }
                        binding.loadingBatik.visibility = View.GONE
                    }
                    is Result.Error -> {
                        binding.loadingBatik.visibility = View.GONE
                        Toast.makeText(
                            requireActivity(),
                            "Terjadi kesalahan" + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is Result.Loading -> {
                        binding.loadingBatik.visibility = View.VISIBLE
                    }
                }
            }
        }

        homeViewModel.getAllArticleData().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Success -> {
                        val selected3Data = result.data.data.blogs.shuffled().take(3)

                        // Init RecyclerView and Adapter
                        val gridArticleAdapter = GridArticleAdapter(selected3Data)

                        binding.rvHomeArticle.apply {
                            layoutManager = GridLayoutManager(requireContext(), 1)
                            adapter = gridArticleAdapter
                        }
                        binding.loadingArticle.visibility = View.GONE
                    }
                    is Result.Error -> {
                        binding.loadingArticle.visibility = View.GONE
                        Toast.makeText(
                            requireActivity(),
                            "Terjadi kesalahan" + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is Result.Loading -> {
                        binding.loadingArticle.visibility = View.VISIBLE
                    }
                }
            }
        }

        // local storage feature - still struggling with insert
        homeViewModel.getAllBatikDataWithDB()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.banner -> {
                val mainActivity = activity as? MainActivity
                mainActivity?.navigateToScan()
            }
            R.id.read_more_btn_batik -> {
                Toast.makeText(
                    requireActivity(),
                    resources.getString(R.string.feature_not_ready),
                    Toast.LENGTH_SHORT
                ).show()
//                val intent = Intent(requireActivity(), MainActivity::class.java)
//                intent.putExtra("activeTab", R.id.navigation_explore)
//                intent.putExtra("activeFragment", "batik")
//                startActivity(intent)
            }
            R.id.read_more_btn_article -> {
                Toast.makeText(
                    requireActivity(),
                    resources.getString(R.string.feature_not_ready),
                    Toast.LENGTH_SHORT
                ).show()
//                val intent = Intent(requireActivity(), MainActivity::class.java)
//                intent.putExtra("activeTab", R.id.navigation_explore)
//                intent.putExtra("activeFragment", "article")
//                startActivity(intent)
            }
        }
    }
}
