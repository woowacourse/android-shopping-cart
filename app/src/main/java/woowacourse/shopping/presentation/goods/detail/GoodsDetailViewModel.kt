package woowacourse.shopping.presentation.goods.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.goods.Goods
import woowacourse.shopping.domain.model.shoppingcart.ShoppingCartItem
import woowacourse.shopping.domain.repository.GoodsRepository
import woowacourse.shopping.domain.repository.RecentGoodsRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.presentation.util.MutableSingleLiveData
import woowacourse.shopping.presentation.util.ShoppingCartEvent
import woowacourse.shopping.presentation.util.SingleLiveData

class GoodsDetailViewModel(
    private val goodsId: Long,
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

    private val _shoppingCartEvent: MutableSingleLiveData<ShoppingCartEvent> =
        MutableSingleLiveData()
    val shoppingCartEvent: SingleLiveData<ShoppingCartEvent>
        get() = _shoppingCartEvent

    init {
        loadGoodsDetail()
        loadRecentGoods()
    }

    fun increaseQuantity() {
        _item.value = _item.value?.increaseQuantity()
    }

    fun decreaseQuantity() {
        _item.value = _item.value?.decreaseQuantity()
    }

    fun addToShoppingCart() {
        val currentItem = _item.value ?: return

        shoppingCartRepository.addOrIncreaseQuantity(currentItem) { result ->
            result
                .onSuccess {
                    _shoppingCartEvent.postValue(ShoppingCartEvent.SUCCESS)
                }
                .onFailure {
                    _shoppingCartEvent.postValue(ShoppingCartEvent.FAILURE)
                }
        }
    }

    private fun loadRecentGoods() {
        recentGoodsRepository.getLatestRecentGoodsId { result ->
            result
                .onSuccess { id ->
                    if (id != null) {
                        _recentGoods.postValue(goodsRepository.getGoodsById(id))
                    }
                }
        }
    }

    private fun loadGoodsDetail() {
        _item.value = ShoppingCartItem(goodsRepository.getGoodsById(goodsId))
        addRecentGoods()
    }

    private fun addRecentGoods() {
        val currentItem = _item.value ?: return
        val currentTime = System.currentTimeMillis()

        recentGoodsRepository.addRecentGoods(currentTime, currentItem.goods) {}
    }
}
