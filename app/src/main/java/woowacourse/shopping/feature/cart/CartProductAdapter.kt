package woowacourse.shopping.feature.cart

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.model.CartProductUiModel

class CartProductAdapter(private val cartProductClickListener: CartProductClickListener) :
    ListAdapter<CartProductUiModel, CartProductViewHolder>(CartDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductViewHolder {
        return CartProductViewHolder.create(parent, cartProductClickListener)
    }

    override fun onBindViewHolder(holder: CartProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setItems(newItems: List<CartProductUiModel>) {
        submitList(newItems)
    }

    companion object {
        private val CartDiffUtil = object : DiffUtil.ItemCallback<CartProductUiModel>() {
            override fun areItemsTheSame(
                oldItem: CartProductUiModel,
                newItem: CartProductUiModel
            ): Boolean {
                return oldItem.cartId == newItem.cartId
            }

            override fun areContentsTheSame(
                oldItem: CartProductUiModel,
                newItem: CartProductUiModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
