package woowacourse.shopping.feature.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.model.Product

class ProductAdapter(private val onClickProductItem: OnClickProductItem) :
    RecyclerView.Adapter<ProductViewHolder>() {
    private var products: List<Product> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int,
    ) {
        holder.bind(onClickProductItem, products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun updateProducts(insertedProducts: List<Product>) {
        val positionStart = insertedProducts.size
        val itemCount = insertedProducts.size - products.size

        products = insertedProducts
        notifyItemRangeChanged(positionStart, itemCount)
    }
}
