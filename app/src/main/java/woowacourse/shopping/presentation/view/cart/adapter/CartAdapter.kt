package woowacourse.shopping.presentation.view.cart.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.model.CartModel
import woowacourse.shopping.presentation.view.cart.CartProductListener
import woowacourse.shopping.presentation.view.cart.viewholder.CartViewHolder

class CartAdapter(
    items: List<CartModel>,
    private val cartProductListener: CartProductListener
) : RecyclerView.Adapter<CartViewHolder>() {
    private val items = items.toMutableList()

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(parent, cartProductListener)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
    override fun getItemId(position: Int): Long = items[position].id

    fun updateList(newItems: List<CartModel>) {
        val diffUtilCallback = CartDiffUtil(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)

        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }
}
