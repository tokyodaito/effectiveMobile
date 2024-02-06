package com.bogsnebes.effectivemobile.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bogsnebes.effectivemobile.R
import com.bogsnebes.effectivemobile.databinding.FragmentCatalogBinding
import com.bogsnebes.effectivemobile.ui.MainActivity
import com.bogsnebes.effectivemobile.ui.catalog.custom_view.CustomSpinnerAdapter
import com.bogsnebes.effectivemobile.ui.catalog.recycler.catalog.CatalogAdapter
import com.bogsnebes.effectivemobile.ui.catalog.recycler.tags.Tag
import com.bogsnebes.effectivemobile.ui.catalog.recycler.tags.TagsAdapter
import com.bogsnebes.effectivemobile.ui.information.InformationFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatalogFragment : Fragment() {
    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CatalogViewModel by viewModels()

    private val tags = listOf(
        Tag("Смотреть все", "all", true),
        Tag("Лицо", "face"),
        Tag("Тело", "body"),
        Tag("Загар", "suntan"),
        Tag("Маски", "mask")
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = FragmentCatalogBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity is MainActivity) {
            (activity as MainActivity).showProgressBar(false)
        }

        initSpinner()
        setupTagsRecyclerView()
        subscribeUI()
    }

    private fun initSpinner() {
        val items = arrayOf("По популярности", "По уменьшению цены", "По возрастанию цены")
        binding.spinner.apply {
            adapter = CustomSpinnerAdapter(
                requireContext(), android.R.layout.simple_spinner_item, items
            )
            onItemSelectedListener = createItemSelectedListener()
        }
    }

    private fun createItemSelectedListener() = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>, view: View?, position: Int, id: Long
        ) {
            val sortType = parent.getItemAtPosition(position).toString()
            viewModel.sortProducts(sortType)
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            // Ничего не выбрано
        }
    }

    private fun setupTagsRecyclerView() {
        val adapter = TagsAdapter(tags, ::onTagSelected, ::onTagDeselected)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun onTagSelected(filter: String) {
        viewModel.filterProductsByTag(filter)
    }

    private fun onTagDeselected() {
        onTagSelected("all")
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
        val adapter = CatalogAdapter(
            catalogItems,
            onFavoriteClicked = { item -> onFavoriteClicked(item) }
        ) {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view_tag, InformationFragment.newInstance(it))
                .addToBackStack(null)
                .commit()
        }
        binding.catalogRecyclerView.adapter = adapter
        binding.catalogRecyclerView.layoutManager = GridLayoutManager(
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
        fun newInstance(): CatalogFragment = CatalogFragment()
    }
}

