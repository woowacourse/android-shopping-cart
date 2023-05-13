package woowacourse.shopping.view.cart

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.CartPageStatus
import woowacourse.shopping.model.CartProductModel
import woowacourse.shopping.model.ProductModel

class CartAdapter(
    private val items: List<CartViewItem>,
    private val onItemClick: OnItemClick,
) : RecyclerView.Adapter<CartItemViewHolder>() {

    interface OnItemClick {
        fun onRemoveClick(id: Int)
        fun onNextClick()
        fun onPrevClick()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        return CartItemViewHolder.of(parent, CartViewType.values()[viewType], onItemClick)
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].type.ordinal
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        when (holder) {
            is CartItemViewHolder.CartProductViewHolder -> holder.bind(items[position] as CartViewItem.CartProductItem)
            is CartItemViewHolder.CartPaginationViewHolder -> holder.bind(items[position] as CartViewItem.PaginationItem)
        }
    }
}
