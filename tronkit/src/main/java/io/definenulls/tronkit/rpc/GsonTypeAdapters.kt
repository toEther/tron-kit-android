package io.definenulls.tronkit.rpc

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import io.definenulls.tronkit.models.Address
import io.definenulls.tronkit.hexStringToBigIntegerOrNull
import io.definenulls.tronkit.hexStringToByteArrayOrNull
import io.definenulls.tronkit.hexStringToIntOrNull
import io.definenulls.tronkit.hexStringToLongOrNull
import io.definenulls.tronkit.toHexString
import java.math.BigInteger

class BigIntegerTypeAdapter(private val isHex: Boolean = true) : TypeAdapter<BigInteger?>() {
    override fun write(writer: JsonWriter, value: BigInteger?) {
        if (value == null) {
            writer.nullValue()
        } else {
            if (isHex)
                writer.value(value.toHexString())
            else
                writer.value(value)
        }
    }

    override fun read(reader: JsonReader): BigInteger? {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull()
            return null
        }
        return if (isHex)
            reader.nextString().hexStringToBigIntegerOrNull()
        else
            BigInteger(reader.nextString())
    }
}

class LongTypeAdapter(private val isHex: Boolean = true) : TypeAdapter<Long?>() {
    override fun write(writer: JsonWriter, value: Long?) {
        if (value == null) {
            writer.nullValue()
        } else {
            if (isHex)
                writer.value(value.toHexString())
            else
                writer.value(value)
        }
    }

    override fun read(reader: JsonReader): Long? {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull()
            return null
        }
        return if (isHex)
            reader.nextString().hexStringToLongOrNull()
        else
            reader.nextString().toLongOrNull()
    }
}

class IntTypeAdapter(private val isHex: Boolean = true) : TypeAdapter<Int?>() {
    override fun write(writer: JsonWriter, value: Int?) {
        if (value == null) {
            writer.nullValue()
        } else {
            val stringValue = if (isHex) value.toHexString() else value.toString()
            writer.value(stringValue)
        }
    }

    override fun read(reader: JsonReader): Int? {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull()
            return null
        }
        val stringValue = reader.nextString()
        return if (isHex) stringValue.hexStringToIntOrNull() else stringValue.toIntOrNull()
    }
}

class ByteArrayTypeAdapter : TypeAdapter<ByteArray?>() {
    override fun write(writer: JsonWriter, value: ByteArray?) {
        if (value == null) {
            writer.nullValue()
        } else {
            writer.value(value.toHexString())
        }
    }

    override fun read(reader: JsonReader): ByteArray? {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull()
            return null
        }
        return reader.nextString().hexStringToByteArrayOrNull()
    }
}

class AddressTypeAdapter : TypeAdapter<Address?>() {
    override fun write(writer: JsonWriter, value: Address?) {
        if (value == null) {
            writer.nullValue()
        } else {
            writer.value(value.hex)
        }
    }

    override fun read(reader: JsonReader): Address? {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull()
            return null
        }
        return try {
            Address.fromHex(reader.nextString())
        } catch (error: Throwable) {
            null
        }
    }
}
