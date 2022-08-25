package com.farmershop.data.repository

import com.farmershop.data.network.RetrofitBuilder

class SearchRepository {
    suspend fun getSearchProduct(searchKey:String,categoryId:String,subCategoryId: String,
        pageSize: String,pageNo: String,customerId: Int) =
        RetrofitBuilder.apiService?.searchProduct(searchKey,categoryId,subCategoryId,pageSize,pageNo,customerId)

}