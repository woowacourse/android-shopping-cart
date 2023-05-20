package woowacourse.shopping.presentation.view.cart.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartListBinding
import woowacourse.shopping.presentation.model.CartModel
import woowacourse.shopping.presentation.view.cart.CartProductListener
import woowacourse.shopping.presentation.view.custom.CountView

class CartViewHolder(
    parent: ViewGroup,
    cartProductListener: CartProductListener
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_cart_list, parent, false)
) {
    private val binding = ItemCartListBinding.bind(itemView)

    init {
        binding.cartProductListener = cartProductListener
        binding.countViewCartListItem.countStateChangeListener = object : CountView.OnCountStateChangeListener {
            override fun onCountChanged(countView: CountView?, count: Int) {
                binding.cart?.let {
                    cartProductListener.onCountClick(it.id, count)
                }
            }
        }
    }

    fun bind(cart: CartModel) {
        binding.cart = cart
        binding.cbCartListItem.isChecked = cart.checked
        binding.countViewCartListItem.updateCount(cart.product.count)
    }
}
