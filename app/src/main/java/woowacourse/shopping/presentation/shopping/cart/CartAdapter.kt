package woowacourse.shopping.presentation.shopping.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.presentation.shopping.product.ProductUi

class CartAdapter(private val onClickItem: (position: Int) -> Unit) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private var products: List<ProductUi> = emptyList()

    override fun getItemCount(): Int = products.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return CartViewHolder(binding, onClickItem)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(products[position])
    }

    fun updateProduct(newProducts: List<ProductUi>) {
        products = newProducts
        notifyDataSetChanged()
    }

    class CartViewHolder(
        private val binding: ItemCartProductBinding,
        private val onClickItem: (position: Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductUi) {
            binding.product = product
            binding.ivShoopingCartClose.setOnClickListener {
                onClickItem(absoluteAdapterPosition)
            }
        }
    }
}
