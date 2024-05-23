package woowacourse.shopping.presentation.ui.shopping.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderRecentProductsBinding
import woowacourse.shopping.databinding.ItemLoadBinding
import woowacourse.shopping.databinding.ItemShoppingProductBinding
import woowacourse.shopping.domain.ProductListItem
import woowacourse.shopping.presentation.ui.shopping.ShoppingHandler

sealed class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    class RecentlyViewedProductViewHolder(
        private val binding: HolderRecentProductsBinding,
        private val shoppingHandler: ShoppingHandler,
    ) : ProductViewHolder(binding.root) {
        fun bind(items: ProductListItem.RecentProductItems) {
            binding.rvRecentProduct.adapter = RecentProductAdapter(items.items, shoppingHandler)
        }
    }

    class ShoppingProductViewHolder(
        private val binding: ItemShoppingProductBinding,
        private val shoppingHandler: ShoppingHandler,
    ) :
        ProductViewHolder(binding.root) {
        fun bind(item: ProductListItem.ShoppingProductItem) {
            binding.product = item
            binding.shoppingHandler = shoppingHandler
        }
    }

    class LoadViewHolder(
        private val binding: ItemLoadBinding,
        private val shoppingHandler: ShoppingHandler,
    ) : ProductViewHolder(binding.root) {
        fun bind() {
            binding.handler = shoppingHandler
        }
    }
}
