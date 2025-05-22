package woowacourse.shopping.view.mapper

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.view.main.adapter.ProductEventHandler
import woowacourse.shopping.view.shoppingcart.ShoppingCartEventHandler

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

@BindingAdapter("shoppingCart:observe", "shoppingCart:observe_action", requireAll = true)
fun setMutableVisibility(
    view: ViewGroup,
    quantity: MutableLiveData<Int>,
    handler: ProductEventHandler,
) {
    view.findViewTreeLifecycleOwner()?.let {
        quantity.removeObservers(it)
        quantity.observe(it) {
            handler.whenQuantityChangedSelectView(quantity)
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

@BindingAdapter(
    "shoppingCart:observe",
    "shoppingCart:observe_action",
    "shoppingCart:observe_argument_item",
    "shoppingCart:observe_argument_page",
    requireAll = true,
)
fun setObservable(
    view: ViewGroup,
    quantity: MutableLiveData<Int>,
    action: ShoppingCartEventHandler,
    item: ShoppingCartItem,
    page: Int,
) {
    view.findViewTreeLifecycleOwner()?.let {
        quantity.removeObservers(it)
        quantity.observe(it) {
            action.whenQuantityChangedSelectView(
                quantity,
                item,
                page,
            )
        }
    }
}
