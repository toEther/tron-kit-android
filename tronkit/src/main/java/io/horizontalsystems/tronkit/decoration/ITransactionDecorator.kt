package io.definenulls.tronkit.decoration

import io.definenulls.tronkit.models.InternalTransaction
import io.definenulls.tronkit.models.TriggerSmartContract

interface ITransactionDecorator {
    fun decoration(
        contract: TriggerSmartContract,
        internalTransactions: List<InternalTransaction>,
        events: List<Event>
    ): TransactionDecoration?
}
