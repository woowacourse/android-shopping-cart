package woowacourse.shopping.ui.cart.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.ui.cart.component.CartBody
import woowacourse.shopping.ui.cart.component.CartTopAppBar
import woowacourse.shopping.ui.cart.viewmodel.CartViewModel
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class)
@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = viewModel(),
    onClose: () -> Unit,
) {
    Scaffold(
        topBar = {
            CartTopAppBar(
                onClick = { onClose() },
            )
        },
        containerColor = Color.White,
    ) { innerPadding ->
        CartBody(
            viewModel = viewModel,
            innerPadding = innerPadding,
            onDeleteProduct = { id ->
                viewModel.deleteProduct(id)
//                val updatedProducts = viewModel.getCartProducts()
//                    if (updatedProducts.isEmpty()) 0 else (updatedProducts.size - 1) / CART_PAGE_SIZE
                // viewModel.adjustCurrentPage()
            },
            modifier = modifier,
        )
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun CartScreenPreview() {
    CartScreen(
        onClose = {},
    )
}
