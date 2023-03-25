package com.technomatesoftware.ergostats.domain.models

enum class MetricsRetrievalModel(val value: Int) {
    ADDRESS_P2PK(0),
    ADDRESS_CONTRACTS(1),
    ADDRESS_MINING(2),
    SUPPLY_P2PK(3),
    SUPPLY_CONTRACTS(4),
    USAGE_VOLUME(5),
    USAGE_TRANSACTIONS(6),
    USAGE_UTXO(7)
}