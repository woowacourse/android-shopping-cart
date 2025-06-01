package woowacourse.shopping.util

import androidx.recyclerview.widget.DiffUtil
import woowacourse.shopping.product.catalog.ProductUiModel

class DiffCallback : DiffUtil.ItemCallback<ProductUiModel>() {
    override fun areItemsTheSame(
        oldItem: ProductUiModel,
        newItem: ProductUiModel,
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: ProductUiModel,
        newItem: ProductUiModel,
    ): Boolean = oldItem == newItem
}
