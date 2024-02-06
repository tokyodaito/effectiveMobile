package com.bogsnebes.effectivemobile.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bogsnebes.effectivemobile.databinding.FragmentFavouritesBinding
import com.bogsnebes.effectivemobile.ui.MainActivity
import com.bogsnebes.effectivemobile.ui.catalog.recycler.catalog.CatalogAdapter
import com.bogsnebes.effectivemobile.ui.catalog.recycler.catalog.CatalogItem
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
        subcribeUI()
        binding.imageView9.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun subcribeUI() {
        viewModel.products.observe(viewLifecycleOwner) { productResponse ->
            updateCatalogRecyclerView(productResponse)
        }
    }

    private fun updateCatalogRecyclerView(catalogItems: List<CatalogItem>) {
        val adapter = CatalogAdapter(
            catalogItems,
            onFavoriteClicked = { item -> onFavoriteClicked(item) }
        )
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