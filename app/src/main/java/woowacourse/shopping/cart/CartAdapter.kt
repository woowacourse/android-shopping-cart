package woowacourse.shopping.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.databinding.ItemCartProductListBinding

class CartAdapter(
    private var cartProducts: List<CartProductModel>,
    private val onCartItemRemoveButtonClick: (CartProductModel) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val onCartItemRemoveButtonViewClick: (Int) -> Unit =
        { onCartItemRemoveButtonClick(cartProducts[it]) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CartViewHolder(
            ItemCartProductListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onCartItemRemoveButtonViewClick
        )
    }

    override fun getItemCount(): Int = cartProducts.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CartViewHolder).bind(cartProducts[position])
    }

    fun updateCartProducts(cartProducts: List<CartProductModel>) {
        this.cartProducts = cartProducts
        notifyDataSetChanged()
    }
}
