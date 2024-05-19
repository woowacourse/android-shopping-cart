package woowacourse.shopping.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.model.Product

class CartAdapter(
    private val onRemoveButtonClick: (Long) -> Unit,
) : RecyclerView.Adapter<CartViewHolder>() {
    private val cart: MutableList<Product> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding, onRemoveButtonClick)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.bind(cart[position])
    }

    override fun getItemCount(): Int = cart.size

    fun setData(products: List<Product>) {
        cart.clear()
        cart.addAll(products)
        notifyDataSetChanged()
    }
}
