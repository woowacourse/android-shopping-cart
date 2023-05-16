package woowacourse.shopping.feature.cart

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class CartProductAdapter : ListAdapter<CartProductItemModel, CartProductViewHolder>(CartDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductViewHolder {
        return CartProductViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CartProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setItems(newItems: List<CartProductItemModel>) {
        submitList(newItems)
    }

    companion object {
        private val CartDiffUtil = object : DiffUtil.ItemCallback<CartProductItemModel>() {
            override fun areItemsTheSame(
                oldItem: CartProductItemModel,
                newItem: CartProductItemModel
            ): Boolean {
                return oldItem.cartProduct.cartId == newItem.cartProduct.cartId
            }

            override fun areContentsTheSame(
                oldItem: CartProductItemModel,
                newItem: CartProductItemModel
            ): Boolean {
                return oldItem.cartProduct == newItem.cartProduct
            }
        }
    }
}
