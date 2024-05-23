package woowacourse.shopping.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.data.model.CartableProduct
import woowacourse.shopping.data.model.CartedProduct
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.presentation.BindableAdapter
import woowacourse.shopping.presentation.home.QuantityListener

class CartAdapter(
    private val cartItemEventListener: CartItemEventListener,
    private val quantityListener: QuantityListener,
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>(), BindableAdapter<CartedProduct> {
    private var orders: List<CartedProduct> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemCartBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_cart, parent, false)
        return CartViewHolder(binding, cartItemEventListener, quantityListener)
    }

    override fun getItemCount(): Int = orders.size

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.bind(orders[position])
    }

    override fun setData(data: List<CartedProduct>) {
        val currentSize = this.orders.size
        this.orders = data
        notifyItemRangeRemoved(0, currentSize)
        notifyItemRangeInserted(0, this.orders.size)
    }

    class CartViewHolder(
        private val binding: ItemCartBinding,
        cartItemEventListener: CartItemEventListener,
        quantityListener: QuantityListener,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.cartItemDeleteClickListener = cartItemEventListener
            binding.quantityListener = quantityListener
        }

        fun bind(cartableProduct: CartedProduct) {
            binding.cartedProduct = cartableProduct
        }
    }
}
