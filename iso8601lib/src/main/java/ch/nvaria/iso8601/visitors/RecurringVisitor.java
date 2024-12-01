package ch.nvaria.iso8601.visitors;

import ch.nvaria.generated.Iso8601BaseVisitor;
import ch.nvaria.generated.Iso8601Parser;

public class RecurringVisitor extends Iso8601BaseVisitor<Integer> {

    @Override
    public Integer visitRecurringCount(Iso8601Parser.RecurringCountContext ctx) {
        visitChildren(ctx);
        if(ctx.getChildCount() ==1){
            return 0;
        }
        String res = ctx.getChild(1).getText();
        return Integer.valueOf(res);

    }



    @Override
    public Integer visitRecurring(Iso8601Parser.RecurringContext ctx) {
        visitChildren(ctx);
        String res = ctx.getText();

        return 0;
    }

    @Override
    public Integer visitRecurringBasic(Iso8601Parser.RecurringBasicContext ctx) {
        visitChildren(ctx);
        String res = ctx.getText();

        return 0;
    }

    @Override
    public Integer visitRecurringExtended(Iso8601Parser.RecurringExtendedContext ctx) {
        visitChildren(ctx);
        String res = ctx.getText();

        return 0;
    }

}
