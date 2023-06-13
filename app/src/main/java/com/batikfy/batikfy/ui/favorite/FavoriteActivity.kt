package com.batikfy.batikfy.ui.favorite

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.batikfy.batikfy.R
import com.batikfy.batikfy.databinding.ActivityFavoriteBinding
import com.google.android.material.tabs.TabLayoutMediator

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(
            this@FavoriteActivity,
            resources.getString(R.string.feature_not_ready),
            Toast.LENGTH_SHORT
        ).show()

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.elevation = 0f

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu_search, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.menu_search)?.actionView as SearchView
        val searchableInfo = searchManager.getSearchableInfo(componentName)

        searchView.setSearchableInfo(searchableInfo)
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                Toast.makeText(
                    this@FavoriteActivity,
                    resources.getString(R.string.feature_not_ready),
                    Toast.LENGTH_SHORT
                ).show()
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(
                    this@FavoriteActivity,
                    resources.getString(R.string.feature_not_ready),
                    Toast.LENGTH_SHORT
                ).show()
                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.menu_search -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_batik,
            R.string.tab_article
        )
    }
}