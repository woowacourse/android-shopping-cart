package woowacourse.shopping.presentation.ui.shopping.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemLoadBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.presentation.ui.shopping.ShoppingHandler

sealed class ShoppingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    class ProductViewHolder(
        private val binding: ItemProductBinding,
        private val shoppingHandler: ShoppingHandler,
    ) :
        ShoppingViewHolder(binding.root) {
        private var id: Long = -1

        init {
            binding.root.setOnClickListener {
                shoppingHandler.onProductClick(id)
            }
        }

        fun bind(item: Product) {
            id = item.id
            binding.product = item
        }
    }

    class LoadViewHolder(
        private val binding: ItemLoadBinding,
        private val shoppingHandler: ShoppingHandler,
    ) : ShoppingViewHolder(binding.root) {
        fun bind() {
            binding.btnShowMore.setOnClickListener {
                shoppingHandler.onLoadMoreClick()
            }
        }
    }
}
