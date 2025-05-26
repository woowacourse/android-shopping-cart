package woowacourse.shopping.presentation.goods.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.RepositoryProvider
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.domain.model.ShoppingCartItem
import woowacourse.shopping.domain.repository.GoodsRepository
import woowacourse.shopping.domain.repository.RecentGoodsRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.presentation.util.MutableSingleLiveData
import woowacourse.shopping.presentation.util.ShoppingCartEvent
import woowacourse.shopping.presentation.util.SingleLiveData

class GoodsDetailViewModel(
    private val goodsRepository: GoodsRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
    private val recentGoodsRepository: RecentGoodsRepository,
) : ViewModel() {
    private val _item: MutableLiveData<ShoppingCartItem> = MutableLiveData()
    val item: LiveData<ShoppingCartItem>
        get() = _item

    private val _recentGoods: MutableLiveData<Goods> = MutableLiveData()
    val recentGoods: LiveData<Goods>
        get() = _recentGoods

    private val _shoppingCartEvent: MutableSingleLiveData<ShoppingCartEvent> = MutableSingleLiveData()
    val shoppingCartEvent: SingleLiveData<ShoppingCartEvent>
        get() = _shoppingCartEvent

    init {
        recentGoodsRepository.getLatestRecentGoods { result ->
            result.onSuccess { id ->
                if (id == null) {
                    _recentGoods.postValue(null)
                } else {
                    _recentGoods.postValue(goodsRepository.getGoodsById(id))
                }
            }
        }
    }

    fun setGoods(id: Long) {
        _item.value = ShoppingCartItem(goodsRepository.getGoodsById(id))
        addRecentGoods()
    }

    fun increaseQuantity() {
        _item.value = _item.value?.increaseQuantity()
    }

    fun decreaseQuantity() {
        _item.value = _item.value?.decreaseQuantity()
    }

    fun addToShoppingCart() {
        val currentItem = _item.value ?: return

        shoppingCartRepository.addOrIncreaseItem(currentItem) { result ->
            result.onSuccess {
                _shoppingCartEvent.postValue(ShoppingCartEvent.SUCCESS)
            }.onFailure {
                _shoppingCartEvent.postValue(ShoppingCartEvent.FAILURE)
            }
        }
    }

    private fun addRecentGoods() {
        val currentItem = _item.value ?: return
        recentGoodsRepository.addRecentGoods(currentItem.goods) { result ->
            result.onFailure {

            }
        }
    }

    companion object {
        val FACTORY: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                GoodsDetailViewModel(
                    goodsRepository = RepositoryProvider.goodsRepository,
                    shoppingCartRepository = RepositoryProvider.shoppingCartRepository,
                    recentGoodsRepository = RepositoryProvider.recentGoodsRepository,
                )
            }
        }
    }
}
