package woowacourse.shopping.view.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.databinding.ItemRecentProductListBinding
import woowacourse.shopping.view.uimodel.ProductUiModel

sealed class MainViewHolder(root: View) : RecyclerView.ViewHolder(root) {
    class ProductsViewHolder(
        private val parent: ViewGroup,
        private val handler: ProductEventHandler,
        private val binding: ItemProductBinding = inflate(parent),
    ) : MainViewHolder(binding.root) {
        init {
            binding.handler = handler
        }

        fun bind(
            item: ProductUiModel,
            quantityLiveData: MutableLiveData<Int>,
        ) {
            binding.product = item
            binding.quantity = quantityLiveData
        }

        companion object {
            fun inflate(parent: ViewGroup): ItemProductBinding {
                return ItemProductBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
            }
        }
    }

    class RecentProductsViewHolder(
        private val adapter: RecentProductsAdapter,
        private val parent: ViewGroup,
        binding: ItemRecentProductListBinding = inflate(parent),
    ) : MainViewHolder(binding.root) {
        init {
            binding.recentProductList.adapter = adapter
        }
    }

    companion object {
        fun inflate(parent: ViewGroup): ItemRecentProductListBinding {
            return ItemRecentProductListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        }
    }
}
