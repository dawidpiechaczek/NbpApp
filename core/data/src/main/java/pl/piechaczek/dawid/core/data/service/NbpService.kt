package pl.piechaczek.dawid.core.data.service

import io.reactivex.Single
import pl.piechaczek.dawid.core.data.model.TableModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NbpService {

    @GET("exchangerates/tables/{table}")
    fun getTable(@Path("table") tableId: Char): Single<Response<List<TableModel>>>
}