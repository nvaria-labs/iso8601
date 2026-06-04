package ch.nvaria.iso8601.base.factories;

import ch.nvaria.generated.Iso8601Lexer;
import ch.nvaria.generated.Iso8601Parser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;

public class ParseTreeFactory {

    private ParseTreeFactory() {
    }

    public static Iso8601Parser createParser(String iso8601){
        CodePointCharStream isoStream =  CharStreams.fromString(iso8601);
        Lexer lexer = new Iso8601Lexer(isoStream);
        // Drop ANTLR's default ConsoleErrorListener: malformed input (e.g. duration("P"), duration(""))
        // is a normal runtime path that callers handle by catching and returning null, so the parse
        // errors must not be dumped to System.err. The errored tree still flows downstream, where
        // Period.parse / Duration.parse reject it. Mirrors how the FEEL grammar is set up in feel-graalvm.
        lexer.removeErrorListeners();
        TokenStream tokenStream = new CommonTokenStream(lexer);
        Iso8601Parser parser = new Iso8601Parser(tokenStream);
        parser.removeErrorListeners();
        return parser;
    }
}
