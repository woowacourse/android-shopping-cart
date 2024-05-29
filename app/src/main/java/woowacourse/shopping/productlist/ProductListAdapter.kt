package woowacourse.shopping.productlist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
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
            setQuantityButtonVisible(item.quantity)
        }

        fun onQuantityChanged(newQuantity: Int) {
            binding.productUiModel = binding.productUiModel?.copy(quantity = newQuantity)
            setQuantityButtonVisible(newQuantity)
        }

        private fun setQuantityButtonVisible(quantity: Int) {
            if (quantity > ProductUiModel.PRODUCT_DEFAULT_QUANTITY) {
                binding.buttonProductListAddToCart.visibility = View.GONE
                binding.buttonProductItemQuantity.visibility = View.VISIBLE
            } else if (quantity == ProductUiModel.PRODUCT_DEFAULT_QUANTITY) {
                binding.buttonProductListAddToCart.visibility = View.VISIBLE
                binding.buttonProductItemQuantity.visibility = View.GONE
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
        holder.onBind(items[position])
    }

    override fun onBindViewHolder(
        holder: ProductListViewHolder,
        position: Int,
        payloads: MutableList<Any>,
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            payloads.forEach { payload ->
                when (payload) {
                    ProductListPayload.QUANTITY_CHANGED -> {
                        holder.onQuantityChanged(items[position].quantity)
                    }

                    else -> {}
                }
            }
        }
    }

    fun submitList(products: List<ProductUiModel>) {
        val previousCount = itemCount
        items = products
        notifyItemRangeInserted(previousCount, products.size - previousCount)
    }

    fun updateItems(productIds: Set<Long>) {
        productIds.forEach { productId ->
            val updatedPosition = items.indexOfFirst { it.id == productId }
            notifyItemChanged(updatedPosition, ProductListPayload.QUANTITY_CHANGED)
        }
    }
}
