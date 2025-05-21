package woowacourse.shopping.view.shoppingcart

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemShoppingCartProductBinding
import woowacourse.shopping.domain.ShoppingCartItem

class ShoppingCartViewHolder(
    private val binding: ItemShoppingCartProductBinding,
    private val handler: ShoppingCartEventHandler,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.handler = handler
    }

    fun bind(
        item: ShoppingCartItem,
        currentPage: Int,
        quantity: MutableLiveData<Int>,
    ) {
        binding.quantity = quantity
        binding.product = item
        binding.page = currentPage
    }
}
