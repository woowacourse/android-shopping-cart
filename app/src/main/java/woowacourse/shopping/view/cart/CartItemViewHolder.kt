package woowacourse.shopping.view.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.databinding.ItemCartPaginationBinding

sealed class CartItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    class CartProductViewHolder(
        private val binding: ItemCartBinding,
        onItemClick: CartAdapter.OnItemClick
    ) :
        CartItemViewHolder(binding.root) {
        init {
            binding.onItemClick = onItemClick
        }

        fun bind(item: CartViewItem.CartProductItem) {
            binding.cartProduct = item.product
            Glide.with(binding.root.context).load(item.product.imageUrl).into(binding.imgProduct)
        }
    }

    class CartPaginationViewHolder(
        private val binding: ItemCartPaginationBinding,
        onItemClick: CartAdapter.OnItemClick
    ) :
        CartItemViewHolder(binding.root) {
        init {
            binding.onItemClick = onItemClick
        }

        fun bind(item: CartViewItem.PaginationItem) {
            binding.status = item.status
        }
    }

    companion object {
        fun of(
            parent: ViewGroup,
            type: CartViewType,
            onItemClick: CartAdapter.OnItemClick
        ): CartItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(type.id, parent, false)
            return when (type) {
                CartViewType.CART_PRODUCT_ITEM -> CartProductViewHolder(
                    ItemCartBinding.bind(view),
                    onItemClick
                )
                CartViewType.PAGINATION_ITEM -> CartPaginationViewHolder(
                    ItemCartPaginationBinding.bind(view),
                    onItemClick
                )
            }
        }
    }
}
