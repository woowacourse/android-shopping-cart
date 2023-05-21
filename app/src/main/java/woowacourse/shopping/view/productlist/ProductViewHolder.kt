package woowacourse.shopping.view.productlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.data.CartDbRepository
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.databinding.ItemRecentViewedBinding
import woowacourse.shopping.databinding.ItemShowMoreBinding
import woowacourse.shopping.model.ProductModel
import woowacourse.shopping.view.productlist.recentviewed.RecentViewedAdapter

sealed class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    class RecentViewedViewHolder(private val binding: ItemRecentViewedBinding) :
        ProductViewHolder(binding.root) {
        fun bind(
            recentViewedProducts: List<ProductModel>,
            onProductClick: (ProductModel) -> Unit,
        ) {
            binding.recyclerRecentViewed.adapter =
                RecentViewedAdapter(recentViewedProducts, onProductClick)
        }
    }

    class ProductItemViewHolder(
        private val binding: ItemProductBinding,
        onProductClick: (ProductModel) -> Unit,
    ) : ProductViewHolder(binding.root) {
        init {
            binding.onItemClick = onProductClick
        }

        fun bind(product: ProductModel, cartRepository: CartDbRepository) {
            binding.product = product
            binding.count.count = product.count
            binding.countOpen.setOnClickListener { cartRepository.add(product.id, 1, true) }
            binding.count.plusClickListener = { cartRepository.plusCount(product.id) }
            binding.count.minusClickListener = {
                if (product.count <= 1) {
                    cartRepository.remove(product.id)
                } else {
                    cartRepository.subCount(product.id)
                }
            }

            if (product.count <= 0) {
                binding.countOpen.visibility = View.VISIBLE
                binding.count.visibility = View.GONE
            } else {
                binding.countOpen.visibility = View.GONE
                binding.count.visibility = View.VISIBLE
            }
        }
    }

    class ShowMoreViewHolder(
        binding: ItemShowMoreBinding,
        onShowMoreClick: () -> Unit,
    ) : ProductViewHolder(binding.root) {
        init {
            binding.onItemClick = onShowMoreClick
        }
    }

    companion object {
        fun of(
            parent: ViewGroup,
            type: ProductListViewType,
            onProductClick: (ProductModel) -> Unit,
            onShowMoreClick: () -> Unit,
        ): ProductViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(type.id, parent, false)
            return when (type) {
                ProductListViewType.RECENT_VIEWED_ITEM -> RecentViewedViewHolder(
                    ItemRecentViewedBinding.bind(view),
                )

                ProductListViewType.PRODUCT_ITEM -> ProductItemViewHolder(
                    ItemProductBinding.bind(
                        view,
                    ),
                    onProductClick,
                )

                ProductListViewType.SHOW_MORE_ITEM -> ShowMoreViewHolder(
                    ItemShowMoreBinding.bind(
                        view,
                    ),
                    onShowMoreClick,
                )
            }
        }
    }
}
