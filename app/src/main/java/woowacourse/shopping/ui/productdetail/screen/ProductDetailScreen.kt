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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import woowacourse.shopping.R
import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.ui.productdetail.component.mintButton
import woowacourse.shopping.ui.productdetail.component.productDetail
import woowacourse.shopping.ui.productdetail.component.productDetailTopAppBar
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun productDetailScreen(
    product: Product?,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = { productDetailTopAppBar(onClose) },
        containerColor = Color.White,
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
        Box(
            modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            if (product != null) {
                productDetail(product)
                mintButton(
                    onClick = {
                        CartRepository.addProduct(product)
                        scope.launch {
                            snackbarHostState.showSnackbar("장바구니에 상품을 담았습니다")
                        }
                    },
                    text = "장바구니 담기",
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
private fun productDetailScreenPreview() {
    productDetailScreen(
        product =
            Product(
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image7}",
                productName = "[든든] 동원 스위트콘",
                price = Price(99800),
            ),
        onClose = {},
    )
}
