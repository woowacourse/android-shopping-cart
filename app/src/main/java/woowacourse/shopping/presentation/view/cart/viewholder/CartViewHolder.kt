package woowacourse.shopping.presentation.view.cart.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartListBinding
import woowacourse.shopping.presentation.model.CartModel
import woowacourse.shopping.presentation.view.custom.CountView

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

    fun bind(cart: CartModel, onCountClick: (Long, Int) -> Unit) {
        binding.cart = cart
        binding.countViewCartListItem.countStateChangeListener = object : CountView.OnCountStateChangeListener {
            override fun onCountChanged(countView: CountView?, count: Int) {
                onCountClick(cart.id, count)
            }
        }
        binding.countViewCartListItem.updateCount(cart.product.count)
    }
}
