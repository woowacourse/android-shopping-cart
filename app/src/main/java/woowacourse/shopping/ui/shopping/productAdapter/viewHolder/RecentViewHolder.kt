package woowacourse.shopping.ui.shopping.productAdapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemProductRecentBinding
import woowacourse.shopping.ui.shopping.productAdapter.ProductsItemType
import woowacourse.shopping.ui.shopping.productAdapter.ProductsListener
import woowacourse.shopping.ui.shopping.recentProductAdapter.RecentProductItem
import woowacourse.shopping.ui.shopping.recentProductAdapter.RecentProductsAdapter

class RecentViewHolder(
    binding: ItemProductRecentBinding,
    listener: ProductsListener
) : ShoppingViewHolder(binding.root) {
    private val adapter = RecentProductsAdapter(mutableListOf(), listener::onClickItem)

    init {
        binding.rvProducts.adapter = adapter
    }
    override fun bind(productItemType: ProductsItemType) {
        (productItemType as ProductsItemType.RecentProducts).let { recentProducts ->
            adapter.submitList(recentProducts.product.map { RecentProductItem(it) })
        }
    }

    companion object {
        fun from(parent: ViewGroup, listener: ProductsListener): RecentViewHolder {
            val binding = ItemProductRecentBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return RecentViewHolder(binding, listener)
        }
    }
}
