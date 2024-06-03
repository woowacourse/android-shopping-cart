package woowacourse.shopping.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.shoppingcart.uimodel.CartItemUiModel
import woowacourse.shopping.shoppingcart.uimodel.ShoppingCartClickAction
import woowacourse.shopping.util.imageUrlToSrc

class ShoppingCartAdapter(
    private val onClicked: ShoppingCartClickAction,
) : RecyclerView.Adapter<ShoppingCartAdapter.ShoppingCartViewHolder>() {
    private val items = mutableListOf<CartItemUiModel>()

    class ShoppingCartViewHolder(
        private val binding: ItemCartBinding,
        private val onClicked: ShoppingCartClickAction,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: CartItemUiModel) {
            with(binding) {
                tvItemCartName.text = item.name
                itemView.context.imageUrlToSrc(item.imageUrl, ivCartProduct)
                binding.cartItem = item
                binding.onClick = onClicked
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShoppingCartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingCartViewHolder(binding, onClicked)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: ShoppingCartViewHolder,
        position: Int,
    ) {
        holder.onBind(items[position])
    }

    fun replaceItems(newItems: List<CartItemUiModel>) {
        items.clear()
        items.addAll(newItems.toList())
        notifyDataSetChanged()
    }

    fun deleteItemByProductId(productId: Long) {
        val deleteIndex = items.indexOfFirst { it.id == productId }
        items.removeAt(deleteIndex)
        notifyItemRemoved(deleteIndex)
    }

    fun addItem(item: CartItemUiModel) {
        items.add(item.copy())
        notifyItemInserted(items.lastIndex)
    }

    fun changeProductInfo(updatedCartItem: CartItemUiModel) {
        val changeIndex = items.indexOfFirst { it.id == updatedCartItem.id }
        items[changeIndex] = updatedCartItem
        notifyItemChanged(changeIndex)
    }
}
