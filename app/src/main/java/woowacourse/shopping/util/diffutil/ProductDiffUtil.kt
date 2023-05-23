package woowacourse.shopping.util.diffutil

import androidx.recyclerview.widget.DiffUtil
import woowacourse.shopping.model.CartProduct

object ProductDiffUtil : DiffUtil.ItemCallback<CartProduct>() {
    override fun areItemsTheSame(
        oldItem: CartProduct,
        newItem: CartProduct,
    ): Boolean = oldItem.product.id == newItem.product.id

    override fun areContentsTheSame(
        oldItem: CartProduct,
        newItem: CartProduct,
    ): Boolean = oldItem == newItem
}
