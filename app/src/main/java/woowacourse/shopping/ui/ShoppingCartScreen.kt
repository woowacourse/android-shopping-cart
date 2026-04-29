package woowacourse.shopping.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import woowacourse.shopping.R
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ShoppingCartItem

@Composable
fun ShoppingCartScreen(
    shoppingCartItems: List<ShoppingCartItem>,
    onBackClick: () -> Unit,
    onRemoveShoppingItemClick: (ShoppingCartItem) -> Unit,
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
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            shoppingCartItems.forEach { shoppingCartItem ->
                ShoppingCartItems(
                    shoppingCartItem = shoppingCartItem,
                    onRemoveShoppingItemClick = onRemoveShoppingItemClick,
                )
            }
        }
    }
}

@Composable
private fun ShoppingCartItems(
    shoppingCartItem: ShoppingCartItem,
    onRemoveShoppingItemClick: (ShoppingCartItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(4.dp),
                ).border(
                    color = MaterialTheme.colorScheme.outline,
                    width = 1.dp,
                    shape = RoundedCornerShape(4.dp),
                ).padding(12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(shoppingCartItem.product.title)
            Image(
                painter = painterResource(R.drawable.close_icon),
                contentDescription = "장바구니 삭제",
                modifier =
                    Modifier
                        .size(22.dp)
                        .clickable { onRemoveShoppingItemClick(shoppingCartItem) },
            )
        }
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            AsyncImage(
                model = shoppingCartItem.product.imageUrl,
                contentDescription = "상품 이미지",
                modifier = Modifier.size(56.dp),
            )
            Text(shoppingCartItem.product.price.toString())
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
                text = "Cart",
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Image(
                    painter = painterResource(R.drawable.back_icon),
                    contentDescription = "상세페이지 닫기",
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
private fun ShoppingCartItemsPreview() {
    ShoppingCartItems(
        shoppingCartItem =
            ShoppingCartItem(
                id = 1,
                product = Product(1, "동원 스위트콘", 99_800, ""),
            ),
        onRemoveShoppingItemClick = {},
    )
}
