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
import com.batikfy.batikfy.data.Result
import com.batikfy.batikfy.databinding.FragmentExploreBatikBinding
import com.batikfy.batikfy.utils.ViewModelFactory

class ExploreBatikFragment : Fragment() {

    private var _binding: FragmentExploreBatikBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentExploreBatikBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val exploreBatikViewModel: ExploreBatikViewModel by viewModels {
            factory
        }

        exploreBatikViewModel.getAllBatikData().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Success -> {
                        val layoutManager = LinearLayoutManager(requireActivity())
                        binding?.rvExploreBatik?.layoutManager = layoutManager
                        val itemDecoration =
                            DividerItemDecoration(requireActivity(), layoutManager.orientation)
                        binding?.rvExploreBatik?.addItemDecoration(itemDecoration)
                        val adapter = ExploreBatikAdapter(result.data.data.batiks)
                        binding?.rvExploreBatik?.adapter = adapter

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