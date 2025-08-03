package com.sleepkqq.sololeveling.ai.extensions

import java.math.BigDecimal
import java.math.RoundingMode
import java.nio.ByteBuffer

fun BigDecimal.toByteBuffer(scale: Int = 2): ByteBuffer {

	val normalized = this.setScale(scale, RoundingMode.HALF_UP)

	val unscaledValue = normalized.unscaledValue()

	val bytes = unscaledValue.toByteArray()

	return ByteBuffer.wrap(bytes)
}
