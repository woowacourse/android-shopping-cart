package woowacourse.shopping.view.recentproduct

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.model.cart.CartItem

class RecentProductViewHolder(
    private val binding: ItemRecentProductBinding,
    private val recentProductClickListener: (CartItem) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(cartItem: CartItem) {
        binding.cartItem = cartItem
        binding.btnSelectedRecentProduct.setOnClickListener {
            recentProductClickListener(cartItem)
        }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            recentProductClickListener: (CartItem) -> Unit,
        ): RecentProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemRecentProductBinding.inflate(inflater, parent, false)
            return RecentProductViewHolder(binding, recentProductClickListener)
        }
    }
}
