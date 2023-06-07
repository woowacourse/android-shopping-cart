package woowacourse.shopping.ui.cart.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.listener.CartItemListener
import woowacourse.shopping.ui.cart.uistate.CartUIState

class CartListAdapter(
    private val cartItems: MutableList<CartUIState>,
    private val cartListener: CartItemListener,
) : RecyclerView.Adapter<CartListAdapter.CartListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_cart,
            parent,
            false,
        )

        return CartListViewHolder(
            ItemCartBinding.bind(view),
            cartListener,
        )
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(holder: CartListViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: List<CartUIState>) {
        cartItems.clear()
        cartItems.addAll(newItems)
        notifyDataSetChanged()
    }

    class CartListViewHolder(
        private val binding: ItemCartBinding,
        cartListener: CartItemListener,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.listener = cartListener
        }

        fun bind(product: CartUIState) {
            binding.item = product
        }
    }
}
