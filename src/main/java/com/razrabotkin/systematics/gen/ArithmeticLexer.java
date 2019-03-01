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
		SQRT=1, VARIABLE=2, SCIENTIFIC_NUMBER=3, LPAREN=4, RPAREN=5, PLUS=6, MINUS=7, 
		TIMES=8, DIV=9, GT=10, LT=11, EQ=12, POINT=13, POW=14, PRIME=15, WS=16;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"SQRT", "VARIABLE", "VALID_ID_START", "VALID_ID_CHAR", "SCIENTIFIC_NUMBER", 
			"NUMBER", "E", "SIGN", "LPAREN", "RPAREN", "PLUS", "MINUS", "TIMES", 
			"DIV", "GT", "LT", "EQ", "POINT", "POW", "PRIME", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'sqrt'", null, null, "'('", "')'", "'+'", "'-'", "'*'", "'/'", 
			"'>'", "'<'", "'='", "'.'", "'^'", "'''"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "SQRT", "VARIABLE", "SCIENTIFIC_NUMBER", "LPAREN", "RPAREN", "PLUS", 
			"MINUS", "TIMES", "DIV", "GT", "LT", "EQ", "POINT", "POW", "PRIME", "WS"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\22y\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\3\2\3\2\3\2\3\2\3\2\3\3\3\3\7"+
		"\3\65\n\3\f\3\16\38\13\3\3\4\5\4;\n\4\3\5\3\5\5\5?\n\5\3\6\3\6\3\6\5\6"+
		"D\n\6\3\6\3\6\5\6H\n\6\3\7\6\7K\n\7\r\7\16\7L\3\7\3\7\6\7Q\n\7\r\7\16"+
		"\7R\5\7U\n\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3"+
		"\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3"+
		"\25\3\26\6\26t\n\26\r\26\16\26u\3\26\3\26\2\2\27\3\3\5\4\7\2\t\2\13\5"+
		"\r\2\17\2\21\2\23\6\25\7\27\b\31\t\33\n\35\13\37\f!\r#\16%\17\'\20)\21"+
		"+\22\3\2\6\5\2C\\aac|\4\2GGgg\4\2--//\5\2\13\f\17\17\"\"\2{\2\3\3\2\2"+
		"\2\2\5\3\2\2\2\2\13\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31"+
		"\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2"+
		"\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\3-\3\2\2\2\5\62\3\2\2\2"+
		"\7:\3\2\2\2\t>\3\2\2\2\13@\3\2\2\2\rJ\3\2\2\2\17V\3\2\2\2\21X\3\2\2\2"+
		"\23Z\3\2\2\2\25\\\3\2\2\2\27^\3\2\2\2\31`\3\2\2\2\33b\3\2\2\2\35d\3\2"+
		"\2\2\37f\3\2\2\2!h\3\2\2\2#j\3\2\2\2%l\3\2\2\2\'n\3\2\2\2)p\3\2\2\2+s"+
		"\3\2\2\2-.\7u\2\2./\7s\2\2/\60\7t\2\2\60\61\7v\2\2\61\4\3\2\2\2\62\66"+
		"\5\7\4\2\63\65\5\t\5\2\64\63\3\2\2\2\658\3\2\2\2\66\64\3\2\2\2\66\67\3"+
		"\2\2\2\67\6\3\2\2\28\66\3\2\2\29;\t\2\2\2:9\3\2\2\2;\b\3\2\2\2<?\5\7\4"+
		"\2=?\4\62;\2><\3\2\2\2>=\3\2\2\2?\n\3\2\2\2@G\5\r\7\2AC\5\17\b\2BD\5\21"+
		"\t\2CB\3\2\2\2CD\3\2\2\2DE\3\2\2\2EF\5\r\7\2FH\3\2\2\2GA\3\2\2\2GH\3\2"+
		"\2\2H\f\3\2\2\2IK\4\62;\2JI\3\2\2\2KL\3\2\2\2LJ\3\2\2\2LM\3\2\2\2MT\3"+
		"\2\2\2NP\7\60\2\2OQ\4\62;\2PO\3\2\2\2QR\3\2\2\2RP\3\2\2\2RS\3\2\2\2SU"+
		"\3\2\2\2TN\3\2\2\2TU\3\2\2\2U\16\3\2\2\2VW\t\3\2\2W\20\3\2\2\2XY\t\4\2"+
		"\2Y\22\3\2\2\2Z[\7*\2\2[\24\3\2\2\2\\]\7+\2\2]\26\3\2\2\2^_\7-\2\2_\30"+
		"\3\2\2\2`a\7/\2\2a\32\3\2\2\2bc\7,\2\2c\34\3\2\2\2de\7\61\2\2e\36\3\2"+
		"\2\2fg\7@\2\2g \3\2\2\2hi\7>\2\2i\"\3\2\2\2jk\7?\2\2k$\3\2\2\2lm\7\60"+
		"\2\2m&\3\2\2\2no\7`\2\2o(\3\2\2\2pq\7)\2\2q*\3\2\2\2rt\t\5\2\2sr\3\2\2"+
		"\2tu\3\2\2\2us\3\2\2\2uv\3\2\2\2vw\3\2\2\2wx\b\26\2\2x,\3\2\2\2\f\2\66"+
		":>CGLRTu\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}