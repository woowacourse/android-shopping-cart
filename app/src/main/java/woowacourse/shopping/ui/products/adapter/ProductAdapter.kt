package woowacourse.shopping.ui.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.model.ProductWithQuantity

class ProductAdapter(
    private val itemClickListener: (Long) -> Unit,
    private val plusCountClickListener: (Long) -> Unit,
    private val minusCountClickListener: (Long) -> Unit,
) : RecyclerView.Adapter<ProductViewHolder>() {
    private val productWithQuantities: MutableList<ProductWithQuantity> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(
            binding,
            itemClickListener,
            plusCountClickListener,
            minusCountClickListener,
        )
    }

    override fun getItemCount(): Int = productWithQuantities.size

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int,
    ) {
        holder.bind(productWithQuantities[position])
    }

    fun setData(newProducts: List<ProductWithQuantity>) {
        if (isLoadMore(newProducts)) {
            val positionStart = productWithQuantities.size
            productWithQuantities.addAll(newProducts)
            notifyItemRangeInserted(positionStart, newProducts.size)
            return
        }
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

    private fun isLoadMore(newProducts: List<ProductWithQuantity>) = newProducts.none { product -> productWithQuantities.contains(product) }
}
