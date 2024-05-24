package woowacourse.shopping.productList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderProductBinding
import woowacourse.shopping.model.Product

class RecentlyViewedAdapter(
    private var products: List<Product> = emptyList(),
    private val onClick: (id: Int) -> Unit,
) : RecyclerView.Adapter<RecentlyViewedAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return ViewHolder(
            HolderProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClick = { id -> onClick(id) }
        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = products.size

    fun updateData(newProducts: List<Product>) {
        this.products = newProducts
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        private val binding: HolderProductBinding,
        private val onClick: (id: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.product = product
            binding.root.setOnClickListener { onClick(product.id) }
        }
    }
}
