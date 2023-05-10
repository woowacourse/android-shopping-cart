package woowacourse.shopping.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.presentation.model.ProductModel

class CartAdapter(
    cartProducts: List<ProductModel>,
    private val deleteItem: (ProductModel) -> Unit,
) : RecyclerView.Adapter<CartItemViewHolder>() {
    private lateinit var binding: ItemCartBinding
    private val _cartProducts = cartProducts.toMutableList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        binding =
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartItemViewHolder(binding, deleteItem)
    }

    override fun getItemCount(): Int {
        return _cartProducts.size
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        holder.bind(_cartProducts[position])
    }

    fun setItems(products: List<ProductModel>) {
        _cartProducts.clear()
        _cartProducts.addAll(products)
        notifyDataSetChanged()
    }
}
