package com.sleepkqq.sololeveling.ai.model

import java.math.BigDecimal

data class GenerateTaskResponse(
	val title: String,
	val description: String,
	val experience: String,
	val currencyReward: BigDecimal,
	val agility: String,
	val strength: String,
	val intelligence: String
)
