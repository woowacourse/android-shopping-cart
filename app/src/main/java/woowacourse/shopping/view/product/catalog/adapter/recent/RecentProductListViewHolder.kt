package woowacourse.shopping.view.product.catalog.adapter.recent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductListBinding
import woowacourse.shopping.view.product.catalog.adapter.ProductCatalogItem

class RecentProductListViewHolder(
    binding: ItemRecentProductListBinding,
    eventHandler: RecentProductViewHolder.EventHandler,
) : RecyclerView.ViewHolder(binding.root) {
    private val adapter = RecentProductAdapter(eventHandler = eventHandler)

    init {
        binding.rvRecentProducts.adapter = adapter
        binding.rvRecentProducts.layoutManager =
            LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL, false)
        binding.rvRecentProducts.itemAnimator = null
    }

    fun bind(item: ProductCatalogItem.RecentProductsItem) {
        adapter.updateItems(item.recentProducts)
    }

    companion object {
        fun from(
            parent: ViewGroup,
            eventHandler: RecentProductViewHolder.EventHandler,
        ): RecentProductListViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemRecentProductListBinding.inflate(inflater, parent, false)
            return RecentProductListViewHolder(binding, eventHandler)
        }
    }
}
