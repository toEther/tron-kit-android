package io.definenulls.tronkit.decoration.trc20

import io.definenulls.tronkit.models.Address
import io.definenulls.tronkit.decoration.TokenInfo
import io.definenulls.tronkit.decoration.TransactionDecoration
import io.definenulls.tronkit.models.TransactionTag
import java.math.BigInteger

class OutgoingTrc20Decoration(
    val contractAddress: Address,
    val to: Address,
    val value: BigInteger,
    val sentToSelf: Boolean,
    val tokenInfo: TokenInfo?
) : TransactionDecoration() {

    override fun tags(userAddress: Address): List<String> =
        listOf(contractAddress.hex, TransactionTag.TRC20_TRANSFER, TransactionTag.trc20Outgoing(contractAddress.hex), TransactionTag.OUTGOING)

}
