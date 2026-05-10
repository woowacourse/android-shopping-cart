@file:Suppress("FunctionName")

package woowacourse.shopping.shoppingcart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.ui.WonMoney
import woowacourse.shopping.ui.component.NumberCounter
import woowacourse.shopping.ui.component.PageNavigation
import woowacourse.shopping.ui.component.ShoppingCartItems

@Composable
fun ShoppingCartScreen(
    onBackClick: () -> Unit,
    onProductChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    shoppingCartViewModel: ShoppingCartViewModel =
        viewModel(
            factory =
                ShoppingCartViewModel.factory(
                    LocalContext.current.applicationContext as ShoppingApplication,
                ),
        ),
) {
    val uiState by shoppingCartViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        shoppingCartViewModel.loadShoppingItems()
    }

    Scaffold(
        topBar = {
            ShoppingCartTopBar(
                onBackClick = onBackClick,
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp,
                    ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(
                    items = uiState.shoppingCartItems,
                    key = { it.id },
                ) { shoppingCartItem ->
                    ShoppingCartItems(
                        title = shoppingCartItem.title,
                        imageUrl = shoppingCartItem.imageUrl,
                        displayableMoney = shoppingCartItem.price,
                        onRemoveShoppingItemClick = {
                            shoppingCartViewModel.removeShoppingItem(
                                shoppingCartItem.productId,
                            )
                            onProductChanged(shoppingCartItem.productId)
                        },
                        quantity = shoppingCartItem.quantity,
                        onIncrementQuantity = {
                            shoppingCartViewModel.increaseItemQuantity(shoppingCartItem.productId, 1)
                            onProductChanged(shoppingCartItem.productId)
                        },
                        onnDecrementQuantity = {
                            shoppingCartViewModel.decreaseItemQuantity(shoppingCartItem.productId, 1)
                            onProductChanged(shoppingCartItem.productId)
                        },
                    )
                }
            }
            PageNavigation(
                currentPage = uiState.currentPage,
                canMoveToPreviousPage = uiState.canMoveToPreviousPage,
                canMoveToNextPage = uiState.canMoveToNextPage,
                onBeforePageClick = shoppingCartViewModel::movePreviousPage,
                onNextPageClick = shoppingCartViewModel::moveNextPage,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShoppingCartTopBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.cart_top_bar_title),
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Image(
                    painter = painterResource(R.drawable.back_icon),
                    contentDescription = stringResource(R.string.close_detail_description),
                    modifier = Modifier.size(16.dp),
                )
            }
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
            ),
        modifier = modifier,
    )
}
//
// @Composable
// @Preview(showBackground = true)
// private fun ShoppingCartScreenPreview() {
//    AndroidShoppingTheme {
//        ShoppingCartScreen(
//            shoppingCartItems =
//                listOf(
//                    ShoppingCartItem(
//                        id = "1",
//                        quantity = Quantity(0),
//                        product = Product("1", ProductTitle("동원 스위트콘"), Price(99_800), ""),
//                    ),
//                    ShoppingCartItem(
//                        id = "2",
//                        quantity = Quantity(1),
//                        product = Product("1", ProductTitle("동원 스위트콘"), Price(99_800), ""),
//                    ),
//                    ShoppingCartItem(
//                        id = "3",
//                        quantity = Quantity(2),
//                        product = Product("1", ProductTitle("동원 스위트콘"), Price(99_800), ""),
//                    ),
//                ),
//            onBackClick = { },
//            onRemoveShoppingItemClick = { },
//            currentPage = 0,
//            canMoveToPreviousPage = false,
//            canMoveToNextPage = true,
//            onBeforePageClick = {},
//            onNextPageClick = {},
//        )
//    }
// }
