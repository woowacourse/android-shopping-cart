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
    private val products: MutableList<ProductWithQuantity> = mutableListOf()

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

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int,
    ) {
        holder.bind(products[position])
    }

    fun setData(newProducts: List<ProductWithQuantity>) {
        if (newProducts.none { product -> products.contains(product) }) {
            val positionStart = products.size
            val itemCount = newProducts.size - products.size
            products.addAll(newProducts.subList(positionStart, newProducts.size))
            notifyItemRangeInserted(positionStart, itemCount)
            return
        }
        val uniqueNewProducts =
            newProducts.filter { newProduct ->
                !products.contains(newProduct)
            }
        products.clear()
        products.addAll(newProducts)
        uniqueNewProducts.forEach {
            notifyItemChanged(it.product.id.toInt())
        }
    }
}
