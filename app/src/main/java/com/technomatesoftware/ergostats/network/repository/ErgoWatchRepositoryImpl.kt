package com.technomatesoftware.ergostats.network.repository

import com.technomatesoftware.ergostats.domain.dao.ErgoWatchDao
import com.technomatesoftware.ergostats.domain.entities.SummaryMetricsEntity
import com.technomatesoftware.ergostats.domain.models.AddressChartDataModel
import com.technomatesoftware.ergostats.domain.models.Response
import com.technomatesoftware.ergostats.domain.models.SummaryMetricsModel
import com.technomatesoftware.ergostats.domain.models.SupplyDistributionModel
import com.technomatesoftware.ergostats.domain.models.UTXOChartDataModel
import com.technomatesoftware.ergostats.domain.models.UsageChartDataModel
import com.technomatesoftware.ergostats.network.interfaces.ErgoWatchRepository
import com.technomatesoftware.ergostats.network.services.ErgoWatchService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErgoWatchRepositoryImpl @Inject constructor(
    private val ergoWatchService: ErgoWatchService,
    private val ergoWatchDao: ErgoWatchDao,
) : ErgoWatchRepository {
    private val storageMultiplier: Int = 10000
    private val retrievalDivider: Double = 100.0
    override suspend fun fetchSummaryUTXOS(): Flow<Response<List<SummaryMetricsModel>>> =
        flow {
            emit(Response.Loading)
            val result = ergoWatchService.fetchSummaryUTXOS()
            emit(Response.Success(result))
        }.flowOn(Dispatchers.IO).catch { err -> emit(Response.Failure(Exception(err))) }

    override suspend fun fetchSummaryVolume(): Flow<Response<List<SummaryMetricsModel>>> =
        flow {
            emit(Response.Loading)
            val result = ergoWatchService.fetchSummaryVolume()
            emit(Response.Success(result))
        }.flowOn(Dispatchers.IO).catch { err -> emit(Response.Failure(Exception(err))) }

    override suspend fun fetchSummaryTransactions(): Flow<Response<List<SummaryMetricsModel>>> =
        flow {
            emit(Response.Loading)
            val result = ergoWatchService.fetchSummaryTransactions()
            emit(Response.Success(result))
        }.flowOn(Dispatchers.IO).catch { err -> emit(Response.Failure(Exception(err))) }

    override suspend fun fetchSummaryMiners(): Flow<Response<List<SummaryMetricsModel>>> =
        flow {
            emit(Response.Loading)
            val result = ergoWatchService.fetchSummaryMiners()
            emit(Response.Success(result))
        }.flowOn(Dispatchers.IO).catch { err -> emit(Response.Failure(Exception(err))) }

    override suspend fun fetchSummaryContracts(): Flow<Response<List<SummaryMetricsModel>>> =
        flow {
            emit(Response.Loading)
            val result = ergoWatchService.fetchSummaryContracts()
            emit(Response.Success(result))

        }.flowOn(Dispatchers.IO).catch { err -> emit(Response.Failure(Exception(err))) }

    override suspend fun fetchSummaryP2pk(): Flow<Response<List<SummaryMetricsModel>>> =
        flow {
            emit(Response.Loading)
            val result = ergoWatchService.fetchSummaryP2pk()
            emit(Response.Success(result))
        }.flowOn(Dispatchers.IO).catch { err -> emit(Response.Failure(Exception(err))) }

    override suspend fun fetchSupplyDistributionP2pk(): Flow<Response<SupplyDistributionModel>> =
        flow {
            emit(Response.Loading)
            val result = ergoWatchService.fetchSupplyDistributionP2pk()
            emit(Response.Success(result))
        }.flowOn(Dispatchers.IO).catch { err -> emit(Response.Failure(Exception(err))) }

    override suspend fun fetchSupplyDistributionContracts(): Flow<Response<SupplyDistributionModel>> =
        flow {
            emit(Response.Loading)
            val result = ergoWatchService.fetchSupplyDistributionContracts()
            emit(Response.Success(result))

        }.flowOn(Dispatchers.IO).catch { err -> emit(Response.Failure(Exception(err))) }

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
        }.flowOn(Dispatchers.IO).catch { err -> emit(Response.Failure(Exception(err))) }

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
        }.flowOn(Dispatchers.IO).catch { err -> emit(Response.Failure(Exception(err))) }

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
        }.flowOn(Dispatchers.IO).catch { err -> emit(Response.Failure(Exception(err))) }

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
        }.flowOn(Dispatchers.IO).catch { err -> emit(Response.Failure(Exception(err))) }

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

        }.flowOn(Dispatchers.IO).catch { err -> emit(Response.Failure(Exception(err))) }

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

        }.flowOn(Dispatchers.IO).catch { err -> emit(Response.Failure(Exception(err))) }

    override suspend fun replaceSupplyDistributionP2pk(metrics: List<SummaryMetricsModel>) {
        if (metrics.isNotEmpty()) {
            ergoWatchDao.clearSupplyDistributionP2pkData()
            val mappedList = metrics.map {
                SummaryMetricsEntity(
                    label = it.label,
                    current = it.current.toDouble().times(storageMultiplier).toLong(),
                    isRelative = true,
                    isP2pks = true,
                    diff1d = it.diff1d.toDouble().times(storageMultiplier).toLong(),
                    diff1w = it.diff1w.toDouble().times(storageMultiplier).toLong(),
                    diff4w = it.diff4w.toDouble().times(storageMultiplier).toLong(),
                    diff6m = it.diff6m.toDouble().times(storageMultiplier).toLong(),
                    diff1y = it.diff1y.toDouble().times(storageMultiplier).toLong()
                )
            }
            ergoWatchDao.insertMetricsData(metrics = mappedList)
        }
    }

    override suspend fun fetchStoredSupplyDistributionP2pk(): Flow<Response<List<SummaryMetricsModel>>> =
        flow {
            emit(Response.Loading)
            val result = ergoWatchDao.getSupplyDistributionP2pkData()
            val mappedList = result.map {
                SummaryMetricsModel(
                    label = it.label,
                    current = it.current.div(retrievalDivider),
                    diff1d = it.diff1d.div(retrievalDivider),
                    diff1w = it.diff1w.div(retrievalDivider),
                    diff4w = it.diff4w.div(retrievalDivider),
                    diff6m = it.diff6m.div(retrievalDivider),
                    diff1y = it.diff1y.div(retrievalDivider)
                )
            }.toList()
            emit(Response.Success(mappedList))
        }.flowOn(Dispatchers.IO).catch { err -> emit(Response.Failure(Exception(err))) }

    override suspend fun replaceSupplyDistributionContracts(metrics: List<SummaryMetricsModel>) {
        //Multiplying by 10000 to store the decimal values on a long
        if (metrics.isNotEmpty()) {
            ergoWatchDao.clearSupplyDistributionContractsData()
            val mappedList = metrics.map {
                SummaryMetricsEntity(
                    label = it.label,
                    current = it.current.toDouble().times(storageMultiplier).toLong(),
                    isRelative = true,
                    isContracts = true,
                    diff1d = it.diff1d.toDouble().times(storageMultiplier).toLong(),
                    diff1w = it.diff1w.toDouble().times(storageMultiplier).toLong(),
                    diff4w = it.diff4w.toDouble().times(storageMultiplier).toLong(),
                    diff6m = it.diff6m.toDouble().times(storageMultiplier).toLong(),
                    diff1y = it.diff1y.toDouble().times(storageMultiplier).toLong()
                )
            }
            ergoWatchDao.insertMetricsData(metrics = mappedList)
        }
    }

    override suspend fun fetchStoredSupplyDistributionContracts(): Flow<Response<List<SummaryMetricsModel>>> =
        flow {
            emit(Response.Loading)
            val result = ergoWatchDao.getSupplyDistributionContractsData()
            val mappedList = result.map {
                SummaryMetricsModel(
                    label = it.label,
                    current = it.current.div(retrievalDivider),
                    diff1d = it.diff1d.div(retrievalDivider),
                    diff1w = it.diff1w.div(retrievalDivider),
                    diff4w = it.diff4w.div(retrievalDivider),
                    diff6m = it.diff6m.div(retrievalDivider),
                    diff1y = it.diff1y.div(retrievalDivider)
                )
            }.toList()
            emit(Response.Success(mappedList))
        }.flowOn(Dispatchers.IO).catch { err -> emit(Response.Failure(Exception(err))) }

    override suspend fun fetchSummaryP2pkChartData(): Flow<Response<AddressChartDataModel>> =
        flow {
            val currentDate = LocalDateTime.now()
            val dateOneMonthAgo = currentDate.minusMonths(1)
            emit(Response.Loading)
            val result = ergoWatchService.fetchSummaryAddressChartData(
                addressType = "p2pk",
                startTime = dateOneMonthAgo.toEpochSecond(ZoneOffset.UTC) * 1000,
                endTime = currentDate.toEpochSecond(ZoneOffset.UTC) * 1000,
                timeWindowResolution = "24h",
                priceData = false
            )
            emit(Response.Success(result))
        }.flowOn(Dispatchers.IO).catch { err -> emit(Response.Failure(Exception(err))) }

    override suspend fun fetchSummaryContractsChartData(): Flow<Response<AddressChartDataModel>> =
        flow {
            val currentDate = LocalDateTime.now()
            val dateOneMonthAgo = currentDate.minusMonths(1)
            emit(Response.Loading)
            val result = ergoWatchService.fetchSummaryAddressChartData(
                addressType = "contracts",
                startTime = dateOneMonthAgo.toEpochSecond(ZoneOffset.UTC) * 1000,
                endTime = currentDate.toEpochSecond(ZoneOffset.UTC) * 1000,
                timeWindowResolution = "24h",
                priceData = false
            )
            emit(Response.Success(result))
        }.flowOn(Dispatchers.IO).catch { err -> emit(Response.Failure(Exception(err))) }

    override suspend fun fetchSummaryMinersChartData(): Flow<Response<AddressChartDataModel>> =
        flow {
            val currentDate = LocalDateTime.now()
            val dateOneMonthAgo = currentDate.minusMonths(1)
            emit(Response.Loading)
            val result = ergoWatchService.fetchSummaryAddressChartData(
                addressType = "miners",
                startTime = dateOneMonthAgo.toEpochSecond(ZoneOffset.UTC) * 1000,
                endTime = currentDate.toEpochSecond(ZoneOffset.UTC) * 1000,
                timeWindowResolution = "24h",
                priceData = false
            )
            emit(Response.Success(result))
        }.flowOn(Dispatchers.IO).catch { err -> emit(Response.Failure(Exception(err))) }

    override suspend fun fetchSummaryTransactionsChartData(): Flow<Response<UsageChartDataModel>> =
        flow {
            val currentDate = LocalDateTime.now()
            val dateOneMonthAgo = currentDate.minusMonths(1)
            emit(Response.Loading)
            val result = ergoWatchService.fetchUsageChartData(
                usageType = "transactions",
                startTime = dateOneMonthAgo.toEpochSecond(ZoneOffset.UTC) * 1000,
                endTime = currentDate.toEpochSecond(ZoneOffset.UTC) * 1000,
                timeWindowResolution = "24h",
                priceData = false
            )
            emit(Response.Success(result))
        }.flowOn(Dispatchers.IO).catch { err -> emit(Response.Failure(Exception(err))) }

    override suspend fun fetchSummaryUTXOsChartData(): Flow<Response<UTXOChartDataModel>> =
        flow {
            val currentDate = LocalDateTime.now()
            val dateOneMonthAgo = currentDate.minusMonths(1)
            emit(Response.Loading)
            val result = ergoWatchService.fetchUsageUTXOChartData(
                startTime = dateOneMonthAgo.toEpochSecond(ZoneOffset.UTC) * 1000,
                endTime = currentDate.toEpochSecond(ZoneOffset.UTC) * 1000,
                timeWindowResolution = "24h",
                priceData = false
            )
            emit(Response.Success(result))
        }.flowOn(Dispatchers.IO).catch { err -> emit(Response.Failure(Exception(err))) }

    override suspend fun fetchSummaryVolumeChartData(): Flow<Response<UsageChartDataModel>> =
        flow {
            val currentDate = LocalDateTime.now()
            val dateOneMonthAgo = currentDate.minusMonths(1)
            emit(Response.Loading)
            val result = ergoWatchService.fetchUsageChartData(
                usageType = "volume",
                startTime = dateOneMonthAgo.toEpochSecond(ZoneOffset.UTC) * 1000,
                endTime = currentDate.toEpochSecond(ZoneOffset.UTC) * 1000,
                timeWindowResolution = "24h",
                priceData = false
            )
            emit(Response.Success(result))
        }.flowOn(Dispatchers.IO).catch { err -> emit(Response.Failure(Exception(err))) }
}