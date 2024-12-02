package ch.nvaria.iso8601.base.visitors;

import ch.nvaria.generated.Iso8601BaseVisitor;
import ch.nvaria.generated.Iso8601Parser;

public class PeriodOfTimeVisitor extends Iso8601BaseVisitor<String> {


    @Override
    public String visitInterval(Iso8601Parser.IntervalContext ctx) {
        visitChildren(ctx);
        return ctx.intervalT().getText();
    }

}
