/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.vehicle_service.tables.daos


import fi.hsl.jore.jore4.jooq.AbstractSpringDAOImpl
import fi.hsl.jore.jore4.jooq.vehicle_service.tables.Block
import fi.hsl.jore.jore4.jooq.vehicle_service.tables.records.BlockRecord

import java.util.UUID

import kotlin.collections.List

import org.jooq.Configuration
import org.jooq.types.YearToSecond
import org.springframework.stereotype.Repository


/**
 * The work of a vehicle from the time it leaves a PARKING POINT after parking
 * until its next return to park at a PARKING POINT. Any subsequent departure
 * from a PARKING POINT after parking marks the start of a new BLOCK. The period
 * of a BLOCK has to be covered by DUTies. Transmodel:
 * https://www.transmodel-cen.eu/model/index.htm?goto=3:5:958 
 */
@Suppress("UNCHECKED_CAST")
@Repository
open class BlockDao(configuration: Configuration?) : AbstractSpringDAOImpl<BlockRecord, fi.hsl.jore.jore4.jooq.vehicle_service.tables.pojos.Block, UUID>(Block.BLOCK, fi.hsl.jore.jore4.jooq.vehicle_service.tables.pojos.Block::class.java, configuration) {

    /**
     * Create a new BlockDao without any configuration
     */
    constructor(): this(null)

    override fun getId(o: fi.hsl.jore.jore4.jooq.vehicle_service.tables.pojos.Block): UUID? = o.blockId

    /**
     * Fetch records that have <code>block_id BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfBlockId(lowerInclusive: UUID?, upperInclusive: UUID?): List<fi.hsl.jore.jore4.jooq.vehicle_service.tables.pojos.Block> = fetchRange(Block.BLOCK.BLOCK_ID, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>block_id IN (values)</code>
     */
    fun fetchByBlockId(vararg values: UUID): List<fi.hsl.jore.jore4.jooq.vehicle_service.tables.pojos.Block> = fetch(Block.BLOCK.BLOCK_ID, *values)

    /**
     * Fetch a unique record that has <code>block_id = value</code>
     */
    fun fetchOneByBlockId(value: UUID): fi.hsl.jore.jore4.jooq.vehicle_service.tables.pojos.Block? = fetchOne(Block.BLOCK.BLOCK_ID, value)

    /**
     * Fetch records that have <code>vehicle_service_id BETWEEN lowerInclusive
     * AND upperInclusive</code>
     */
    fun fetchRangeOfVehicleServiceId(lowerInclusive: UUID, upperInclusive: UUID): List<fi.hsl.jore.jore4.jooq.vehicle_service.tables.pojos.Block> = fetchRange(Block.BLOCK.VEHICLE_SERVICE_ID, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>vehicle_service_id IN (values)</code>
     */
    fun fetchByVehicleServiceId(vararg values: UUID): List<fi.hsl.jore.jore4.jooq.vehicle_service.tables.pojos.Block> = fetch(Block.BLOCK.VEHICLE_SERVICE_ID, *values)

    /**
     * Fetch records that have <code>preparing_time BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfPreparingTime(lowerInclusive: YearToSecond?, upperInclusive: YearToSecond?): List<fi.hsl.jore.jore4.jooq.vehicle_service.tables.pojos.Block> = fetchRange(Block.BLOCK.PREPARING_TIME, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>preparing_time IN (values)</code>
     */
    fun fetchByPreparingTime(vararg values: YearToSecond): List<fi.hsl.jore.jore4.jooq.vehicle_service.tables.pojos.Block> = fetch(Block.BLOCK.PREPARING_TIME, *values)

    /**
     * Fetch records that have <code>finishing_time BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfFinishingTime(lowerInclusive: YearToSecond?, upperInclusive: YearToSecond?): List<fi.hsl.jore.jore4.jooq.vehicle_service.tables.pojos.Block> = fetchRange(Block.BLOCK.FINISHING_TIME, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>finishing_time IN (values)</code>
     */
    fun fetchByFinishingTime(vararg values: YearToSecond): List<fi.hsl.jore.jore4.jooq.vehicle_service.tables.pojos.Block> = fetch(Block.BLOCK.FINISHING_TIME, *values)

    /**
     * Fetch records that have <code>vehicle_type_id BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfVehicleTypeId(lowerInclusive: UUID?, upperInclusive: UUID?): List<fi.hsl.jore.jore4.jooq.vehicle_service.tables.pojos.Block> = fetchRange(Block.BLOCK.VEHICLE_TYPE_ID, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>vehicle_type_id IN (values)</code>
     */
    fun fetchByVehicleTypeId(vararg values: UUID): List<fi.hsl.jore.jore4.jooq.vehicle_service.tables.pojos.Block> = fetch(Block.BLOCK.VEHICLE_TYPE_ID, *values)
}