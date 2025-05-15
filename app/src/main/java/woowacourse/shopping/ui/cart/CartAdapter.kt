package woowacourse.shopping.ui.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.CartItemBinding
import woowacourse.shopping.domain.product.Product

class CartAdapter(
    private var items: List<Product>,
    private val cartClickListener: CartClickListener,
) : RecyclerView.Adapter<CartViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: CartItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.cart_item, parent, false)
        return CartViewHolder(binding, cartClickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        val item = items[position]
        holder.bind(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItem(it: List<Product>?) {
        items = it.orEmpty()
        notifyDataSetChanged()
    }
}
