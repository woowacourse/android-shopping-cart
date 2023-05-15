package woowacourse.shopping.presentation.productlist.recentproduct

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.util.ProductDiffUtil

class RecentProductAdapter(
    private val showProductDetail: (ProductModel) -> Unit,
) : ListAdapter<ProductModel, RecentProductItemViewHolder>(ProductDiffUtil.itemCallBack()) {

    private lateinit var binding: ItemRecentProductBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentProductItemViewHolder {
        binding =
            ItemRecentProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentProductItemViewHolder(binding, showProductDetail)
    }
    override fun onBindViewHolder(holder: RecentProductItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
