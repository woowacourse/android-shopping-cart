package woowacourse.shopping.ui.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.model.Product
import woowacourse.shopping.ui.utils.OnDecreaseProductQuantity
import woowacourse.shopping.ui.utils.OnIncreaseProductQuantity

class ProductsAdapter(
    private val onClickProductItem: OnClickProductItem,
    private val onIncreaseProductQuantity: OnIncreaseProductQuantity,
    private val onDecreaseProductQuantity: OnDecreaseProductQuantity,
) :
    RecyclerView.Adapter<ProductsViewHolder>() {
    private val products: MutableList<Product> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return ProductsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProductsViewHolder,
        position: Int,
    ) {
        holder.bind(
            products[position],
            onClickProductItem,
            onIncreaseProductQuantity,
            onDecreaseProductQuantity,
        )
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun insertProducts(insertedProducts: List<Product>) {
        val positionStart = insertedProducts.size
        val itemCount = insertedProducts.size - products.size

        products.addAll(insertedProducts.subList(products.size, insertedProducts.size))
        notifyItemRangeChanged(positionStart, itemCount)
    }

    fun replaceProduct(replacedProduct: Product) {
        val replacedProductPosition = products.indexOfFirst { it.id == replacedProduct.id }
        products[replacedProductPosition] = replacedProduct
        notifyItemChanged(replacedProductPosition)
    }
}
