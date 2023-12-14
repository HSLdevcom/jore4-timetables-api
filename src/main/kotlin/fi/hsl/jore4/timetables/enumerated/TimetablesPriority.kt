package fi.hsl.jore4.timetables.enumerated

enum class TimetablesPriority(val value: Int) {
    STANDARD(10),
    TEMPORARY(20),
    SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE(23),
    SPECIAL(25),
    DRAFT(30),
    STAGING(40);

    companion object {
        fun fromInt(value: Int): TimetablesPriority? {
            return values().firstOrNull { it.value == value }
        }
    }
}
