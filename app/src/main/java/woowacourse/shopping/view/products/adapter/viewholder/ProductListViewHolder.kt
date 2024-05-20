package woowacourse.shopping.view.products.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemMoreLoadBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.view.products.ProductListActionHandler

sealed class ProductListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    class ProductViewHolder(
        private val binding: ItemProductBinding,
        private val actionHandler: ProductListActionHandler,
    ) : ProductListViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.product = product
            binding.productListActionHandler = actionHandler
        }
    }

    class LoadMoreViewHolder(
        private val binding: ItemMoreLoadBinding,
        private val actionHandler: ProductListActionHandler,
    ) : ProductListViewHolder(binding.root) {
        fun bind(shouldShowMore: Boolean) {
            binding.productListActionHandler = actionHandler
            binding.shouldShowMore = shouldShowMore
        }
    }
}
