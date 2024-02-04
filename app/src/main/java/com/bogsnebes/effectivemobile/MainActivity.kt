package com.bogsnebes.effectivemobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bogsnebes.effectivemobile.databinding.ActivityMainBinding
import com.bogsnebes.effectivemobile.ui.authorization.AuthorizationFragment
import com.bogsnebes.effectivemobile.ui.catalog.CatalogFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) { // Проверка, чтобы избежать пересоздания фрагмента при каждом вызове onCreate
            val authorizationFragment = AuthorizationFragment.newInstance()
            val catalogFragment = CatalogFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container_view_tag, catalogFragment)
                .commit()
        }
    }
}
