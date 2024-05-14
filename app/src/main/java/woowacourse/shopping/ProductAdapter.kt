package woowacourse.shopping

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding

class ProductAdapter(private val onClickProductItem: OnClickProductItem) : RecyclerView.Adapter<ProductViewHolder>() {
    private var products: List<Product> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int,
    ) {
        holder.bind(onClickProductItem, products[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateProducts(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }
}
