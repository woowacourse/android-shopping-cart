package woowacourse.shopping.view.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.uimodel.ProductUIModel

class ShoppingCartAdapter(
    private var cartProducts: List<CartProductUIModel>,
    private val onClickRemove: (ProductUIModel) -> Unit
) : RecyclerView.Adapter<ShoppingCartViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = ItemCartProductBinding.inflate(inflater, parent, false)
        return ShoppingCartViewHolder(view, cartProducts, onClickRemove)
    }

    override fun getItemCount(): Int = cartProducts.size

    override fun onBindViewHolder(holder: ShoppingCartViewHolder, position: Int) {
        holder.bind(cartProducts[position].productUIModel)
    }

    fun remove(product: ProductUIModel) {
        val index = cartProducts.indexOf(CartProductUIModel(product))
        cartProducts = cartProducts - CartProductUIModel(product)
        notifyItemRemoved(index)
    }
}
