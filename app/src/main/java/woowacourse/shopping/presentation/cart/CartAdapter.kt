package woowacourse.shopping.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.data.model.cart.CartedProduct
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.presentation.BindableAdapter
import woowacourse.shopping.presentation.home.products.QuantityListener

class CartAdapter(
    private val cartItemEventListener: CartItemEventListener,
    private val quantityListener: QuantityListener,
) : ListAdapter<CartedProduct, CartAdapter.CartViewHolder>(CartDiffUtil) {
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.itemAnimator = null
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemCartBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_cart, parent, false)
        return CartViewHolder(binding, cartItemEventListener, quantityListener)
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.bind(currentList[position])
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
