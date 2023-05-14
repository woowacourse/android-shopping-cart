package woowacourse.shopping.presentation.view.cart.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartListBinding
import woowacourse.shopping.presentation.model.CartModel

class CartViewHolder(
    parent: ViewGroup,
    onCloseClick: (Int) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_cart_list, parent, false)
) {
    private val binding = ItemCartListBinding.bind(itemView)

    init {
        binding.btIvCartListClose.setOnClickListener {
            onCloseClick(absoluteAdapterPosition)
        }
    }

    fun bind(cart: CartModel) {
        binding.cart = cart
        binding.tvCartListTitle.text = cart.product.title
        binding.tvCartListPrice.text =
            binding.root.context.getString(R.string.product_price_format, cart.product.price)
    }
}
