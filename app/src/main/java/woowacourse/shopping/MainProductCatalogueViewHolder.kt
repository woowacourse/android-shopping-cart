package woowacourse.shopping

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.databinding.ItemProductCatalogueBinding

class MainProductCatalogueViewHolder(
    private val binding: ItemProductCatalogueBinding,
    private val mainProductCatalogueUIModel: MainProductCatalogueUIModel,
    private val productOnClick: (ProductUIModel) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            productOnClick(mainProductCatalogueUIModel.items[adapterPosition - 1])
        }
    }

    fun bind(product: ProductUIModel) {
        Glide.with(binding.root.context)
            .load(product.url)
            .into(binding.ivProductImage)
        binding.tvProductName.text = product.name
        binding.tvProductPrice.text = product.price.toString()
    }
}
