package io.definenulls.tronkit.decoration.trc20

import io.definenulls.tronkit.models.Address
import io.definenulls.tronkit.decoration.Event
import io.definenulls.tronkit.decoration.TokenInfo
import io.definenulls.tronkit.models.TransactionTag
import io.definenulls.tronkit.models.Trc20EventRecord
import java.math.BigInteger

class Trc20ApproveEvent(
    record: Trc20EventRecord
) : Event(record.transactionHash, record.contractAddress) {

    val owner: Address = record.from
    val spender: Address = record.to
    val value: BigInteger = record.value
    val tokenInfo: TokenInfo = TokenInfo(record.tokenName, record.tokenSymbol, record.tokenDecimal)

    override fun tags(userAddress: Address): List<String> {
        return mutableListOf(contractAddress.hex, TransactionTag.TRC20_APPROVE)
    }
}
