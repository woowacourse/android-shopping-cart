package woowacourse.shopping.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.databinding.ItemProductListBinding

class ProductAdapter(
    private var products: List<ProductModel>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemProductListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ProductViewHolder).bind(products[position])
    }

    fun updateProducts(products: List<ProductModel>) {
        this.products = products
        notifyDataSetChanged()
    }
}
