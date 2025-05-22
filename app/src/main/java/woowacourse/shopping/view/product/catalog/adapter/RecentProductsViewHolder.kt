package woowacourse.shopping.view.product.catalog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductsBinding

class RecentProductsViewHolder(
    private val binding: ItemRecentProductsBinding,
) : RecyclerView.ViewHolder(binding.root) {
    private val adapter = RecentProductAdapter()

    init {
        binding.rvRecentProducts.adapter = adapter
        binding.rvRecentProducts.layoutManager =
            LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL, false)
    }

    fun bind(item: ProductCatalogItem.RecentProductsItem) {
        adapter.updateItems(item.recentProducts)
    }

    companion object {
        fun from(parent: ViewGroup): RecentProductsViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemRecentProductsBinding.inflate(inflater, parent, false)
            return RecentProductsViewHolder(binding)
        }
    }
}
