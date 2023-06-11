package com.batikfy.batikfy.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.batikfy.batikfy.BuildConfig
import com.batikfy.batikfy.MainActivity
import com.batikfy.batikfy.R
import com.batikfy.batikfy.data.remote.response.BatikItem
import com.batikfy.batikfy.databinding.FragmentHomeBinding
import com.batikfy.batikfy.utils.ViewModelFactory

class HomeFragment : Fragment(), View.OnClickListener, GridBatikAdapter.OnItemClickCallback {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var gridBatikAdapter: GridBatikAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.banner.setOnClickListener(this)

        // Init RecyclerView and Adapter
        gridBatikAdapter = GridBatikAdapter(listOf()) // Pass empty list initially
        gridBatikAdapter.setOnItemClickCallback(this)

        binding.rvHomeBatik.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = gridBatikAdapter
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity(), BuildConfig.BASE_URL)
        val homeViewModel: HomeViewModel by viewModels {
            factory
        }

//        homeViewModel.getBatikItems().observe(viewLifecycleOwner) { batikItems ->
//            gridBatikAdapter.setBatikItems(batikItems)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.banner  -> {
                val mainActivity = activity as? MainActivity
                mainActivity?.navigateToScan()
            }
        }
    }

    override fun onItemClicked(data: BatikItem) {
    }
}
