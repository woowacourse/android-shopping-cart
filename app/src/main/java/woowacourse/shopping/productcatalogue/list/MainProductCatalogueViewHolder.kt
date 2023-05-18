package woowacourse.shopping.productcatalogue.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.ProductClickListener
import woowacourse.shopping.databinding.ItemProductCatalogueBinding
import woowacourse.shopping.uimodel.ProductUIModel

class MainProductCatalogueViewHolder(
    private val binding: ItemProductCatalogueBinding,
    productOnClick: ProductClickListener,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.listener = productOnClick
    }

    fun bind(product: ProductUIModel) {
        binding.product = product
    }

    companion object {
        fun getView(parent: ViewGroup): ItemProductCatalogueBinding {
            val inflater = LayoutInflater.from(parent.context)
            return ItemProductCatalogueBinding.inflate(inflater, parent, false)
        }
    }
}
