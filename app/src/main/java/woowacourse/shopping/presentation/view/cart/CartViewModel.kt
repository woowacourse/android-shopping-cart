package woowacourse.shopping.presentation.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.domain.model.Price
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.model.ProductUiModel

class CartViewModel(
    private val shoppingCart: List<Product>,
) : ViewModel() {
    private val _products = MutableLiveData<List<ProductUiModel>>()
    val products: LiveData<List<ProductUiModel>> = _products

    init {
        fetchShoppingCart()
    }

    private fun fetchShoppingCart() {
        _products.value =
            shoppingCart.map {
                ProductUiModel(
                    id = it.id,
                    name = it.name,
                    imageUrl = it.imageUrl,
                    price = it.price.value,
                )
            }
    }

    fun deleteProduct(product: ProductUiModel) {
        _products.value = _products.value?.minus(product)
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T =
                    CartViewModel(
                        listOf(
                            Product(
                                1,
                                "나이키 에어 포스 1 '07 LX",
                                "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/afbbdaaf-1169-449e-98ff-745eba632fd9/WMNS+AIR+FORCE+1+%2707+LX.png",
                                Price(149_000),
                            ),
                            Product(
                                2,
                                "나이키 줌 보메로 5",
                                "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/1471109b-d228-4e18-8885-99c8d2d01fb1/W+NIKE+ZOOM+VOMERO+5.png",
                                Price(189_000),
                            ),
                        ),
                    ) as T
            }
    }
}
