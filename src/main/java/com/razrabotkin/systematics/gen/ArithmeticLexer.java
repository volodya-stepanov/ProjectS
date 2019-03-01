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
		EXP=1, SQRT=2, VARIABLE=3, SCIENTIFIC_NUMBER=4, LPAREN=5, RPAREN=6, PLUS=7, 
		MINUS=8, TIMES=9, DIV=10, GT=11, LT=12, EQ=13, POINT=14, POW=15, PRIME=16, 
		WS=17;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"EXP", "SQRT", "VARIABLE", "VALID_ID_START", "VALID_ID_CHAR", "SCIENTIFIC_NUMBER", 
			"NUMBER", "E", "SIGN", "LPAREN", "RPAREN", "PLUS", "MINUS", "TIMES", 
			"DIV", "GT", "LT", "EQ", "POINT", "POW", "PRIME", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'exp'", "'sqrt'", null, null, "'('", "')'", "'+'", "'-'", "'*'", 
			"'/'", "'>'", "'<'", "'='", "'.'", "'^'", "'''"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "EXP", "SQRT", "VARIABLE", "SCIENTIFIC_NUMBER", "LPAREN", "RPAREN", 
			"PLUS", "MINUS", "TIMES", "DIV", "GT", "LT", "EQ", "POINT", "POW", "PRIME", 
			"WS"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\23\177\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\3\2\3\2\3\2\3\2\3\3"+
		"\3\3\3\3\3\3\3\3\3\4\3\4\7\4;\n\4\f\4\16\4>\13\4\3\5\5\5A\n\5\3\6\3\6"+
		"\5\6E\n\6\3\7\3\7\3\7\5\7J\n\7\3\7\3\7\5\7N\n\7\3\b\6\bQ\n\b\r\b\16\b"+
		"R\3\b\3\b\6\bW\n\b\r\b\16\bX\5\b[\n\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3"+
		"\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23"+
		"\3\24\3\24\3\25\3\25\3\26\3\26\3\27\6\27z\n\27\r\27\16\27{\3\27\3\27\2"+
		"\2\30\3\3\5\4\7\5\t\2\13\2\r\6\17\2\21\2\23\2\25\7\27\b\31\t\33\n\35\13"+
		"\37\f!\r#\16%\17\'\20)\21+\22-\23\3\2\6\5\2C\\aac|\4\2GGgg\4\2--//\5\2"+
		"\13\f\17\17\"\"\2\u0081\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\r\3\2\2"+
		"\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2"+
		"\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2"+
		"+\3\2\2\2\2-\3\2\2\2\3/\3\2\2\2\5\63\3\2\2\2\78\3\2\2\2\t@\3\2\2\2\13"+
		"D\3\2\2\2\rF\3\2\2\2\17P\3\2\2\2\21\\\3\2\2\2\23^\3\2\2\2\25`\3\2\2\2"+
		"\27b\3\2\2\2\31d\3\2\2\2\33f\3\2\2\2\35h\3\2\2\2\37j\3\2\2\2!l\3\2\2\2"+
		"#n\3\2\2\2%p\3\2\2\2\'r\3\2\2\2)t\3\2\2\2+v\3\2\2\2-y\3\2\2\2/\60\7g\2"+
		"\2\60\61\7z\2\2\61\62\7r\2\2\62\4\3\2\2\2\63\64\7u\2\2\64\65\7s\2\2\65"+
		"\66\7t\2\2\66\67\7v\2\2\67\6\3\2\2\28<\5\t\5\29;\5\13\6\2:9\3\2\2\2;>"+
		"\3\2\2\2<:\3\2\2\2<=\3\2\2\2=\b\3\2\2\2><\3\2\2\2?A\t\2\2\2@?\3\2\2\2"+
		"A\n\3\2\2\2BE\5\t\5\2CE\4\62;\2DB\3\2\2\2DC\3\2\2\2E\f\3\2\2\2FM\5\17"+
		"\b\2GI\5\21\t\2HJ\5\23\n\2IH\3\2\2\2IJ\3\2\2\2JK\3\2\2\2KL\5\17\b\2LN"+
		"\3\2\2\2MG\3\2\2\2MN\3\2\2\2N\16\3\2\2\2OQ\4\62;\2PO\3\2\2\2QR\3\2\2\2"+
		"RP\3\2\2\2RS\3\2\2\2SZ\3\2\2\2TV\7\60\2\2UW\4\62;\2VU\3\2\2\2WX\3\2\2"+
		"\2XV\3\2\2\2XY\3\2\2\2Y[\3\2\2\2ZT\3\2\2\2Z[\3\2\2\2[\20\3\2\2\2\\]\t"+
		"\3\2\2]\22\3\2\2\2^_\t\4\2\2_\24\3\2\2\2`a\7*\2\2a\26\3\2\2\2bc\7+\2\2"+
		"c\30\3\2\2\2de\7-\2\2e\32\3\2\2\2fg\7/\2\2g\34\3\2\2\2hi\7,\2\2i\36\3"+
		"\2\2\2jk\7\61\2\2k \3\2\2\2lm\7@\2\2m\"\3\2\2\2no\7>\2\2o$\3\2\2\2pq\7"+
		"?\2\2q&\3\2\2\2rs\7\60\2\2s(\3\2\2\2tu\7`\2\2u*\3\2\2\2vw\7)\2\2w,\3\2"+
		"\2\2xz\t\5\2\2yx\3\2\2\2z{\3\2\2\2{y\3\2\2\2{|\3\2\2\2|}\3\2\2\2}~\b\27"+
		"\2\2~.\3\2\2\2\f\2<@DIMRXZ{\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}