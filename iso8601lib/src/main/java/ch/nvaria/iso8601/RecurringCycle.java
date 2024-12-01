package ch.nvaria.iso8601;


import ch.nvaria.generated.Iso8601Parser;
import ch.nvaria.iso8601.base.factories.ParseTreeFactory;
import ch.nvaria.iso8601.base.visitors.RecurringVisitor;
import org.antlr.v4.runtime.tree.ParseTree;

public final class RecurringCycle {

    private RecurringCycle() {
    }

    //-----------------------------------------------------------------------
    /**
     * Retrieves the number of repetitions of a iso8601 string.
     * Example: R5/<someDate> wil result in 5
     *
     * @return the number of repetitions
     * @since 1.0
     */
    public static Integer repetitions(String iso8601) {
        Iso8601Parser parser = ParseTreeFactory.createParser(iso8601);
        ParseTree parseTree = parser.recurringCount();
        return new RecurringVisitor().visit(parseTree);
     }
}
