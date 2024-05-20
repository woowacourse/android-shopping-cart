package woowacourse.shopping.presentation.shopping.product.adpater

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemPlusProductBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.presentation.shopping.product.ShoppingUiModel

sealed class ShoppingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    class Product(
        private val binding: ItemProductBinding,
        private val productListener: ProductListener,
    ) : ShoppingViewHolder(binding.root) {
        fun bind(product: ShoppingUiModel.Product) {
            binding.product = product
            binding.listener = productListener
        }

        fun interface ProductListener {
            fun onClickProduct(id: Long)
        }
    }

    class LoadMore(
        private val binding: ItemPlusProductBinding,
        private val loadMoreListener: LoadMoreListener,
    ) : ShoppingViewHolder(binding.root) {
        fun bind() {
            binding.listener = loadMoreListener
        }

        fun interface LoadMoreListener {
            fun onClickLoadMore()
        }
    }
}
