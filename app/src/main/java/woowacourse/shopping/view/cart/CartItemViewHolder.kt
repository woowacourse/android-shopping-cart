package woowacourse.shopping.view.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.databinding.ItemCartPaginationBinding
import woowacourse.shopping.model.ProductModel
import woowacourse.shopping.util.PriceFormatter

sealed class CartItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
    class CartProductViewHolder(private val binding: ItemCartBinding) : CartItemViewHolder(binding.root) {
        fun bind(product: ProductModel, onItemClick: CartAdapter.OnItemClick) {
            binding.cartProduct = product
            binding.textPrice.text = binding.root.context.getString(R.string.korean_won, PriceFormatter.format(product.price))
            Glide.with(binding.root.context).load(product.imageUrl).into(binding.imgProduct)
            binding.onItemClick = onItemClick
        }
    }

    class CartPaginationViewHolder(private val binding: ItemCartPaginationBinding, onItemClick: CartAdapter.OnItemClick) : CartItemViewHolder(binding.root) {
        init {
            binding.onItemClick = onItemClick
        }
        fun bind(count: Int) {
            binding.count = count
        }
    }

    companion object {
        fun of(parent: ViewGroup, type: CartViewType, onItemClick: CartAdapter.OnItemClick): CartItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(type.id, parent, false)
            return when (type) {
                CartViewType.CART_PRODUCT_ITEM -> CartProductViewHolder(ItemCartBinding.bind(view))
                CartViewType.PAGINATION_ITEM -> CartPaginationViewHolder(ItemCartPaginationBinding.bind(view), onItemClick)
            }
        }
    }
}

