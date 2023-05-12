package woowacourse.shopping.ui.shopping.product

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.ui.model.UiProduct
import woowacourse.shopping.ui.shopping.ShoppingViewType

class ProductAdapter(private val onItemClick: (UiProduct) -> Unit) :
    ListAdapter<UiProduct, ProductViewHolder>(productDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder =
        ProductViewHolder(parent) { onItemClick(currentList[it]) }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = ShoppingViewType.PRODUCT.value

    companion object {
        private val productDiffUtil = object : DiffUtil.ItemCallback<UiProduct>() {
            override fun areItemsTheSame(oldItem: UiProduct, newItem: UiProduct): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UiProduct, newItem: UiProduct): Boolean =
                oldItem == newItem
        }
    }
}
