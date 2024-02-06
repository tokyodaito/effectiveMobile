package com.bogsnebes.effectivemobile.ui.favourites.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bogsnebes.effectivemobile.R
import com.bogsnebes.effectivemobile.ui.catalog.CatalogItem
import com.bogsnebes.effectivemobile.ui.catalog.recycler.catalog.recycler.HorizontalImagesAdapter

class FavoritesAdapter(
    private val items: MutableList<CatalogItem>,
    private val onFavoriteClicked: (String) -> Unit,
    private val onItemClicked: (CatalogItem) -> Unit
) :
    RecyclerView.Adapter<FavoritesAdapter.CatalogViewHolder>() {

    class CatalogViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val snapHelper = PagerSnapHelper()
        val priceTextView: TextView = view.findViewById(R.id.strikethroughTextView)
        val discountPriceTextView: TextView = view.findViewById(R.id.textView7)
        val discountTextView: TextView = view.findViewById(R.id.discount)
        val nameTextView: TextView = view.findViewById(R.id.textView3)
        val description: TextView = view.findViewById(R.id.textView4)
        val markTextView: TextView = view.findViewById(R.id.mark)
        val heartImageView: ImageView = view.findViewById(R.id.imageView4)

        val indicatorsLayout: LinearLayout = view.findViewById(R.id.indicators_layout)
        val recyclerView: RecyclerView =
            view.findViewById<RecyclerView?>(R.id.recyclerView2).apply {
                snapHelper.attachToRecyclerView(this)
                this.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_catalog_item, parent, false)
        return CatalogViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        val item = items[position]
        with(holder) {
            priceTextView.text = "${item.item.price.price} ₽"
            discountPriceTextView.text = "${item.item.price.priceWithDiscount} ₽"
            discountTextView.text = "- ${item.item.price.discount}%"
            nameTextView.text = item.item.title
            description.text = item.item.description
            markTextView.text = "${item.item.feedback.rating} (${item.item.feedback.count})"
            heartImageView.setImageResource(R.drawable.ic_heart_full)

            itemView.setOnClickListener {
                onItemClicked(item)
            }

            heartImageView.setOnClickListener {
                onFavoriteClicked(item.item.id)
                items.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, itemCount)
            }

            val adapter = HorizontalImagesAdapter(item.images, indicatorsLayout)
            recyclerView.adapter = adapter
            recyclerView.setupScrollListener(
                adapter,
                snapHelper
            )
        }
    }

    private fun RecyclerView.setupScrollListener(
        imagesAdapter: HorizontalImagesAdapter,
        snapHelper: PagerSnapHelper
    ) {
        this.clearOnScrollListeners()
        this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val snapView = snapHelper.findSnapView(layoutManager) ?: return
                val newPosition = layoutManager.getPosition(snapView)
                if (newPosition != RecyclerView.NO_POSITION) {
                    imagesAdapter.selectDot(newPosition)
                }
            }
        })
    }

    override fun getItemCount(): Int = items.size
}

