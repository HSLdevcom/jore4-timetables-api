/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.return_value.tables


import fi.hsl.jore.jore4.jooq.return_value.ReturnValue
import fi.hsl.jore.jore4.jooq.return_value.keys.TIMETABLE_VERSION__TIMETABLE_VERSION_DAY_TYPE_ID_FKEY
import fi.hsl.jore.jore4.jooq.return_value.keys.TIMETABLE_VERSION__TIMETABLE_VERSION_SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE_ID_FKEY
import fi.hsl.jore.jore4.jooq.return_value.keys.TIMETABLE_VERSION__TIMETABLE_VERSION_VEHICLE_SCHEDULE_FRAME_ID_FKEY
import fi.hsl.jore.jore4.jooq.service_calendar.tables.DayType
import fi.hsl.jore.jore4.jooq.service_calendar.tables.SubstituteOperatingDayByLineType
import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.VehicleScheduleFrame

import java.time.LocalDate
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
import org.jooq.impl.DSL
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl


/**
 * This return value is used for functions that determine what timetable versions 
 * are in effect. In effect will be true for all the timetable version rows 
 * that
 * are valid on given observation day and are the highest priority of that 
 * day type. As an example if we have:
 * Saturday Standard priority valid for 1.1.2023 - 30.6.2023
 * Saturday Temporary priority valid for 1.5.2023 - 31.5.2023
 * Saturday Special priority valid for 20.5.2023 - 20.5.2023
 * 
 * If we check the timetable versions for the date 1.2.2023, for Saturday 
 * we only get the Standard priority, beacuse it is the only one valid on 
 * that time. So that 
 * row would have in_effect = true. 
 * If we check the timetable versions for the date 1.5.2023, for Saturday 
 * we would get the Standard and the Temporary priority for this date, as 
 * they are both valid.
 * But only the higher priority is in effect on this date. So the Saturday 
 * Temporary priority would have in_effect = true, and the Saturday Standard 
 * priority would 
 * have in_effect = false.
 * If we check the timetable versions for the date 20.5.2023, for Saturday 
 * we have all three valid, but only one can be in_effect, and that would 
 * be the Special 
 * priority in this case.
 */
