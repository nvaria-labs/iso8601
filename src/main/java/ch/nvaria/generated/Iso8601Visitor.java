// Generated from Iso8601.g4 by ANTLR 4.13.2
package ch.nvaria.generated;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link Iso8601Parser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface Iso8601Visitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#int_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInt_(Iso8601Parser.Int_Context ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#dec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDec(Iso8601Parser.DecContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#int2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInt2(Iso8601Parser.Int2Context ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#int3}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInt3(Iso8601Parser.Int3Context ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#int4}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInt4(Iso8601Parser.Int4Context ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#sint2p}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSint2p(Iso8601Parser.Sint2pContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#sint4p}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSint4p(Iso8601Parser.Sint4pContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#dec2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDec2(Iso8601Parser.Dec2Context ctx);
	/**
	 * Visit a parse tree produced by the {@code CompleteCentury}
	 * labeled alternative in {@link Iso8601Parser#century}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompleteCentury(Iso8601Parser.CompleteCenturyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpandedCentury}
	 * labeled alternative in {@link Iso8601Parser#century}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpandedCentury(Iso8601Parser.ExpandedCenturyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CompleteYear}
	 * labeled alternative in {@link Iso8601Parser#year}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompleteYear(Iso8601Parser.CompleteYearContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpandedYear}
	 * labeled alternative in {@link Iso8601Parser#year}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpandedYear(Iso8601Parser.ExpandedYearContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#month}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMonth(Iso8601Parser.MonthContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#day}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDay(Iso8601Parser.DayContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#ordinalDay}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrdinalDay(Iso8601Parser.OrdinalDayContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#week}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWeek(Iso8601Parser.WeekContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#weekDay}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWeekDay(Iso8601Parser.WeekDayContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#hour}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHour(Iso8601Parser.HourContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#minute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMinute(Iso8601Parser.MinuteContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#second}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSecond(Iso8601Parser.SecondContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#hourFraction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHourFraction(Iso8601Parser.HourFractionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#minuteFraction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMinuteFraction(Iso8601Parser.MinuteFractionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#secondFraction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSecondFraction(Iso8601Parser.SecondFractionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#calendarDate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCalendarDate(Iso8601Parser.CalendarDateContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#calendarDateBasic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCalendarDateBasic(Iso8601Parser.CalendarDateBasicContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#calendarDateExtended}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCalendarDateExtended(Iso8601Parser.CalendarDateExtendedContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#specificMonthCalendarDate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpecificMonthCalendarDate(Iso8601Parser.SpecificMonthCalendarDateContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#specificYearCalendarDate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpecificYearCalendarDate(Iso8601Parser.SpecificYearCalendarDateContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#specificCenturyCalendarDate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpecificCenturyCalendarDate(Iso8601Parser.SpecificCenturyCalendarDateContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#ordinalDate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrdinalDate(Iso8601Parser.OrdinalDateContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#ordinalDateBasic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrdinalDateBasic(Iso8601Parser.OrdinalDateBasicContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#ordinalDateExtended}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrdinalDateExtended(Iso8601Parser.OrdinalDateExtendedContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#weekDate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWeekDate(Iso8601Parser.WeekDateContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#weekDateBasic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWeekDateBasic(Iso8601Parser.WeekDateBasicContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#weekDateExtended}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWeekDateExtended(Iso8601Parser.WeekDateExtendedContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#specificWeekWeekDate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpecificWeekWeekDate(Iso8601Parser.SpecificWeekWeekDateContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#specificWeekWeekDateBasic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpecificWeekWeekDateBasic(Iso8601Parser.SpecificWeekWeekDateBasicContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#specificWeekWeekDateExtended}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpecificWeekWeekDateExtended(Iso8601Parser.SpecificWeekWeekDateExtendedContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#datePrecise}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDatePrecise(Iso8601Parser.DatePreciseContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#datePreciseBasic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDatePreciseBasic(Iso8601Parser.DatePreciseBasicContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#datePreciseExtended}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDatePreciseExtended(Iso8601Parser.DatePreciseExtendedContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#date}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDate(Iso8601Parser.DateContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#dateBasic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDateBasic(Iso8601Parser.DateBasicContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#dateExtended}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDateExtended(Iso8601Parser.DateExtendedContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#localTimePrecise}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalTimePrecise(Iso8601Parser.LocalTimePreciseContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#localTimePreciseBasic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalTimePreciseBasic(Iso8601Parser.LocalTimePreciseBasicContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#localTimePreciseExtended}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalTimePreciseExtended(Iso8601Parser.LocalTimePreciseExtendedContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#specificMinuteLocalTime}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpecificMinuteLocalTime(Iso8601Parser.SpecificMinuteLocalTimeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#specificMinuteLocalTimeBasic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpecificMinuteLocalTimeBasic(Iso8601Parser.SpecificMinuteLocalTimeBasicContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#specificMinuteLocalTimeExtended}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpecificMinuteLocalTimeExtended(Iso8601Parser.SpecificMinuteLocalTimeExtendedContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#specificHourLocalTime}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpecificHourLocalTime(Iso8601Parser.SpecificHourLocalTimeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#localTime}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalTime(Iso8601Parser.LocalTimeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#localTimeBasic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalTimeBasic(Iso8601Parser.LocalTimeBasicContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#localTimeExtended}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalTimeExtended(Iso8601Parser.LocalTimeExtendedContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#timeZoneUtc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimeZoneUtc(Iso8601Parser.TimeZoneUtcContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#timeZone}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimeZone(Iso8601Parser.TimeZoneContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#timeZoneBasic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimeZoneBasic(Iso8601Parser.TimeZoneBasicContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#timeZoneExtended}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimeZoneExtended(Iso8601Parser.TimeZoneExtendedContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#localTimeAndTimeZone}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalTimeAndTimeZone(Iso8601Parser.LocalTimeAndTimeZoneContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#localTimeAndTimeZoneBasic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalTimeAndTimeZoneBasic(Iso8601Parser.LocalTimeAndTimeZoneBasicContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#localTimeAndTimeZoneExtended}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalTimeAndTimeZoneExtended(Iso8601Parser.LocalTimeAndTimeZoneExtendedContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#time}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTime(Iso8601Parser.TimeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#datetimePrecise}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDatetimePrecise(Iso8601Parser.DatetimePreciseContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#datetimePreciseBasic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDatetimePreciseBasic(Iso8601Parser.DatetimePreciseBasicContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#datetimePreciseExtended}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDatetimePreciseExtended(Iso8601Parser.DatetimePreciseExtendedContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#datetime}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDatetime(Iso8601Parser.DatetimeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#datetimeBasic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDatetimeBasic(Iso8601Parser.DatetimeBasicContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#datetimeExtended}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDatetimeExtended(Iso8601Parser.DatetimeExtendedContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#interval}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterval(Iso8601Parser.IntervalContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#intervalBasic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntervalBasic(Iso8601Parser.IntervalBasicContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#intervalExtended}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntervalExtended(Iso8601Parser.IntervalExtendedContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#intervalT}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntervalT(Iso8601Parser.IntervalTContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#monthDateBasic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMonthDateBasic(Iso8601Parser.MonthDateBasicContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#monthDateExtended}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMonthDateExtended(Iso8601Parser.MonthDateExtendedContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#intervalYMDTimeBasic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntervalYMDTimeBasic(Iso8601Parser.IntervalYMDTimeBasicContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#intervalYMDTimeExtended}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntervalYMDTimeExtended(Iso8601Parser.IntervalYMDTimeExtendedContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#intervalYMD}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntervalYMD(Iso8601Parser.IntervalYMDContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#intervalYMDBasic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntervalYMDBasic(Iso8601Parser.IntervalYMDBasicContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#intervalYMDExtended}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntervalYMDExtended(Iso8601Parser.IntervalYMDExtendedContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#timeBeginEnd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimeBeginEnd(Iso8601Parser.TimeBeginEndContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#timeBeginEndBasic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimeBeginEndBasic(Iso8601Parser.TimeBeginEndBasicContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#timeBeginEndExtended}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimeBeginEndExtended(Iso8601Parser.TimeBeginEndExtendedContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#timeBeginInterval}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimeBeginInterval(Iso8601Parser.TimeBeginIntervalContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#timeBeginIntervalBasic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimeBeginIntervalBasic(Iso8601Parser.TimeBeginIntervalBasicContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#timeBeginIntervalExtended}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimeBeginIntervalExtended(Iso8601Parser.TimeBeginIntervalExtendedContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#timeIntervalEnd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimeIntervalEnd(Iso8601Parser.TimeIntervalEndContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#timeIntervalEndBasic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimeIntervalEndBasic(Iso8601Parser.TimeIntervalEndBasicContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#timeIntervalEndExtended}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimeIntervalEndExtended(Iso8601Parser.TimeIntervalEndExtendedContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#duration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDuration(Iso8601Parser.DurationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#durationBasic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDurationBasic(Iso8601Parser.DurationBasicContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#durationExtended}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDurationExtended(Iso8601Parser.DurationExtendedContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#recurringCount}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecurringCount(Iso8601Parser.RecurringCountContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#recurring}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecurring(Iso8601Parser.RecurringContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#recurringBasic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecurringBasic(Iso8601Parser.RecurringBasicContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#recurringExtended}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecurringExtended(Iso8601Parser.RecurringExtendedContext ctx);
	/**
	 * Visit a parse tree produced by {@link Iso8601Parser#iso}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIso(Iso8601Parser.IsoContext ctx);
}