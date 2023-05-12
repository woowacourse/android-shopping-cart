package woowacourse.shopping.view.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductInCartBinding
import woowacourse.shopping.uimodel.CartProductsUIModel
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.uimodel.mapper.toDomain
import woowacourse.shopping.uimodel.mapper.toUIModel

class ShoppingCartAdapter(
    private var cartProducts: CartProductsUIModel,
    private val onClickRemove: (ProductUIModel) -> Unit
) : RecyclerView.Adapter<ShoppingCartViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = ItemProductInCartBinding.inflate(inflater, parent, false)
        return ShoppingCartViewHolder(view, cartProducts, onClickRemove)
    }

    override fun getItemCount(): Int = cartProducts.products.size

    override fun onBindViewHolder(holder: ShoppingCartViewHolder, position: Int) {
        holder.bind(cartProducts.products[position])
    }

    fun remove(it: ProductUIModel) {
        cartProducts = cartProducts.toDomain().remove(it.toDomain()).toUIModel()
    }
}
