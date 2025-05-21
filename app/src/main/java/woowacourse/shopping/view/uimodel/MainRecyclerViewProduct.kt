package woowacourse.shopping.view.uimodel

import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.data.page.Page
import woowacourse.shopping.domain.Product

data class MainRecyclerViewProduct(
    val page: Page<Product>,
    val quantityMap: MutableMap<Product, MutableLiveData<Int>>,
)
