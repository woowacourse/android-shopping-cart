package woowacourse.shopping.ui.productDetail

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import woowacourse.shopping.R
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.mock.MockData
import woowacourse.shopping.repository.cart.MemoryCartRepository
import woowacourse.shopping.repository.product.ProductRepositoryMockImpl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel,
    modifier: Modifier = Modifier,
    onAddToCartClick: () -> Unit = {},
) {
    val productDetailState by viewModel.productDetailState.collectAsState()
    val product = productDetailState.product

    val activity = LocalActivity.current

    Column(
        modifier = modifier
    ) {
        ProductDetailTopBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            onCloseClick = {
                activity?.finish()
            }
        )

        ProductDetailContent(
            modifier = Modifier.weight(1f),
            imageUrl = product.imageUrl.value,
            productName = product.name.value,
            price = product.price.value,
        )

        ProductDetailBottomBar(
            modifier = Modifier,
            onAddToCartClick = onAddToCartClick
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductDetailTopBar(modifier: Modifier = Modifier, onCloseClick: () -> Unit) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = "") },
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
                containerColor = Color(0xFF555555),
                scrolledContainerColor = Color.Unspecified,
                navigationIconContentColor = Color.White,
                titleContentColor = Color.White,
                actionIconContentColor = Color.White,
            ),
        windowInsets = WindowInsets(0, 0, 0, 0),
    )
}

@Composable
private fun ProductDetailBottomBar(modifier: Modifier = Modifier, onAddToCartClick: () -> Unit) {
    Button(
        onClick = onAddToCartClick,
        modifier =
            modifier
                .fillMaxWidth()
                .height(52.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = Color(0xFF14C3B1),
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

@Composable
private fun ProductDetailContent(
    modifier: Modifier = Modifier,
    imageUrl: String,
    productName: String,
    price: Int,
) {
    Column(
        modifier = modifier.background(Color.White),
    ) {
        ProductImageSection(imageUrl = imageUrl)
        Spacer(modifier = Modifier.height(16.dp))
        ProductNameSection(productName = productName)
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(
            color = Color(0xFFE0E0E0),
            thickness = 1.dp,
        )
        ProductPriceSection(price = price)
    }
}

@Composable
private fun ProductImageSection(imageUrl: String) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(280.dp)
                .background(Color(0xFFF5F5F5)),
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
private fun ProductNameSection(productName: String) {
    Text(
        text = productName,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 16.dp),
        color = Color.Black,
    )
}

@Composable
private fun ProductPriceSection(price: Int) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "가격",
            fontSize = 18.sp,
            color = Color(0xFF333333),
        )
        Text(
            text = String.format("%,d원", price),
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
    contentDescription: String = "상품 이미지",
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun ProductDetailScreenPreview() {
    ProductDetailScreen(
        viewModel = ProductDetailViewModel(
            "0",
            ProductRepositoryMockImpl(),
            MemoryCartRepository
        ),
        onAddToCartClick = {},
    )
}
