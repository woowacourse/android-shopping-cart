package woowacourse.shopping.productlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductListBinding
import woowacourse.shopping.util.imageUrlToSrc

class ProductListAdapter(
    private val onClick: ProductListClickAction,
) : RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>() {
    private val items: MutableList<ProductUiModel> = mutableListOf()

    class ProductListViewHolder(
        private val binding: ItemProductListBinding,
        private val onClickHandler: ProductListClickAction,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: ProductUiModel) {
            with(binding) {
                tvProductListName.text = item.name
                tvProductListPrice.text =
                    itemView.context.getString(R.string.product_price_format, item.price)
                itemView.context.imageUrlToSrc(item.imageUrl, ivProductItem)
                binding.product = item
                binding.onClick = onClickHandler
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductListViewHolder {
        val binding =
            ItemProductListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductListViewHolder(binding, onClick)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: ProductListViewHolder,
        position: Int,
    ) {
        return holder.onBind(items[position])
    }

    fun submitItems(products: List<ProductUiModel>) {
        val previous = items
        items.clear()
        items.addAll(products)
        notifyItemRangeInserted(previous.size, products.size)
    }

    fun changeProductsInfo(products: List<ProductUiModel>) {
        products.forEach { changeProductsInfo(it) }
    }

    private fun changeProductsInfo(product: ProductUiModel) {
        val changeIndex = items.indexOfFirst { it.id == product.id }
        items[changeIndex] = product
        notifyItemChanged(changeIndex)
    }
}
