package woowacourse.shopping.ui

import android.icu.text.DecimalFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import woowacourse.shopping.R
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ShoppingCartItem
import woowacourse.shopping.ui.theme.AndroidShoppingTheme

private const val CART_TOP_BAR_TITLE = "Cart"
private const val BACK_ICON_DESCRIPTION = "상세페이지 닫기"
private const val REMOVE_ITEM_DESCRIPTION = "장바구니 삭제"
private const val PRODUCT_IMAGE_DESCRIPTION = "상품 이미지"
private const val PRICE_FORMAT_PATTERN = "#,###원"

@Composable
fun ShoppingCartScreen(
    shoppingCartItems: List<ShoppingCartItem>,
    onBackClick: () -> Unit,
    onRemoveShoppingItemClick: (ShoppingCartItem) -> Unit,
    modifier: Modifier = Modifier,
    bottomContent: @Composable () -> Unit = {},
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

            bottomContent()
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
                )
                .border(
                    color = MaterialTheme.colorScheme.outline,
                    width = 1.dp,
                    shape = RoundedCornerShape(4.dp),
                )
                .padding(12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                shoppingCartItem.product.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Image(
                painter = painterResource(R.drawable.remove_icon),
                contentDescription = REMOVE_ITEM_DESCRIPTION,
                modifier =
                    Modifier
                        .size(16.dp)
                        .clickable { onRemoveShoppingItemClick(shoppingCartItem) },
            )
        }
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
        ) {
            AsyncImage(
                model = shoppingCartItem.product.imageUrl,
                contentDescription = PRODUCT_IMAGE_DESCRIPTION,
                contentScale = ContentScale.Crop,
                modifier =
                    Modifier
                        .width(136.dp)
                        .height(72.dp)
                        .padding(bottom = 8.dp)
                        .background(MaterialTheme.colorScheme.surfaceContainer),
            )
            Text(
                text = DecimalFormat(PRICE_FORMAT_PATTERN).format(shoppingCartItem.product.price),
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
                text = CART_TOP_BAR_TITLE,
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Image(
                    painter = painterResource(R.drawable.back_icon),
                    contentDescription = BACK_ICON_DESCRIPTION,
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
                        product = Product(1, "동원 스위트콘", 99_800, ""),
                    ),
                    ShoppingCartItem(
                        id = 1,
                        product = Product(1, "동원 스위트콘", 99_800, ""),
                    ),
                    ShoppingCartItem(
                        id = 1,
                        product = Product(1, "동원 스위트콘", 99_800, ""),
                    ),
                ),
            onBackClick = { },
            onRemoveShoppingItemClick = { },
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
                product = Product(1, "동원 스위트콘", 99_800, ""),
            ),
        onRemoveShoppingItemClick = {},
    )
}
