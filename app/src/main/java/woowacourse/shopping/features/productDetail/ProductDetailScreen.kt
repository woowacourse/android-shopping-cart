package woowacourse.shopping.features.productDetail

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import woowacourse.shopping.data.cart.CartRepositoryMockImpl
import woowacourse.shopping.data.product.ProductRepositoryMockImpl
import woowacourse.shopping.features.constant.Format.formatPrice
import woowacourse.shopping.features.constant.ShoppingColor.APP_BAR_COLOR
import woowacourse.shopping.features.constant.ShoppingColor.CART_ADD_BUTTON_COLOR
import woowacourse.shopping.features.constant.ShoppingColor.PRODUCT_DETAIL_BACKGROUND_COLOR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel,
    modifier: Modifier = Modifier,
    onAddToCartClick: () -> Unit = {},
) {
    val productDetailState by viewModel.productDetailState.collectAsState()
    val product = productDetailState.product!!
    val activity = LocalActivity.current

    Column(
        modifier = modifier,
    ) {
        ProductDetailTopAppBar(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(56.dp),
            onCloseClick = {
                activity?.finish()
            },
        )

        ProductDetailContent(
            modifier =
                Modifier
                    .background(Color.White)
                    .weight(1f),
            imageUrl = product.imageUrl.value,
            productName = product.name.value,
            price = product.price.value,
        )

        CardAddButton(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(52.dp),
            onAddToCartClick = onAddToCartClick,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductDetailTopAppBar(
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = "")
        },
        actions = {
            IconButton(onClick = onCloseClick) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "닫기",
                    tint = Color.White,
                )
            }
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = Color(APP_BAR_COLOR),
                scrolledContainerColor = Color.Unspecified,
                navigationIconContentColor = Color.White,
                titleContentColor = Color.White,
                actionIconContentColor = Color.White,
            ),
        windowInsets = WindowInsets(0, 0, 0, 0),
    )
}

@Composable
private fun ProductDetailContent(
    modifier: Modifier = Modifier,
    imageUrl: String,
    productName: String,
    price: Int,
) {
    Column(
        modifier = modifier,
    ) {
        ProductImageSection(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(
                        Color(PRODUCT_DETAIL_BACKGROUND_COLOR),
                    ),
            imageUrl = imageUrl,
        )
        ProductNameSection(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            productName = productName,
        )
        HorizontalDivider(
            color = Color.Black,
            thickness = 1.dp,
        )
        ProductPriceSection(price = price, modifier = Modifier)
    }
}

@Composable
private fun ProductImageSection(
    modifier: Modifier = Modifier,
    imageUrl: String,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        ProductImage(
            imageUrl = imageUrl,
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(40.dp),
        )
    }
}

@Composable
private fun ProductNameSection(
    productName: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = productName,
        modifier = modifier,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
    )
}

@Composable
private fun ProductPriceSection(
    price: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .padding(horizontal = 16.dp, vertical = 14.dp)
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "가격",
            fontSize = 18.sp,
            color = Color.Black,
        )
        Text(
            text = formatPrice(price),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
        )
    }
}

@Composable
private fun ProductImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
) {
    AsyncImage(
        modifier = modifier,
        model = imageUrl,
        contentDescription = "상품 이미지",
    )
}

@Composable
private fun CardAddButton(
    modifier: Modifier = Modifier,
    onAddToCartClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onAddToCartClick,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = Color(CART_ADD_BUTTON_COLOR),
                contentColor = Color.White,
            ),
        shape = RoundedCornerShape(0.dp),
    ) {
        Text(
            text = "장바구니 담기",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailScreenPreview() {
    ProductDetailScreen(
        viewModel =
            ProductDetailViewModel(
                "0",
                ProductRepositoryMockImpl(),
                CartRepositoryMockImpl,
            ),
        onAddToCartClick = {},
    )
}
