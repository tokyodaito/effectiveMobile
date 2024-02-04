package com.bogsnebes.effectivemobile.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bogsnebes.effectivemobile.R
import com.bogsnebes.effectivemobile.databinding.ActivityMainBinding
import com.bogsnebes.effectivemobile.ui.authorization.AuthorizationFragment
import com.bogsnebes.effectivemobile.ui.catalog.CatalogFragment
import com.bogsnebes.effectivemobile.ui.information.InformationFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) { // Проверка, чтобы избежать пересоздания фрагмента при каждом вызове onCreate
            val authorizationFragment = AuthorizationFragment.newInstance()
            val catalogFragment = CatalogFragment.newInstance()
            val informationFragment = InformationFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container_view_tag, informationFragment)
                .commit()
        }
    }
}
