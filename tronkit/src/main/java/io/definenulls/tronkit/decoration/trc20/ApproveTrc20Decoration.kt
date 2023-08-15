package io.definenulls.tronkit.decoration.trc20

import io.definenulls.tronkit.models.Address
import io.definenulls.tronkit.decoration.TransactionDecoration
import io.definenulls.tronkit.models.TransactionTag
import java.math.BigInteger

class ApproveTrc20Decoration(
    val contractAddress: Address,
    val spender: Address,
    val value: BigInteger
) : TransactionDecoration() {

    override fun tags(userAddress: Address): List<String> =
        listOf(contractAddress.hex, TransactionTag.TRC20_APPROVE)
}
