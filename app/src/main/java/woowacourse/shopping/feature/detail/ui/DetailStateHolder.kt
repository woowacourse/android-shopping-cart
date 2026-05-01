package woowacourse.shopping.feature.detail.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import woowacourse.shopping.core.uimodel.ProductUiModel
import woowacourse.shopping.feature.cart.model.AddItemResult
import woowacourse.shopping.feature.detail.bridge.DetailBridge

class DetailStateHolder(
    private val scope: CoroutineScope,
    private val id: String,
    private val detailBridge: DetailBridge = DetailBridge(),
) {
    var product by mutableStateOf(
        ProductUiModel(
            id = "",
            name = "",
            imageUrl = "",
            price = 0,
        ),
    )

    init {
        loadProduct()
    }

    fun loadProduct() {
        scope.launch {
            product = detailBridge.getProduct(id)
        }
    }

    fun addToCart(onResult: (AddItemResult) -> Unit) {
        scope.launch {
            onResult(detailBridge.addToCart(id))
        }
    }
}
