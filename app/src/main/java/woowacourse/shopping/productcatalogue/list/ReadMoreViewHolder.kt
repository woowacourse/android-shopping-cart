package woowacourse.shopping.productcatalogue.list

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductReadMoreBinding
import woowacourse.shopping.datas.ProductDataRepository
import woowacourse.shopping.datas.ProductRepository

class ReadMoreViewHolder(
    binding: ItemProductReadMoreBinding,
    readMoreOnClick: (ProductRepository, Int, Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btReadMore.setOnClickListener {
            readMoreOnClick(
                ProductDataRepository,
                PRODUCT_UNIT_SIZE,
                ProductDataRepository.productCataloguePageNumber
            )
        }
    }

    companion object {
        private const val PRODUCT_UNIT_SIZE = 20
    }
}
