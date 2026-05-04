@file:Suppress("FunctionName")

package woowacourse.shopping.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.R
import woowacourse.shopping.model.Price
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductTitle
import woowacourse.shopping.model.ShoppingCartItem
import woowacourse.shopping.ui.component.PageNavigation
import woowacourse.shopping.ui.component.ShoppingCartItems
import woowacourse.shopping.ui.theme.AndroidShoppingTheme

@Composable
fun ShoppingCartScreen(
    shoppingCartItems: List<ShoppingCartItem>,
    onBackClick: () -> Unit,
    onRemoveShoppingItemClick: (ShoppingCartItem) -> Unit,
    currentPage: Int,
    canMoveToPreviousPage: Boolean,
    canMoveToNextPage: Boolean,
    onBeforePageClick: () -> Unit,
    onNextPageClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
            Column(
                modifier =
                    Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                shoppingCartItems.forEach { shoppingCartItem ->
                    ShoppingCartItems(
                        shoppingCartItem = shoppingCartItem,
                        onRemoveShoppingItemClick = onRemoveShoppingItemClick,
                    )
                }
            }
            PageNavigation(
                currentPage = currentPage,
                canMoveToPreviousPage = canMoveToPreviousPage,
                canMoveToNextPage = canMoveToNextPage,
                onBeforePageClick = onBeforePageClick,
                onNextPageClick = onNextPageClick,
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

@Composable
@Preview(showBackground = true)
private fun ShoppingCartScreenPreview() {
    AndroidShoppingTheme {
        ShoppingCartScreen(
            shoppingCartItems =
                listOf(
                    ShoppingCartItem(
                        id = 1,
                        product = Product(1, ProductTitle("동원 스위트콘"), Price(99_800), ""),
                    ),
                    ShoppingCartItem(
                        id = 1,
                        product = Product(1, ProductTitle("동원 스위트콘"), Price(99_800), ""),
                    ),
                    ShoppingCartItem(
                        id = 1,
                        product = Product(1, ProductTitle("동원 스위트콘"), Price(99_800), ""),
                    ),
                ),
            onBackClick = { },
            onRemoveShoppingItemClick = { },
            currentPage = 0,
            canMoveToPreviousPage = false,
            canMoveToNextPage = true,
            onBeforePageClick = {},
            onNextPageClick = {},
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun ShoppingCartItemsPreview() {
    ShoppingCartItems(
        shoppingCartItem =
            ShoppingCartItem(
                id = 1,
                product = Product(1, ProductTitle("동원 스위트콘"), Price(99_800), ""),
            ),
        onRemoveShoppingItemClick = {},
    )
}
