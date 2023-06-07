package woowacourse.shopping.ui.cart.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.ui.cart.uistate.CartUIState

class CartListAdapter(
    private val cartItems: MutableList<CartUIState>,
    private val cartListener: CartListener,
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
        cartListener: CartListener,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.listener = cartListener
        }

        fun bind(product: CartUIState) {
            binding.item = product

            binding.cbCart.isChecked = product.isChecked

            Glide.with(itemView)
                .load(product.imageUrl)
                .into(binding.ivCart)
        }
    }
}
