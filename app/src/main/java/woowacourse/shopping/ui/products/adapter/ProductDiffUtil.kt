package woowacourse.shopping.ui.products.adapter

import androidx.recyclerview.widget.DiffUtil
import woowacourse.shopping.model.ProductWithQuantity

object ProductDiffUtil : DiffUtil.ItemCallback<ProductWithQuantity>() {
    override fun areItemsTheSame(
        oldItem: ProductWithQuantity,
        newItem: ProductWithQuantity,
    ): Boolean {
        return oldItem.product.id == newItem.product.id
    }

    override fun areContentsTheSame(
        oldItem: ProductWithQuantity,
        newItem: ProductWithQuantity,
    ): Boolean {
        return oldItem == newItem
    }
}
