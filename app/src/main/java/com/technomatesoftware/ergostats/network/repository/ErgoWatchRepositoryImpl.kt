package com.technomatesoftware.ergostats.network.repository

import com.technomatesoftware.ergostats.domain.dao.ErgoWatchDao
import com.technomatesoftware.ergostats.domain.entities.SummaryMetricsEntity
import com.technomatesoftware.ergostats.domain.models.Response
import com.technomatesoftware.ergostats.domain.models.SummaryMetricsModel
import com.technomatesoftware.ergostats.domain.models.SupplyDistributionModel
import com.technomatesoftware.ergostats.network.interfaces.ErgoWatchRepository
import com.technomatesoftware.ergostats.network.services.ErgoWatchService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErgoWatchRepositoryImpl @Inject constructor(
    private val ergoWatchService: ErgoWatchService,
    private val ergoWatchDao: ErgoWatchDao,
) : ErgoWatchRepository {

    override suspend fun fetchSummaryUTXOS(): Flow<Response<List<SummaryMetricsModel>>> =
        flow {
            try {
                emit(Response.Loading)
                val result = ergoWatchService.fetchSummaryUTXOS()
                emit(Response.Success(result))
            } catch (e: Exception) {
                emit(Response.Failure(e))
            }

        }.flowOn(Dispatchers.IO)

    override suspend fun fetchSummaryVolume(): Flow<Response<List<SummaryMetricsModel>>> =
        flow {
            try {
                emit(Response.Loading)
                val result = ergoWatchService.fetchSummaryVolume()
                emit(Response.Success(result))
            } catch (e: Exception) {
                emit(Response.Failure(e))
            }

        }.flowOn(Dispatchers.IO)

    override suspend fun fetchSummaryTransactions(): Flow<Response<List<SummaryMetricsModel>>> =
        flow {
            try {
                emit(Response.Loading)
                val result = ergoWatchService.fetchSummaryTransactions()
                emit(Response.Success(result))
            } catch (e: Exception) {
                emit(Response.Failure(e))
            }

        }.flowOn(Dispatchers.IO)

    override suspend fun fetchSummaryMiners(): Flow<Response<List<SummaryMetricsModel>>> =
        flow {
            try {
                emit(Response.Loading)
                val result = ergoWatchService.fetchSummaryMiners()
                emit(Response.Success(result))
            } catch (e: Exception) {
                emit(Response.Failure(e))
            }

        }.flowOn(Dispatchers.IO)

    override suspend fun fetchSummaryContracts(): Flow<Response<List<SummaryMetricsModel>>> =
        flow {
            try {
                emit(Response.Loading)
                val result = ergoWatchService.fetchSummaryContracts()
                emit(Response.Success(result))
            } catch (e: Exception) {
                emit(Response.Failure(e))
            }

        }.flowOn(Dispatchers.IO)

    override suspend fun fetchSummaryP2pk(): Flow<Response<List<SummaryMetricsModel>>> =
        flow {
            try {
                emit(Response.Loading)
                val result = ergoWatchService.fetchSummaryP2pk()
                emit(Response.Success(result))
            } catch (e: Exception) {
                emit(Response.Failure(e))
            }

        }.flowOn(Dispatchers.IO)

    override suspend fun fetchSupplyDistributionP2pk(): Flow<Response<SupplyDistributionModel>> =
        flow {
            try {
                emit(Response.Loading)
                val result = ergoWatchService.fetchSupplyDistributionP2pk()
                emit(Response.Success(result))
            } catch (e: Exception) {
                emit(Response.Failure(e))
            }

        }.flowOn(Dispatchers.IO)

    override suspend fun fetchSupplyDistributionContracts(): Flow<Response<SupplyDistributionModel>> =
        flow {
            try {
                emit(Response.Loading)
                val result = ergoWatchService.fetchSupplyDistributionContracts()
                emit(Response.Success(result))
            } catch (e: Exception) {
                emit(Response.Failure(e))
            }

        }.flowOn(Dispatchers.IO)

    override suspend fun replaceSummaryUTXOS(metrics: List<SummaryMetricsModel>) {
        if (metrics.isNotEmpty()) {
            ergoWatchDao.clearSummaryUTXOData(true)
            val mappedList = metrics.map {
                SummaryMetricsEntity(
                    label = it.label,
                    current = it.current.toLong(),
                    isUtxos = true,
                    diff1d = it.diff1d.toLong(),
                    diff1w = it.diff1w.toLong(),
                    diff4w = it.diff4w.toLong(),
                    diff6m = it.diff6m.toLong(),
                    diff1y = it.diff1y.toLong()
                )
            }
            ergoWatchDao.insertMetricsData(metrics = mappedList)
        }
    }

    override suspend fun fetchStoredSummaryUTXOS(): Flow<Response<List<SummaryMetricsModel>>> =
        flow {
            try {
                emit(Response.Loading)
                val result = ergoWatchDao.getSummaryUTXOData()
                val mappedList = result.map {
                    SummaryMetricsModel(
                        label = it.label,
                        current = it.current,
                        diff1d = it.diff1d,
                        diff1w = it.diff1w,
                        diff4w = it.diff4w,
                        diff6m = it.diff6m,
                        diff1y = it.diff1y
                    )
                }.toList()
                emit(Response.Success(mappedList))
            } catch (e: Exception) {
                emit(Response.Failure(e))
            }

        }.flowOn(Dispatchers.IO)

    override suspend fun replaceSummaryP2pk(metrics: List<SummaryMetricsModel>) {
        if (metrics.isNotEmpty()) {
            ergoWatchDao.clearSummaryP2pkData()
            val mappedList = metrics.map {
                SummaryMetricsEntity(
                    label = it.label,
                    current = it.current.toLong(),
                    isP2pks = true,
                    diff1d = it.diff1d.toLong(),
                    diff1w = it.diff1w.toLong(),
                    diff4w = it.diff4w.toLong(),
                    diff6m = it.diff6m.toLong(),
                    diff1y = it.diff1y.toLong()
                )
            }
            ergoWatchDao.insertMetricsData(metrics = mappedList)
        }
    }

    override suspend fun fetchStoredSummaryP2pk(): Flow<Response<List<SummaryMetricsModel>>> =
        flow {
            try {
                emit(Response.Loading)
                val result = ergoWatchDao.getSummaryP2pkData()
                val mappedList = result.map {
                    SummaryMetricsModel(
                        label = it.label,
                        current = it.current,
                        diff1d = it.diff1d,
                        diff1w = it.diff1w,
                        diff4w = it.diff4w,
                        diff6m = it.diff6m,
                        diff1y = it.diff1y
                    )
                }.toList()
                emit(Response.Success(mappedList))
            } catch (e: Exception) {
                emit(Response.Failure(e))
            }

        }.flowOn(Dispatchers.IO)

    override suspend fun replaceSummaryVolume(metrics: List<SummaryMetricsModel>) {
        if (metrics.isNotEmpty()) {
            ergoWatchDao.clearSummaryVolumeData()
            val mappedList = metrics.map {
                SummaryMetricsEntity(
                    label = it.label,
                    current = it.current.toLong(),
                    isVolume = true,
                    diff1d = it.diff1d.toLong(),
                    diff1w = it.diff1w.toLong(),
                    diff4w = it.diff4w.toLong(),
                    diff6m = it.diff6m.toLong(),
                    diff1y = it.diff1y.toLong()
                )
            }
            ergoWatchDao.insertMetricsData(metrics = mappedList)
        }
    }

    override suspend fun fetchStoredSummaryVolume(): Flow<Response<List<SummaryMetricsModel>>> =
        flow {
            try {
                emit(Response.Loading)
                val result = ergoWatchDao.getSummaryVolumeData()
                val mappedList = result.map {
                    SummaryMetricsModel(
                        label = it.label,
                        current = it.current,
                        diff1d = it.diff1d,
                        diff1w = it.diff1w,
                        diff4w = it.diff4w,
                        diff6m = it.diff6m,
                        diff1y = it.diff1y
                    )
                }.toList()
                emit(Response.Success(mappedList))
            } catch (e: Exception) {
                emit(Response.Failure(e))
            }

        }.flowOn(Dispatchers.IO)

    override suspend fun replaceSummaryTransactions(metrics: List<SummaryMetricsModel>) {
        if (metrics.isNotEmpty()) {
            ergoWatchDao.clearSummaryTransactionsData()
            val mappedList = metrics.map {
                SummaryMetricsEntity(
                    label = it.label,
                    current = it.current.toLong(),
                    isTransactions = true,
                    diff1d = it.diff1d.toLong(),
                    diff1w = it.diff1w.toLong(),
                    diff4w = it.diff4w.toLong(),
                    diff6m = it.diff6m.toLong(),
                    diff1y = it.diff1y.toLong()
                )
            }
            ergoWatchDao.insertMetricsData(metrics = mappedList)
        }
    }

    override suspend fun fetchStoredSummaryTransactions(): Flow<Response<List<SummaryMetricsModel>>> =
        flow {
            try {
                emit(Response.Loading)
                val result = ergoWatchDao.getSummaryTransactionsData()
                val mappedList = result.map {
                    SummaryMetricsModel(
                        label = it.label,
                        current = it.current,
                        diff1d = it.diff1d,
                        diff1w = it.diff1w,
                        diff4w = it.diff4w,
                        diff6m = it.diff6m,
                        diff1y = it.diff1y
                    )
                }.toList()
                emit(Response.Success(mappedList))
            } catch (e: Exception) {
                emit(Response.Failure(e))
            }

        }.flowOn(Dispatchers.IO)

    override suspend fun replaceSummaryMiners(metrics: List<SummaryMetricsModel>) {
        if (metrics.isNotEmpty()) {
            ergoWatchDao.clearSummaryMinersData()
            val mappedList = metrics.map {
                SummaryMetricsEntity(
                    label = it.label,
                    current = it.current.toLong(),
                    isMiners = true,
                    diff1d = it.diff1d.toLong(),
                    diff1w = it.diff1w.toLong(),
                    diff4w = it.diff4w.toLong(),
                    diff6m = it.diff6m.toLong(),
                    diff1y = it.diff1y.toLong()
                )
            }
            ergoWatchDao.insertMetricsData(metrics = mappedList)
        }
    }

    override suspend fun fetchStoredSummaryMiners(): Flow<Response<List<SummaryMetricsModel>>> =
        flow {
            try {
                emit(Response.Loading)
                val result = ergoWatchDao.getSummaryMinersData()
                val mappedList = result.map {
                    SummaryMetricsModel(
                        label = it.label,
                        current = it.current,
                        diff1d = it.diff1d,
                        diff1w = it.diff1w,
                        diff4w = it.diff4w,
                        diff6m = it.diff6m,
                        diff1y = it.diff1y
                    )
                }.toList()
                emit(Response.Success(mappedList))
            } catch (e: Exception) {
                emit(Response.Failure(e))
            }

        }.flowOn(Dispatchers.IO)

    override suspend fun replaceSummaryContracts(metrics: List<SummaryMetricsModel>) {
        if (metrics.isNotEmpty()) {
            ergoWatchDao.clearSummaryContractsData()
            val mappedList = metrics.map {
                SummaryMetricsEntity(
                    label = it.label,
                    current = it.current.toLong(),
                    isContracts = true,
                    diff1d = it.diff1d.toLong(),
                    diff1w = it.diff1w.toLong(),
                    diff4w = it.diff4w.toLong(),
                    diff6m = it.diff6m.toLong(),
                    diff1y = it.diff1y.toLong()
                )
            }
            ergoWatchDao.insertMetricsData(metrics = mappedList)
        }
    }

    override suspend fun fetchStoredSummaryContracts(): Flow<Response<List<SummaryMetricsModel>>> =
        flow {
            try {
                emit(Response.Loading)
                val result = ergoWatchDao.getSummaryContractsData()
                val mappedList = result.map {
                    SummaryMetricsModel(
                        label = it.label,
                        current = it.current,
                        diff1d = it.diff1d,
                        diff1w = it.diff1w,
                        diff4w = it.diff4w,
                        diff6m = it.diff6m,
                        diff1y = it.diff1y
                    )
                }.toList()
                emit(Response.Success(mappedList))
            } catch (e: Exception) {
                emit(Response.Failure(e))
            }

        }.flowOn(Dispatchers.IO)

    override suspend fun replaceSupplyDistributionP2pk(metrics: List<SummaryMetricsModel>) {
        if (metrics.isNotEmpty()) {
            ergoWatchDao.clearSupplyDistributionP2pkData()
            val mappedList = metrics.map {
                SummaryMetricsEntity(
                    label = it.label,
                    current = it.current.toLong(),
                    isRelative = true,
                    isP2pks = true,
                    diff1d = it.diff1d.toLong(),
                    diff1w = it.diff1w.toLong(),
                    diff4w = it.diff4w.toLong(),
                    diff6m = it.diff6m.toLong(),
                    diff1y = it.diff1y.toLong()
                )
            }
            ergoWatchDao.insertMetricsData(metrics = mappedList)
        }
    }

    override suspend fun fetchStoredSupplyDistributionP2pk(): Flow<Response<List<SummaryMetricsModel>>> =
        flow {
            try {
                emit(Response.Loading)
                val result = ergoWatchDao.getSupplyDistributionP2pkData()
                val mappedList = result.map {
                    SummaryMetricsModel(
                        label = it.label,
                        current = it.current,
                        diff1d = it.diff1d,
                        diff1w = it.diff1w,
                        diff4w = it.diff4w,
                        diff6m = it.diff6m,
                        diff1y = it.diff1y
                    )
                }.toList()
                emit(Response.Success(mappedList))
            } catch (e: Exception) {
                emit(Response.Failure(e))
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun replaceSupplyDistributionContracts(metrics: List<SummaryMetricsModel>) {
        if (metrics.isNotEmpty()) {
            ergoWatchDao.clearSupplyDistributionContractsData()
            val mappedList = metrics.map {
                SummaryMetricsEntity(
                    label = it.label,
                    current = it.current.toLong(),
                    isRelative = true,
                    isContracts = true,
                    diff1d = it.diff1d.toLong(),
                    diff1w = it.diff1w.toLong(),
                    diff4w = it.diff4w.toLong(),
                    diff6m = it.diff6m.toLong(),
                    diff1y = it.diff1y.toLong()
                )
            }
            ergoWatchDao.insertMetricsData(metrics = mappedList)
        }
    }

    override suspend fun fetchStoredSupplyDistributionContracts(): Flow<Response<List<SummaryMetricsModel>>> =
        flow {
            try {
                emit(Response.Loading)
                val result = ergoWatchDao.getSupplyDistributionContractsData()
                val mappedList = result.map {
                    SummaryMetricsModel(
                        label = it.label,
                        current = it.current,
                        diff1d = it.diff1d,
                        diff1w = it.diff1w,
                        diff4w = it.diff4w,
                        diff6m = it.diff6m,
                        diff1y = it.diff1y
                    )
                }.toList()
                emit(Response.Success(mappedList))
            } catch (e: Exception) {
                emit(Response.Failure(e))
            }
        }.flowOn(Dispatchers.IO)


}