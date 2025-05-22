package woowacourse.shopping.view.shoppingCart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemShoppingCartPaginationBinding

class ShoppingCartPaginationViewHolder(
    private val binding: ItemShoppingCartPaginationBinding,
    onShoppingCartPaginationListener: OnShoppingCartPaginationListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onShoppingCartPaginationListener = onShoppingCartPaginationListener
    }

    fun bind(item: ShoppingCartItem.PaginationItem) {
        binding.paginationItem = item
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
