package woowacourse.shopping.productlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductListBinding
import woowacourse.shopping.uimodel.ProductUiModel

class ProductListAdapter(
    private val onClick: ProductListClickAction,
) : RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>() {
    private var items: List<ProductUiModel> = emptyList()

    class ProductListViewHolder(
        private val binding: ItemProductListBinding,
        private val onClick: ProductListClickAction,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: ProductUiModel) {
            binding.productUiModel = item
            binding.clickListener = onClick
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

    fun submitList(products: List<ProductUiModel>) {
        val previousCount = itemCount
        items = products
        notifyItemRangeInserted(previousCount, products.size - previousCount)
    }
}
