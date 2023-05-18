package woowacourse.shopping.productcatalogue.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductReadMoreBinding
import woowacourse.shopping.datas.ProductDataRepository

class ReadMoreViewHolder(
    binding: ItemProductReadMoreBinding,
    readMoreOnClick: (Int, Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btReadMore.setOnClickListener {
            readMoreOnClick(
                PRODUCT_UNIT_SIZE,
                ProductDataRepository.productCataloguePageNumber
            )
        }
    }

    companion object {
        private const val PRODUCT_UNIT_SIZE = 20

        fun getView(parent: ViewGroup): ItemProductReadMoreBinding {
            val inflater = LayoutInflater.from(parent.context)
            return ItemProductReadMoreBinding.inflate(inflater, parent, false)
        }
    }
}
