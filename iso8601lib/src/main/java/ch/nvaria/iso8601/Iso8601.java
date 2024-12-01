package ch.nvaria.iso8601;


import ch.nvaria.generated.Iso8601Lexer;
import ch.nvaria.generated.Iso8601Parser;
import ch.nvaria.iso8601.visitors.RecurringVisitor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Iso8601 {


     public static void main(String[] args) {

        String isoString = "R12/1900-01-01T00:00:00/P4294967296S";

        CodePointCharStream isostram =  CharStreams.fromString(isoString);

        Lexer lexer = new Iso8601Lexer(isostram);
        /*
         * get a TokenStream on the Lexer
         */
        TokenStream tokenStream = new CommonTokenStream(lexer);
        /*
         * make a Parser on the token stream
         */
        Iso8601Parser parser = new Iso8601Parser(tokenStream);
        ParseTree parseTree = parser.recurringCount();

         Integer answer = new RecurringVisitor().visit(parseTree);
         System.out.println(answer);

    }
}
