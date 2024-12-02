// Generated from Iso8601.g4 by ANTLR 4.13.2
package ch.nvaria.generated;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link Iso8601Parser}.
 */
public interface Iso8601Listener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#int_}.
	 * @param ctx the parse tree
	 */
	void enterInt_(Iso8601Parser.Int_Context ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#int_}.
	 * @param ctx the parse tree
	 */
	void exitInt_(Iso8601Parser.Int_Context ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#dec}.
	 * @param ctx the parse tree
	 */
	void enterDec(Iso8601Parser.DecContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#dec}.
	 * @param ctx the parse tree
	 */
	void exitDec(Iso8601Parser.DecContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#int2}.
	 * @param ctx the parse tree
	 */
	void enterInt2(Iso8601Parser.Int2Context ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#int2}.
	 * @param ctx the parse tree
	 */
	void exitInt2(Iso8601Parser.Int2Context ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#int3}.
	 * @param ctx the parse tree
	 */
	void enterInt3(Iso8601Parser.Int3Context ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#int3}.
	 * @param ctx the parse tree
	 */
	void exitInt3(Iso8601Parser.Int3Context ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#int4}.
	 * @param ctx the parse tree
	 */
	void enterInt4(Iso8601Parser.Int4Context ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#int4}.
	 * @param ctx the parse tree
	 */
	void exitInt4(Iso8601Parser.Int4Context ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#sint2p}.
	 * @param ctx the parse tree
	 */
	void enterSint2p(Iso8601Parser.Sint2pContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#sint2p}.
	 * @param ctx the parse tree
	 */
	void exitSint2p(Iso8601Parser.Sint2pContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#sint4p}.
	 * @param ctx the parse tree
	 */
	void enterSint4p(Iso8601Parser.Sint4pContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#sint4p}.
	 * @param ctx the parse tree
	 */
	void exitSint4p(Iso8601Parser.Sint4pContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#dec2}.
	 * @param ctx the parse tree
	 */
	void enterDec2(Iso8601Parser.Dec2Context ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#dec2}.
	 * @param ctx the parse tree
	 */
	void exitDec2(Iso8601Parser.Dec2Context ctx);
	/**
	 * Enter a parse tree produced by the {@code CompleteCentury}
	 * labeled alternative in {@link Iso8601Parser#century}.
	 * @param ctx the parse tree
	 */
	void enterCompleteCentury(Iso8601Parser.CompleteCenturyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CompleteCentury}
	 * labeled alternative in {@link Iso8601Parser#century}.
	 * @param ctx the parse tree
	 */
	void exitCompleteCentury(Iso8601Parser.CompleteCenturyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExpandedCentury}
	 * labeled alternative in {@link Iso8601Parser#century}.
	 * @param ctx the parse tree
	 */
	void enterExpandedCentury(Iso8601Parser.ExpandedCenturyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExpandedCentury}
	 * labeled alternative in {@link Iso8601Parser#century}.
	 * @param ctx the parse tree
	 */
	void exitExpandedCentury(Iso8601Parser.ExpandedCenturyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CompleteYear}
	 * labeled alternative in {@link Iso8601Parser#year}.
	 * @param ctx the parse tree
	 */
	void enterCompleteYear(Iso8601Parser.CompleteYearContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CompleteYear}
	 * labeled alternative in {@link Iso8601Parser#year}.
	 * @param ctx the parse tree
	 */
	void exitCompleteYear(Iso8601Parser.CompleteYearContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExpandedYear}
	 * labeled alternative in {@link Iso8601Parser#year}.
	 * @param ctx the parse tree
	 */
	void enterExpandedYear(Iso8601Parser.ExpandedYearContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExpandedYear}
	 * labeled alternative in {@link Iso8601Parser#year}.
	 * @param ctx the parse tree
	 */
	void exitExpandedYear(Iso8601Parser.ExpandedYearContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#month}.
	 * @param ctx the parse tree
	 */
	void enterMonth(Iso8601Parser.MonthContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#month}.
	 * @param ctx the parse tree
	 */
	void exitMonth(Iso8601Parser.MonthContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#day}.
	 * @param ctx the parse tree
	 */
	void enterDay(Iso8601Parser.DayContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#day}.
	 * @param ctx the parse tree
	 */
	void exitDay(Iso8601Parser.DayContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#ordinalDay}.
	 * @param ctx the parse tree
	 */
	void enterOrdinalDay(Iso8601Parser.OrdinalDayContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#ordinalDay}.
	 * @param ctx the parse tree
	 */
	void exitOrdinalDay(Iso8601Parser.OrdinalDayContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#week}.
	 * @param ctx the parse tree
	 */
	void enterWeek(Iso8601Parser.WeekContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#week}.
	 * @param ctx the parse tree
	 */
	void exitWeek(Iso8601Parser.WeekContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#weekDay}.
	 * @param ctx the parse tree
	 */
	void enterWeekDay(Iso8601Parser.WeekDayContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#weekDay}.
	 * @param ctx the parse tree
	 */
	void exitWeekDay(Iso8601Parser.WeekDayContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#hour}.
	 * @param ctx the parse tree
	 */
	void enterHour(Iso8601Parser.HourContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#hour}.
	 * @param ctx the parse tree
	 */
	void exitHour(Iso8601Parser.HourContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#minute}.
	 * @param ctx the parse tree
	 */
	void enterMinute(Iso8601Parser.MinuteContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#minute}.
	 * @param ctx the parse tree
	 */
	void exitMinute(Iso8601Parser.MinuteContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#second}.
	 * @param ctx the parse tree
	 */
	void enterSecond(Iso8601Parser.SecondContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#second}.
	 * @param ctx the parse tree
	 */
	void exitSecond(Iso8601Parser.SecondContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#hourFraction}.
	 * @param ctx the parse tree
	 */
	void enterHourFraction(Iso8601Parser.HourFractionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#hourFraction}.
	 * @param ctx the parse tree
	 */
	void exitHourFraction(Iso8601Parser.HourFractionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#minuteFraction}.
	 * @param ctx the parse tree
	 */
	void enterMinuteFraction(Iso8601Parser.MinuteFractionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#minuteFraction}.
	 * @param ctx the parse tree
	 */
	void exitMinuteFraction(Iso8601Parser.MinuteFractionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#secondFraction}.
	 * @param ctx the parse tree
	 */
	void enterSecondFraction(Iso8601Parser.SecondFractionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#secondFraction}.
	 * @param ctx the parse tree
	 */
	void exitSecondFraction(Iso8601Parser.SecondFractionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#calendarDate}.
	 * @param ctx the parse tree
	 */
	void enterCalendarDate(Iso8601Parser.CalendarDateContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#calendarDate}.
	 * @param ctx the parse tree
	 */
	void exitCalendarDate(Iso8601Parser.CalendarDateContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#calendarDateBasic}.
	 * @param ctx the parse tree
	 */
	void enterCalendarDateBasic(Iso8601Parser.CalendarDateBasicContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#calendarDateBasic}.
	 * @param ctx the parse tree
	 */
	void exitCalendarDateBasic(Iso8601Parser.CalendarDateBasicContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#calendarDateExtended}.
	 * @param ctx the parse tree
	 */
	void enterCalendarDateExtended(Iso8601Parser.CalendarDateExtendedContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#calendarDateExtended}.
	 * @param ctx the parse tree
	 */
	void exitCalendarDateExtended(Iso8601Parser.CalendarDateExtendedContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#specificMonthCalendarDate}.
	 * @param ctx the parse tree
	 */
	void enterSpecificMonthCalendarDate(Iso8601Parser.SpecificMonthCalendarDateContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#specificMonthCalendarDate}.
	 * @param ctx the parse tree
	 */
	void exitSpecificMonthCalendarDate(Iso8601Parser.SpecificMonthCalendarDateContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#specificYearCalendarDate}.
	 * @param ctx the parse tree
	 */
	void enterSpecificYearCalendarDate(Iso8601Parser.SpecificYearCalendarDateContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#specificYearCalendarDate}.
	 * @param ctx the parse tree
	 */
	void exitSpecificYearCalendarDate(Iso8601Parser.SpecificYearCalendarDateContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#specificCenturyCalendarDate}.
	 * @param ctx the parse tree
	 */
	void enterSpecificCenturyCalendarDate(Iso8601Parser.SpecificCenturyCalendarDateContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#specificCenturyCalendarDate}.
	 * @param ctx the parse tree
	 */
	void exitSpecificCenturyCalendarDate(Iso8601Parser.SpecificCenturyCalendarDateContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#ordinalDate}.
	 * @param ctx the parse tree
	 */
	void enterOrdinalDate(Iso8601Parser.OrdinalDateContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#ordinalDate}.
	 * @param ctx the parse tree
	 */
	void exitOrdinalDate(Iso8601Parser.OrdinalDateContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#ordinalDateBasic}.
	 * @param ctx the parse tree
	 */
	void enterOrdinalDateBasic(Iso8601Parser.OrdinalDateBasicContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#ordinalDateBasic}.
	 * @param ctx the parse tree
	 */
	void exitOrdinalDateBasic(Iso8601Parser.OrdinalDateBasicContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#ordinalDateExtended}.
	 * @param ctx the parse tree
	 */
	void enterOrdinalDateExtended(Iso8601Parser.OrdinalDateExtendedContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#ordinalDateExtended}.
	 * @param ctx the parse tree
	 */
	void exitOrdinalDateExtended(Iso8601Parser.OrdinalDateExtendedContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#weekDate}.
	 * @param ctx the parse tree
	 */
	void enterWeekDate(Iso8601Parser.WeekDateContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#weekDate}.
	 * @param ctx the parse tree
	 */
	void exitWeekDate(Iso8601Parser.WeekDateContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#weekDateBasic}.
	 * @param ctx the parse tree
	 */
	void enterWeekDateBasic(Iso8601Parser.WeekDateBasicContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#weekDateBasic}.
	 * @param ctx the parse tree
	 */
	void exitWeekDateBasic(Iso8601Parser.WeekDateBasicContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#weekDateExtended}.
	 * @param ctx the parse tree
	 */
	void enterWeekDateExtended(Iso8601Parser.WeekDateExtendedContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#weekDateExtended}.
	 * @param ctx the parse tree
	 */
	void exitWeekDateExtended(Iso8601Parser.WeekDateExtendedContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#specificWeekWeekDate}.
	 * @param ctx the parse tree
	 */
	void enterSpecificWeekWeekDate(Iso8601Parser.SpecificWeekWeekDateContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#specificWeekWeekDate}.
	 * @param ctx the parse tree
	 */
	void exitSpecificWeekWeekDate(Iso8601Parser.SpecificWeekWeekDateContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#specificWeekWeekDateBasic}.
	 * @param ctx the parse tree
	 */
	void enterSpecificWeekWeekDateBasic(Iso8601Parser.SpecificWeekWeekDateBasicContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#specificWeekWeekDateBasic}.
	 * @param ctx the parse tree
	 */
	void exitSpecificWeekWeekDateBasic(Iso8601Parser.SpecificWeekWeekDateBasicContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#specificWeekWeekDateExtended}.
	 * @param ctx the parse tree
	 */
	void enterSpecificWeekWeekDateExtended(Iso8601Parser.SpecificWeekWeekDateExtendedContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#specificWeekWeekDateExtended}.
	 * @param ctx the parse tree
	 */
	void exitSpecificWeekWeekDateExtended(Iso8601Parser.SpecificWeekWeekDateExtendedContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#datePrecise}.
	 * @param ctx the parse tree
	 */
	void enterDatePrecise(Iso8601Parser.DatePreciseContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#datePrecise}.
	 * @param ctx the parse tree
	 */
	void exitDatePrecise(Iso8601Parser.DatePreciseContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#datePreciseBasic}.
	 * @param ctx the parse tree
	 */
	void enterDatePreciseBasic(Iso8601Parser.DatePreciseBasicContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#datePreciseBasic}.
	 * @param ctx the parse tree
	 */
	void exitDatePreciseBasic(Iso8601Parser.DatePreciseBasicContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#datePreciseExtended}.
	 * @param ctx the parse tree
	 */
	void enterDatePreciseExtended(Iso8601Parser.DatePreciseExtendedContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#datePreciseExtended}.
	 * @param ctx the parse tree
	 */
	void exitDatePreciseExtended(Iso8601Parser.DatePreciseExtendedContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#date}.
	 * @param ctx the parse tree
	 */
	void enterDate(Iso8601Parser.DateContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#date}.
	 * @param ctx the parse tree
	 */
	void exitDate(Iso8601Parser.DateContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#dateBasic}.
	 * @param ctx the parse tree
	 */
	void enterDateBasic(Iso8601Parser.DateBasicContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#dateBasic}.
	 * @param ctx the parse tree
	 */
	void exitDateBasic(Iso8601Parser.DateBasicContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#dateExtended}.
	 * @param ctx the parse tree
	 */
	void enterDateExtended(Iso8601Parser.DateExtendedContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#dateExtended}.
	 * @param ctx the parse tree
	 */
	void exitDateExtended(Iso8601Parser.DateExtendedContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#localTimePrecise}.
	 * @param ctx the parse tree
	 */
	void enterLocalTimePrecise(Iso8601Parser.LocalTimePreciseContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#localTimePrecise}.
	 * @param ctx the parse tree
	 */
	void exitLocalTimePrecise(Iso8601Parser.LocalTimePreciseContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#localTimePreciseBasic}.
	 * @param ctx the parse tree
	 */
	void enterLocalTimePreciseBasic(Iso8601Parser.LocalTimePreciseBasicContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#localTimePreciseBasic}.
	 * @param ctx the parse tree
	 */
	void exitLocalTimePreciseBasic(Iso8601Parser.LocalTimePreciseBasicContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#localTimePreciseExtended}.
	 * @param ctx the parse tree
	 */
	void enterLocalTimePreciseExtended(Iso8601Parser.LocalTimePreciseExtendedContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#localTimePreciseExtended}.
	 * @param ctx the parse tree
	 */
	void exitLocalTimePreciseExtended(Iso8601Parser.LocalTimePreciseExtendedContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#specificMinuteLocalTime}.
	 * @param ctx the parse tree
	 */
	void enterSpecificMinuteLocalTime(Iso8601Parser.SpecificMinuteLocalTimeContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#specificMinuteLocalTime}.
	 * @param ctx the parse tree
	 */
	void exitSpecificMinuteLocalTime(Iso8601Parser.SpecificMinuteLocalTimeContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#specificMinuteLocalTimeBasic}.
	 * @param ctx the parse tree
	 */
	void enterSpecificMinuteLocalTimeBasic(Iso8601Parser.SpecificMinuteLocalTimeBasicContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#specificMinuteLocalTimeBasic}.
	 * @param ctx the parse tree
	 */
	void exitSpecificMinuteLocalTimeBasic(Iso8601Parser.SpecificMinuteLocalTimeBasicContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#specificMinuteLocalTimeExtended}.
	 * @param ctx the parse tree
	 */
	void enterSpecificMinuteLocalTimeExtended(Iso8601Parser.SpecificMinuteLocalTimeExtendedContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#specificMinuteLocalTimeExtended}.
	 * @param ctx the parse tree
	 */
	void exitSpecificMinuteLocalTimeExtended(Iso8601Parser.SpecificMinuteLocalTimeExtendedContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#specificHourLocalTime}.
	 * @param ctx the parse tree
	 */
	void enterSpecificHourLocalTime(Iso8601Parser.SpecificHourLocalTimeContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#specificHourLocalTime}.
	 * @param ctx the parse tree
	 */
	void exitSpecificHourLocalTime(Iso8601Parser.SpecificHourLocalTimeContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#localTime}.
	 * @param ctx the parse tree
	 */
	void enterLocalTime(Iso8601Parser.LocalTimeContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#localTime}.
	 * @param ctx the parse tree
	 */
	void exitLocalTime(Iso8601Parser.LocalTimeContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#localTimeBasic}.
	 * @param ctx the parse tree
	 */
	void enterLocalTimeBasic(Iso8601Parser.LocalTimeBasicContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#localTimeBasic}.
	 * @param ctx the parse tree
	 */
	void exitLocalTimeBasic(Iso8601Parser.LocalTimeBasicContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#localTimeExtended}.
	 * @param ctx the parse tree
	 */
	void enterLocalTimeExtended(Iso8601Parser.LocalTimeExtendedContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#localTimeExtended}.
	 * @param ctx the parse tree
	 */
	void exitLocalTimeExtended(Iso8601Parser.LocalTimeExtendedContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#timeZoneUtc}.
	 * @param ctx the parse tree
	 */
	void enterTimeZoneUtc(Iso8601Parser.TimeZoneUtcContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#timeZoneUtc}.
	 * @param ctx the parse tree
	 */
	void exitTimeZoneUtc(Iso8601Parser.TimeZoneUtcContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#timeZone}.
	 * @param ctx the parse tree
	 */
	void enterTimeZone(Iso8601Parser.TimeZoneContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#timeZone}.
	 * @param ctx the parse tree
	 */
	void exitTimeZone(Iso8601Parser.TimeZoneContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#timeZoneBasic}.
	 * @param ctx the parse tree
	 */
	void enterTimeZoneBasic(Iso8601Parser.TimeZoneBasicContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#timeZoneBasic}.
	 * @param ctx the parse tree
	 */
	void exitTimeZoneBasic(Iso8601Parser.TimeZoneBasicContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#timeZoneExtended}.
	 * @param ctx the parse tree
	 */
	void enterTimeZoneExtended(Iso8601Parser.TimeZoneExtendedContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#timeZoneExtended}.
	 * @param ctx the parse tree
	 */
	void exitTimeZoneExtended(Iso8601Parser.TimeZoneExtendedContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#localTimeAndTimeZone}.
	 * @param ctx the parse tree
	 */
	void enterLocalTimeAndTimeZone(Iso8601Parser.LocalTimeAndTimeZoneContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#localTimeAndTimeZone}.
	 * @param ctx the parse tree
	 */
	void exitLocalTimeAndTimeZone(Iso8601Parser.LocalTimeAndTimeZoneContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#localTimeAndTimeZoneBasic}.
	 * @param ctx the parse tree
	 */
	void enterLocalTimeAndTimeZoneBasic(Iso8601Parser.LocalTimeAndTimeZoneBasicContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#localTimeAndTimeZoneBasic}.
	 * @param ctx the parse tree
	 */
	void exitLocalTimeAndTimeZoneBasic(Iso8601Parser.LocalTimeAndTimeZoneBasicContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#localTimeAndTimeZoneExtended}.
	 * @param ctx the parse tree
	 */
	void enterLocalTimeAndTimeZoneExtended(Iso8601Parser.LocalTimeAndTimeZoneExtendedContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#localTimeAndTimeZoneExtended}.
	 * @param ctx the parse tree
	 */
	void exitLocalTimeAndTimeZoneExtended(Iso8601Parser.LocalTimeAndTimeZoneExtendedContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#time}.
	 * @param ctx the parse tree
	 */
	void enterTime(Iso8601Parser.TimeContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#time}.
	 * @param ctx the parse tree
	 */
	void exitTime(Iso8601Parser.TimeContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#datetimePrecise}.
	 * @param ctx the parse tree
	 */
	void enterDatetimePrecise(Iso8601Parser.DatetimePreciseContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#datetimePrecise}.
	 * @param ctx the parse tree
	 */
	void exitDatetimePrecise(Iso8601Parser.DatetimePreciseContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#datetimePreciseBasic}.
	 * @param ctx the parse tree
	 */
	void enterDatetimePreciseBasic(Iso8601Parser.DatetimePreciseBasicContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#datetimePreciseBasic}.
	 * @param ctx the parse tree
	 */
	void exitDatetimePreciseBasic(Iso8601Parser.DatetimePreciseBasicContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#datetimePreciseExtended}.
	 * @param ctx the parse tree
	 */
	void enterDatetimePreciseExtended(Iso8601Parser.DatetimePreciseExtendedContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#datetimePreciseExtended}.
	 * @param ctx the parse tree
	 */
	void exitDatetimePreciseExtended(Iso8601Parser.DatetimePreciseExtendedContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#datetime}.
	 * @param ctx the parse tree
	 */
	void enterDatetime(Iso8601Parser.DatetimeContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#datetime}.
	 * @param ctx the parse tree
	 */
	void exitDatetime(Iso8601Parser.DatetimeContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#datetimeBasic}.
	 * @param ctx the parse tree
	 */
	void enterDatetimeBasic(Iso8601Parser.DatetimeBasicContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#datetimeBasic}.
	 * @param ctx the parse tree
	 */
	void exitDatetimeBasic(Iso8601Parser.DatetimeBasicContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#datetimeExtended}.
	 * @param ctx the parse tree
	 */
	void enterDatetimeExtended(Iso8601Parser.DatetimeExtendedContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#datetimeExtended}.
	 * @param ctx the parse tree
	 */
	void exitDatetimeExtended(Iso8601Parser.DatetimeExtendedContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#interval}.
	 * @param ctx the parse tree
	 */
	void enterInterval(Iso8601Parser.IntervalContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#interval}.
	 * @param ctx the parse tree
	 */
	void exitInterval(Iso8601Parser.IntervalContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#intervalBasic}.
	 * @param ctx the parse tree
	 */
	void enterIntervalBasic(Iso8601Parser.IntervalBasicContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#intervalBasic}.
	 * @param ctx the parse tree
	 */
	void exitIntervalBasic(Iso8601Parser.IntervalBasicContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#intervalExtended}.
	 * @param ctx the parse tree
	 */
	void enterIntervalExtended(Iso8601Parser.IntervalExtendedContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#intervalExtended}.
	 * @param ctx the parse tree
	 */
	void exitIntervalExtended(Iso8601Parser.IntervalExtendedContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#intervalT}.
	 * @param ctx the parse tree
	 */
	void enterIntervalT(Iso8601Parser.IntervalTContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#intervalT}.
	 * @param ctx the parse tree
	 */
	void exitIntervalT(Iso8601Parser.IntervalTContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#monthDateBasic}.
	 * @param ctx the parse tree
	 */
	void enterMonthDateBasic(Iso8601Parser.MonthDateBasicContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#monthDateBasic}.
	 * @param ctx the parse tree
	 */
	void exitMonthDateBasic(Iso8601Parser.MonthDateBasicContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#monthDateExtended}.
	 * @param ctx the parse tree
	 */
	void enterMonthDateExtended(Iso8601Parser.MonthDateExtendedContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#monthDateExtended}.
	 * @param ctx the parse tree
	 */
	void exitMonthDateExtended(Iso8601Parser.MonthDateExtendedContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#intervalYMDTimeBasic}.
	 * @param ctx the parse tree
	 */
	void enterIntervalYMDTimeBasic(Iso8601Parser.IntervalYMDTimeBasicContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#intervalYMDTimeBasic}.
	 * @param ctx the parse tree
	 */
	void exitIntervalYMDTimeBasic(Iso8601Parser.IntervalYMDTimeBasicContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#intervalYMDTimeExtended}.
	 * @param ctx the parse tree
	 */
	void enterIntervalYMDTimeExtended(Iso8601Parser.IntervalYMDTimeExtendedContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#intervalYMDTimeExtended}.
	 * @param ctx the parse tree
	 */
	void exitIntervalYMDTimeExtended(Iso8601Parser.IntervalYMDTimeExtendedContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#intervalYMD}.
	 * @param ctx the parse tree
	 */
	void enterIntervalYMD(Iso8601Parser.IntervalYMDContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#intervalYMD}.
	 * @param ctx the parse tree
	 */
	void exitIntervalYMD(Iso8601Parser.IntervalYMDContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#intervalYMDBasic}.
	 * @param ctx the parse tree
	 */
	void enterIntervalYMDBasic(Iso8601Parser.IntervalYMDBasicContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#intervalYMDBasic}.
	 * @param ctx the parse tree
	 */
	void exitIntervalYMDBasic(Iso8601Parser.IntervalYMDBasicContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#intervalYMDExtended}.
	 * @param ctx the parse tree
	 */
	void enterIntervalYMDExtended(Iso8601Parser.IntervalYMDExtendedContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#intervalYMDExtended}.
	 * @param ctx the parse tree
	 */
	void exitIntervalYMDExtended(Iso8601Parser.IntervalYMDExtendedContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#timeBeginEnd}.
	 * @param ctx the parse tree
	 */
	void enterTimeBeginEnd(Iso8601Parser.TimeBeginEndContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#timeBeginEnd}.
	 * @param ctx the parse tree
	 */
	void exitTimeBeginEnd(Iso8601Parser.TimeBeginEndContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#timeBeginEndBasic}.
	 * @param ctx the parse tree
	 */
	void enterTimeBeginEndBasic(Iso8601Parser.TimeBeginEndBasicContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#timeBeginEndBasic}.
	 * @param ctx the parse tree
	 */
	void exitTimeBeginEndBasic(Iso8601Parser.TimeBeginEndBasicContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#timeBeginEndExtended}.
	 * @param ctx the parse tree
	 */
	void enterTimeBeginEndExtended(Iso8601Parser.TimeBeginEndExtendedContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#timeBeginEndExtended}.
	 * @param ctx the parse tree
	 */
	void exitTimeBeginEndExtended(Iso8601Parser.TimeBeginEndExtendedContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#timeBeginInterval}.
	 * @param ctx the parse tree
	 */
	void enterTimeBeginInterval(Iso8601Parser.TimeBeginIntervalContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#timeBeginInterval}.
	 * @param ctx the parse tree
	 */
	void exitTimeBeginInterval(Iso8601Parser.TimeBeginIntervalContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#timeBeginIntervalBasic}.
	 * @param ctx the parse tree
	 */
	void enterTimeBeginIntervalBasic(Iso8601Parser.TimeBeginIntervalBasicContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#timeBeginIntervalBasic}.
	 * @param ctx the parse tree
	 */
	void exitTimeBeginIntervalBasic(Iso8601Parser.TimeBeginIntervalBasicContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#timeBeginIntervalExtended}.
	 * @param ctx the parse tree
	 */
	void enterTimeBeginIntervalExtended(Iso8601Parser.TimeBeginIntervalExtendedContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#timeBeginIntervalExtended}.
	 * @param ctx the parse tree
	 */
	void exitTimeBeginIntervalExtended(Iso8601Parser.TimeBeginIntervalExtendedContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#timeIntervalEnd}.
	 * @param ctx the parse tree
	 */
	void enterTimeIntervalEnd(Iso8601Parser.TimeIntervalEndContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#timeIntervalEnd}.
	 * @param ctx the parse tree
	 */
	void exitTimeIntervalEnd(Iso8601Parser.TimeIntervalEndContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#timeIntervalEndBasic}.
	 * @param ctx the parse tree
	 */
	void enterTimeIntervalEndBasic(Iso8601Parser.TimeIntervalEndBasicContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#timeIntervalEndBasic}.
	 * @param ctx the parse tree
	 */
	void exitTimeIntervalEndBasic(Iso8601Parser.TimeIntervalEndBasicContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#timeIntervalEndExtended}.
	 * @param ctx the parse tree
	 */
	void enterTimeIntervalEndExtended(Iso8601Parser.TimeIntervalEndExtendedContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#timeIntervalEndExtended}.
	 * @param ctx the parse tree
	 */
	void exitTimeIntervalEndExtended(Iso8601Parser.TimeIntervalEndExtendedContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#duration}.
	 * @param ctx the parse tree
	 */
	void enterDuration(Iso8601Parser.DurationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#duration}.
	 * @param ctx the parse tree
	 */
	void exitDuration(Iso8601Parser.DurationContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#durationBasic}.
	 * @param ctx the parse tree
	 */
	void enterDurationBasic(Iso8601Parser.DurationBasicContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#durationBasic}.
	 * @param ctx the parse tree
	 */
	void exitDurationBasic(Iso8601Parser.DurationBasicContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#durationExtended}.
	 * @param ctx the parse tree
	 */
	void enterDurationExtended(Iso8601Parser.DurationExtendedContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#durationExtended}.
	 * @param ctx the parse tree
	 */
	void exitDurationExtended(Iso8601Parser.DurationExtendedContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#recurringCount}.
	 * @param ctx the parse tree
	 */
	void enterRecurringCount(Iso8601Parser.RecurringCountContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#recurringCount}.
	 * @param ctx the parse tree
	 */
	void exitRecurringCount(Iso8601Parser.RecurringCountContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#recurring}.
	 * @param ctx the parse tree
	 */
	void enterRecurring(Iso8601Parser.RecurringContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#recurring}.
	 * @param ctx the parse tree
	 */
	void exitRecurring(Iso8601Parser.RecurringContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#recurringBasic}.
	 * @param ctx the parse tree
	 */
	void enterRecurringBasic(Iso8601Parser.RecurringBasicContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#recurringBasic}.
	 * @param ctx the parse tree
	 */
	void exitRecurringBasic(Iso8601Parser.RecurringBasicContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#recurringExtended}.
	 * @param ctx the parse tree
	 */
	void enterRecurringExtended(Iso8601Parser.RecurringExtendedContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#recurringExtended}.
	 * @param ctx the parse tree
	 */
	void exitRecurringExtended(Iso8601Parser.RecurringExtendedContext ctx);
	/**
	 * Enter a parse tree produced by {@link Iso8601Parser#iso}.
	 * @param ctx the parse tree
	 */
	void enterIso(Iso8601Parser.IsoContext ctx);
	/**
	 * Exit a parse tree produced by {@link Iso8601Parser#iso}.
	 * @param ctx the parse tree
	 */
	void exitIso(Iso8601Parser.IsoContext ctx);
}