package ch.nvaria.iso8601.base.visitors;

import ch.nvaria.generated.Iso8601BaseVisitor;
import ch.nvaria.generated.Iso8601Parser;

public class RecurringVisitor extends Iso8601BaseVisitor<Integer> {

    @Override
    public Integer visitRecurringCount(Iso8601Parser.RecurringCountContext ctx) {
        visitChildren(ctx);
        if(ctx.getChildCount() == 1){
            return 0;
        }
        String res = ctx.getChild(1).getText();
        return Integer.valueOf(res);

    }

}
