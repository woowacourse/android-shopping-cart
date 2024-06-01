package woowacourse.shopping.view.products.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemMoreLoadBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.model.ProductWithQuantity
import woowacourse.shopping.view.CountActionHandler
import woowacourse.shopping.view.products.ProductListActionHandler

sealed class ProductListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    class ProductViewHolder(
        private val binding: ItemProductBinding,
        private val actionHandler: ProductListActionHandler,
        private val countActionHandler: CountActionHandler,
    ) : ProductListViewHolder(binding.root) {
        fun bind(productWithQuantity: ProductWithQuantity) {
            binding.product = productWithQuantity.product
            binding.quantity = productWithQuantity.quantity
            binding.productListActionHandler = actionHandler
            binding.countActionHandler = countActionHandler
        }
    }

    class LoadMoreViewHolder(
        private val binding: ItemMoreLoadBinding,
        private val actionHandler: ProductListActionHandler,
    ) : ProductListViewHolder(binding.root) {
        fun bind() {
            binding.productListActionHandler = actionHandler
        }
    }
}
