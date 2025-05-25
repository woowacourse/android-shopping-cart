package woowacourse.shopping.presentation.view.catalog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.presentation.model.ProductUiModel

class RecentProductViewHolder private constructor(
    private val binding: ItemRecentProductBinding,
    eventListener: CatalogAdapter.CatalogEventListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.eventListener = eventListener
    }

    fun bind(recentProduct: ProductUiModel) {
        binding.recentProduct = recentProduct
    }

    companion object {
        fun from(
            parent: ViewGroup,
            eventListener: CatalogAdapter.CatalogEventListener,
        ): RecentProductViewHolder {
            val binding =
                ItemRecentProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return RecentProductViewHolder(binding, eventListener)
        }
    }
}
