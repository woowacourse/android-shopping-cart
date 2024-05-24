package woowacourse.shopping.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.presentation.util.ItemUpdateHelper

class CartAdapter(
    private val cartProductListener: CartProductListener,
) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private var products: List<CartProductUi> = emptyList()
    private val updateHelper: ItemUpdateHelper<CartProductUi> =
        ItemUpdateHelper<CartProductUi>(
            adapter = this,
            areItemsTheSame = { oldItem, newItem -> oldItem.product.id == newItem.product.id },
            areContentsTheSame = { oldItem, newItem -> oldItem == newItem },
        )

    override fun getItemCount(): Int = products.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val binding =
            ItemCartProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return CartViewHolder(binding, cartProductListener)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.bind(products[position])
    }

    override fun getItemId(position: Int): Long = products[position].product.id

    fun updateProduct(newProducts: List<CartProductUi>) {
        val oldProducts = products.toList()
        products = newProducts
        updateHelper.update(oldProducts, newProducts)
    }

    class CartViewHolder(
        private val binding: ItemCartProductBinding,
        private val cartProductListener: CartProductListener,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: CartProductUi) {
            binding.cartProduct = product
            binding.listener = cartProductListener
        }
    }
}
