package woowacourse.shopping.ui

import android.icu.text.DecimalFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import woowacourse.shopping.R
import woowacourse.shopping.model.Product
import woowacourse.shopping.ui.theme.AndroidShoppingTheme

@Composable
fun DetailProductScreen(
    product: Product,
    onAddToCartClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            DetailProductTopBar(
                onBackClick = onBackClick,
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = stringResource(R.string.product_image_content_description, product.title),
                contentScale = ContentScale.Crop,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(360.dp)
                        .background(MaterialTheme.colorScheme.surfaceContainer),
            )
            Text(
                text = product.title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(16.dp),
            )
            HorizontalDivider()
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(R.string.price_label),
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = DecimalFormat(stringResource(R.string.price_format_pattern)).format(product.price),
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onAddToCartClick,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                shape = RectangleShape,
            ) {
                Text(stringResource(R.string.add_to_cart_button_text))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailProductTopBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {},
        actions = {
            IconButton(onClick = onBackClick) {
                Image(
                    painter = painterResource(R.drawable.close_icon),
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
private fun DetailProductScreenPreview() {
    AndroidShoppingTheme {
        DetailProductScreen(
            product =
                Product(
                    id = 1,
                    title = "동원 스위트콘",
                    price = 99_800,
                    imageUrl = "https://img.dongwonmall.com/dwmall/static_root/model_img/main/153/15327_1_a.jpg?f=webp&q=80",
                ),
            onAddToCartClick = {},
            onBackClick = {},
        )
    }
}
