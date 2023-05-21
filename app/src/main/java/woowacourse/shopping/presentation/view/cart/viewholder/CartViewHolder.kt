package woowacourse.shopping.presentation.view.cart.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartListBinding
import woowacourse.shopping.presentation.model.CartProductModel

class CartViewHolder(
    parent: ViewGroup,
    onCloseClick: (Int) -> Unit,
    onCheckedChangeListener: (Int, Boolean) -> Unit,
    onCountChanged: (Int, Int) -> Unit,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_cart_list, parent, false)
) {
    private val binding = ItemCartListBinding.bind(itemView)

    init {
        binding.btIvCartListClose.setOnClickListener {
            onCloseClick(absoluteAdapterPosition)
        }
        binding.checkboxCartList.setOnCheckedChangeListener { _, isChecked ->
            onCheckedChangeListener(absoluteAdapterPosition, isChecked)
        }
        binding.countViewProductListOrderCount.setOnPlusClickListener {
            onCountChanged(absoluteAdapterPosition, it.count)
        }
        binding.countViewProductListOrderCount.setOnMinusClickListener {
            onCountChanged(absoluteAdapterPosition, it.count)
        }
    }

    fun bind(cart: CartProductModel) {
        binding.cartItem = cart
    }
}
