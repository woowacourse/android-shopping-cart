package woowacourse.shopping.presentation.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.ShoppingDataBase
import woowacourse.shopping.domain.model.Goods

class ShoppingCartViewModel : ViewModel() {
    private val _goods: MutableLiveData<List<Goods>> = MutableLiveData()

    val goods: LiveData<List<Goods>>
        get() = _goods

    init {
        _goods.value = ShoppingDataBase.getAll()
    }

    fun deleteGoods(goods: Goods) {
        removeGoods(goods)
        ShoppingDataBase.removeItem(goods)
    }

    private fun removeGoods(goods: Goods) {
        val indexToRemove = _goods.value?.indexOf(goods)
        _goods.value = _goods.value?.filterIndexed { index, _ -> index != indexToRemove }
    }
}
