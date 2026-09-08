package ch.nvaria.iso8601.base.factories;

import ch.nvaria.generated.Iso8601Lexer;
import ch.nvaria.generated.Iso8601Parser;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;

import java.time.DateTimeException;
import java.time.format.DateTimeParseException;
import java.util.function.Function;

public class ParseTreeFactory {

    private ParseTreeFactory() {
    }

    /**
     * Creates a parser that silently tolerates malformed input (ANTLR error recovery).
     * Prefer {@link #parse(String, Function)} which fails fast with a {@link DateTimeParseException}.
     *
     * @param iso8601 the input
     * @return a parser with no error listeners attached
     */
    public static Iso8601Parser createParser(String iso8601) {
        CodePointCharStream isoStream = CharStreams.fromString(iso8601);
        Lexer lexer = new Iso8601Lexer(isoStream);
        lexer.removeErrorListeners();
        TokenStream tokenStream = new CommonTokenStream(lexer);
        Iso8601Parser parser = new Iso8601Parser(tokenStream);
        parser.removeErrorListeners();
        return parser;
    }

    /**
     * Parses the whole input with the given entry rule.
     * The first syntax error, and any trailing input the rule did not consume, results in a
     * {@link DateTimeParseException}.
     *
     * @param iso8601 the input
     * @param rule    the entry rule, e.g. {@code Iso8601Parser::datetime}
     * @param <T>     the context type produced by the rule
     * @return the parse tree
     * @throws DateTimeParseException if the input is not valid for the rule
     */
    public static <T extends ParserRuleContext> T parse(String iso8601, Function<Iso8601Parser, T> rule) {
        if (iso8601 == null) {
            throw new DateTimeParseException("iso8601 string is null", "", 0);
        }
        Iso8601Parser parser = createParser(iso8601);
        ThrowingErrorListener listener = new ThrowingErrorListener(iso8601);
        ((Lexer) parser.getTokenStream().getTokenSource()).addErrorListener(listener);
        parser.addErrorListener(listener);

        T ctx = rule.apply(parser);

        Token next = parser.getCurrentToken();
        if (next.getType() != Token.EOF) {
            throw new DateTimeParseException("Unexpected input '" + next.getText() + "' at index "
                    + next.getStartIndex() + " in '" + iso8601 + "'", iso8601, next.getStartIndex());
        }
        return ctx;
    }

    /**
     * Parses the whole input and maps the tree to a value. Range errors raised while mapping
     * (e.g. month 13) are reported as {@link DateTimeParseException} as well, so callers only
     * need to handle one exception type.
     *
     * @param iso8601 the input
     * @param rule    the entry rule
     * @param mapper  converts the tree to the result
     * @param <T>     the context type produced by the rule
     * @param <R>     the result type
     * @return the mapped value
     * @throws DateTimeParseException if the input is not valid
     */
    public static <T extends ParserRuleContext, R> R parse(String iso8601, Function<Iso8601Parser, T> rule, Function<T, R> mapper) {
        T ctx = parse(iso8601, rule);
        try {
            return mapper.apply(ctx);
        } catch (DateTimeParseException e) {
            throw e;
        } catch (DateTimeException | ArithmeticException e) {
            throw new DateTimeParseException(e.getMessage() + " in '" + iso8601 + "'", iso8601, 0, e);
        }
    }

    private static final class ThrowingErrorListener extends BaseErrorListener {
        private final String input;

        private ThrowingErrorListener(String input) {
            this.input = input;
        }

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
                                int charPositionInLine, String msg, RecognitionException e) {
            throw new DateTimeParseException("Invalid iso8601 '" + input + "': " + msg + " at index "
                    + charPositionInLine, input, charPositionInLine, e);
        }
    }
}
