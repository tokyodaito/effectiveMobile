package com.bogsnebes.effectivemobile.ui.information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bogsnebes.effectivemobile.R
import com.bogsnebes.effectivemobile.databinding.FragmentInformationAboutItemBinding
import com.bogsnebes.effectivemobile.model.network.ProductInfo
import com.bogsnebes.effectivemobile.ui.MainActivity
import com.bogsnebes.effectivemobile.ui.catalog.CatalogItem
import com.bogsnebes.effectivemobile.ui.catalog.recycler.catalog.recycler.HorizontalImagesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InformationFragment : Fragment() {
    private var _binding: FragmentInformationAboutItemBinding? = null
    private val binding get() = _binding!!

    private val viewModel: InformationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentInformationAboutItemBinding.inflate(inflater, container, false).apply {
            _binding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? MainActivity)?.showProgressBar(false)
        setupUI()
    }

    private fun setupUI() {
        arguments?.getParcelable<CatalogItem>(ARG_CATALOG_ITEM)?.let { catalogItem ->
            updateUIForCatalogItem(catalogItem)
            setupImageRecyclerView(catalogItem.images)
            handleFavorite(catalogItem)
            setupBackNavigation()
            displayItemInformation(catalogItem.item.info)
        }
    }

    private fun updateUIForCatalogItem(catalogItem: CatalogItem) = with(binding) {
        title.text = catalogItem.item.title
        ratingView.estimation = catalogItem.item.feedback.rating.toFloat()
        textView16.text = "${catalogItem.item.feedback.count} отзывов"
        textView15.text = catalogItem.item.feedback.rating.toString()
        textView14.setText(catalogItem.item.description)
        textView10.text = "Доступно для заказа ${
            resources.getQuantityString(
                R.plurals.available_count,
                catalogItem.item.available,
                catalogItem.item.available
            )
        }"
        textView11.text = "${catalogItem.item.price.priceWithDiscount} ₽"
        strikethroughTextView2.text = "${catalogItem.item.price.price} ₽"
        textView12.text = "-${catalogItem.item.price.discount}%"
        brand.text = catalogItem.item.title
        subtitle.text = catalogItem.item.subtitle
        expandableTextView.setText(catalogItem.item.ingredients)
        priceOnButton.text = "${catalogItem.item.price.priceWithDiscount}₽"
        priceWithoutDiscountOnButton.text = "${catalogItem.item.price.price}₽"
        imageView10.setImageResource(if (catalogItem.favorite) R.drawable.ic_heart_full else R.drawable.ic_heart)
    }

    private fun setupImageRecyclerView(images: List<Int>) {
        val imagesAdapter = HorizontalImagesAdapter(images, binding.indicatorsLayout)
        with(binding.recyclerView3) {
            PagerSnapHelper().attachToRecyclerView(this)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = imagesAdapter
            setupScrollListener(imagesAdapter)
        }
    }

    private fun RecyclerView.setupScrollListener(imagesAdapter: HorizontalImagesAdapter) {
        val snapHelper = PagerSnapHelper()
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val snapView = snapHelper.findSnapView(layoutManager) ?: return
                val newPosition = layoutManager.getPosition(snapView)
                imagesAdapter.selectDot(newPosition)
            }
        })
    }

    private fun handleFavorite(catalogItem: CatalogItem) = with(binding.imageView10) {
        setOnClickListener {
            viewModel.toggleFavorite(catalogItem.item.id)
            catalogItem.favorite = !catalogItem.favorite
            setImageResource(if (catalogItem.favorite) R.drawable.ic_heart_full else R.drawable.ic_heart)
        }
    }

    private fun setupBackNavigation() {
        binding.imageView5.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun displayItemInformation(info: List<ProductInfo>) {
        info.forEach {
            when (it.title) {
                "Артикул товара" -> binding.textView21.text = it.value
                "Область использования" -> binding.textView22.text = it.value
                else -> binding.textView23.text = it.value
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_CATALOG_ITEM = "catalogItem"

        fun newInstance(catalogItem: CatalogItem): InformationFragment =
            InformationFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CATALOG_ITEM, catalogItem)
                }
            }
    }
}
