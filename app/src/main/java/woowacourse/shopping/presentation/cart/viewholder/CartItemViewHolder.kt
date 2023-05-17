package woowacourse.shopping.presentation.cart.viewholder

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.presentation.cart.CartListener
import woowacourse.shopping.presentation.model.CartProductModel

class CartItemViewHolder(
    parent: ViewGroup,
    inflater: LayoutInflater,
    cartListener: CartListener,
) : RecyclerView.ViewHolder(
    inflater.inflate(R.layout.item_cart, parent, false),
) {
    // 사용하진 않지만 확장성을 위해 정의
    constructor(parent: ViewGroup, cartListener: CartListener) :
        this(parent, LayoutInflater.from(parent.context), cartListener)

    private val binding = ItemCartBinding.bind(itemView)

    init {
        binding.cartListener = cartListener
        Log.d("asdfff", cartListener.toString())
    }

    fun bind(cartProductModel: CartProductModel) {
        binding.cartProductModel = cartProductModel
    }
}
