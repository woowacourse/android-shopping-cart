package woowacourse.shopping.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.data.model.CartableProduct
import woowacourse.shopping.data.model.RecentProduct
import woowacourse.shopping.databinding.ItemLoadMoreBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.databinding.ItemProductHistoryBinding
import woowacourse.shopping.databinding.ItemProductHistoryListBinding
import woowacourse.shopping.presentation.BindableAdapter
import java.lang.IllegalArgumentException

class ProductAdapter(
    private val homeItemClickListener: HomeItemEventListener,
    private val quantityListener: QuantityListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), BindableAdapter<CartableProduct> {
    private var products: List<CartableProduct> = emptyList()
    private var loadStatus: LoadStatus = LoadStatus()
//    private var homeItems: List<CartableProduct> = emptyList()
    private var historyProducts: List<RecentProduct> = emptyList()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.itemAnimator = null
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_HISTORY -> {
                val binding: ItemProductHistoryListBinding =
                    DataBindingUtil.inflate(layoutInflater, R.layout.item_product_history_list, parent, false)
                HistoryViewHolder(binding, historyProducts, homeItemClickListener)
            }

            TYPE_PRODUCT -> {
                val binding: ItemProductBinding =
                    DataBindingUtil.inflate(layoutInflater, R.layout.item_product, parent, false)
                ProductViewHolder(binding, homeItemClickListener, quantityListener)
            }

            TYPE_LOAD -> {
                val binding: ItemLoadMoreBinding =
                    DataBindingUtil.inflate(layoutInflater, R.layout.item_load_more, parent, false)
                LoadingViewHolder(binding, homeItemClickListener)
            }

            else -> throw IllegalArgumentException(EXCEPTION_ILLEGAL_VIEW_TYPE)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is HistoryViewHolder -> Unit
            is ProductViewHolder -> holder.bind(products[position - 1])
            is LoadingViewHolder -> holder.bind(loadStatus)
            else -> throw IllegalArgumentException(EXCEPTION_ILLEGAL_VIEW_TYPE)
        }
    }

    override fun getItemCount(): Int = products.size + 2

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            TYPE_HISTORY
        } else if (position - 1 < products.size) {
            TYPE_PRODUCT
        } else {
            TYPE_LOAD
        }
    }

    override fun setData(data: List<CartableProduct>) {
//        val previousSize = products.size
        products = data
        notifyDataSetChanged()
//        notifyItemRangeInserted(previousSize, data.size - previousSize)
    }

    fun updateLoadStatus(loadStatus: LoadStatus) {
        this.loadStatus = loadStatus
    }

    class ProductViewHolder(
        private val binding: ItemProductBinding,
        homeItemClickListener: HomeItemEventListener,
        quantityListener: QuantityListener,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.homeItemClickListener = homeItemClickListener
            binding.quantityListener = quantityListener
        }

        fun bind(product: CartableProduct) {
            binding.cartableProduct = product
        }
    }

    class LoadingViewHolder(
        private val binding: ItemLoadMoreBinding,
        homeItemClickListener: HomeItemEventListener,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.homeItemClickListener = homeItemClickListener
        }

        fun bind(loadStatus: LoadStatus) {
            binding.loadStatus = loadStatus
        }
    }

    class HistoryViewHolder(
        binding: ItemProductHistoryListBinding,
        historyProducts: List<RecentProduct>,
        homeItemClickListener: HomeItemEventListener,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.adapter = HistoryAdapter(historyProducts, homeItemClickListener)
            binding.executePendingBindings()
        }
    }

    companion object {
        const val TYPE_PRODUCT = 1000
        const val TYPE_LOAD = 1001
        const val TYPE_HISTORY = 1002
        private const val EXCEPTION_ILLEGAL_VIEW_TYPE = "유효하지 않은 뷰 타입입니다."
    }
}

class HistoryAdapter(
    private val historyItems: List<RecentProduct>,
    private val homeItemClickListener: HomeItemEventListener,
) : RecyclerView.Adapter<HistoryAdapter.HistoryItemViewHolder>() {
//    private var historyItems: List<RecentProduct> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): HistoryItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemProductHistoryBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_product_history, parent, false)
        return HistoryItemViewHolder(binding, homeItemClickListener)
    }

    override fun getItemCount(): Int = historyItems.size

    override fun onBindViewHolder(holder: HistoryItemViewHolder, position: Int) {
        holder.bind(historyItems[position])
    }

    class HistoryItemViewHolder(
        private val binding: ItemProductHistoryBinding,
        homeItemClickListener: HomeItemEventListener,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.homeItemClickListener = homeItemClickListener
        }
        fun bind(historyItem: RecentProduct) {
            binding.recentProduct = historyItem
        }
    }
}

@BindingAdapter("productThumbnail")
fun ImageView.setProductThumbnail(thumbnailUrl: String?) {
    Glide.with(context)
        .load(thumbnailUrl)
        .into(this)
}

@BindingAdapter("loadStatus")
fun Button.isLoadingButtonVisible(loadStatus: LoadStatus?) {
    if (loadStatus == null) return
    visibility =
        if (loadStatus.loadingAvailable && !loadStatus.isLoadingPage) View.VISIBLE else View.GONE
}
