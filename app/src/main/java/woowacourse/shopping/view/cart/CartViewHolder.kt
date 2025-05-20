package woowacourse.shopping.view.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductInCartBinding
import woowacourse.shopping.model.product.Product

class CartViewHolder(
    private val binding: ItemProductInCartBinding,
) : RecyclerView.ViewHolder(binding.root) {
    val removeProductButton = binding.removeProductBtn

    fun bind(item: Product) {
        binding.product = item
    }

    companion object {
        fun from(parent: ViewGroup): CartViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemProductInCartBinding.inflate(inflater, parent, false)
            return CartViewHolder(binding)
        }
    }
}
