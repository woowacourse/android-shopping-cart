package woowacourse.shopping.util.diffutil

import androidx.recyclerview.widget.DiffUtil
import woowacourse.shopping.model.UiBasketProduct

object BasketDiffUtil : DiffUtil.ItemCallback<UiBasketProduct>() {
    override fun areItemsTheSame(
        oldItem: UiBasketProduct,
        newItem: UiBasketProduct,
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: UiBasketProduct,
        newItem: UiBasketProduct,
    ): Boolean = oldItem == newItem
}
