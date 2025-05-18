package woowacourse.shopping.view.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductInCartBinding
import woowacourse.shopping.model.products.Product

class CartViewHolder(
    private val binding: ItemProductInCartBinding,
    private val onProductRemoveClickListener: OnProductRemoveClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Product) {
        binding.onProductRemoveClickListener = onProductRemoveClickListener
        binding.product = item
    }

    companion object {
        fun from(
            parent: ViewGroup,
            clickListener: OnProductRemoveClickListener,
        ): CartViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemProductInCartBinding.inflate(inflater, parent, false)
            return CartViewHolder(binding, clickListener)
        }
    }
}
