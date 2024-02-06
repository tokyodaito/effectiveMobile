package com.bogsnebes.effectivemobile.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bogsnebes.effectivemobile.R
import com.bogsnebes.effectivemobile.databinding.FragmentFavouritesBinding
import com.bogsnebes.effectivemobile.ui.MainActivity
import com.bogsnebes.effectivemobile.ui.catalog.CatalogItem
import com.bogsnebes.effectivemobile.ui.favourites.recycler.FavoritesAdapter
import com.bogsnebes.effectivemobile.ui.information.InformationFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : Fragment() {
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavouritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = FragmentFavouritesBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity is MainActivity) {
            (activity as MainActivity).showProgressBar(false)
        }
        subscribeUI()
        binding.imageView9.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun subscribeUI() {
        viewModel.products.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DataState.Loading -> showLoadingIndicator(true)
                is DataState.Success -> {
                    showLoadingIndicator(false)
                    updateCatalogRecyclerView(state.data)
                }

                is DataState.Error -> {
                    showLoadingIndicator(false)
                    showError()
                }
            }
        }
    }

    private fun showLoadingIndicator(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun showError() {
        Toast.makeText(context, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show()
    }

    private fun updateCatalogRecyclerView(catalogItems: List<CatalogItem>) {
        val adapter = FavoritesAdapter(
            catalogItems.toMutableList(),
            onFavoriteClicked = { item -> onFavoriteClicked(item) }
        ) {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view_tag, InformationFragment.newInstance(it))
                .addToBackStack(null)
                .commit()
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(
            requireContext(),
            2
        )
    }

    private fun onFavoriteClicked(item: String) {
        viewModel.toggleFavorite(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): FavouritesFragment = FavouritesFragment()
    }
}