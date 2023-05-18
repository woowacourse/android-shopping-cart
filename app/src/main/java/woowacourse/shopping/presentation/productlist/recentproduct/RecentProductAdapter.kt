package woowacourse.shopping.presentation.productlist.recentproduct

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.model.ProductModel

class RecentProductAdapter(
    recentProducts: List<ProductModel>,
    private val showProductDetail: (ProductModel) -> Unit,
) : RecyclerView.Adapter<RecentProductItemViewHolder>() {

    private lateinit var layoutInflater: LayoutInflater

    private val _recentProducts = recentProducts.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentProductItemViewHolder {
        initLayoutInflater(parent)
        return RecentProductItemViewHolder(parent, layoutInflater, ::onItemClick)
    }

    private fun initLayoutInflater(parent: ViewGroup) {
        if (!::layoutInflater.isInitialized) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
    }

    override fun getItemCount(): Int = _recentProducts.size

    override fun onBindViewHolder(holder: RecentProductItemViewHolder, position: Int) {
        holder.bind(_recentProducts[position])
    }

    private fun onItemClick(position: Int) {
        showProductDetail(_recentProducts[position])
    }
}
