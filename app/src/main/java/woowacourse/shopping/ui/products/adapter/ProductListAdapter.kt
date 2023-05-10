package woowacourse.shopping.ui.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.ui.products.ProductUIState
import java.text.DecimalFormat

class ProductListAdapter(
    private val products: List<ProductUIState>,
    private val onClick: (Int) -> Unit,
) : RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_product,
            parent,
            false,
        )

        return ProductListViewHolder(ItemProductBinding.bind(view), onClick)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.bind(products[position])
    }

    class ProductListViewHolder(
        private val binding: ItemProductBinding,
        private val onClick: (Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { onClick(adapterPosition) }
        }

        fun bind(product: ProductUIState) {
            binding.product = product
            binding.tvProductPrice.text = itemView.context.getString(R.string.product_price)
                .format(PRICE_FORMATTER.format(product.price))
            Glide.with(itemView)
                .load(product.imageUrl)
                .into(binding.ivProduct)
        }

        companion object {
            private val PRICE_FORMATTER = DecimalFormat("#,###")
        }
    }
}
