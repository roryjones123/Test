package com.masterwork.jlptest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val productRepo = ProductRepo()

    private val _productData = MutableLiveData<ProductRangeData>()
    val productRangeData: LiveData<ProductRangeData>
        get() = _productData

    private val _focusedProduct = MutableLiveData<DetailsDatum>()
    val focusedProduct: LiveData<DetailsDatum>
        get() = _focusedProduct


    fun fetchProductData(hackyData: String) {
        GlobalScope.launch {
            _productData.postValue(productRepo.getProductRangeData(hackyData))
        }
    }

    fun onItemClick(productId: String, hackyData: String) {
        GlobalScope.launch {
            val fetchedProduct = productRepo.getProductData(hackyData)?.detailsData?.find {
                it.productID == productId
            }

            _focusedProduct.postValue(fetchedProduct)
        }
    }
}