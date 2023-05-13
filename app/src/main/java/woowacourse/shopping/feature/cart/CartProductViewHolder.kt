package woowacourse.shopping.feature.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartProductBinding

class CartProductViewHolder private constructor(
    private val binding: ItemCartProductBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(cartProduct: CartProductItemModel) {
        binding.itemModel = cartProduct
    }

    companion object {
        fun create(parent: ViewGroup): CartProductViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemCartProductBinding.inflate(layoutInflater, parent, false)
            return CartProductViewHolder(binding)
        }
    }
}
