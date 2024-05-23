package woowacourse.shopping.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderCartBinding
import woowacourse.shopping.listener.OnClickCartItemCounter
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product

class CartItemRecyclerViewAdapter(
    private var values: List<Product>,
    private val onClickCartItemCounter: OnClickCartItemCounter,
    private val onClick: (id: Int) -> Unit,
) : RecyclerView.Adapter<CartItemRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return ViewHolder(
            HolderCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            onClick = { id -> onClick(id) },
        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val item = values[position]
        holder.bind(item, cartItem = CartItem(item.id, 1))
        holder.binding.listener = onClickCartItemCounter
        holder.binding.cartQuantityButton.productDetailProductCount.visibility =
            View.VISIBLE
        holder.binding.cartQuantityButton.productDetailPlus.visibility = View.VISIBLE
        holder.binding.cartQuantityButton.productDetailMinus.visibility =
            View.VISIBLE
    }

    override fun getItemCount(): Int = values.size.coerceAtMost(5)

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<Product>) {
        this.values = newData
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        val binding: HolderCartBinding,
        private val onClick: (id: Int) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product, cartItem: CartItem) {
            binding.product = product
            binding.cartItem = cartItem
            binding.cartProductDelete.setOnClickListener { onClick(product.id) }
        }
    }
}
