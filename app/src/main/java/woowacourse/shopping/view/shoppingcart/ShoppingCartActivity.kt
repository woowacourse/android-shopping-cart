package woowacourse.shopping.view.shoppingcart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.view.base.ActivityBoilerPlateCode
import woowacourse.shopping.view.base.ActivityBoilerPlateCodeImpl
import woowacourse.shopping.view.base.QuantitySelectorEventHandler

class ShoppingCartActivity :
    AppCompatActivity(),
    ActivityBoilerPlateCode<ActivityShoppingCartBinding> by ActivityBoilerPlateCodeImpl(
        R.layout.activity_shopping_cart,
    ) {
    private val viewModel: ShoppingCartViewModel by viewModels { ShoppingCartViewModel.Factory }
    private val shoppingCartAdapter: ShoppingCartAdapter by lazy {
        ShoppingCartAdapter(ShoppingCartEventHandlerImpl())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()

        setMenubar(binding.toolbar as Toolbar)
        viewModel.apply {
            requestProductsPage(0)
            productsLiveData.observe(this@ShoppingCartActivity) { page ->
                shoppingCartAdapter.updateProducts(page)
            }
        }
        binding.apply {
            shoppingCartList.adapter = shoppingCartAdapter
            viewModel = this@ShoppingCartActivity.viewModel
            handler = ShoppingCartEventHandlerImpl()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveCurrentShoppingCart(
            shoppingCartAdapter.quantityMap.map {
                ShoppingCartItem(
                    product = it.key.product,
                    quantity = it.value.value!!,
                )
            },
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setMenubar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            this.title = MENU_BAR_TAG
        }
    }

    companion object {
        private const val MENU_BAR_TAG = "Cart"

        fun newIntent(context: Context): Intent {
            return Intent(context, ShoppingCartActivity::class.java)
        }
    }

    private inner class ShoppingCartEventHandlerImpl : ShoppingCartEventHandler {
        override var item: ShoppingCartItem? = null
        override var page: Int? = null

        override fun onPagination(page: Int) {
            viewModel.saveCurrentShoppingCart(
                shoppingCartAdapter.quantityMap.map {
                    ShoppingCartItem(
                        product = it.key.product,
                        quantity = it.value.value!!,
                    )
                },
            )
            viewModel.requestProductsPage(page)
        }

        override fun onQuantityMinusSelected(quantity: MutableLiveData<Int>) {
            quantity.value = quantity.value?.minus(1)
            if (quantity.value == 0) {
                onProductRemove(item!!, page!!)
            }
        }

        override fun onQuantityPlusSelected(quantity: MutableLiveData<Int>) {
            quantity.value = quantity.value?.plus(1)
        }

        override fun onProductRemove(
            product: ShoppingCartItem,
            page: Int,
        ) {
            viewModel.removeProduct(product, page)
        }

        override fun whenQuantityChangedSelectView(quantity: MutableLiveData<Int>) {
        }
    }
}

interface ShoppingCartEventHandler : QuantitySelectorEventHandler {
    var item: ShoppingCartItem?

    var page: Int?

    fun onPagination(page: Int)

    fun onProductRemove(
        product: ShoppingCartItem,
        page: Int,
    )

    fun whenQuantityChangedSelectView(quantity: MutableLiveData<Int>)
}
