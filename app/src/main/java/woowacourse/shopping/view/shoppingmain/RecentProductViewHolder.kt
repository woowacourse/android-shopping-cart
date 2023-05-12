package woowacourse.shopping.view.shoppingmain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductRecentBinding
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.uimodel.RecentProductUIModel

class RecentProductViewHolder(
    parent: ViewGroup,
    productOnClick: (ProductUIModel) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_product_recent, parent, false)
) {
    private val binding = ItemProductRecentBinding.bind(itemView)
    private lateinit var recentProduct: RecentProductUIModel

    init {
        binding.root.setOnClickListener {
            productOnClick(recentProduct.productUIModel)
        }
    }

    fun bind(item: ProductUIModel) {
        recentProduct = RecentProductUIModel(item)

        binding.product = recentProduct.productUIModel
        Glide.with(binding.root.context)
            .load(recentProduct.productUIModel.url)
            .into(binding.ivProductImage)
    }
}
