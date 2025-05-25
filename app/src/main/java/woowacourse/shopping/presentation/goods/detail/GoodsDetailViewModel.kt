package woowacourse.shopping.presentation.goods.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.RepositoryProvider
import woowacourse.shopping.domain.model.ShoppingCartItem
import woowacourse.shopping.domain.repository.GoodsRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.presentation.util.MutableSingleLiveData
import woowacourse.shopping.presentation.util.ShoppingCartEvent
import woowacourse.shopping.presentation.util.SingleLiveData

class GoodsDetailViewModel(
    private val goodsRepository: GoodsRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    private val _item: MutableLiveData<ShoppingCartItem> = MutableLiveData()
    val item: LiveData<ShoppingCartItem>
        get() = _item

    private val _shoppingCartEvent: MutableSingleLiveData<ShoppingCartEvent> = MutableSingleLiveData()
    val shoppingCartEvent: SingleLiveData<ShoppingCartEvent>
        get() = _shoppingCartEvent

    fun setGoods(id: Long) {
        _item.value = ShoppingCartItem(goodsRepository.getGoodsById(id))
    }

    fun addToShoppingCart() {
        val currentItem = _item.value ?: return

        shoppingCartRepository.addItem(currentItem) { result ->
            result.onSuccess {
                _shoppingCartEvent.postValue(ShoppingCartEvent.SUCCESS)
            }.onFailure {
                _shoppingCartEvent.postValue(ShoppingCartEvent.FAILURE)
            }
        }
    }

    companion object {
        val FACTORY: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                GoodsDetailViewModel(
                    goodsRepository = RepositoryProvider.goodsRepository,
                    shoppingCartRepository = RepositoryProvider.shoppingCartRepository,
                )
            }
        }
    }
}
