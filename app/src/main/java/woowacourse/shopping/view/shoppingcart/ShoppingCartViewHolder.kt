package woowacourse.shopping.view.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.model.uimodel.CartProductUIModel

class ShoppingCartViewHolder(
    parent: ViewGroup,
    private val onClickRemove: (CartProductUIModel) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_cart_product, parent, false)
) {
    private val binding = ItemCartProductBinding.bind(itemView)
    private lateinit var cartProduct: CartProductUIModel

    init {
        binding.ivCancel.setOnClickListener {
            onClickRemove(cartProduct)
        }
    }

    fun bind(item: CartProductUIModel) {
        cartProduct = item
        binding.cartProduct = cartProduct
    }
}
