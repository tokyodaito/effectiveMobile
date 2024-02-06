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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = FragmentInformationAboutItemBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val catalogItem: CatalogItem? = arguments?.getParcelable(ARG_CATALOG_ITEM)
        if (activity is MainActivity) {
            (activity as MainActivity).showProgressBar(false)
        }

        if (catalogItem != null) {
            binding.ratingView.estimation = catalogItem.item.feedback.rating.toFloat()
            binding.textView16.text = "${catalogItem.item.feedback.count} отзывов"
            val imagesAdapter = HorizontalImagesAdapter(
                catalogItem.images,
                binding.indicatorsLayout
            )
            binding.recyclerView3.apply {
                PagerSnapHelper().attachToRecyclerView(this)
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                adapter = imagesAdapter
            }
            binding.recyclerView3.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val snapHelper = PagerSnapHelper()
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val snapView = snapHelper.findSnapView(layoutManager) ?: return
                    val newPosition = layoutManager.getPosition(snapView)
                    if (newPosition != RecyclerView.NO_POSITION) {
                        imagesAdapter.selectDot(newPosition)
                    }
                }
            })
            binding.textView14.setText(catalogItem.item.description)
            binding.textView10.text = "Доступно для заказа ${catalogItem.item.available} штук"
            binding.textView11.text = "${catalogItem.item.price.priceWithDiscount} ₽"
            binding.strikethroughTextView2.text = "${catalogItem.item.price.price} ₽"
            binding.textView12.text = "-${catalogItem.item.price.discount}%"
            binding.brend.text = "${catalogItem.item.title}"
            binding.subtitle.text = "${catalogItem.item.subtitle}"
            binding.expandableTextView.setText("${catalogItem.item.ingredients}")
            binding.priceOnButton.text = "${catalogItem.item.price.priceWithDiscount}₽"
            binding.priceWithoutDiscountOnButton.text = "${catalogItem.item.price.price}₽"

            if (catalogItem.favorite) {
                binding.imageView6.setImageResource(R.drawable.ic_heart_full)
            } else {
                binding.imageView6.setImageResource(R.drawable.ic_heart)
            }

            binding.imageView6.setOnClickListener {
                viewModel.toggleFavorite(catalogItem.item.id)
                if (catalogItem.favorite) {
                    binding.imageView6.setImageResource(R.drawable.ic_heart_full)
                } else {
                    binding.imageView6.setImageResource(R.drawable.ic_heart)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_CATALOG_ITEM = "catalogItem"

        fun newInstance(catalogItem: CatalogItem): InformationFragment {
            val fragment = InformationFragment()
            val args = Bundle()
            args.putParcelable(ARG_CATALOG_ITEM, catalogItem)
            fragment.arguments = args
            return fragment
        }
    }

}