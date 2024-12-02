// Generated from Iso8601.g4 by ANTLR 4.13.2
package ch.nvaria.generated;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class Iso8601Lexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, Newline=6, T=7, Z=8, W=9, P=10, 
		Y=11, M=12, D=13, H=14, S=15, R=16, Digit=17, Fraction=18;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "Newline", "T", "Z", "W", "P", 
			"Y", "M", "D", "H", "S", "R", "Digit", "Fraction"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'+'", "'-'", "':'", "'/'", "'-1'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, "Newline", "T", "Z", "W", "P", "Y", 
			"M", "D", "H", "S", "R", "Digit", "Fraction"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public Iso8601Lexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Iso8601.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u0012P\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0001"+
		"\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001"+
		"\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0007\u0001"+
		"\u0007\u0001\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001\n\u0001\u000b\u0001"+
		"\u000b\u0001\f\u0001\f\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000f"+
		"\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0004\u0011"+
		"M\b\u0011\u000b\u0011\f\u0011N\u0000\u0000\u0012\u0001\u0001\u0003\u0002"+
		"\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013"+
		"\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011"+
		"#\u0012\u0001\u0000\r\u0002\u0000\n\n\r\r\u0002\u0000TTtt\u0002\u0000"+
		"ZZzz\u0002\u0000WWww\u0002\u0000PPpp\u0002\u0000YYyy\u0002\u0000MMmm\u0002"+
		"\u0000DDdd\u0002\u0000HHhh\u0002\u0000SSss\u0002\u0000RRrr\u0001\u0000"+
		"09\u0002\u0000,,..P\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001"+
		"\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001"+
		"\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000"+
		"\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000"+
		"\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000"+
		"\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000"+
		"\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000"+
		"\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000"+
		"\u0000\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000\u0001"+
		"%\u0001\u0000\u0000\u0000\u0003\'\u0001\u0000\u0000\u0000\u0005)\u0001"+
		"\u0000\u0000\u0000\u0007+\u0001\u0000\u0000\u0000\t-\u0001\u0000\u0000"+
		"\u0000\u000b0\u0001\u0000\u0000\u0000\r4\u0001\u0000\u0000\u0000\u000f"+
		"6\u0001\u0000\u0000\u0000\u00118\u0001\u0000\u0000\u0000\u0013:\u0001"+
		"\u0000\u0000\u0000\u0015<\u0001\u0000\u0000\u0000\u0017>\u0001\u0000\u0000"+
		"\u0000\u0019@\u0001\u0000\u0000\u0000\u001bB\u0001\u0000\u0000\u0000\u001d"+
		"D\u0001\u0000\u0000\u0000\u001fF\u0001\u0000\u0000\u0000!H\u0001\u0000"+
		"\u0000\u0000#J\u0001\u0000\u0000\u0000%&\u0005+\u0000\u0000&\u0002\u0001"+
		"\u0000\u0000\u0000\'(\u0005-\u0000\u0000(\u0004\u0001\u0000\u0000\u0000"+
		")*\u0005:\u0000\u0000*\u0006\u0001\u0000\u0000\u0000+,\u0005/\u0000\u0000"+
		",\b\u0001\u0000\u0000\u0000-.\u0005-\u0000\u0000./\u00051\u0000\u0000"+
		"/\n\u0001\u0000\u0000\u000001\u0007\u0000\u0000\u000012\u0001\u0000\u0000"+
		"\u000023\u0006\u0005\u0000\u00003\f\u0001\u0000\u0000\u000045\u0007\u0001"+
		"\u0000\u00005\u000e\u0001\u0000\u0000\u000067\u0007\u0002\u0000\u0000"+
		"7\u0010\u0001\u0000\u0000\u000089\u0007\u0003\u0000\u00009\u0012\u0001"+
		"\u0000\u0000\u0000:;\u0007\u0004\u0000\u0000;\u0014\u0001\u0000\u0000"+
		"\u0000<=\u0007\u0005\u0000\u0000=\u0016\u0001\u0000\u0000\u0000>?\u0007"+
		"\u0006\u0000\u0000?\u0018\u0001\u0000\u0000\u0000@A\u0007\u0007\u0000"+
		"\u0000A\u001a\u0001\u0000\u0000\u0000BC\u0007\b\u0000\u0000C\u001c\u0001"+
		"\u0000\u0000\u0000DE\u0007\t\u0000\u0000E\u001e\u0001\u0000\u0000\u0000"+
		"FG\u0007\n\u0000\u0000G \u0001\u0000\u0000\u0000HI\u0007\u000b\u0000\u0000"+
		"I\"\u0001\u0000\u0000\u0000JL\u0007\f\u0000\u0000KM\u0003!\u0010\u0000"+
		"LK\u0001\u0000\u0000\u0000MN\u0001\u0000\u0000\u0000NL\u0001\u0000\u0000"+
		"\u0000NO\u0001\u0000\u0000\u0000O$\u0001\u0000\u0000\u0000\u0002\u0000"+
		"N\u0001\u0000\u0001\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}