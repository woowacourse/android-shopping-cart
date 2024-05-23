package woowacourse.shopping.productList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderProductBinding
import woowacourse.shopping.listener.OnClickCartItemCounter
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product

class ProductRecyclerViewAdapter(
    private var values: List<Product>,
    private var cartItems: List<CartItem>,
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
        if (holder.binding.mainProductListAddCartButton.visibility == View.VISIBLE) {
            holder.binding.mainProductListAddCartButton.setOnClickListener {
                holder.binding.mainProductListAddCartButton.visibility = View.GONE
                showQuantityButton(holder)
            }
        }
    }

    private fun showQuantityButton(holder: ViewHolder) {
        holder.binding.mainProductListQuantityButton.productDetailProductCount.visibility =
            View.VISIBLE
        holder.binding.mainProductListQuantityButton.productDetailPlus.visibility = View.VISIBLE
        holder.binding.mainProductListQuantityButton.productDetailMinus.visibility =
            View.VISIBLE
        holder.binding.cartItem = CartItem(holder.binding.product!!.id, 1)
    }

    override fun getItemCount(): Int = values.size

    fun updateData(newData: List<Product>) {
        val start = this.values.size
        this.values = newData
        notifyItemRangeInserted(start, newData.size - start)
    }

    fun updateCartItem(cartItem: CartItem) {
        val index = values.indexOfFirst { it.id == cartItem.productId }
        if (index != -1) {
            notifyItemChanged(index)
        }
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
