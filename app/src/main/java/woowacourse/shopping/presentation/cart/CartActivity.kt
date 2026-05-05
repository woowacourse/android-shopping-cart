package woowacourse.shopping.presentation.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.presentation.cart.screen.CartScreen
import woowacourse.shopping.presentation.theme.androidshoppingTheme
import kotlin.uuid.ExperimentalUuidApi

class CartActivity : ComponentActivity() {
    @OptIn(ExperimentalUuidApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        @OptIn(ExperimentalUuidApi::class)
        setContent {
            androidshoppingTheme {
                var pageIndex by rememberSaveable { mutableStateOf(0) }

                val stateHolder =
                    remember {
                        CartStateHolder(
                            cartRepository = CartRepositoryImpl,
                            initialPageIndex = pageIndex,
                            onPageIndexChanged = { pageIndex = it },
                        )
                    }
                CartScreen(
                    stateHolder = stateHolder,
                    onBack = { finish() },
                )
            }
        }
    }

    companion object {
        @OptIn(ExperimentalUuidApi::class)
        fun newIntent(context: Context): Intent = Intent(context, CartActivity::class.java)
    }
}
