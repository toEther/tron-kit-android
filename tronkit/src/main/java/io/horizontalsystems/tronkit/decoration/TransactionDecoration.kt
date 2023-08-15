package io.definenulls.tronkit.decoration

import io.definenulls.tronkit.models.Address

open class TransactionDecoration {
    open fun tags(userAddress: Address): List<String> = listOf()
}
