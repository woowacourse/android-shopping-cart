package woowacourse.shopping.productList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderProductBinding
import woowacourse.shopping.listener.OnClickCartItemCounter
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product

class ProductRecyclerViewAdapter(
    private var values: List<Product> = emptyList(),
    private var cartItems: List<CartItem> = emptyList(),
    private val onClickCartItemCounter: OnClickCartItemCounter,
    private val onClick: (id: Int) -> Unit,
) : RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val binding = HolderProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding) { id -> onClick(id) }
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val product = values[position]
        val cartItems =
            cartItems.find { it.productId == product.id } ?: CartItem(product.id, 0)
        holder.bind(product, cartItems)
        holder.binding.listener = onClickCartItemCounter
    }

    override fun getItemCount(): Int = values.size

    fun updateData(newData: List<Product>) {
        val start = this.values.size
        this.values = newData
        notifyItemRangeInserted(start, newData.size - start)
    }

    fun updateCartItems(newData: List<CartItem>) {
        this.cartItems = newData
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        val binding: HolderProductBinding,
        private val onClick: (id: Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product, cartItem: CartItem) {
            binding.product = product
            binding.cartItem = cartItem
            binding.root.setOnClickListener { onClick(product.id) }
        }
    }
}
