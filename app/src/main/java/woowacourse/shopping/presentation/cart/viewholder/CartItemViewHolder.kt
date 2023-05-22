package woowacourse.shopping.presentation.cart.viewholder

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.presentation.cart.CartListener
import woowacourse.shopping.presentation.model.CartProductModel
import woowacourse.shopping.presentation.model.CheckableCartProductModel
import woowacourse.shopping.util.executeExceptionHandler

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
    }

    fun bind(cartProductModel: CartProductModel) {
        val checkableCart =
            cartProductModel as? CheckableCartProductModel
                ?: return (itemView.context as Activity)
                    .executeExceptionHandler(TYPE_CAST_EXCEPTION_MESSAGE)

        binding.checkBoxCart.isChecked = checkableCart.isChecked
        binding.cartProductModel = cartProductModel
    }

    companion object {
        private const val TYPE_CAST_EXCEPTION_MESSAGE = "잘못된 형태의 정보가 전달되었습니다."
    }
}
