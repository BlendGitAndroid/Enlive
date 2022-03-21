package com.blend.base.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

/**
 * [Any] 序列化器
 */
object AnyKSerializer : KSerializer<Any> {

    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor("kotlin.Any") {
        }

    override fun serialize(encoder: Encoder, value: Any) {
        encoder.encodeStructure(descriptor) {
        }
    }

    override fun deserialize(decoder: Decoder): Any {
        return decoder.decodeStructure(descriptor) {
            Any()
        }
    }
}