package woowacourse.shopping.view.main.adapter

import androidx.recyclerview.widget.DiffUtil

class ProductAdapterDiffer : DiffUtil.ItemCallback<ProductRvItems>() {
    override fun areItemsTheSame(
        oldItem: ProductRvItems,
        newItem: ProductRvItems,
    ): Boolean {
        if (oldItem::class != newItem::class) return false

        return when (oldItem) {
            ProductRvItems.DividerItem,
            ProductRvItems.LoadItem,
            -> true

            is ProductRvItems.ProductItem -> {
                val new = newItem as ProductRvItems.ProductItem
                oldItem.data.item.id == new.data.item.id
            }

            is ProductRvItems.RecentProductItem -> {
                val new = newItem as ProductRvItems.RecentProductItem
                oldItem.items == new.items
            }
        }
    }

    override fun areContentsTheSame(
        oldItem: ProductRvItems,
        newItem: ProductRvItems,
    ): Boolean {
        return oldItem == newItem
    }
}
