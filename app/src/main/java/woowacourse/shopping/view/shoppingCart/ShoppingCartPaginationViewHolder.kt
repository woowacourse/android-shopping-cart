package woowacourse.shopping.view.shoppingCart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemShoppingCartPaginationBinding

class ShoppingCartPaginationViewHolder(
    private val binding: ItemShoppingCartPaginationBinding,
    private val onShoppingCartPaginationListener: OnShoppingCartPaginationListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onShoppingCartPaginationListener = onShoppingCartPaginationListener
    }

    fun bind(item: ShoppingCartItem.PaginationItem) {
        binding.page = item.page
    }

    companion object {
        fun of(
            parent: ViewGroup,
            onShoppingCartPaginationListener: OnShoppingCartPaginationListener,
        ): ShoppingCartPaginationViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemShoppingCartPaginationBinding.inflate(layoutInflater, parent, false)
            return ShoppingCartPaginationViewHolder(
                binding,
                onShoppingCartPaginationListener,
            )
        }
    }
}
