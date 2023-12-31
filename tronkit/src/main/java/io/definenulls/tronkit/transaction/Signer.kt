package io.definenulls.tronkit.transaction

import io.definenulls.hdwalletkit.ECKey
import io.definenulls.hdwalletkit.HDWallet
import io.definenulls.tronkit.crypto.Utils
import io.definenulls.tronkit.hexStringToByteArray
import io.definenulls.tronkit.models.Address
import io.definenulls.tronkit.network.CreatedTransaction
import io.definenulls.tronkit.network.Network
import io.definenulls.tronkit.toRawHexString
import java.math.BigInteger

class Signer(
    private val privateKey: BigInteger
) {

    fun sign(createdTransaction: CreatedTransaction): ByteArray {
        val rawTransactionHash = io.definenulls.hdwalletkit.Utils.sha256(createdTransaction.raw_data_hex.hexStringToByteArray())

        return Utils.ellipticSign(rawTransactionHash, privateKey)
    }

    companion object {
        fun getInstance(seed: ByteArray, network: Network): Signer {
            return Signer(privateKey(seed, network))
        }

        fun privateKey(seed: ByteArray, network: Network): BigInteger {
            val hdWallet = HDWallet(seed, network.coinType, HDWallet.Purpose.BIP44)
            return hdWallet.privateKey(0, 0, true).privKey
        }

        fun address(privateKey: BigInteger, network: Network): Address {
            val publicKey = ECKey(privateKey, false).pubKey.drop(1).toByteArray()
            val raw = byteArrayOf(network.addressPrefixByte) + Utils.sha3(publicKey).takeLast(20).toByteArray()
            return Address(raw)
        }
    }
}