@Suppress("UNCHECKED_CAST")
open class TimetableVersion(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, Record>?,
    aliased: Table<Record>?,
    parameters: Array<Field<*>?>?
): TableImpl<Record>(
    alias,
    ReturnValue.RETURN_VALUE,
    child,
    path,
    aliased,
    parameters,
    DSL.comment("This return value is used for functions that determine what timetable versions are in effect. In effect will be true for all the timetable version rows that\nare valid on given observation day and are the highest priority of that day type. As an example if we have:\nSaturday Standard priority valid for 1.1.2023 - 30.6.2023\nSaturday Temporary priority valid for 1.5.2023 - 31.5.2023\nSaturday Special priority valid for 20.5.2023 - 20.5.2023\n\nIf we check the timetable versions for the date 1.2.2023, for Saturday we only get the Standard priority, beacuse it is the only one valid on that time. So that \nrow would have in_effect = true. \nIf we check the timetable versions for the date 1.5.2023, for Saturday we would get the Standard and the Temporary priority for this date, as they are both valid.\nBut only the higher priority is in effect on this date. So the Saturday Temporary priority would have in_effect = true, and the Saturday Standard priority would \nhave in_effect = false.\nIf we check the timetable versions for the date 20.5.2023, for Saturday we have all three valid, but only one can be in_effect, and that would be the Special \npriority in this case.\n"),
    TableOptions.table()
) {
    companion object {

        /**
         * The reference instance of <code>return_value.timetable_version</code>
         */
        val TIMETABLE_VERSION = TimetableVersion()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<Record> = Record::class.java

    /**
     * The column <code>return_value.timetable_version.vehicle_schedule_frame_id</code>.
     */
    val VEHICLE_SCHEDULE_FRAME_ID: TableField<Record, UUID?> = createField(DSL.name("vehicle_schedule_frame_id"), SQLDataType.UUID, this, "")

    /**
     * The column <code>return_value.timetable_version.substitute_operating_day_by_line_type_id</code>.
     */
    val SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE_ID: TableField<Record, UUID?> = createField(DSL.name("substitute_operating_day_by_line_type_id"), SQLDataType.UUID, this, "")

    /**
     * The column <code>return_value.timetable_version.validity_start</code>.
     */
    val VALIDITY_START: TableField<Record, LocalDate?> = createField(DSL.name("validity_start"), SQLDataType.LOCALDATE.nullable(false), this, "")

    /**
     * The column <code>return_value.timetable_version.validity_end</code>.
     */
    val VALIDITY_END: TableField<Record, LocalDate?> = createField(DSL.name("validity_end"), SQLDataType.LOCALDATE.nullable(false), this, "")

    /**
     * The column <code>return_value.timetable_version.priority</code>.
     */
    val PRIORITY: TableField<Record, Int?> = createField(DSL.name("priority"), SQLDataType.INTEGER.nullable(false), this, "")

    /**
     * The column <code>return_value.timetable_version.in_effect</code>.
     */
    val IN_EFFECT: TableField<Record, Boolean?> = createField(DSL.name("in_effect"), SQLDataType.BOOLEAN.nullable(false), this, "")

    /**
     * The column <code>return_value.timetable_version.day_type_id</code>.
     */
    val DAY_TYPE_ID: TableField<Record, UUID?> = createField(DSL.name("day_type_id"), SQLDataType.UUID.nullable(false), this, "")

    private constructor(alias: Name, aliased: Table<Record>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<Record>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>return_value.timetable_version</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>return_value.timetable_version</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>return_value.timetable_version</code> table reference
     */
    constructor(): this(DSL.name("timetable_version"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, Record>): this(Internal.createPathAlias(child, key), child, key, TIMETABLE_VERSION, null)
    override fun getSchema(): Schema = ReturnValue.RETURN_VALUE
    override fun getReferences(): List<ForeignKey<Record, *>> = listOf(TIMETABLE_VERSION__TIMETABLE_VERSION_VEHICLE_SCHEDULE_FRAME_ID_FKEY, TIMETABLE_VERSION__TIMETABLE_VERSION_SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE_ID_FKEY, TIMETABLE_VERSION__TIMETABLE_VERSION_DAY_TYPE_ID_FKEY)

    private lateinit var _vehicleScheduleFrame: VehicleScheduleFrame
    private lateinit var _substituteOperatingDayByLineType: SubstituteOperatingDayByLineType
    private lateinit var _dayType: DayType
    fun vehicleScheduleFrame(): VehicleScheduleFrame {
        if (!this::_vehicleScheduleFrame.isInitialized)
            _vehicleScheduleFrame = VehicleScheduleFrame(this, TIMETABLE_VERSION__TIMETABLE_VERSION_VEHICLE_SCHEDULE_FRAME_ID_FKEY)

        return _vehicleScheduleFrame;
    }
    fun substituteOperatingDayByLineType(): SubstituteOperatingDayByLineType {
        if (!this::_substituteOperatingDayByLineType.isInitialized)
            _substituteOperatingDayByLineType = SubstituteOperatingDayByLineType(this, TIMETABLE_VERSION__TIMETABLE_VERSION_SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE_ID_FKEY)

        return _substituteOperatingDayByLineType;
    }
    fun dayType(): DayType {
        if (!this::_dayType.isInitialized)
            _dayType = DayType(this, TIMETABLE_VERSION__TIMETABLE_VERSION_DAY_TYPE_ID_FKEY)

        return _dayType;
    }
    override fun `as`(alias: String): TimetableVersion = TimetableVersion(DSL.name(alias), this)
    override fun `as`(alias: Name): TimetableVersion = TimetableVersion(alias, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): TimetableVersion = TimetableVersion(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): TimetableVersion = TimetableVersion(name, null)
}
