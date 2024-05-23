package woowacourse.shopping.presentation.shopping.product.adpater

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemLoadMoreProductBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.presentation.shopping.product.ProductItemListener
import woowacourse.shopping.presentation.shopping.product.ShoppingUiModel

sealed class ShoppingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    class Product(
        private val binding: ItemProductBinding,
        private val productListener: ProductItemListener,
    ) : ShoppingViewHolder(binding.root) {
        fun bind(product: ShoppingUiModel.Product) {
            binding.product = product
            binding.listener = productListener
        }
    }

    class LoadMore(
        private val binding: ItemLoadMoreProductBinding,
        private val loadMoreListener: ProductItemListener,
    ) : ShoppingViewHolder(binding.root) {
        fun bind() {
            binding.listener = loadMoreListener
            binding.executePendingBindings()
        }
    }
}
