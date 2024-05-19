package woowacourse.shopping.feature.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.model.Product

class ProductsAdapter(private val onClickProductItem: OnClickProductItem) :
    RecyclerView.Adapter<ProductsViewHolder>() {
    private var products: List<Product> = emptyList()

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
        holder.bind(onClickProductItem, products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun insertProducts(insertedProducts: List<Product>) {
        val positionStart = insertedProducts.size
        val itemCount = insertedProducts.size - products.size

        products = insertedProducts
        notifyItemRangeChanged(positionStart, itemCount)
    }
}
