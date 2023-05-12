package woowacourse.shopping.view.productcatalogue

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductCatalogueBinding
import woowacourse.shopping.uimodel.ProductUIModel

class MainProductCatalogueViewHolder(
    private val binding: ItemProductCatalogueBinding,
    private val products: List<ProductUIModel>,
    private val productOnClick: (ProductUIModel) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            productOnClick(products[adapterPosition - 1])
        }
    }

    fun bind(item: ProductUIModel) {
        binding.product = item
    }
}
