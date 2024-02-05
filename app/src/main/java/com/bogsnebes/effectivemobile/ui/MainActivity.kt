package com.bogsnebes.effectivemobile.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bogsnebes.effectivemobile.R
import com.bogsnebes.effectivemobile.databinding.ActivityMainBinding
import com.bogsnebes.effectivemobile.ui.authorization.AuthorizationFragment
import com.bogsnebes.effectivemobile.ui.catalog.CatalogFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            if (hasUserData()) {
                openCatalogFragment()
            } else {
                openAuthorizationFragment()
            }
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    Toast.makeText(this, "Заглушка", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.navigation_catalog -> {
                    // Обработка выбора "Каталог"
                    true
                }
                // Добавьте остальные пункты аналогично
                else -> false
            }
        }
    }

    private fun hasUserData(): Boolean {
        val sharedPreferences = getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.contains("editText2")
    }

    private fun openCatalogFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view_tag, CatalogFragment.newInstance())
            .commit()
        showBottomNav(true)
    }

    private fun openAuthorizationFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view_tag, AuthorizationFragment.newInstance())
            .commit()
        showBottomNav(false)
    }

    fun showBottomNav(show: Boolean) {
        binding.bottomNavigation.visibility = if (show) View.VISIBLE else View.GONE
    }
}
