package woowacourse.shopping.ui.productdetail.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import woowacourse.shopping.AppContainer.cartRepository
import woowacourse.shopping.ProductFixture
import woowacourse.shopping.R
import woowacourse.shopping.domain.Product
import woowacourse.shopping.ui.productdetail.component.MintButton
import woowacourse.shopping.ui.productdetail.component.ProductDetail
import woowacourse.shopping.ui.productdetail.component.ProductDetailTopAppBar
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    product: Product?,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val addToCartText = stringResource(R.string.add_to_the_shopping_cart)
    val addToCartSnackbarText = stringResource(R.string.add_to_the_shopping_cart_snackbar_text)
    var quantity by remember { mutableIntStateOf(1) }

    Scaffold(
        topBar = { ProductDetailTopAppBar(onClose) },
        containerColor = Color.White,
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
        Box(
            modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            if (product != null) {
                ProductDetail(
                    product = product,
                    quantity = quantity,
                    increaseQuantity = { quantity++ },
                    decreaseQuantity = { quantity-- },
                )
                MintButton(
                    onClick = {
                        scope.launch {
                            cartRepository.addProduct(
                                product = product,
                                quantityToAdd = quantity,
                            )
                            snackbarHostState.showSnackbar(addToCartSnackbarText)
                        }
                    },
                    text = addToCartText,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                )
            }
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun ProductDetailScreenPreview() {
    val packageName = LocalContext.current.packageName

    ProductDetailScreen(
        product = ProductFixture.productList(packageName).last(),
        onClose = {},
    )
}
