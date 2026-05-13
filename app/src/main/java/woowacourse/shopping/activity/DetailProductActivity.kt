@file:Suppress("FunctionName")

package woowacourse.shopping.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.ui.DetailProductScreen
import woowacourse.shopping.ui.theme.AndroidShoppingTheme
import woowacourse.shopping.viewmodel.DetailProductViewModel
import woowacourse.shopping.viewmodel.ViewModelFactory

class DetailProductActivity : ComponentActivity() {
    private val detailProductViewModel: DetailProductViewModel by viewModels {
        ViewModelFactory((application as ShoppingApplication).appContainer)
    }

    companion object {
        fun start(
            context: Context,
            productId: Long,
            showLastViewed: Boolean = true,
        ) {
            val intent = Intent(context, DetailProductActivity::class.java)
            intent.putExtra(DetailProductViewModel.EXTRA_PRODUCT_ID, productId)
            intent.putExtra(DetailProductViewModel.EXTRA_SHOW_LAST_VIEWED, showLastViewed)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        detailProductViewModel.initializeFromIntentExtras(intent.extras)
        setContent {
            val uiState by detailProductViewModel.uiState.collectAsStateWithLifecycle()
            AndroidShoppingTheme {
                val shoppingItem = uiState.shoppingItem
                if (shoppingItem != null) {
                    DetailProductScreen(
                        shoppingItem = shoppingItem,
                        lastViewedShoppingItem = uiState.lastViewedShoppingItem,
                        onAddToCartClick = {
                            detailProductViewModel.addSelectedProductToCart()
                            this.finish()
                        },
                        onLastViewedProductClick = { selectedProductId ->
                            start(
                                context = this,
                                productId = selectedProductId,
                                showLastViewed = false,
                            )
                            finish()
                        },
                        onBackClick = this::finish,
                        quantity = uiState.selectedQuantity,
                        quantityPrice = uiState.quantityPrice,
                        onQuantityPlusClick = detailProductViewModel::increaseSelectedQuantity,
                        onQuantityMinusClick = detailProductViewModel::decreaseSelectedQuantity,
                    )
                } else {
                    Text(stringResource(R.string.product_not_found_message))
                }
            }
        }
    }
}
