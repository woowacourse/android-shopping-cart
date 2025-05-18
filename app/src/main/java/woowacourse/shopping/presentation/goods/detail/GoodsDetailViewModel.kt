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

    fun setGoods(goods: Goods) {
        _goods.value = goods
    }

    fun addToShoppingCart() {
        _goods.value?.let { ShoppingDataBase.addItem(it) }
    }
}
