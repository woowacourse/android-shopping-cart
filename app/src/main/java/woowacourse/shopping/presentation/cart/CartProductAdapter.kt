package woowacourse.shopping.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.domain.Product

class CartProductAdapter(
    private val onDeleteClick: (Product) -> Unit,
) : RecyclerView.Adapter<CartProductAdapter.CartProductViewHolder>() {
    private var products: List<Product> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartProductViewHolder {
        val binding =
            ItemCartProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return CartProductViewHolder(binding, onDeleteClick)
    }

    override fun onBindViewHolder(
        holder: CartProductViewHolder,
        position: Int,
    ) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    fun setData(newList: List<Product>) {
        products = newList
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
