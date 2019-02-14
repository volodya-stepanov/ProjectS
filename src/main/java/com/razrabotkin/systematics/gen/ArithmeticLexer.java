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
		TIMES=8, DIV=9, GT=10, LT=11, EQ=12, POINT=13, POW=14, WS=15;
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
			"DIV", "GT", "LT", "EQ", "POINT", "POW", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'sqrt'", null, null, "'('", "')'", "'+'", "'-'", "'*'", "'/'", 
			"'>'", "'<'", "'='", "'.'", "'^'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "SQRT", "VARIABLE", "SCIENTIFIC_NUMBER", "LPAREN", "RPAREN", "PLUS", 
			"MINUS", "TIMES", "DIV", "GT", "LT", "EQ", "POINT", "POW", "WS"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\21u\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\3\2\3\2\3\2\3\2\3\2\3\3\3\3\7\3\63\n\3"+
		"\f\3\16\3\66\13\3\3\4\5\49\n\4\3\5\3\5\5\5=\n\5\3\6\3\6\3\6\5\6B\n\6\3"+
		"\6\3\6\5\6F\n\6\3\7\6\7I\n\7\r\7\16\7J\3\7\3\7\6\7O\n\7\r\7\16\7P\5\7"+
		"S\n\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17"+
		"\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\6\25p\n\25"+
		"\r\25\16\25q\3\25\3\25\2\2\26\3\3\5\4\7\2\t\2\13\5\r\2\17\2\21\2\23\6"+
		"\25\7\27\b\31\t\33\n\35\13\37\f!\r#\16%\17\'\20)\21\3\2\6\5\2C\\aac|\4"+
		"\2GGgg\4\2--//\5\2\13\f\17\17\"\"\2w\2\3\3\2\2\2\2\5\3\2\2\2\2\13\3\2"+
		"\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2"+
		"\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2"+
		"\2\2)\3\2\2\2\3+\3\2\2\2\5\60\3\2\2\2\78\3\2\2\2\t<\3\2\2\2\13>\3\2\2"+
		"\2\rH\3\2\2\2\17T\3\2\2\2\21V\3\2\2\2\23X\3\2\2\2\25Z\3\2\2\2\27\\\3\2"+
		"\2\2\31^\3\2\2\2\33`\3\2\2\2\35b\3\2\2\2\37d\3\2\2\2!f\3\2\2\2#h\3\2\2"+
		"\2%j\3\2\2\2\'l\3\2\2\2)o\3\2\2\2+,\7u\2\2,-\7s\2\2-.\7t\2\2./\7v\2\2"+
		"/\4\3\2\2\2\60\64\5\7\4\2\61\63\5\t\5\2\62\61\3\2\2\2\63\66\3\2\2\2\64"+
		"\62\3\2\2\2\64\65\3\2\2\2\65\6\3\2\2\2\66\64\3\2\2\2\679\t\2\2\28\67\3"+
		"\2\2\29\b\3\2\2\2:=\5\7\4\2;=\4\62;\2<:\3\2\2\2<;\3\2\2\2=\n\3\2\2\2>"+
		"E\5\r\7\2?A\5\17\b\2@B\5\21\t\2A@\3\2\2\2AB\3\2\2\2BC\3\2\2\2CD\5\r\7"+
		"\2DF\3\2\2\2E?\3\2\2\2EF\3\2\2\2F\f\3\2\2\2GI\4\62;\2HG\3\2\2\2IJ\3\2"+
		"\2\2JH\3\2\2\2JK\3\2\2\2KR\3\2\2\2LN\7\60\2\2MO\4\62;\2NM\3\2\2\2OP\3"+
		"\2\2\2PN\3\2\2\2PQ\3\2\2\2QS\3\2\2\2RL\3\2\2\2RS\3\2\2\2S\16\3\2\2\2T"+
		"U\t\3\2\2U\20\3\2\2\2VW\t\4\2\2W\22\3\2\2\2XY\7*\2\2Y\24\3\2\2\2Z[\7+"+
		"\2\2[\26\3\2\2\2\\]\7-\2\2]\30\3\2\2\2^_\7/\2\2_\32\3\2\2\2`a\7,\2\2a"+
		"\34\3\2\2\2bc\7\61\2\2c\36\3\2\2\2de\7@\2\2e \3\2\2\2fg\7>\2\2g\"\3\2"+
		"\2\2hi\7?\2\2i$\3\2\2\2jk\7\60\2\2k&\3\2\2\2lm\7`\2\2m(\3\2\2\2np\t\5"+
		"\2\2on\3\2\2\2pq\3\2\2\2qo\3\2\2\2qr\3\2\2\2rs\3\2\2\2st\b\25\2\2t*\3"+
		"\2\2\2\f\2\648<AEJPRq\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}