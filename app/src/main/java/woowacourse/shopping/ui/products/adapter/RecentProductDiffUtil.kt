package woowacourse.shopping.ui.products.adapter

import androidx.recyclerview.widget.DiffUtil
import woowacourse.shopping.model.Product

object RecentProductDiffUtil : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(
        oldItem: Product,
        newItem: Product,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Product,
        newItem: Product,
    ): Boolean {
        return oldItem == newItem
    }
}
