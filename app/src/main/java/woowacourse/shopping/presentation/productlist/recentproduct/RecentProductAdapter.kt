package woowacourse.shopping.presentation.productlist.recentproduct

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.presentation.model.ProductModel

class RecentProductAdapter(
    recentProducts: List<ProductModel>,
    private val showProductDetail: (ProductModel) -> Unit,
) :
    RecyclerView.Adapter<RecentProductItemViewHolder>() {

    private lateinit var binding: ItemRecentProductBinding

    private val _recentProducts = recentProducts.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentProductItemViewHolder {
        binding =
            ItemRecentProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentProductItemViewHolder(binding, showProductDetail)
    }

    override fun getItemCount(): Int = _recentProducts.size

    override fun onBindViewHolder(holder: RecentProductItemViewHolder, position: Int) {
        holder.bind(_recentProducts[position])
    }
}
