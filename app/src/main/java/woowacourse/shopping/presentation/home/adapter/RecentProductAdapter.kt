package woowacourse.shopping.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.data.model.Product
import woowacourse.shopping.databinding.ItemRecentProductBinding

class RecentProductAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var products: List<Product> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemRecentProductBinding =
            ItemRecentProductBinding.inflate(layoutInflater, parent, false)
        return RecentProductViewHolder(binding)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is RecentProductViewHolder -> {
                holder.bind(products[position])
            }

            else -> throw IllegalArgumentException(EXCEPTION_ILLEGAL_VIEW_TYPE)
        }
    }

    fun setProducts(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

    companion object {
        private const val EXCEPTION_ILLEGAL_VIEW_TYPE = "유효하지 않은 뷰 타입입니다."
    }
}
