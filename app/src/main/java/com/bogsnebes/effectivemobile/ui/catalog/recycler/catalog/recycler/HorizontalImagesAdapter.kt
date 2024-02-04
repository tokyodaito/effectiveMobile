package com.bogsnebes.effectivemobile.ui.catalog.recycler.catalog.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bogsnebes.effectivemobile.R

class HorizontalImagesAdapter(
    private val images: List<String>,
    private val indicatorLayout: LinearLayout
) :
    RecyclerView.Adapter<HorizontalImagesAdapter.ImageViewHolder>() {

    private var currentSelected = 0

    init {
        images.forEachIndexed { index, _ ->
            val dot = View(indicatorLayout.context).apply {
                val size =
                    indicatorLayout.resources.getDimensionPixelSize(R.dimen.indicator_dot_size)
                val layoutParams = LinearLayout.LayoutParams(size, size)
                layoutParams.setMargins(size, 0, size, 0)
                this.layoutParams = layoutParams
                this.setBackgroundResource(if (index == 0) R.drawable.ic_dot_active else R.drawable.ic_dot_inactive)
            }
            indicatorLayout.addView(dot)
        }
    }

    inner class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_catalog_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUrl = images[position]
        if (imageUrl.isNotBlank()) {
            holder.imageView.load(imageUrl) {
                crossfade(true)
                error(R.drawable.ic_clear)
            }
        } else {
            holder.imageView.setImageResource(R.drawable.ic_clear)
        }
    }

    override fun getItemCount(): Int = images.size

    fun selectDot(position: Int) {
        indicatorLayout.getChildAt(currentSelected)
            .setBackgroundResource(R.drawable.ic_dot_inactive)
        indicatorLayout.getChildAt(position).setBackgroundResource(R.drawable.ic_dot_active)
        currentSelected = position
    }
}

