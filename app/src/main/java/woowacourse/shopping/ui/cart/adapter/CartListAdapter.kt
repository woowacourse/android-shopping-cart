package woowacourse.shopping.ui.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.ui.cart.uistate.CartItemUIState
import woowacourse.shopping.utils.PRICE_FORMAT

class CartListAdapter(
    private val cartItems: MutableList<CartItemUIState>,
    private val onCloseButtonClick: (Long) -> Unit,
    private val onCheckButtonClick: (Long, Boolean) -> Unit
) : RecyclerView.Adapter<CartListAdapter.CartListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartListViewHolder {
        return CartListViewHolder.create(parent, onCloseButtonClick, onCheckButtonClick)
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(holder: CartListViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    fun setCartItemSelected(productId: Long, isSelected: Boolean) {
        val index = cartItems.indexOfFirst { it.productId == productId }
        if (index == -1) return
        cartItems[index] = cartItems[index].copy(isSelected = isSelected)
        if (hasStableIds()) notifyItemChanged(index)
    }

    class CartListViewHolder private constructor(
        private val binding: ItemCartBinding,
        private val onCloseButtonClick: (Long) -> Unit,
        private val onCheckButtonClick: (Long, Boolean) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cartItem: CartItemUIState) {
            binding.cbCartItemSelected.isChecked = cartItem.isSelected
            binding.tvCartName.text = cartItem.name
            binding.tvCartPrice.text = itemView.context.getString(R.string.product_price).format(
                PRICE_FORMAT.format(cartItem.price),
            )
            Glide.with(itemView)
                .load(cartItem.imageUrl)
                .into(binding.ivCart)
            binding.cbCartItemSelected.isChecked = cartItem.isSelected
            binding.cbCartItemSelected.setOnCheckedChangeListener { _, isChecked ->
                onCheckButtonClick(cartItem.productId, isChecked)
            }
            binding.btnCartClose.setOnClickListener {
                onCloseButtonClick(cartItem.productId)
            }
        }

        companion object {
            fun create(
                parent: ViewGroup,
                onCloseButtonClick: (Long) -> Unit,
                onCheckButtonClick: (Long, Boolean) -> Unit
            ): CartListViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_cart, parent, false)
                val binding = ItemCartBinding.bind(view)
                return CartListViewHolder(binding, onCloseButtonClick, onCheckButtonClick)
            }
        }
    }
}
