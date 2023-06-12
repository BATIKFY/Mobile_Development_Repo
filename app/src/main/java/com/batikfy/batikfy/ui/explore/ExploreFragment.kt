package com.batikfy.batikfy.ui.explore

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.batikfy.batikfy.R
import com.batikfy.batikfy.databinding.FragmentExploreBinding
import com.batikfy.batikfy.ui.favorite.FavoriteActivity
import com.google.android.material.tabs.TabLayoutMediator


class ExploreFragment : Fragment(), MenuProvider {

    private var _binding: FragmentExploreBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val exploreViewModel =
            ViewModelProvider(this).get(ExploreViewModel::class.java)

        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (arguments != null && requireArguments().containsKey("activeFragment")) {
            val activeFragment = requireArguments().getString("activeFragment")
            if (activeFragment == "batik") {
                binding.viewPager.setCurrentItem(0, false)
            } else if (activeFragment == "article") {
                binding.viewPager.setCurrentItem(1, false)
            }
        }

//        //example view with viewmodel
//        val textView: TextView = binding.textExplore
//        exploreViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = requireActivity().resources.getString(TAB_TITLES[position])
        }.attach()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.option_menu_explore, menu)

        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.menu_search)?.actionView as SearchView
        val searchableInfo = searchManager.getSearchableInfo(requireActivity().componentName)

        searchView.setSearchableInfo(searchableInfo)
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    Toast.makeText(requireActivity(), "Query is empty", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireActivity(), newText, Toast.LENGTH_SHORT).show()
                }
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(requireActivity(), query, Toast.LENGTH_SHORT).show()
                return true
            }
        })
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menu_search -> {
                true
            }
            R.id.menu_bookmark -> {
                val intent = Intent(requireActivity(), FavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            else -> false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_batik,
            R.string.tab_article
        )
    }
}