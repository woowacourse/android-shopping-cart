package woowacourse.shopping.presentation.product

import androidx.recyclerview.widget.DiffUtil

class ProductDiffCallback : DiffUtil.ItemCallback<ProductListItem>() {
    override fun areItemsTheSame(
        oldItem: ProductListItem,
        newItem: ProductListItem,
    ): Boolean =
        when {
            oldItem is ProductListItem.Product && newItem is ProductListItem.Product ->
                oldItem.item.product.productId == newItem.item.product.productId

            oldItem is ProductListItem.LoadMore && newItem is ProductListItem.LoadMore -> true
            else -> false
        }

    override fun areContentsTheSame(
        oldItem: ProductListItem,
        newItem: ProductListItem,
    ): Boolean = oldItem == newItem
}
