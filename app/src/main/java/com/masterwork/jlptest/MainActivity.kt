package com.masterwork.jlptest

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.masterwork.jlptest.ui.theme.JlpTestTheme
import java.io.IOException
import java.lang.Exception
import java.net.URL
import java.nio.charset.Charset

class MainActivity : ComponentActivity() {

    lateinit var vm: ProductViewModel

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        val productViewModel: ProductViewModel by viewModels()
        vm = productViewModel

        val hackyData = getJsonFromAssets(this.applicationContext, "data1.json")
        productViewModel.fetchProductData(hackyData.toString())

        super.onCreate(savedInstanceState)
        setContent {
            JlpTestTheme {
                val productData = productViewModel.productRangeData.observeAsState().value
                val focusedProduct = productViewModel.focusedProduct.observeAsState().value


                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    if (focusedProduct != null) {
                        ProductPage(focusedProduct)
                    } else if (productData != null) {
                        ProductList(productData)
                    }
                }
            }
        }
    }

    @ExperimentalFoundationApi
    @Composable
    fun ProductList(productRangeData: ProductRangeData) {
        val numberOfDishwashers = productRangeData.products.size

        Column(Modifier.fillMaxWidth()) {
            Text(
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(Color(0xE6F4F4F4))
                    .padding(vertical = 18.dp, horizontal = 10.dp)
                    .fillMaxWidth(),
                text = "Dishwashers ($numberOfDishwashers)",
                color = Color(0xFF63666A),
                fontSize = 22.sp
            )

            LazyVerticalGrid(
                cells = GridCells.Fixed(2)
            ) {
                items(productRangeData.products) { item ->
                    ProductCard(item)
                }
            }

        }
    }

    @Composable
    fun ProductCard(product: Product) {
        val title = product.title
        val price = product.price.now
        val imageUrl = try {
            URL(product.image)
        } catch (exception: Exception) {
            "https://picsum.photos/400/400"
        }

        val hackyData = getJsonFromAssets(this.applicationContext, "data2.json")

        Box(
            modifier = Modifier
                .border(
                    BorderStroke(
                        0.3.dp, SolidColor(Color.LightGray)
                    )
                )
                .clickable { vm.onItemClick(product.productID, hackyData ?: "") }
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .height(290.dp)
                    .fillMaxWidth()
            ) {
                val (productImage, productTitle, productPrice) = createRefs()

                Image(painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = null, modifier = Modifier
                        .padding(top = 16.dp, start = 14.dp, end = 14.dp)
                        .constrainAs(productImage) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        })

                Text(text = title, modifier = Modifier
                    .padding(top = 16.dp, start = 14.dp, end = 14.dp)
                    .constrainAs(productTitle) {
                        top.linkTo(productImage.bottom)

                    })

                Text(fontWeight = FontWeight.Bold, text = "£$price", modifier = Modifier
                    .padding(bottom = 24.dp, start = 14.dp, end = 14.dp)
                    .constrainAs(productPrice) {
                        top.linkTo(productTitle.bottom)

                    })
            }
        }
    }

    @Composable
    fun ProductPage(product: DetailsDatum) {
        val title = product.title
        val price = product.price.now
        val productDetails = "Parse product information string, it still has HTML!"
        val productCode = product.code
        val guaranteed = product.additionalServices.includedServices.first()
        val adjustable = product.dynamicAttributes.adjustable ?: "No"
        val childLock = product.dynamicAttributes.childlock.value
        val dryingPerformance = product.dynamicAttributes.dryingperformance
        val delicateWash = product.dynamicAttributes.delicatewash?.value

        val lazyString = "Child lock " +
                "information " + childLock + "\nDrying performance information " + dryingPerformance + "\nDelicate " +
                "Wash " + delicateWash

        val imageUrl = try {
            URL(product.media.images.urls.first())
        } catch (exception: Exception) {
            "https://picsum.photos/400/400"
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxWidth()
            ) {
                val (productImage, productTitle, productPrice, productCodeC, productInformation, productDetailsC,
                    readMore, data) =
                    createRefs()

                Text(text = title, modifier = Modifier
                    .padding(top = 16.dp, start = 14.dp, end = 14.dp)
                    .constrainAs(productTitle) {
                        top.linkTo(parent.top)
                    })

                Image(painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = null, modifier = Modifier
                        .padding(top = 16.dp, start = 14.dp, end = 14.dp)
                        .constrainAs(productImage) {
                            top.linkTo(productTitle.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        })

                Text(text = "Product code: $productCode", modifier = Modifier
                    .padding(top = 16.dp, start = 14.dp, end = 14.dp)
                    .constrainAs(productCodeC) {
                        top.linkTo(productImage.bottom)

                    })

                Text(text = "Product Information", modifier = Modifier
                    .padding(top = 16.dp, start = 14.dp, end = 14.dp)
                    .constrainAs(productInformation) {
                        top.linkTo(productCodeC.bottom)

                    })

                Text(text = productDetails, modifier = Modifier
                    .padding(top = 16.dp, start = 14.dp, end = 14.dp)
                    .constrainAs(productDetailsC) {
                        top.linkTo(productInformation.bottom)

                    })

                Text(text = "Read more", modifier = Modifier
                    .padding(top = 16.dp, start = 14.dp, end = 14.dp)
                    .constrainAs(readMore) {
                        top.linkTo(productDetailsC.bottom)
                    })

                Text(text = lazyString, modifier = Modifier
                    .padding(top = 16.dp, start = 14.dp, end = 14.dp)
                    .constrainAs(data) {
                        top.linkTo(readMore.bottom)
                    })
            }
        }
    }

    @ExperimentalFoundationApi
    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        JlpTestTheme {
        }
    }

    fun getJsonFromAssets(context: Context, fileName: String): String? {
        val jsonString = try {
            val inputStream = context.assets.open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charset.defaultCharset())
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return jsonString
    }
}