package com.bogsnebes.effectivemobile.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bogsnebes.effectivemobile.databinding.FragmentCatalogBinding
import com.bogsnebes.effectivemobile.ui.catalog.custom_view.CustomSpinnerAdapter
import com.bogsnebes.effectivemobile.ui.catalog.recycler.catalog.CatalogAdapter
import com.bogsnebes.effectivemobile.ui.catalog.recycler.catalog.CatalogItem
import com.bogsnebes.effectivemobile.ui.catalog.recycler.tags.Tag
import com.bogsnebes.effectivemobile.ui.catalog.recycler.tags.TagsAdapter

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
        setupCatalogRecyclerView()
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

    private fun setupCatalogRecyclerView() {
        val catalogItems = listOf(
            CatalogItem(
                0,
                "749",
                "489",
                "35",
                "Аянами Рей",
                "Редко говорит, но все её любят",
                "4.5 (51)",
                listOf(
                    "https://i.pinimg.com/originals/d4/ff/87/d4ff874411c91c897f3dc8ec0eba2fdf.jpg",
                    "https://i1.sndcdn.com/artworks-9HMf98ByULKrx1aO-lORCxg-t500x500.jpg",
                    "https://cs14.pikabu.ru/post_img/2022/08/06/11/165981410716676233.jpg"
                ),
            ),
            CatalogItem(
                0,
                "1488",
                "489",
                "35",
                "Аска Ленгли",
                "Топ тян за свои деньги",
                "4.5 (51)",
                listOf(
                    "https://static.wikia.nocookie.net/evangelion/images/7/7b/2.22.jpg/revision/latest?cb=20190721122435&path-prefix=ru",
                    "data:image/jpeg;base64,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",
                    "https://diskomir.ru/upload/resize_cache/webp/iblock/895/8951609ece2717bd4eb4201fe0106dae.webp",
                )
            ),
            CatalogItem(
                0,
                "1000000",
                "4123",
                "35",
                "Мисато Кацураги",
                "Милфочка",
                "4.5 (51)",
                listOf(
                    "https://i.pinimg.com/736x/83/11/0c/83110c6604f80210bfce7171ca7b2024.jpg",
                    "https://sun9-79.userapi.com/c237131/u34601202/d58/-3/o_5d7bb888c0.jpg",
                    "https://lh3.googleusercontent.com/proxy/QecnzfvHUlWK7bCofjGAcGK8lM1U56EBtFiJS2fTs3rDk5j4TeFMVwN8RluPZ3JEpQEBsQ9eenZqFtoiUn25Gt6dTSqysnIYlqs"
                )
            )
        )
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

