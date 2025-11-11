package com.sleepkqq.sololeveling.ai.model

import com.sleepkqq.sololeveling.avro.localization.LocalizationItem

data class GenerateTaskResponse(
	val title: LocalizationItem,
	val description: LocalizationItem,
	val experience: Int,
	val currencyReward: Int,
	val agility: Int,
	val strength: Int,
	val intelligence: Int
)
