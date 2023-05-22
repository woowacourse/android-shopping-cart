package woowacourse.shopping.productcatalogue.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.ProductClickListener
import woowacourse.shopping.databinding.ItemProductCatalogueBinding
import woowacourse.shopping.productcatalogue.ProductCountClickListener
import woowacourse.shopping.uimodel.ProductUIModel

class MainProductCatalogueViewHolder(
    private val binding: ItemProductCatalogueBinding,
    productCountClickListener: ProductCountClickListener,
    productOnClick: ProductClickListener,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.listener = productOnClick
        binding.countClickListener = productCountClickListener
    }

    fun bind(product: ProductUIModel, count: Int) {
        binding.product = product
        binding.btCountDown.visibility = View.GONE
        binding.btCountUp.visibility = View.GONE
        binding.tvProductCount.visibility = View.GONE
        binding.tvProductCount.text = count.toString()
        binding.fabShow.visibility = View.VISIBLE
    }

    companion object {
        fun getView(parent: ViewGroup): ItemProductCatalogueBinding {
            val inflater = LayoutInflater.from(parent.context)
            return ItemProductCatalogueBinding.inflate(inflater, parent, false)
        }
    }
}
