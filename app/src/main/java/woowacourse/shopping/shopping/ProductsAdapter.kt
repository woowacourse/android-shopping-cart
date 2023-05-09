package woowacourse.shopping.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.databinding.ProductItemBinding

class ProductsAdapter(
    private val productUIModels: List<ProductUIModel>,
    private val onClickItem: (data: ProductUIModel) -> Unit
) : RecyclerView.Adapter<ProductsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding: ProductItemBinding =
            ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductsViewHolder(binding) { onClickItem(productUIModels[it]) }
    }

    override fun getItemCount(): Int = productUIModels.size
    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(productUIModels[position])
    }
}
