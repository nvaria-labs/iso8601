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
		T__0=1, T__1=2, T__2=3, T__3=4, Newline=5, T=6, Z=7, W=8, P=9, Y=10, M=11, 
		D=12, H=13, S=14, R=15, Digit=16, Fraction=17;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "Newline", "T", "Z", "W", "P", "Y", "M", 
			"D", "H", "S", "R", "Digit", "Fraction"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'+'", "'-'", "':'", "'/'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, "Newline", "T", "Z", "W", "P", "Y", "M", 
			"D", "H", "S", "R", "Digit", "Fraction"
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
		"\u0004\u0000\u0011K\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0001\u0000\u0001\u0000\u0001"+
		"\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001"+
		"\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001"+
		"\t\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\r\u0001"+
		"\r\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010"+
		"\u0004\u0010H\b\u0010\u000b\u0010\f\u0010I\u0000\u0000\u0011\u0001\u0001"+
		"\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f"+
		"\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e\u001d\u000f"+
		"\u001f\u0010!\u0011\u0001\u0000\r\u0002\u0000\n\n\r\r\u0002\u0000TTtt"+
		"\u0002\u0000ZZzz\u0002\u0000WWww\u0002\u0000PPpp\u0002\u0000YYyy\u0002"+
		"\u0000MMmm\u0002\u0000DDdd\u0002\u0000HHhh\u0002\u0000SSss\u0002\u0000"+
		"RRrr\u0001\u000009\u0002\u0000,,..K\u0000\u0001\u0001\u0000\u0000\u0000"+
		"\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000"+
		"\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000"+
		"\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f"+
		"\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013"+
		"\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017"+
		"\u0001\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b"+
		"\u0001\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f"+
		"\u0001\u0000\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0001#\u0001\u0000"+
		"\u0000\u0000\u0003%\u0001\u0000\u0000\u0000\u0005\'\u0001\u0000\u0000"+
		"\u0000\u0007)\u0001\u0000\u0000\u0000\t+\u0001\u0000\u0000\u0000\u000b"+
		"/\u0001\u0000\u0000\u0000\r1\u0001\u0000\u0000\u0000\u000f3\u0001\u0000"+
		"\u0000\u0000\u00115\u0001\u0000\u0000\u0000\u00137\u0001\u0000\u0000\u0000"+
		"\u00159\u0001\u0000\u0000\u0000\u0017;\u0001\u0000\u0000\u0000\u0019="+
		"\u0001\u0000\u0000\u0000\u001b?\u0001\u0000\u0000\u0000\u001dA\u0001\u0000"+
		"\u0000\u0000\u001fC\u0001\u0000\u0000\u0000!E\u0001\u0000\u0000\u0000"+
		"#$\u0005+\u0000\u0000$\u0002\u0001\u0000\u0000\u0000%&\u0005-\u0000\u0000"+
		"&\u0004\u0001\u0000\u0000\u0000\'(\u0005:\u0000\u0000(\u0006\u0001\u0000"+
		"\u0000\u0000)*\u0005/\u0000\u0000*\b\u0001\u0000\u0000\u0000+,\u0007\u0000"+
		"\u0000\u0000,-\u0001\u0000\u0000\u0000-.\u0006\u0004\u0000\u0000.\n\u0001"+
		"\u0000\u0000\u0000/0\u0007\u0001\u0000\u00000\f\u0001\u0000\u0000\u0000"+
		"12\u0007\u0002\u0000\u00002\u000e\u0001\u0000\u0000\u000034\u0007\u0003"+
		"\u0000\u00004\u0010\u0001\u0000\u0000\u000056\u0007\u0004\u0000\u0000"+
		"6\u0012\u0001\u0000\u0000\u000078\u0007\u0005\u0000\u00008\u0014\u0001"+
		"\u0000\u0000\u00009:\u0007\u0006\u0000\u0000:\u0016\u0001\u0000\u0000"+
		"\u0000;<\u0007\u0007\u0000\u0000<\u0018\u0001\u0000\u0000\u0000=>\u0007"+
		"\b\u0000\u0000>\u001a\u0001\u0000\u0000\u0000?@\u0007\t\u0000\u0000@\u001c"+
		"\u0001\u0000\u0000\u0000AB\u0007\n\u0000\u0000B\u001e\u0001\u0000\u0000"+
		"\u0000CD\u0007\u000b\u0000\u0000D \u0001\u0000\u0000\u0000EG\u0007\f\u0000"+
		"\u0000FH\u0003\u001f\u000f\u0000GF\u0001\u0000\u0000\u0000HI\u0001\u0000"+
		"\u0000\u0000IG\u0001\u0000\u0000\u0000IJ\u0001\u0000\u0000\u0000J\"\u0001"+
		"\u0000\u0000\u0000\u0002\u0000I\u0001\u0000\u0001\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}