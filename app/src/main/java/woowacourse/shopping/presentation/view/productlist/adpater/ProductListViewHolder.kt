package woowacourse.shopping.presentation.view.productlist.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductListBinding
import woowacourse.shopping.presentation.model.ProductModel

class ProductListViewHolder(
    view: View,
    private val onClick: (Int) -> Unit
) : RecyclerView.ViewHolder(view) {
    private val binding = ItemProductListBinding.bind(view)

    constructor(view: ViewGroup, onClick: (Int) -> Unit) : this(
        LayoutInflater.from((view.context)).inflate(
            R.layout.item_product_list, view, false
        ),
        onClick
    )

    init {
        binding.root.setOnClickListener {
            onClick(absoluteAdapterPosition)
        }
    }

    fun bind(product: ProductModel) {
        Glide.with(binding.root).load(product.imageUrl).into(binding.ivProductThumbnail)
        binding.tvProductTitle.text = product.title
        binding.tvProductPrice.text = product.price.toString()
    }
}
