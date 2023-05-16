package woowacourse.shopping.feature.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.model.CartProductUiModel

class CartProductViewHolder private constructor(
    private val binding: ItemCartProductBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(cartProduct: CartProductUiModel, listener: CartProductClickListener) {
        binding.cartProduct = cartProduct
        binding.listener = listener
    }

    companion object {
        fun create(parent: ViewGroup): CartProductViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemCartProductBinding.inflate(layoutInflater, parent, false)
            return CartProductViewHolder(binding)
        }
    }
}
