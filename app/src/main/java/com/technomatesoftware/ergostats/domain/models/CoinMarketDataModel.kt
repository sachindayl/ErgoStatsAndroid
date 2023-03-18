package com.technomatesoftware.ergostats.domain.models

import com.google.gson.annotations.SerializedName
import com.technomatesoftware.ergostats.domain.entities.CoinMarketDataEntity
import java.math.RoundingMode
import java.text.DecimalFormat

data class CoinMarketDataModel(
    var id: String,
    var symbol: String,
    var name: String,
    var image: String?,
    @SerializedName("current_price")
    var currentPrice: Double?,
    @SerializedName("market_cap")
    var marketCap: Double?,
    @SerializedName("market_cap_rank")
    var marketCapRank: Double?,
    @SerializedName("fully_diluted_valuation")
    var fullyDilutedValuation: Double?,
    @SerializedName("total_volume")
    var totalVolume: Double?,
    @SerializedName("high_24h")
    var high24H: Double?,
    @SerializedName("low_24h")
    var low24H: Double?,
    @SerializedName("price_change_24h")
    var priceChange24H: Double?,
    @SerializedName("price_change_percentage_24h")
    var priceChangePercentage24H: Double?,
    @SerializedName("market_cap_change_24h")
    var marketCapChange24H: Double?,
    @SerializedName("market_cap_change_percentage_24h")
    var marketCapChangePercentage24H: Double?,
    @SerializedName("circulating_supply")
    var circulatingSupply: Double?,
    @SerializedName("total_supply")
    var totalSupply: Double?,
    @SerializedName("max_supply")
    var maxSupply: Double?,
    var ath: Double?,
    @SerializedName("ath_change_percentage")
    var athChangePercentage: Double?,
    @SerializedName("ath_date")
    var athDate: String?,
    var atl: Double?,
    @SerializedName("atl_change_percentage")
    var atlChangePercentage: Double?,
    @SerializedName("atl_date")
    var atlDate: String?,
    var roi: Double?,
    @SerializedName("last_updated")
    var lastUpdated: String?,
    @SerializedName("price_change_percentage_24h_in_currency")
    var priceChangePercentage24HInCurrency: Double?
) {
    fun formattedPriceChangePercentage24H(): String? {
        if (priceChangePercentage24H != null) {
            return priceChangePercentage24H?.toBigDecimal()?.setScale(2, RoundingMode.HALF_UP)
                ?.toString()
        }
        return "0.00"
    }

    fun formattedMarketCapChangePercentage24H(): String? {
        if (marketCapChangePercentage24H != null) {
            return marketCapChangePercentage24H?.toBigDecimal()?.setScale(2, RoundingMode.HALF_UP)
                ?.toString()
        }
        return "0.00"
    }

    fun formattedMarketCap(): String {
        if (marketCap != null) {
            val formatter = DecimalFormat("#,###")
            return "$${formatter.format(marketCap)}"
        }
        return "0.00"
    }

    fun formattedCurrentPrice(): String {
        if (currentPrice != null) {
            val formatter = DecimalFormat("#,###.00")
            return "$${formatter.format(currentPrice)}"
        }
        return "0.00"
    }

    fun formattedTotalVolume(): String {
        if (totalVolume != null) {
            val formatter = DecimalFormat("#,###")
            return "$${formatter.format(totalVolume)}"
        }
        return "0.00"
    }

    fun formattedLow24h(): String {
        if (low24H != null) {
            val formatter = DecimalFormat("#,###.00")
            return "$${formatter.format(low24H)}"
        }
        return "0.00"
    }

    fun formattedHigh24h(): String {
        if (high24H != null) {
            val formatter = DecimalFormat("#,###.00")
            return "$${formatter.format(high24H)}"
        }
        return "0.00"
    }

    fun toCoinMarketDataEntity(): CoinMarketDataEntity {
        return CoinMarketDataEntity(
           coinId = id,
            symbol = symbol,
            name = name,
            image = image,
            currentPrice = currentPrice,
            marketCap = marketCap,
            marketCapRank = marketCapRank,
            fullyDilutedValuation = fullyDilutedValuation,
            totalVolume = totalVolume,
            high24H = high24H,
            low24H = low24H,
            priceChange24H = priceChange24H,
            priceChangePercentage24H = priceChangePercentage24H,
            marketCapChange24H = marketCapChange24H,
            marketCapChangePercentage24H = marketCapChangePercentage24H,
            circulatingSupply = circulatingSupply,
            totalSupply = totalSupply,
            maxSupply = maxSupply,
            ath = ath,
            athChangePercentage = athChangePercentage,
            athDate = athDate,
            atl = atl,
            atlChangePercentage = atlChangePercentage,
            atlDate = atlDate,
            lastUpdated = lastUpdated,
            roi = roi,
            priceChangePercentage24HInCurrency = priceChangePercentage24HInCurrency
        )
    }


}
