package woowacourse.shopping.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.cart.viewholder.CartItemViewHolder
import woowacourse.shopping.presentation.model.ProductModel

class CartAdapter(
    cartProducts: List<ProductModel>,
    private val deleteItem: (ProductModel) -> Unit,
) : RecyclerView.Adapter<CartItemViewHolder>() {

    private val _cartProducts = cartProducts.toMutableList()
    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        initLayoutInflater(parent)
        return CartItemViewHolder(parent, inflater, ::onCloseClick)
    }

    private fun initLayoutInflater(parent: ViewGroup) {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }
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

    private fun onCloseClick(position: Int) {
        deleteItem(_cartProducts[position])
    }
}
