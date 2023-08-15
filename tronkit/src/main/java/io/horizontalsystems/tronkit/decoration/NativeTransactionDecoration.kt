package io.definenulls.tronkit.decoration

import io.definenulls.tronkit.models.Address
import io.definenulls.tronkit.models.Contract
import io.definenulls.tronkit.models.TransactionTag
import io.definenulls.tronkit.models.TransferAssetContract
import io.definenulls.tronkit.models.TransferContract

class NativeTransactionDecoration(
    val contract: Contract
) : TransactionDecoration() {

    override fun tags(userAddress: Address): List<String> {
        val tags = mutableListOf<String>()

        when (contract) {
            is TransferContract -> {
                if (contract.ownerAddress == userAddress) {
                    tags.add(TransactionTag.TRX_COIN_OUTGOING)
                    tags.add(TransactionTag.OUTGOING)
                }
                if (contract.toAddress == userAddress) {
                    tags.add(TransactionTag.TRX_COIN_INCOMING)
                    tags.add(TransactionTag.INCOMING)
                }
            }

            is TransferAssetContract -> {
                if (contract.ownerAddress == userAddress) {
                    tags.add(TransactionTag.trc10Outgoing(contract.assetName))
                    tags.add(TransactionTag.OUTGOING)
                }
                if (contract.toAddress == userAddress) {
                    tags.add(TransactionTag.trc10Incoming(contract.assetName))
                    tags.add(TransactionTag.INCOMING)
                }
            }

            else -> {}
        }

        return tags
    }

}
