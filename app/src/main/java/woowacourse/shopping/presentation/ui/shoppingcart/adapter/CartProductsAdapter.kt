package woowacourse.shopping.presentation.ui.shoppingcart.adapter

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderCartProductBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.ui.shoppingcart.ShoppingCartActionHandler

class CartProductsAdapter(
    private val actionHandler: ShoppingCartActionHandler,
    private val cartProductList: MutableList<Product> = mutableListOf(),
) : RecyclerView.Adapter<CartProductsAdapter.CartProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HolderCartProductBinding.inflate(inflater, parent, false)
        return CartProductViewHolder(binding, actionHandler)
    }

    override fun getItemCount(): Int = cartProductList.size

    override fun onBindViewHolder(
        holder: CartProductViewHolder,
        position: Int,
    ) {
        holder.bind(cartProductList[position])
    }

    fun updateCartProducts(newCartProductList: List<Product>) {
        cartProductList.clear()
        cartProductList.addAll(newCartProductList)

        if (cartProductList.size != PAGE_SIZE) {
            notifyItemRangeRemoved(
                cartProductList.size,
                PAGE_SIZE,
            )
        }

        notifyDataSetChanged()
    }

    class CartProductViewHolder(
        private val binding: HolderCartProductBinding,
        private val actionHandler: ShoppingCartActionHandler,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cartProduct: Product) {
            binding.product = cartProduct
            binding.actionHandler = actionHandler
        }
    }
}
