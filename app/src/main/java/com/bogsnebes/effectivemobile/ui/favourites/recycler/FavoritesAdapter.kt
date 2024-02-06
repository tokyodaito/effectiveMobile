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

    class CatalogViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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
                PagerSnapHelper().attachToRecyclerView(this)
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_catalog_item, parent, false)
        return CatalogViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        val item = items[position]
        holder.priceTextView.text = "${item.item.price.price} ₽"
        holder.discountPriceTextView.text = "${item.item.price.priceWithDiscount} ₽"
        holder.discountTextView.text = "- ${item.item.price.discount}%"
        holder.nameTextView.text = item.item.title
        holder.description.text = item.item.description
        holder.markTextView.text = "${item.item.feedback.rating} (${item.item.feedback.count})"
        holder.heartImageView.setImageResource(R.drawable.ic_heart_full)

        // Настройка HorizontalImagesAdapter для каждого элемента каталога
        val imagesAdapter = HorizontalImagesAdapter(item.images, holder.indicatorsLayout)
        holder.recyclerView.apply {
            adapter = imagesAdapter
        }

        holder.heartImageView.setOnClickListener {
            onFavoriteClicked(item.item.id)
            items.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
        }

        holder.itemView.setOnClickListener {
            onItemClicked(item)
        }

        holder.recyclerView.clearOnScrollListeners() // Очистка перед добавлением нового
        holder.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
    }

    override fun getItemCount(): Int = items.size
}

