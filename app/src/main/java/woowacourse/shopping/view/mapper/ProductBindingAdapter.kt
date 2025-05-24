package woowacourse.shopping.view.mapper

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import woowacourse.shopping.R
import woowacourse.shopping.view.detail.ProductDetailViewModel
import woowacourse.shopping.view.main.adapter.ProductEventHandler
import woowacourse.shopping.view.uimodel.ProductUiModel

@BindingAdapter("android:price")
fun setPrice(
    view: TextView,
    price: Int,
) {
    view.text = view.context.getString(R.string.template_price, price)
}

@BindingAdapter("shoppingCart:image")
fun setImage(
    view: ImageView,
    url: String,
) {
    Glide.with(view.context)
        .load(url)
        .placeholder(R.drawable.placeholder_product)
        .into(view)
}

@BindingAdapter(
    "shoppingCart:observe",
    "shoppingCart:observe_action",
    "shoppingCart:observe_argument_item",
    requireAll = true,
)
fun setMutableVisibility(
    view: ViewGroup,
    quantity: MutableLiveData<Int>,
    handler: ProductEventHandler,
    item: ProductUiModel,
) {
    view.findViewTreeLifecycleOwner()?.let {
        quantity.removeObservers(it)
        quantity.observe(it) {
            handler.whenQuantityChangedSelectView(view, item)
        }
    }
}

@BindingAdapter("shoppingCart:quantity")
fun setQuantity(
    view: TextView,
    quantity: MutableLiveData<Int>,
) {
    view.findViewTreeLifecycleOwner()?.let {
        quantity.observe(it) {
            view.text = quantity.value.toString()
        }
    }
}

@BindingAdapter("shoppingCart:allLoaded")
fun setWhenAllLoaded(
    view: TextView,
    viewModel: ProductDetailViewModel,
) {
    view.findViewTreeLifecycleOwner()?.let {
        viewModel.allLoaded.observe(it) {
            view.text = viewModel.lastViewedProductName()
        }
    }
}

@BindingAdapter("shoppingCart:allLoaded")
fun setWhenAllLoaded(
    view: MaterialCardView,
    viewModel: ProductDetailViewModel,
) {
    view.findViewTreeLifecycleOwner()?.let {
        viewModel.allLoaded.observe(it) {
            view.visibility = viewModel.isInRecentProducts()
        }
    }
}
