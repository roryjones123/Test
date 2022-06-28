package com.masterwork.jlptest

import okhttp3.OkHttpClient
import okhttp3.Request

class ProductRepo {
    companion object {
        const val DISHWASHER_RANGE_URL =
            "https://gitlab.com/roryjones223/jlp-android-test/-/blob/main/mockData/data.json"

        const val DISHWASHER_PRODUCT_URL =
            "https://api.johnlewis.com/search/api/rest/v2/catalog/products/search/keyword?q=dishwasher&key=AIzaSyDD_6O5gUgC4tRW5f9kxC0_76XRC8W7_mI"
    }

    fun getProductRangeData(hackyData: String): ProductRangeData? {
        //val jsonProductData = fetchDataFromClient(DISHWASHER_PRODUCT_URL)
        return ProductRangeData.fromJson(hackyData)
    }

    fun getProductData(hackyData: String): ProductData? {
        return ProductData.fromJson(hackyData)
    }

    private fun fetchDataFromClient(url: String): String? {
        val client = OkHttpClient()

        val request: Request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).execute().use { response -> return response.body?.string() }
    }
}