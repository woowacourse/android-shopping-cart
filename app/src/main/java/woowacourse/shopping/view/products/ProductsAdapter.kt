package woowacourse.shopping.view.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.model.Product

class ProductsAdapter(
    private val products: List<Product>,
) : RecyclerView.Adapter<ProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val productBinding =
            ItemProductBinding.inflate(
                inflater,
                parent,
                false,
            )

        return ProductViewHolder(productBinding)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int,
    ) {
        holder.bind(products[position])
    }
}
