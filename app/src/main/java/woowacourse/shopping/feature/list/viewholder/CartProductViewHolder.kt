package woowacourse.shopping.feature.list.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.feature.list.item.CartProductItem

class CartProductViewHolder(
    val parent: ViewGroup,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_cart_product, parent, false),
) {
    private val binding = ItemCartProductBinding.bind(itemView)

    fun bind(cartProductItem: CartProductItem, onClick: (CartProductItem) -> Unit) {
        binding.cartProduct = cartProductItem
        binding.cartClearImageView.setOnClickListener { onClick(cartProductItem) }
    }
}
