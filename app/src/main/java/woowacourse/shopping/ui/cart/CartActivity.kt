package woowacourse.shopping.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.core.content.IntentCompat
import androidx.core.content.IntentCompat.getParcelableArrayListExtra
import woowacourse.shopping.ui.cart.stateholder.CartStateHolder
import woowacourse.shopping.ui.cart.ui.theme.AndroidshoppingcartTheme
import woowacourse.shopping.ui.state.ProductUiModel

class CartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val stateHolder = rememberSaveable(saver = CartStateHolder.Saver) {
                CartStateHolder(getCartItems(intent))
            }
            AndroidshoppingcartTheme {
                CartScreen(
                    cartContents = stateHolder.cartContents,
                    onCloseClick = { finish() },
                    onDelete = { id ->
                        stateHolder.deleteCartItem(id)
                        setResult(RESULT_OK, deletedListResult(stateHolder.totalCartContents))
                    },
                    canMoveToPreviousPage = stateHolder.isStartPage().not(),
                    onLeftClick = {
                        stateHolder.moveToPreviousPage()
                    },
                    canMoveToNextPage = stateHolder.isEndPage().not(),
                    onRightClick = {
                        stateHolder.moveToNextPage()
                    },
                    page = stateHolder.page,
                )
            }
        }
    }

    companion object {
        private const val EXTRA_CART_ITEMS = "extra_cart_items"
        private const val EXTRA_DELETED_LIST = "deleted_cart_list"

        fun newIntent(
            context: Context,
            cartContents: List<ProductUiModel>,
        ): Intent = Intent(context, CartActivity::class.java)
            .putParcelableArrayListExtra(EXTRA_CART_ITEMS, ArrayList(cartContents))

        fun getDeletedList(intent: Intent): List<ProductUiModel> =
            IntentCompat.getParcelableArrayListExtra(intent, EXTRA_DELETED_LIST, ProductUiModel::class.java)
                ?: emptyList()

        private fun getCartItems(intent: Intent): List<ProductUiModel> =
            IntentCompat.getParcelableArrayListExtra(intent, EXTRA_CART_ITEMS, ProductUiModel::class.java)
                ?: emptyList()

        private fun deletedListResult(items: List<ProductUiModel>): Intent =
            Intent().putParcelableArrayListExtra(EXTRA_DELETED_LIST, ArrayList(items))
    }
}
