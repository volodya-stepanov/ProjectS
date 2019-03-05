// Generated from D:/version-control/ProjectS/src/main\Arithmetic.g4 by ANTLR 4.7.2
package com.razrabotkin.systematics.gen;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ArithmeticLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		SIN=1, COS=2, TAN=3, CTAN=4, LOG=5, LN=6, EXP=7, SQRT=8, VARIABLE=9, SCIENTIFIC_NUMBER=10, 
		LPAREN=11, RPAREN=12, PLUS=13, MINUS=14, TIMES=15, DIV=16, GT=17, LT=18, 
		EQ=19, POINT=20, POW=21, PRIME=22, COMMA=23, WS=24;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"SIN", "COS", "TAN", "CTAN", "LOG", "LN", "EXP", "SQRT", "VARIABLE", 
			"VALID_ID_START", "VALID_ID_CHAR", "SCIENTIFIC_NUMBER", "NUMBER", "E", 
			"SIGN", "LPAREN", "RPAREN", "PLUS", "MINUS", "TIMES", "DIV", "GT", "LT", 
			"EQ", "POINT", "POW", "PRIME", "COMMA", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'sin'", "'cos'", "'tan'", "'ctan'", "'log'", "'ln'", "'exp'", 
			"'sqrt'", null, null, "'('", "')'", "'+'", "'-'", "'*'", "'/'", "'>'", 
			"'<'", "'='", "'.'", "'^'", "'''", "','"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "SIN", "COS", "TAN", "CTAN", "LOG", "LN", "EXP", "SQRT", "VARIABLE", 
			"SCIENTIFIC_NUMBER", "LPAREN", "RPAREN", "PLUS", "MINUS", "TIMES", "DIV", 
			"GT", "LT", "EQ", "POINT", "POW", "PRIME", "COMMA", "WS"
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


	public ArithmeticLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Arithmetic.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\32\u00a7\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\3\2\3\2\3\2\3"+
		"\2\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6"+
		"\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\7\na\n\n\f\n"+
		"\16\nd\13\n\3\13\5\13g\n\13\3\f\3\f\5\fk\n\f\3\r\3\r\3\r\5\rp\n\r\3\r"+
		"\3\r\5\rt\n\r\3\16\6\16w\n\16\r\16\16\16x\3\16\3\16\6\16}\n\16\r\16\16"+
		"\16~\5\16\u0081\n\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23"+
		"\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32"+
		"\3\33\3\33\3\34\3\34\3\35\3\35\3\36\6\36\u00a2\n\36\r\36\16\36\u00a3\3"+
		"\36\3\36\2\2\37\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\2\27\2\31"+
		"\f\33\2\35\2\37\2!\r#\16%\17\'\20)\21+\22-\23/\24\61\25\63\26\65\27\67"+
		"\309\31;\32\3\2\6\5\2C\\aac|\4\2GGgg\4\2--//\5\2\13\f\17\17\"\"\2\u00a9"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\31\3\2\2\2\2!\3\2\2\2\2"+
		"#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3"+
		"\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2"+
		"\2;\3\2\2\2\3=\3\2\2\2\5A\3\2\2\2\7E\3\2\2\2\tI\3\2\2\2\13N\3\2\2\2\r"+
		"R\3\2\2\2\17U\3\2\2\2\21Y\3\2\2\2\23^\3\2\2\2\25f\3\2\2\2\27j\3\2\2\2"+
		"\31l\3\2\2\2\33v\3\2\2\2\35\u0082\3\2\2\2\37\u0084\3\2\2\2!\u0086\3\2"+
		"\2\2#\u0088\3\2\2\2%\u008a\3\2\2\2\'\u008c\3\2\2\2)\u008e\3\2\2\2+\u0090"+
		"\3\2\2\2-\u0092\3\2\2\2/\u0094\3\2\2\2\61\u0096\3\2\2\2\63\u0098\3\2\2"+
		"\2\65\u009a\3\2\2\2\67\u009c\3\2\2\29\u009e\3\2\2\2;\u00a1\3\2\2\2=>\7"+
		"u\2\2>?\7k\2\2?@\7p\2\2@\4\3\2\2\2AB\7e\2\2BC\7q\2\2CD\7u\2\2D\6\3\2\2"+
		"\2EF\7v\2\2FG\7c\2\2GH\7p\2\2H\b\3\2\2\2IJ\7e\2\2JK\7v\2\2KL\7c\2\2LM"+
		"\7p\2\2M\n\3\2\2\2NO\7n\2\2OP\7q\2\2PQ\7i\2\2Q\f\3\2\2\2RS\7n\2\2ST\7"+
		"p\2\2T\16\3\2\2\2UV\7g\2\2VW\7z\2\2WX\7r\2\2X\20\3\2\2\2YZ\7u\2\2Z[\7"+
		"s\2\2[\\\7t\2\2\\]\7v\2\2]\22\3\2\2\2^b\5\25\13\2_a\5\27\f\2`_\3\2\2\2"+
		"ad\3\2\2\2b`\3\2\2\2bc\3\2\2\2c\24\3\2\2\2db\3\2\2\2eg\t\2\2\2fe\3\2\2"+
		"\2g\26\3\2\2\2hk\5\25\13\2ik\4\62;\2jh\3\2\2\2ji\3\2\2\2k\30\3\2\2\2l"+
		"s\5\33\16\2mo\5\35\17\2np\5\37\20\2on\3\2\2\2op\3\2\2\2pq\3\2\2\2qr\5"+
		"\33\16\2rt\3\2\2\2sm\3\2\2\2st\3\2\2\2t\32\3\2\2\2uw\4\62;\2vu\3\2\2\2"+
		"wx\3\2\2\2xv\3\2\2\2xy\3\2\2\2y\u0080\3\2\2\2z|\7\60\2\2{}\4\62;\2|{\3"+
		"\2\2\2}~\3\2\2\2~|\3\2\2\2~\177\3\2\2\2\177\u0081\3\2\2\2\u0080z\3\2\2"+
		"\2\u0080\u0081\3\2\2\2\u0081\34\3\2\2\2\u0082\u0083\t\3\2\2\u0083\36\3"+
		"\2\2\2\u0084\u0085\t\4\2\2\u0085 \3\2\2\2\u0086\u0087\7*\2\2\u0087\"\3"+
		"\2\2\2\u0088\u0089\7+\2\2\u0089$\3\2\2\2\u008a\u008b\7-\2\2\u008b&\3\2"+
		"\2\2\u008c\u008d\7/\2\2\u008d(\3\2\2\2\u008e\u008f\7,\2\2\u008f*\3\2\2"+
		"\2\u0090\u0091\7\61\2\2\u0091,\3\2\2\2\u0092\u0093\7@\2\2\u0093.\3\2\2"+
		"\2\u0094\u0095\7>\2\2\u0095\60\3\2\2\2\u0096\u0097\7?\2\2\u0097\62\3\2"+
		"\2\2\u0098\u0099\7\60\2\2\u0099\64\3\2\2\2\u009a\u009b\7`\2\2\u009b\66"+
		"\3\2\2\2\u009c\u009d\7)\2\2\u009d8\3\2\2\2\u009e\u009f\7.\2\2\u009f:\3"+
		"\2\2\2\u00a0\u00a2\t\5\2\2\u00a1\u00a0\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3"+
		"\u00a1\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\u00a6\b\36"+
		"\2\2\u00a6<\3\2\2\2\f\2bfjosx~\u0080\u00a3\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}