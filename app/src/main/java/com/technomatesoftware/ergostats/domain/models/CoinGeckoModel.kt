package com.technomatesoftware.ergostats.domain.models

import com.google.gson.annotations.SerializedName

data class CoinGeckoModel(
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
   @SerializedName("sparkline_in_7d")
   var sparklineIn7D: SparkLineModel?,
   @SerializedName("price_change_percentage_24h_in_currency")
   var priceChangePercentage24HInCurrency: Double?
)