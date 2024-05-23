package woowacourse.shopping.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.CartRepository
import woowacourse.shopping.data.mapper.toModel
import woowacourse.shopping.model.Product
import woowacourse.shopping.ui.livedata.MutableSingleLiveData
import woowacourse.shopping.ui.livedata.SingleLiveData
import kotlin.concurrent.thread

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {

    private val _cartProducts: MutableLiveData<List<Product>> =
        MutableLiveData(emptyList())
    val cartProducts: LiveData<List<Product>> get() = _cartProducts

    private val _onCartProductDeleted = MutableSingleLiveData<Unit>()
    val onCartProductDeleted: SingleLiveData<Unit> get() = _onCartProductDeleted

    fun getAllCartProducts() {
        thread {
            val cartProducts = cartRepository
                .getAllCartProducts()
                .map { it.toModel() }
            _cartProducts.postValue(cartProducts)
        }
    }

    fun deleteCartProduct(id: Int) {
        thread {
            cartRepository.deleteCartProduct(id.toLong())
        }.join()
        _onCartProductDeleted.setValue(Unit)
    }
}
