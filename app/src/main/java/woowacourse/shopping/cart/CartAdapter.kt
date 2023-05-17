package woowacourse.shopping.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.common.model.CartOrdinalProductModel
import woowacourse.shopping.databinding.ItemCartProductListBinding

class CartAdapter(
    private var cartProducts: List<CartOrdinalProductModel>,
    private val onCartItemRemoveButtonClick: (CartOrdinalProductModel) -> Unit
) : RecyclerView.Adapter<CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            ItemCartProductListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onCartItemRemoveButtonClick
        )
    }

    override fun getItemCount(): Int = cartProducts.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartProducts[position])
    }

    override fun getItemViewType(position: Int): Int = cartProducts.size

    fun updateCartProducts(cartProducts: List<CartOrdinalProductModel>) {
        this.cartProducts = cartProducts
        notifyDataSetChanged()
    }
}
