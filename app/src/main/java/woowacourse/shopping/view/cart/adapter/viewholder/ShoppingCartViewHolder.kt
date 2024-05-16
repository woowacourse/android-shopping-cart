package woowacourse.shopping.view.cart.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import woowacourse.shopping.databinding.ItemShoppingCartBinding
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.view.cart.OnClickShoppingCart

class ShoppingCartViewHolder(
    private val binding: ItemShoppingCartBinding,
    private val onClickShoppingCart: OnClickShoppingCart,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(cartItem: CartItem) {
        binding.cartItem = cartItem
        binding.onClickShoppingCart = onClickShoppingCart
        Glide.with(itemView.context)
            .load(cartItem.product.imageUrl)
            .override(Target.SIZE_ORIGINAL)
            .into(binding.ivCartItemImage)
    }
}
