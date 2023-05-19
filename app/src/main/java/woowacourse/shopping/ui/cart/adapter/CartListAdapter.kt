package woowacourse.shopping.ui.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.ui.cart.uistate.CartUIState
import woowacourse.shopping.utils.PRICE_FORMAT

class CartListAdapter(
    private val cartItems: MutableList<CartUIState>,
    private val onCloseButtonClick: (productId: Long) -> Unit,
    private val onPlusCountButtonClick: (productId: Long, oldCount: Int) -> Unit,
    private val onMinusCountButtonClick: (productId: Long, oldCount: Int) -> Unit,
) : RecyclerView.Adapter<CartListAdapter.CartListViewHolder>() {

    private val countChangeListener: (isPlusButton: Boolean, position: Int) -> Unit =
        { isPlus: Boolean, position: Int ->
            if (isPlus) {
                onPlusCountButtonClick(cartItems[position].id, cartItems[position].count)
            } else {
                onMinusCountButtonClick(cartItems[position].id, cartItems[position].count)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_cart,
            parent,
            false,
        )

        return CartListViewHolder(
            ItemCartBinding.bind(view),
            { position: Int -> onCloseButtonClick(cartItems[position].id) },
            countChangeListener,
        )
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(holder: CartListViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    fun updateItems(newItems: List<CartUIState>) {
        cartItems.clear()
        cartItems.addAll(newItems)
        notifyDataSetChanged()
    }

    class CartListViewHolder(
        private val binding: ItemCartBinding,
        onCloseButtonClick: (Int) -> Unit,
        onCountButtonsClick: (Boolean, Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnCartClose.setOnClickListener {
                onCloseButtonClick(position)
            }
            binding.btnCartPlusCount.setOnClickListener {
                onCountButtonsClick(isPlusButton, position)
            }
            binding.btnCartMinusCount.setOnClickListener {
                onCountButtonsClick(isMinusButton, position)
            }
        }

        fun bind(product: CartUIState) {
            binding.item = product

            binding.tvCartPrice.text = itemView.context.getString(R.string.product_price).format(
                PRICE_FORMAT.format(product.price),
            )
            Glide.with(itemView)
                .load(product.imageUrl)
                .into(binding.ivCart)
        }

        companion object {
            private const val isPlusButton = true
            private const val isMinusButton = false
        }
    }
}
