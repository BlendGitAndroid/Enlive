package com.blend.base.tools


import com.blend.base.serializer.AnyKSerializer
import com.blend.base.serializer.CookieKSerializer
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import okhttp3.Cookie

/**
 * 数据转换相关
 */

/** 默认 [Json] 解析 */
val jsonDefault = Json {
    // 是否编码默认值
    encodeDefaults = true
    // 忽略未知 key 不抛出异常
    ignoreUnknownKeys = true
    // 是否使用宽松模式
    isLenient = true
    // 是否允许将 key-value 转换为 数组
    allowStructuredMapKeys = false
    // 是否对打印的 json 格式化
    prettyPrint = true
    // 指定打印缩进字符串
//    prettyPrintIndent = "    "
    // 非空类型为空或找不到对应枚举时使用默认值
    coerceInputValues = false
    // 将多态序列化为默认数组格式
    useArrayPolymorphism = false
    // 多态序列化的类描述符属性的名称
//    classDiscriminator = "type"
    // 是否取消对特殊浮点值的规范
    allowSpecialFloatingPointValues = false
    // 指定序列化模块
    serializersModule = SerializersModule {
        contextual(Cookie::class, CookieKSerializer)
        contextual(Any::class, AnyKSerializer)
    }
}

/**
 * 使用 [json] 以及 [deserializer] 将 [String] 解析为 [T] 数据实体
 * > 转换失败返回 `null`
 */
inline fun <reified T> String?.toTypeEntity(
    json: Json = jsonDefault,
    deserializer: DeserializationStrategy<T>? = null,
): T? {
    return when {
        this.isNullOrBlank() -> null
        null != deserializer -> json.decodeFromString(deserializer, this)
        else -> json.decodeFromString(this)
    }
}

/**
 * 使用 [json] 以及 [serializer] 将数据实体 [T] 转换为 [String]
 * > 转换失败返回 `""`
 */
inline fun <reified T> T?.toJsonString(
    json: Json = jsonDefault,
    serializer: SerializationStrategy<T>? = null,
): String {
    return when {
        null == this -> ""
        null != serializer -> json.encodeToString(serializer, this)
        else -> json.encodeToString(this)
    }
}