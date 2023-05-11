package woowacourse.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductInCartBinding
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toUIModel

class CartRecyclerViewAdapter(
    private var cartProducts: CartUIModel,
    private val onClickRemove: (ProductUIModel) -> Unit
) : RecyclerView.Adapter<CartRecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartRecyclerViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = ItemProductInCartBinding.inflate(inflater, parent, false)
        return CartRecyclerViewHolder(view, cartProducts, onClickRemove)
    }

    override fun getItemCount(): Int = cartProducts.products.size

    override fun onBindViewHolder(holder: CartRecyclerViewHolder, position: Int) {
        holder.bind(cartProducts.products[position])
    }

    fun remove(it: ProductUIModel) {
        cartProducts = cartProducts.toDomain().remove(it.toDomain()).toUIModel()
    }
}
