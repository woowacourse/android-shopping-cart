package woowacourse.shopping.productcatalogue.list

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.databinding.ItemProductCatalogueBinding
import woowacourse.shopping.datas.ProductDataRepository
import woowacourse.shopping.uimodel.ProductUIModel

class MainProductCatalogueViewHolder(
    private val binding: ItemProductCatalogueBinding,
    private val productOnClick: (ProductUIModel) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            productOnClick(ProductDataRepository.products[adapterPosition - 1])
        }
    }

    fun bind(product: ProductUIModel) {
        Glide.with(binding.root.context)
            .load(product.imageUrl)
            .into(binding.ivProductImage)
        binding.tvProductName.text = product.name
        binding.tvProductPrice.text = product.price.toString()
    }
}
