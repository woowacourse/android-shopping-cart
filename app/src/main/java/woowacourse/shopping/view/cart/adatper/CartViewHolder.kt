package woowacourse.shopping.view.cart.adatper

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.Product

class CartViewHolder(private val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Product) {
        binding.model = item
    }
}
