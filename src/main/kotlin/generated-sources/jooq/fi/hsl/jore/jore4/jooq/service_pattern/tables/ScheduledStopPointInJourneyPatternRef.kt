/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.service_pattern.tables


import fi.hsl.jore.jore4.jooq.journey_pattern.tables.JourneyPatternRef
import fi.hsl.jore.jore4.jooq.service_pattern.ServicePattern
import fi.hsl.jore.jore4.jooq.service_pattern.keys.SCHEDULED_STOP_POINT_IN_JOURNEY_PATTERN_REF_PKEY
import fi.hsl.jore.jore4.jooq.service_pattern.keys.SCHEDULED_STOP_POINT_IN_JOURNEY_PATTERN_REF__SCHEDULED_STOP_POINT_IN_JOURNEY_PAT_JOURNEY_PATTERN_REF_ID_FKEY

import java.util.UUID

import kotlin.collections.List

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Name
import org.jooq.Record
import org.jooq.Schema
import org.jooq.Table
import org.jooq.TableField
import org.jooq.TableOptions
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl


/**
 * Reference the a SCHEDULED STOP POINT within a JOURNEY PATTERN. Transmodel: 
 * https://www.transmodel-cen.eu/model/index.htm?goto=2:3:4:729 
 */
@Suppress("UNCHECKED_CAST")
open class ScheduledStopPointInJourneyPatternRef(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, Record>?,
    aliased: Table<Record>?,
    parameters: Array<Field<*>?>?
): TableImpl<Record>(
    alias,
    ServicePattern.SERVICE_PATTERN,
    child,
    path,
    aliased,
    parameters,
    DSL.comment("Reference the a SCHEDULED STOP POINT within a JOURNEY PATTERN. Transmodel: https://www.transmodel-cen.eu/model/index.htm?goto=2:3:4:729 "),
    TableOptions.table()
) {
    companion object {

        /**
         * The reference instance of <code>service_pattern.scheduled_stop_point_in_journey_pattern_ref</code>
         */
        val SCHEDULED_STOP_POINT_IN_JOURNEY_PATTERN_REF = ScheduledStopPointInJourneyPatternRef()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<Record> = Record::class.java

    /**
     * The column <code>service_pattern.scheduled_stop_point_in_journey_pattern_ref.scheduled_stop_point_in_journey_pattern_ref_id</code>.
     */
    val SCHEDULED_STOP_POINT_IN_JOURNEY_PATTERN_REF_ID: TableField<Record, UUID?> = createField(DSL.name("scheduled_stop_point_in_journey_pattern_ref_id"), SQLDataType.UUID.nullable(false).defaultValue(DSL.field("gen_random_uuid()", SQLDataType.UUID)), this, "")

    /**
     * The column <code>service_pattern.scheduled_stop_point_in_journey_pattern_ref.journey_pattern_ref_id</code>. JOURNEY PATTERN to which the SCHEDULED STOP POINT belongs
     */
    val JOURNEY_PATTERN_REF_ID: TableField<Record, UUID?> = createField(DSL.name("journey_pattern_ref_id"), SQLDataType.UUID.nullable(false), this, "JOURNEY PATTERN to which the SCHEDULED STOP POINT belongs")

    /**
     * The column <code>service_pattern.scheduled_stop_point_in_journey_pattern_ref.scheduled_stop_point_label</code>. The label of the SCHEDULED STOP POINT
     */
    val SCHEDULED_STOP_POINT_LABEL: TableField<Record, String?> = createField(DSL.name("scheduled_stop_point_label"), SQLDataType.CLOB.nullable(false), this, "The label of the SCHEDULED STOP POINT")

    /**
     * The column <code>service_pattern.scheduled_stop_point_in_journey_pattern_ref.scheduled_stop_point_sequence</code>. The order of the SCHEDULED STOP POINT within the JOURNEY PATTERN.
     */
    val SCHEDULED_STOP_POINT_SEQUENCE: TableField<Record, Int?> = createField(DSL.name("scheduled_stop_point_sequence"), SQLDataType.INTEGER.nullable(false), this, "The order of the SCHEDULED STOP POINT within the JOURNEY PATTERN.")

    private constructor(alias: Name, aliased: Table<Record>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<Record>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>service_pattern.scheduled_stop_point_in_journey_pattern_ref</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>service_pattern.scheduled_stop_point_in_journey_pattern_ref</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>service_pattern.scheduled_stop_point_in_journey_pattern_ref</code> table reference
     */
    constructor(): this(DSL.name("scheduled_stop_point_in_journey_pattern_ref"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, Record>): this(Internal.createPathAlias(child, key), child, key, SCHEDULED_STOP_POINT_IN_JOURNEY_PATTERN_REF, null)
    override fun getSchema(): Schema = ServicePattern.SERVICE_PATTERN
    override fun getPrimaryKey(): UniqueKey<Record> = SCHEDULED_STOP_POINT_IN_JOURNEY_PATTERN_REF_PKEY
    override fun getKeys(): List<UniqueKey<Record>> = listOf(SCHEDULED_STOP_POINT_IN_JOURNEY_PATTERN_REF_PKEY)
    override fun getReferences(): List<ForeignKey<Record, *>> = listOf(SCHEDULED_STOP_POINT_IN_JOURNEY_PATTERN_REF__SCHEDULED_STOP_POINT_IN_JOURNEY_PAT_JOURNEY_PATTERN_REF_ID_FKEY)

    private lateinit var _journeyPatternRef: JourneyPatternRef
    fun journeyPatternRef(): JourneyPatternRef {
        if (!this::_journeyPatternRef.isInitialized)
            _journeyPatternRef = JourneyPatternRef(this, SCHEDULED_STOP_POINT_IN_JOURNEY_PATTERN_REF__SCHEDULED_STOP_POINT_IN_JOURNEY_PAT_JOURNEY_PATTERN_REF_ID_FKEY)

        return _journeyPatternRef;
    }
    override fun `as`(alias: String): ScheduledStopPointInJourneyPatternRef = ScheduledStopPointInJourneyPatternRef(DSL.name(alias), this)
    override fun `as`(alias: Name): ScheduledStopPointInJourneyPatternRef = ScheduledStopPointInJourneyPatternRef(alias, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): ScheduledStopPointInJourneyPatternRef = ScheduledStopPointInJourneyPatternRef(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): ScheduledStopPointInJourneyPatternRef = ScheduledStopPointInJourneyPatternRef(name, null)
}
