package woowacourse.shopping.presentation.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.domain.Product

class CartProductAdapter(
    private val context: Context,
    private val onDeleteClick: (Product) -> Unit,
) : RecyclerView.Adapter<CartProductAdapter.CartProductViewHolder>() {
    private var products: List<Product> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartProductViewHolder {
        val view =
            ItemCartProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartProductViewHolder(view, onDeleteClick)
    }

    override fun onBindViewHolder(
        holder: CartProductViewHolder,
        position: Int,
    ) {
        val cartProduct = products[position]
        CartProductViewHolder(holder.binding, onDeleteClick).bind(cartProduct)
    }

    override fun getItemCount(): Int = products.size

    fun setData(list: List<Product>) {
        products = list
        notifyDataSetChanged()
    }

    class CartProductViewHolder(
        val binding: ItemCartProductBinding,
        private val onDeleteClick: (Product) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var product: Product

        init {
            binding.ibCartProductDelete.setOnClickListener {
                onDeleteClick(product)
            }
        }

        fun bind(product: Product) {
            binding.product = product
            this.product = product
        }
    }
}
