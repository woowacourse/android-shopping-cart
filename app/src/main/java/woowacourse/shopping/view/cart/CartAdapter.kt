package woowacourse.shopping.view.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.model.ProductModel

class CartAdapter(
    private val products: List<ProductModel>,
    private val onItemClick: OnItemClick,
) : RecyclerView.Adapter<CartViewHolder>() {

    fun interface OnItemClick {
        fun onRemoveClick(id: Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(ItemCartBinding.bind(view))
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(products[position], onItemClick)
    }
}
