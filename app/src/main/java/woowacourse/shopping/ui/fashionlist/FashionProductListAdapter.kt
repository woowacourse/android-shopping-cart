package woowacourse.shopping.ui.fashionlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.LoadMoreItemBinding
import woowacourse.shopping.databinding.ProductItemBinding

class FashionProductListAdapter(
    private var items: List<ProductListViewType>,
    private val productClickListener: ProductClickListener,
    private val loadMoreClickListener: LoadMoreClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ProductListViewType.FashionProductItemType -> R.layout.product_item
            is ProductListViewType.LoadMoreType -> R.layout.load_more_item
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.product_item -> {
                val binding: ProductItemBinding =
                    DataBindingUtil.inflate(inflater, R.layout.product_item, parent, false)
                FashionProductItemViewHolder(binding, productClickListener)
            }

            R.layout.load_more_item -> {
                val binding: LoadMoreItemBinding =
                    DataBindingUtil.inflate(inflater, R.layout.load_more_item, parent, false)
                LoadMoreViewHolder(binding, loadMoreClickListener)
            }

            else -> throw IllegalArgumentException("지원하지 않는 타입입니다.")
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is FashionProductItemViewHolder -> holder.bind(items[position] as ProductListViewType.FashionProductItemType)
        }
    }

    override fun getItemCount() = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun update(it: List<ProductListViewType>?) {
        items = it.orEmpty()
        notifyDataSetChanged()
    }
}
