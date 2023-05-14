package woowacourse.shopping.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemReadMoreBinding
import woowacourse.shopping.databinding.ItemShoppingProductBinding
import woowacourse.shopping.databinding.RecentViewedLayoutBinding
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.model.RecentViewedProductUiModel

sealed class ShoppingRecyclerItemViewHolder private constructor(binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    class RecentViewedViewHolder(
        private val binding: RecentViewedLayoutBinding
    ) : ShoppingRecyclerItemViewHolder(binding) {

        fun bind(recentViewedProducts: List<RecentViewedProductUiModel>) {
            binding.recyclerViewRecentViewedProduct.adapter = RecentViewedRecyclerAdapter(
                products = recentViewedProducts
            )
        }
    }

    class ProductViewHolder(
        private val binding: ItemShoppingProductBinding
    ) : ShoppingRecyclerItemViewHolder(binding) {

        fun bind(productUiModel: ProductUiModel, onClicked: (ProductUiModel) -> Unit) {
            with(binding) {
                Glide.with(binding.root.context)
                    .load(productUiModel.imageUrl)
                    .into(imageProduct)

                textProductName.text = productUiModel.name
                textProductPrice.text = productUiModel.price.toString()
                root.setOnClickListener { onClicked(productUiModel) }
            }
        }
    }

    class ReadMoreViewHolder(
        private val binding: ItemReadMoreBinding
    ) : ShoppingRecyclerItemViewHolder(binding) {

        fun bind(
            readMoreDescription: String = binding.root.context.getString(R.string.read_more),
            onClicked: () -> Unit
        ) {
            binding.buttonReadMore.text = readMoreDescription
            binding.buttonReadMore.setOnClickListener {
                onClicked()
            }
        }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            viewType: ShoppingRecyclerItemViewType
        ): ShoppingRecyclerItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)

            return when (viewType) {
                ShoppingRecyclerItemViewType.RECENT_VIEWED -> RecentViewedViewHolder(
                    RecentViewedLayoutBinding.inflate(layoutInflater, parent, false)
                )

                ShoppingRecyclerItemViewType.PRODUCT -> ProductViewHolder(
                    ItemShoppingProductBinding.inflate(layoutInflater, parent, false)
                )

                ShoppingRecyclerItemViewType.READ_MORE -> ReadMoreViewHolder(
                    ItemReadMoreBinding.inflate(layoutInflater, parent, false)
                )
            }
        }
    }
}
