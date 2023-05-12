package woowacourse.shopping.presentation.productlist.product

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.presentation.model.ProductModel

abstract class ProductBaseViewHolder(
    val view: View,
    showProductDetail: (ProductModel) -> Unit,
) : RecyclerView.ViewHolder(view) {
    protected var _productModel: ProductModel? = null
    protected val productModel get() = _productModel!!

    init {
        itemView.setOnClickListener { showProductDetail(productModel) }
    }

    abstract fun bind(productModel: ProductModel)

    protected fun setImage(recentProduct: ProductModel, imageView: ImageView) {
        Glide.with(imageView.context)
            .load(recentProduct.imageUrl)
            .error(R.drawable.default_image)
            .centerCrop()
            .into(imageView)
    }
}
