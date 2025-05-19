package woowacourse.shopping.view.shoppingCart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemShoppingCartPaginationBinding

class ShoppingCartPaginationViewHolder(
    private val binding: ItemShoppingCartPaginationBinding,
    private val onShoppingCartPaginationClickListener: OnShoppingCartPaginationClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onShoppingCartPaginationListener = onShoppingCartPaginationClickListener
    }

    fun bind(item: ShoppingCartItem.PaginationItem) {
        binding.page = item.page
        binding.shoppingCartPageCountPlusButton.isEnabled = item.nextEnabled
        binding.shoppingCartPageCountMinusButton.isEnabled = item.previousEnabled
        binding.root.isVisible = item.nextEnabled || item.previousEnabled
    }

    companion object {
        fun of(
            parent: ViewGroup,
            onShoppingCartPaginationClickListener: OnShoppingCartPaginationClickListener,
        ): ShoppingCartPaginationViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemShoppingCartPaginationBinding.inflate(layoutInflater, parent, false)
            return ShoppingCartPaginationViewHolder(
                binding,
                onShoppingCartPaginationClickListener,
            )
        }
    }
}
