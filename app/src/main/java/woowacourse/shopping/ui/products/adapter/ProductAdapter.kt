package woowacourse.shopping.ui.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.model.ProductWithQuantity
import woowacourse.shopping.ui.products.ProductItemClickListener
import woowacourse.shopping.ui.products.viewmodel.ProductContentsViewModel

class ProductAdapter(
    private val productItemClickListener: ProductItemClickListener,
    private val viewModel: ProductContentsViewModel,
) : RecyclerView.Adapter<ProductViewHolder>() {
    private val productWithQuantities: MutableList<ProductWithQuantity> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(
            binding,
            productItemClickListener,
            viewModel,
        )
    }

    override fun getItemCount(): Int = productWithQuantities.size

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int,
    ) {
        holder.bind(productWithQuantities[position])
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun setData(newProducts: List<ProductWithQuantity>) {
        if (setProductsAtLoadMore(newProducts)) return
        setProductsAtQuantityChanged(newProducts)
    }

    private fun setProductsAtQuantityChanged(newProducts: List<ProductWithQuantity>) {
        val uniqueNewProducts =
            newProducts.filter { newProduct ->
                !productWithQuantities.contains(newProduct)
            }

        productWithQuantities.clear()
        productWithQuantities.addAll(newProducts)
        uniqueNewProducts.forEach {
            notifyItemChanged(it.product.id.toInt())
        }
    }

    private fun setProductsAtLoadMore(newProducts: List<ProductWithQuantity>): Boolean {
        if (isLoadMore(newProducts)) {
            val positionStart = productWithQuantities.size
            val itemCount = newProducts.size - productWithQuantities.size
            productWithQuantities.addAll(newProducts.subList(positionStart, newProducts.size))
            notifyItemRangeInserted(positionStart, itemCount)
            return true
        }
        return false
    }

    private fun isLoadMore(newProducts: List<ProductWithQuantity>) = newProducts.none { product -> productWithQuantities.contains(product) }
}
