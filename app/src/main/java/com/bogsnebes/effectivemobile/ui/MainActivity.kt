package com.bogsnebes.effectivemobile.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bogsnebes.effectivemobile.R
import com.bogsnebes.effectivemobile.databinding.ActivityMainBinding
import com.bogsnebes.effectivemobile.ui.authorization.AuthorizationFragment
import com.bogsnebes.effectivemobile.ui.cabinet.CabinetFragment
import com.bogsnebes.effectivemobile.ui.catalog.CatalogFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showBottomNav(false)

        if (savedInstanceState == null) {
            if (hasUserData()) {
                openCatalogFragment()
                showBottomNav(true)
                binding.bottomNavigation.selectedItemId = R.id.navigation_catalog
            } else {
                openAuthorizationFragment()
                binding.bottomNavigation.selectedItemId = R.id.navigation_catalog
                showBottomNav(false)
            }
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    Toast.makeText(this, "Заглушка", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.navigation_catalog -> {
                    openCatalogFragment()
                    true
                }

                R.id.navigation_basket -> {
                    Toast.makeText(this, "Заглушка", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.navigation_sales -> {
                    Toast.makeText(this, "Заглушка", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.navigation_profile -> {
                    openCabinetFragment()
                    true
                }

                else -> false
            }
        }
    }

    private fun hasUserData(): Boolean {
        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        return sharedPreferences.contains("name")
    }

    private fun openCatalogFragment() {
        showProgressBar(true)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view_tag, CatalogFragment.newInstance())
            .commit()
    }

    private fun openCabinetFragment() {
        showProgressBar(true)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view_tag, CabinetFragment.newInstance())
            .commit()
    }

    private fun openAuthorizationFragment() {
        showProgressBar(true)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view_tag, AuthorizationFragment.newInstance())
            .commit()
    }

    fun showBottomNav(show: Boolean) {
        binding.bottomNavigation.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun showProgressBar(show: Boolean) {
        binding.fragmentContainerViewTag.visibility = if (show) View.GONE else View.VISIBLE
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }
}
