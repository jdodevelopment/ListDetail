package ar.com.jdodevelopment.listdetail.data.api

import ar.com.jdodevelopment.listdetail.data.model.Product
import retrofit2.Response
import retrofit2.http.GET

interface ProductsApi {

    @GET("list")
    suspend fun getList(): Response<List<Product>>

}