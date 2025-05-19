package woowacourse.shopping.presentation.view.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.presentation.model.ProductUiModel

class CartViewHolder(
    private val binding: ItemCartBinding,
    eventListener: CartAdapter.CartEventListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.eventListener = eventListener
    }

    fun bind(product: ProductUiModel) {
        binding.product = product
    }

    companion object {
        fun from(
            parent: ViewGroup,
            eventListener: CartAdapter.CartEventListener,
        ): CartViewHolder {
            val binding =
                ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CartViewHolder(binding, eventListener)
        }
    }
}
