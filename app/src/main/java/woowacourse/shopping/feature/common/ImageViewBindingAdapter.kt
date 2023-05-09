package woowacourse.shopping.feature.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import woowacourse.shopping.model.ProductUiModel

@BindingAdapter("imgUrl")
fun setImgUrl(imageView: ImageView, productUiModel: ProductUiModel) {
    Glide.with(imageView.context)
        .load(productUiModel.imgUrl)
        .into(imageView)
}