package pl.piechaczek.dawid.table.data.service

import io.reactivex.Single
import pl.piechaczek.dawid.core.data.model.TableModel
import retrofit2.Response

interface TableService {
     fun getTable(tableType: Char): Single<Response<List<TableModel>>>
}