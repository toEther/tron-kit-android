package io.definenulls.tronkit.decoration.trc20

import io.definenulls.tronkit.models.Address
import io.definenulls.tronkit.decoration.Event
import io.definenulls.tronkit.decoration.TokenInfo
import io.definenulls.tronkit.models.TransactionTag
import io.definenulls.tronkit.models.Trc20EventRecord
import java.math.BigInteger

class Trc20TransferEvent(
    record: Trc20EventRecord
) : Event(record.transactionHash, record.contractAddress) {

    val from: Address = record.from
    val to: Address = record.to
    val value: BigInteger = record.value
    val tokenInfo: TokenInfo = TokenInfo(record.tokenName, record.tokenSymbol, record.tokenDecimal)

    override fun tags(userAddress: Address): List<String> {
        val tags = mutableListOf(contractAddress.hex, TransactionTag.TRC20_TRANSFER)

        if (from == userAddress) {
            tags.add(TransactionTag.trc20Outgoing(contractAddress.hex))
            tags.add(TransactionTag.OUTGOING)
        }

        if (to == userAddress) {
            tags.add(TransactionTag.trc20Incoming(contractAddress.hex))
            tags.add(TransactionTag.INCOMING)
        }

        return tags
    }
}
