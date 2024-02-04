package com.bogsnebes.effectivemobile.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bogsnebes.effectivemobile.databinding.FragmentCatalogBinding
import com.bogsnebes.effectivemobile.ui.catalog.custom_view.CustomSpinnerAdapter
import com.bogsnebes.effectivemobile.ui.catalog.recycler.Tag
import com.bogsnebes.effectivemobile.ui.catalog.recycler.TagsAdapter

class CatalogFragment : Fragment() {
    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!

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
        initSpinner()
        setupTagsRecyclerView()
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): CatalogFragment = CatalogFragment()
    }
}

