package woowacourse.shopping.presentation.product

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding

class ProductAdapter(private val onClickItem: (id: Long) -> Unit) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private var products: List<ProductUi> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductViewHolder {
        val binding =
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return ProductViewHolder(binding, onClickItem)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int,
    ) {
        holder.bind(products[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateProducts(newProducts: List<ProductUi>) {
        this.products = newProducts
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = products.size

    class ProductViewHolder(
        private val binding: ItemProductBinding,
        private val onClickItem: (id: Long) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductUi) {
            binding.product = product
            binding.root.setOnClickListener { onClickItem(product.id) }
        }
    }
}
