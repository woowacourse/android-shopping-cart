package woowacourse.shopping.presentation.view.catalog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding

class ProductViewHolder(
    private val binding: ItemProductBinding,
    eventListener: CatalogAdapter.CatalogEventListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.eventListener = eventListener
    }

    fun bind(item: CatalogItem.ProductItem) {
        binding.product = item.product
    }

    companion object {
        fun from(
            parent: ViewGroup,
            eventListener: CatalogAdapter.CatalogEventListener,
        ): ProductViewHolder {
            val binding =
                ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ProductViewHolder(binding, eventListener)
        }
    }
}
