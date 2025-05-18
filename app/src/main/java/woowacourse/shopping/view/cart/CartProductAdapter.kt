package woowacourse.shopping.view.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.domain.Product

class CartProductAdapter(
    private val onDeleteClick: (Product, Int) -> Unit,
) : RecyclerView.Adapter<CartProductViewHolder>() {
    private var products: List<Product> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartProductViewHolder {
        val binding =
            ItemCartProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartProductViewHolder(binding, onDeleteClick)
    }

    override fun onBindViewHolder(
        holder: CartProductViewHolder,
        position: Int,
    ) {
        holder.bind(products[position], position)
    }

    override fun getItemCount(): Int = products.size

    fun removeProduct(position: Int) {
        this.products =
            this.products.toMutableList().apply {
                removeAt(position)
            }
        notifyItemRemoved(position)
    }

    fun setData(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }
}
