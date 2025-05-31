package woowacourse.shopping.view.cart.adapter

import androidx.recyclerview.widget.DiffUtil
import woowacourse.shopping.view.main.state.ProductState

class CartDiffer : DiffUtil.ItemCallback<ProductState>() {
    override fun areItemsTheSame(
        oldItem: ProductState,
        newItem: ProductState,
    ): Boolean {
        return oldItem.item.id == newItem.item.id
    }

    override fun areContentsTheSame(
        oldItem: ProductState,
        newItem: ProductState,
    ): Boolean {
        return oldItem == newItem
    }
}
