package io.definenulls.tronkit.decoration

import io.definenulls.tronkit.database.Storage
import io.definenulls.tronkit.models.Contract
import io.definenulls.tronkit.models.FullTransaction
import io.definenulls.tronkit.models.InternalTransaction
import io.definenulls.tronkit.models.Transaction
import io.definenulls.tronkit.models.Trc20EventRecord
import io.definenulls.tronkit.models.TriggerSmartContract

class DecorationManager(
    private val storage: Storage
) {
    private val transactionDecorators = mutableListOf<ITransactionDecorator>()

    fun addTransactionDecorator(decorator: ITransactionDecorator) {
        transactionDecorators.add(decorator)
    }

    fun decorateTransaction(contract: Contract): TransactionDecoration {
        if (contract is TriggerSmartContract) {
            for (decorator in transactionDecorators) {
                val decoration = decorator.decoration(contract, listOf(), listOf())
                if (decoration != null) return decoration
            }
        }

        return NativeTransactionDecoration(contract)
    }

    fun decorateTransactions(transactions: List<Transaction>): List<FullTransaction> {
        val internalTransactionsMap: MutableMap<String, List<InternalTransaction>> = getInternalTransactionsMap(transactions).toMutableMap()
        val eventsMap = getEventsMap(transactions)

        return transactions.map { transaction ->
            val decoration = decoration(
                contract = transaction.contract,
                internalTransactions = internalTransactionsMap[transaction.hashString] ?: listOf(),
                events = eventsMap[transaction.hashString] ?: listOf()
            )

            return@map FullTransaction(transaction, decoration)
        }
    }

    private fun getEventsMap(transactions: List<Transaction>): Map<String, List<Event>> {
        val trc20EventRecords: List<Trc20EventRecord> = if (transactions.size > 100) {
            storage.getTrc20Events()
        } else {
            val hashes = transactions.map { it.hash }
            storage.getTrc20EventsByHashes(hashes)
        }

        val map: MutableMap<String, List<Event>> = mutableMapOf()

        for (trc20EventRecord in trc20EventRecords) {
            val event = EventHelper.eventFromRecord(trc20EventRecord)
            val currentEvents = map[trc20EventRecord.hashString] ?: mutableListOf()

            if (event != null) {
                map[trc20EventRecord.hashString] = currentEvents + event
            }
        }

        return map
    }

    private fun getInternalTransactionsMap(transactions: List<Transaction>): Map<String, List<InternalTransaction>> {
        val internalTransactions: List<InternalTransaction> = if (transactions.size > 100) {
            storage.getInternalTransactions()
        } else {
            val hashes = transactions.map { it.hash }
            storage.getInternalTransactionsByHashes(hashes)
        }

        val map: MutableMap<String, List<InternalTransaction>> = mutableMapOf()

        for (internalTransaction in internalTransactions) {
            map[internalTransaction.hashString] = (map[internalTransaction.hashString] ?: mutableListOf()) + listOf(internalTransaction)
        }

        return map
    }

    private fun decoration(
        contract: Contract?,
        internalTransactions: List<InternalTransaction> = listOf(),
        events: List<Event> = listOf()
    ): TransactionDecoration {
        when (contract) {
            null -> {
                return UnknownTransactionDecoration(
                    contract = null,
                    internalTransactions = internalTransactions,
                    events = events
                )
            }

            is TriggerSmartContract -> {
                for (decorator in transactionDecorators) {
                    val decoration = decorator.decoration(contract, internalTransactions, events)
                    if (decoration != null) return decoration
                }
                return UnknownTransactionDecoration(contract, internalTransactions, events)
            }

            else -> {
                return NativeTransactionDecoration(contract)
            }
        }
    }
}
