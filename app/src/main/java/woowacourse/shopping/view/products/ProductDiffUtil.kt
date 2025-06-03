package woowacourse.shopping.view.products

import androidx.recyclerview.widget.DiffUtil

class ProductDiffUtil : DiffUtil.ItemCallback<ProductListViewType>() {
    override fun areItemsTheSame(
        oldItem: ProductListViewType,
        newItem: ProductListViewType,
    ): Boolean {
        return when (oldItem) {
            is ProductListViewType.ProductType -> {
                val new = newItem as ProductListViewType.ProductType
                return oldItem.product.id == new.product.id
            }

            ProductListViewType.LoadMoreType -> true
        }
    }

    override fun areContentsTheSame(
        oldItem: ProductListViewType,
        newItem: ProductListViewType,
    ): Boolean = oldItem == newItem
}
