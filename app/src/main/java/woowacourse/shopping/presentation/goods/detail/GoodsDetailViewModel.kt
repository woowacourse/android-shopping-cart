package woowacourse.shopping.presentation.goods.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.ShoppingDataBase
import woowacourse.shopping.domain.model.Goods

class GoodsDetailViewModel : ViewModel() {
    private val _goods: MutableLiveData<Goods> = MutableLiveData()
    val goods: LiveData<Goods>
        get() = _goods

    private val _isItemAddedToCart: MutableLiveData<Boolean> = MutableLiveData(false)
    val isItemAddedToCart: LiveData<Boolean>
        get() = _isItemAddedToCart

    fun setGoods(goods: Goods) {
        _goods.value = goods
    }

    fun addToShoppingCart() {
        _goods.value?.let { ShoppingDataBase.addItem(it) }
        updateIsItemAddedToCart(true)
    }

    fun updateIsItemAddedToCart(added: Boolean) {
        _isItemAddedToCart.value = added
    }
}
