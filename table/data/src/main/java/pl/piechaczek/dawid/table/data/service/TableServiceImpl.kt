package pl.piechaczek.dawid.table.data.service

import io.reactivex.Single
import pl.piechaczek.dawid.core.data.service.NbpService
import pl.piechaczek.dawid.core.data.model.TableModel
import retrofit2.Response
import javax.inject.Inject

class TableServiceImpl @Inject constructor(
    private val service: NbpService
) : TableService {

    override fun getTable(tableType: Char): Single<Response<List<TableModel>>> =
        service.getTable(tableType)
}