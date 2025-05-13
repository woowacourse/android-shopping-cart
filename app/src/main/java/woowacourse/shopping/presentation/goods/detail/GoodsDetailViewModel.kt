package woowacourse.shopping.presentation.goods.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.presentation.data.ShoppingDataBase

class GoodsDetailViewModel : ViewModel() {
    private val _goods: MutableLiveData<Goods> = MutableLiveData()

    val goods: LiveData<Goods>
        get() = _goods

    fun setGoods(goods: Goods) {
        if (_goods.value == null) _goods.value = goods
    }

    fun addToShoppingCart() {
        _goods.value?.let { ShoppingDataBase.addItem(it) }
    }
}
