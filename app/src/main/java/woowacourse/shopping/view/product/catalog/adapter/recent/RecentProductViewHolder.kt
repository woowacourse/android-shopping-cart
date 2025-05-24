package woowacourse.shopping.view.product.catalog.adapter.recent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.domain.model.RecentProduct

class RecentProductViewHolder(
    private val binding: ItemRecentProductBinding,
    eventHandler: EventHandler,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.handler = eventHandler
    }

    fun bind(recentProduct: RecentProduct) {
        binding.recentProduct = recentProduct
    }

    interface EventHandler {
        fun onRecentProductClick(item: RecentProduct)
    }

    companion object {
        fun from(
            parent: ViewGroup,
            eventHandler: EventHandler,
        ): RecentProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemRecentProductBinding.inflate(inflater, parent, false)
            return RecentProductViewHolder(binding, eventHandler)
        }
    }
}
