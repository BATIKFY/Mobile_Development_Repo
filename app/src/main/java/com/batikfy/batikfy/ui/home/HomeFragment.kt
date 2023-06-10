package com.batikfy.batikfy.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.batikfy.batikfy.MainActivity
import com.batikfy.batikfy.R
import com.batikfy.batikfy.data.remote.response.BatikItem
import com.batikfy.batikfy.databinding.FragmentHomeBinding
import com.batikfy.batikfy.ui.adapter.GridBatikAdapter
import com.batikfy.batikfy.utils.ViewModelFactory

class HomeFragment : Fragment(), View.OnClickListener  {

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
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val homeViewModel: HomeViewModel by viewModels {
            factory
        }
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
}