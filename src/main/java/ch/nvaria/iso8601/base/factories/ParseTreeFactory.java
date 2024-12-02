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
        CodePointCharStream isostram =  CharStreams.fromString(iso8601);
        Lexer lexer = new Iso8601Lexer(isostram);
        TokenStream tokenStream = new CommonTokenStream(lexer);
        return new Iso8601Parser(tokenStream);
    }
}
