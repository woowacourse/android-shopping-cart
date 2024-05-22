package woowacourse.shopping.presentation.ui.shoppingcart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderCartProductBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.common.ProductCountHandler
import woowacourse.shopping.presentation.ui.shoppingcart.ShoppingCartActionHandler

class CartProductsAdapter(
    private val actionHandler: ShoppingCartActionHandler,
    private val productCountHandler: ProductCountHandler,
    private val cartProductList: MutableList<Product> = mutableListOf(),
) : RecyclerView.Adapter<CartProductsAdapter.CartProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HolderCartProductBinding.inflate(inflater, parent, false)
        return CartProductViewHolder(binding, actionHandler, productCountHandler)
    }

    override fun getItemCount(): Int = cartProductList.size

    override fun onBindViewHolder(
        holder: CartProductViewHolder,
        position: Int,
    ) {
        holder.bind(cartProductList[position], position)
    }

    fun updateCartProducts(newCartProductList: List<Product>) {
        cartProductList.clear()
        cartProductList.addAll(newCartProductList)
        notifyDataSetChanged()
    }

    class CartProductViewHolder(
        private val binding: HolderCartProductBinding,
        private val actionHandler: ShoppingCartActionHandler,
        private val productCountHandler: ProductCountHandler,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            cartProduct: Product,
            position: Int,
        ) {
            binding.product = cartProduct
            binding.actionHandler = actionHandler
            binding.position = position
            binding.productCountHandler = productCountHandler
        }
    }
}
