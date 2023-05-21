package woowacourse.shopping.util.diffutil

import androidx.recyclerview.widget.DiffUtil
import woowacourse.shopping.model.BasketProduct

object ProductDiffUtil : DiffUtil.ItemCallback<BasketProduct>() {
    override fun areItemsTheSame(
        oldItem: BasketProduct,
        newItem: BasketProduct,
    ): Boolean = oldItem.product.id == newItem.product.id

    override fun areContentsTheSame(
        oldItem: BasketProduct,
        newItem: BasketProduct,
    ): Boolean = oldItem == newItem
}
