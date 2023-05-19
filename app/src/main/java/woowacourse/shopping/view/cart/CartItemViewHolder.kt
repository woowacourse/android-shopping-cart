package woowacourse.shopping.view.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.databinding.ItemCartPaginationBinding
import woowacourse.shopping.util.PriceFormatter

sealed class CartItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    class CartProductViewHolder(
        private val binding: ItemCartBinding,
        private val onItemClick: CartAdapter.OnItemClick
    ) :
        CartItemViewHolder(binding.root) {
        init {
            binding.onItemClick = onItemClick
        }

        fun bind(item: CartViewItem.CartProductItem) {
            binding.cartProduct = item.product
            binding.textPrice.text = binding.root.context.getString(
                R.string.korean_won,
                PriceFormatter.format(item.product.price * item.product.count)
            )
            Glide.with(binding.root.context).load(item.product.imageUrl).into(binding.imgProduct)
            binding.btnMinus.setOnClickListener {
                onItemClick.onUpdateCount(item.product.id, item.product.count - 1)
            }
            binding.btnPlus.setOnClickListener {
                onItemClick.onUpdateCount(item.product.id, item.product.count + 1)
            }
            binding.checkboxSelect.setOnClickListener {
                onItemClick.onSelectProduct(item.product)
            }
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
