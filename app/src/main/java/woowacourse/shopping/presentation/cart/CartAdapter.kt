package woowacourse.shopping.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.cart.viewholder.CartItemViewHolder
import woowacourse.shopping.presentation.model.CartProductModel

class CartAdapter(
    cartProducts: List<CartProductModel>,
    private val cartListener: CartListener,
) : RecyclerView.Adapter<CartItemViewHolder>() {

    private val _cartProducts = cartProducts.toMutableList()
    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        initLayoutInflater(parent)
        return CartItemViewHolder(parent, inflater, cartListener)
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

    fun setItems(cartProducts: List<CartProductModel>) {
        _cartProducts.clear()
        _cartProducts.addAll(cartProducts)
        notifyDataSetChanged()
    }
}
