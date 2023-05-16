package woowacourse.shopping.ui.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.ui.cart.uistate.CartItemUIState
import java.text.DecimalFormat

class CartListAdapter(
    private val cartItems: List<CartItemUIState>,
    private val onCloseButtonClick: (Long) -> Unit,
) : RecyclerView.Adapter<CartListAdapter.CartListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_cart,
            parent,
            false,
        )

        return CartListViewHolder(ItemCartBinding.bind(view), onCloseButtonClick)
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(holder: CartListViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    class CartListViewHolder(
        private val binding: ItemCartBinding,
        private val onCloseButtonClick: (Long) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cartItem: CartItemUIState) {
            binding.tvCartName.text = cartItem.name
            binding.tvCartPrice.text = itemView.context.getString(R.string.product_price).format(
                PRICE_FORMAT.format(cartItem.price),
            )
            Glide.with(itemView)
                .load(cartItem.imageUrl)
                .into(binding.ivCart)
            binding.btnCartClose.setOnClickListener {
                onCloseButtonClick(cartItem.id)
            }
        }

        companion object {
            private val PRICE_FORMAT = DecimalFormat("#,###")
        }
    }
}
