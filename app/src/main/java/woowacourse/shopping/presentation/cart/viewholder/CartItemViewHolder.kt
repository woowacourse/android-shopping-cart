package woowacourse.shopping.presentation.cart.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.presentation.model.CartProductInfoModel

class CartItemViewHolder(
    private val binding: ItemCartBinding,
    deleteItem: (CartProductInfoModel) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var cartProduct: CartProductInfoModel

    init {
        binding.imageCartDelete.setOnClickListener { deleteItem(cartProduct) }
    }

    fun bind(cartProductInfoModel: CartProductInfoModel) {
        cartProduct = cartProductInfoModel
        binding.cartProductInfo = cartProductInfoModel
    }
}
