package woowacourse.shopping.ui.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.ui.cart.uistate.CartUIState
import java.text.DecimalFormat

class CartListAdapter(
    private val cartItems: List<CartUIState>,
    private val onCloseButtonClick: (Int) -> Unit,
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
        private val onCloseButtonClick: (Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnCartClose.setOnClickListener {
                onCloseButtonClick(adapterPosition)
            }
        }

        fun bind(product: CartUIState) {
            binding.tvCartName.text = product.name
            binding.tvCartPrice.text = itemView.context.getString(R.string.product_price).format(
                PRICE_FORMAT.format(product.price),
            )
            Glide.with(itemView)
                .load(product.imageUrl)
                .into(binding.ivCart)
        }

        companion object {
            private val PRICE_FORMAT = DecimalFormat("#,###")
        }
    }
}
