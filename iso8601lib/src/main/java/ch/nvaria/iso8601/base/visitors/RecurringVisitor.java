package ch.nvaria.iso8601.base.visitors;

import ch.nvaria.generated.Iso8601BaseVisitor;
import ch.nvaria.generated.Iso8601Parser;

public class RecurringVisitor extends Iso8601BaseVisitor<String> {

    @Override
    public String visitRecurringCount(Iso8601Parser.RecurringCountContext ctx) {
        visitChildren(ctx);
        if(ctx.getChildCount() == 1){
            return "0";
        }
        if(ctx.getChild(1).getText().equals("-1")){
            return String.valueOf(Integer.MAX_VALUE);
        }
        return ctx.getChild(1).getText();
    }


    @Override
    public String visitRecurringExtended(Iso8601Parser.RecurringExtendedContext ctx) {
        visitChildren(ctx);
        if(ctx.getChildCount()<3){
            return null;
        } else {
            return ctx.getChild(2).getChild(0).getChild(0).getText();
        }
    }

}
