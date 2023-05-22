package woowacourse.shopping.presentation.ui.shoppingCart.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.domain.model.ProductInCart

class ShoppingCartAdapter(
    private val listener: ShoppingCartClickListener,
) : ListAdapter<ProductInCart, ShoppingCartViewHolder>(ShoppingCartComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartViewHolder {
        return ShoppingCartViewHolder(listener, ShoppingCartViewHolder.getView(parent))
    }

    override fun onBindViewHolder(holder: ShoppingCartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ShoppingCartComparator : DiffUtil.ItemCallback<ProductInCart>() {
        override fun areItemsTheSame(oldItem: ProductInCart, newItem: ProductInCart): Boolean {
            return oldItem.product.id == newItem.product.id
        }

        override fun areContentsTheSame(oldItem: ProductInCart, newItem: ProductInCart): Boolean {
            return oldItem == newItem
        }
    }
}
