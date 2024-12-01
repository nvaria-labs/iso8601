package ch.nvaria.iso8601.base.visitors;

import ch.nvaria.generated.Iso8601BaseVisitor;
import ch.nvaria.generated.Iso8601Parser;

public class RecurringVisitor extends Iso8601BaseVisitor<String> {

    @Override
    public String visitRecurring(Iso8601Parser.RecurringContext ctx) {
        visitChildren(ctx);

        if (ctx.recurringBasic() != null) {
            return ctx.recurringBasic().durationBasic().intervalBasic().intervalT().getText();
        }

        if (ctx.recurringExtended() != null) {
            return ctx.recurringExtended().durationExtended().getChild(0).getChild(2).getText();
        }

        return null;
    }


    @Override
    public String visitRecurringCount(Iso8601Parser.RecurringCountContext ctx) {
        visitChildren(ctx);
        if (ctx.getChildCount() == 1) {
            return "0";
        }
        if (ctx.getChild(1).getText().equals("-1")) {
            return String.valueOf(Integer.MAX_VALUE);
        }
        return ctx.getChild(1).getText();
    }







}
