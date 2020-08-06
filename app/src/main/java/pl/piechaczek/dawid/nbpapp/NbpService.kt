package pl.piechaczek.dawid.nbpapp

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NbpService {

    @GET("exchangerates/tables/{table}")
    fun getTable(@Path("table") tableId: Char): Single<Response<List<TableModel>>>
}