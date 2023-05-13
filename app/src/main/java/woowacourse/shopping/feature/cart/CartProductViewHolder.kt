package woowacourse.shopping.feature.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartProductBinding

class CartProductViewHolder private constructor(
    private val binding: ItemCartProductBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(cartProduct: CartProductItemModel) {
        binding.itemModel = cartProduct
        binding.position = bindingAdapterPosition
    }

    companion object {
        fun create(parent: ViewGroup): CartProductViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = DataBindingUtil.inflate<ItemCartProductBinding>(
                layoutInflater,
                R.layout.item_cart_product,
                parent,
                false
            )
            return CartProductViewHolder(binding)
        }
    }
}
