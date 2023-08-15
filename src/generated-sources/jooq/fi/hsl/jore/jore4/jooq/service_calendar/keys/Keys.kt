/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.service_calendar.keys


import fi.hsl.jore.jore4.jooq.route.keys.TYPE_OF_LINE_PKEY
import fi.hsl.jore.jore4.jooq.route.tables.TypeOfLine
import fi.hsl.jore.jore4.jooq.service_calendar.tables.DayType
import fi.hsl.jore.jore4.jooq.service_calendar.tables.DayTypeActiveOnDayOfWeek
import fi.hsl.jore.jore4.jooq.service_calendar.tables.SubstituteOperatingDayByLineType
import fi.hsl.jore.jore4.jooq.service_calendar.tables.SubstituteOperatingPeriod

import org.jooq.ForeignKey
import org.jooq.Record
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal



// -------------------------------------------------------------------------
// UNIQUE and PRIMARY KEY definitions
// -------------------------------------------------------------------------

val DAY_TYPE_PKEY: UniqueKey<Record> = Internal.createUniqueKey(DayType.DAY_TYPE, DSL.name("day_type_pkey"), arrayOf(DayType.DAY_TYPE.DAY_TYPE_ID), true)
val DAY_TYPE_ACTIVE_ON_DAY_OF_WEEK_PKEY: UniqueKey<Record> = Internal.createUniqueKey(DayTypeActiveOnDayOfWeek.DAY_TYPE_ACTIVE_ON_DAY_OF_WEEK, DSL.name("day_type_active_on_day_of_week_pkey"), arrayOf(DayTypeActiveOnDayOfWeek.DAY_TYPE_ACTIVE_ON_DAY_OF_WEEK.DAY_TYPE_ID, DayTypeActiveOnDayOfWeek.DAY_TYPE_ACTIVE_ON_DAY_OF_WEEK.DAY_OF_WEEK), true)
val SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE_PKEY: UniqueKey<Record> = Internal.createUniqueKey(SubstituteOperatingDayByLineType.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE, DSL.name("substitute_operating_day_by_line_type_pkey"), arrayOf(SubstituteOperatingDayByLineType.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE_ID), true)
val SUBSTITUTE_OPERATING_PERIOD_PERIOD_NAME_KEY: UniqueKey<Record> = Internal.createUniqueKey(SubstituteOperatingPeriod.SUBSTITUTE_OPERATING_PERIOD, DSL.name("substitute_operating_period_period_name_key"), arrayOf(SubstituteOperatingPeriod.SUBSTITUTE_OPERATING_PERIOD.PERIOD_NAME), true)
val SUBSTITUTE_OPERATING_PERIOD_PKEY: UniqueKey<Record> = Internal.createUniqueKey(SubstituteOperatingPeriod.SUBSTITUTE_OPERATING_PERIOD, DSL.name("substitute_operating_period_pkey"), arrayOf(SubstituteOperatingPeriod.SUBSTITUTE_OPERATING_PERIOD.SUBSTITUTE_OPERATING_PERIOD_ID), true)

// -------------------------------------------------------------------------
// FOREIGN KEY definitions
// -------------------------------------------------------------------------

val DAY_TYPE_ACTIVE_ON_DAY_OF_WEEK__DAY_TYPE_ACTIVE_ON_DAY_OF_WEEK_DAY_TYPE_ID_FKEY: ForeignKey<Record, Record> = Internal.createForeignKey(DayTypeActiveOnDayOfWeek.DAY_TYPE_ACTIVE_ON_DAY_OF_WEEK, DSL.name("day_type_active_on_day_of_week_day_type_id_fkey"), arrayOf(DayTypeActiveOnDayOfWeek.DAY_TYPE_ACTIVE_ON_DAY_OF_WEEK.DAY_TYPE_ID), fi.hsl.jore.jore4.jooq.service_calendar.keys.DAY_TYPE_PKEY, arrayOf(DayType.DAY_TYPE.DAY_TYPE_ID), true)
val SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE__SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE_SUBSTITUTE_PERIOD_FKEY: ForeignKey<Record, Record> = Internal.createForeignKey(SubstituteOperatingDayByLineType.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE, DSL.name("substitute_operating_day_by_line_type_substitute_period_fkey"), arrayOf(SubstituteOperatingDayByLineType.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE.SUBSTITUTE_OPERATING_PERIOD_ID), fi.hsl.jore.jore4.jooq.service_calendar.keys.SUBSTITUTE_OPERATING_PERIOD_PKEY, arrayOf(SubstituteOperatingPeriod.SUBSTITUTE_OPERATING_PERIOD.SUBSTITUTE_OPERATING_PERIOD_ID), true)
val SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE__SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE_TYPE_OF_LINE_FKEY: ForeignKey<Record, Record> = Internal.createForeignKey(SubstituteOperatingDayByLineType.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE, DSL.name("substitute_operating_day_by_line_type_type_of_line_fkey"), arrayOf(SubstituteOperatingDayByLineType.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE.TYPE_OF_LINE), TYPE_OF_LINE_PKEY, arrayOf(TypeOfLine.TYPE_OF_LINE.TYPE_OF_LINE_), true)