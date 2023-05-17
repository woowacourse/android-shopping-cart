package woowacourse.shopping.productcatalogue.list

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductCatalogueBinding
import woowacourse.shopping.datas.ProductDataRepository
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.uimodel.ProductUIModel

class MainProductCatalogueViewHolder(
    private val binding: ItemProductCatalogueBinding,
    private val productOnClick: (ProductUIModel) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            productOnClick(ProductDataRepository.products[adapterPosition - 1].toUIModel())
        }
    }

    fun bind(product: ProductUIModel) {
        binding.product = product
    }
}
