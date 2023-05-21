package woowacourse.shopping.ui.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.ui.products.uistate.ProductUIState
import woowacourse.shopping.utils.PRICE_FORMAT

class ProductListAdapter(
    private val products: MutableList<ProductUIState>,
    private val onClick: (Long) -> Unit,
    private val onClickAddToCartButton: (Long) -> Unit
) : RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        return ProductListViewHolder.create(parent, onClick, onClickAddToCartButton)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.bind(products[position])
    }

    fun addItems(newProducts: List<ProductUIState>) {
        products.addAll(newProducts)
        notifyDataSetChanged()
    }

    fun replaceItem(newProduct: ProductUIState) {
        val index = products.indexOfFirst { newProduct.id == it.id }
        if (index == -1) return
        products[index] = newProduct
        notifyItemChanged(index)
    }

    class ProductListViewHolder private constructor(
        private val binding: ItemProductBinding,
        private val onClick: (Long) -> Unit,
        private val onClickAddToCartButton: (Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductUIState) {
            binding.product = product
            binding.tvProductPrice.text = itemView.context.getString(R.string.product_price)
                .format(PRICE_FORMAT.format(product.price))
            Glide.with(itemView)
                .load(product.imageUrl)
                .into(binding.ivProduct)
            binding.root.setOnClickListener { onClick(product.id) }
            binding.btnAddToCart.isVisible = product.count == null
            binding.btnAddToCart.setOnClickListener {
                onClickAddToCartButton(product.id)
            }
        }

        companion object {
            fun create(
                parent: ViewGroup,
                onClick: (Long) -> Unit,
                onClickAddToCartButton: (Long) -> Unit
            ): ProductListViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_product, parent, false)
                val binding = ItemProductBinding.bind(view)
                return ProductListViewHolder(binding, onClick, onClickAddToCartButton)
            }
        }
    }
}
