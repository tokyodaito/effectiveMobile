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
import com.bogsnebes.effectivemobile.ui.catalog.recycler.catalog.recycler.HorizontalImagesAdapter

class CatalogAdapter(
    private val items: List<CatalogItem>,
    private val onFavoriteClicked: (String) -> Unit,
) :
    RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder>() {

    private var filteredItems: List<CatalogItem> = items.filter { it.favorite }

    fun updateItems(newItems: List<CatalogItem>) {
        this.filteredItems = newItems.filter { it.favorite }
        notifyDataSetChanged()
    }


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
        val item = filteredItems[position]
        holder.priceTextView.text = "${item.price} ₽"
        holder.discountPriceTextView.text = "${item.discountPrice} ₽"
        holder.discountTextView.text = "- ${item.discountPercentage}%"
        holder.nameTextView.text = item.productName
        holder.description.text = item.productDescription
        holder.markTextView.text = item.rating
        holder.heartImageView.setImageResource(R.drawable.ic_heart_full)

        // Настройка HorizontalImagesAdapter для каждого элемента каталога
        val imagesAdapter = HorizontalImagesAdapter(item.imageUrls, holder.indicatorsLayout)
        holder.recyclerView.apply {
            adapter = imagesAdapter
        }

        holder.heartImageView.setOnClickListener {
            filteredItems.drop(position)
            updateItems(filteredItems)
        }

        holder.recyclerView.clearOnScrollListeners() // Очистка перед добавлением нового
        holder.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // Используйте SnapHelper для определения текущего видимого элемента
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

