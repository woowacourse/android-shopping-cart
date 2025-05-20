package woowacourse.shopping.presentation.goods.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.shoppingcart.repository.ShoppingCartRepository
import woowacourse.shopping.presentation.model.GoodsUiModel
import woowacourse.shopping.presentation.model.toDomainModel

class GoodsDetailViewModel(
    goods: GoodsUiModel,
    private val repository: ShoppingCartRepository,
) : ViewModel() {
    private val _goodsUiModel: MutableLiveData<GoodsUiModel> = MutableLiveData(goods)

    val goodsUiModel: LiveData<GoodsUiModel>
        get() = _goodsUiModel

    fun addToShoppingCart() {
        _goodsUiModel.value?.let { repository.addGoods(it.toDomainModel()) }
    }
}
