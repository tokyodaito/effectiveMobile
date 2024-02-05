package com.bogsnebes.effectivemobile.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bogsnebes.effectivemobile.databinding.FragmentCatalogBinding
import com.bogsnebes.effectivemobile.ui.MainActivity
import com.bogsnebes.effectivemobile.ui.catalog.custom_view.CustomSpinnerAdapter
import com.bogsnebes.effectivemobile.ui.catalog.recycler.catalog.CatalogAdapter
import com.bogsnebes.effectivemobile.ui.catalog.recycler.catalog.CatalogItem
import com.bogsnebes.effectivemobile.ui.catalog.recycler.tags.Tag
import com.bogsnebes.effectivemobile.ui.catalog.recycler.tags.TagsAdapter
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
        subcribeUI()
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
            // Реализация логики при выборе элемента
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
        // Здесь добавьте логику для фильтрации списка товаров по выбранному тэгу
        // Например, обновите адаптер вашего RecyclerView с товарами, используя фильтр
    }

    private fun onTagDeselected() {
        // Здесь добавьте логику для отображения всех товаров, когда выбор с тэга снят
        // Это может быть просто вызов onTagSelected с фильтром, который не исключает никаких товаров
        onTagSelected("all")
    }

    private fun subcribeUI() {
        viewModel.products.observe(viewLifecycleOwner) { productResponse ->
            val catalogItems = productResponse.items.map { productItem ->
                CatalogItem(
                    id = productItem.id.hashCode(), // Используйте hashCode для генерации уникального ID
                    price = productItem.price.price,
                    discountPrice = productItem.price.priceWithDiscount,
                    discountPercentage = "${productItem.price.discount}%",
                    productName = productItem.title,
                    productDescription = productItem.description,
                    rating = "${productItem.feedback.rating}",
                    imageUrls = listOf() // Заполните, если у вас есть URL изображений
                )
            }
            updateCatalogRecyclerView(catalogItems)
            if (activity is MainActivity) {
                (activity as MainActivity).showProgressBar(false)
            }
        }
    }

    private fun updateCatalogRecyclerView(catalogItems: List<CatalogItem>) {
        val adapter = CatalogAdapter(catalogItems)
        binding.catalogRecyclerView.adapter = adapter
        binding.catalogRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): CatalogFragment = CatalogFragment()
    }
}

