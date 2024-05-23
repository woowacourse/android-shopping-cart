package woowacourse.shopping.ui.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.ui.products.ProductUiModel
import woowacourse.shopping.ui.utils.OnDecreaseProductQuantity
import woowacourse.shopping.ui.utils.OnIncreaseProductQuantity

class ProductsAdapter(
    private val onClickProductItem: OnClickProductItem,
    private val onIncreaseProductQuantity: OnIncreaseProductQuantity,
    private val onDecreaseProductQuantity: OnDecreaseProductQuantity,
) :
    RecyclerView.Adapter<ProductsViewHolder>() {
    private val productUiModels: MutableList<ProductUiModel> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return ProductsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProductsViewHolder,
        position: Int,
    ) {
        holder.bind(
            productUiModels[position],
            onClickProductItem,
            onIncreaseProductQuantity,
            onDecreaseProductQuantity,
        )
    }

    override fun getItemCount(): Int {
        return productUiModels.size
    }

    fun insertProducts(insertedProductUiModel: List<ProductUiModel>) {
        productUiModels.clear()
        productUiModels.addAll(insertedProductUiModel)
        notifyDataSetChanged()
//        val positionStart = insertedProductUiModel.size
//        val itemCount = insertedProductUiModel.size - productUiModels.size
//
//        productUiModels.addAll(insertedProductUiModel.subList(productUiModels.size, insertedProductUiModel.size))
//        notifyItemRangeChanged(positionStart, itemCount)
    }

    fun replaceProduct(replacedProductUiModel: ProductUiModel) {
        val replacedProductPosition = productUiModels.indexOfFirst { it.productId == replacedProductUiModel.productId }
        productUiModels[replacedProductPosition] = replacedProductUiModel
        notifyItemChanged(replacedProductPosition)
    }
}
