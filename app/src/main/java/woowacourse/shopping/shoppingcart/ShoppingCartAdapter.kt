package woowacourse.shopping.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.ViewModelQuantityActions
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.uimodel.CartItemUiModel

class ShoppingCartAdapter(
    private val onClick: ShoppingCartClickAction,
    private val onQuantityControlClick: ViewModelQuantityActions,
) : RecyclerView.Adapter<ShoppingCartAdapter.ShoppingCartViewHolder>() {
    private var items = emptyList<CartItemUiModel>()

    class ShoppingCartViewHolder(
        private val binding: ItemCartBinding,
        private val onClick: ShoppingCartClickAction,
        private val onQuantityControlClick: ViewModelQuantityActions,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: CartItemUiModel) {
            binding.cartItemUiModel = item
            binding.clickListener = onClick
            binding.quantityControlClickListener = onQuantityControlClick
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShoppingCartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingCartViewHolder(binding, onClick, onQuantityControlClick)
    }

    fun submitList(updatedItems: List<CartItemUiModel>) {
        items = updatedItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: ShoppingCartViewHolder,
        position: Int,
    ) {
        holder.onBind(items[position])
    }
}
