package woowacourse.shopping.feature.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.domain.model.Goods

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    val cart: LiveData<List<Goods>> = cartRepository.getAll()

    fun delete(goods: Goods) {
        cartRepository.delete(goods)
    }
}
