/*
BSD License
Copyright (c) 2013, Tom Everett
All rights reserved.
Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:
1. Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the distribution.
3. Neither the name of Tom Everett nor the names of its contributors
   may be used to endorse or promote products derived from this software
   without specific prior written permission.
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

grammar Arithmetic;

// Правила парсера
// Уравнение - два выражения, между которыми стоит операция отношения
equation
   : expression relop expression
   ;

// Выражение - член, к которому может быть прибавлен или из которого может быть вычтен (но не обязательно) другой член
expression
   : term ((PLUS | MINUS) term)*
   ;

// Член - множитель, который может быть умножен или разделён (но не обязательно) на другой множитель
term
   : factor ((TIMES | DIV) factor)*
   ;

// Множитель: атом со знаком, который может быть возведён (но не обязательно) в степень такого же атома со знаком
factor
   : signedAtom (POW signedAtom)*
   ;

// Атом со знаком: представляет собой такой же атом со знаком, перед которым стоит знак плюс или минус (TODO: Почему?) или просто атом
// ВНИМАНИЕ! Временно выполнена замена signedAtom на atom в первых двух строчках.
signedAtom
   : PLUS atom
   | MINUS atom
   | atom
   ;

// Атом (мельчайшая частица). Представляет собой число в экспоненциальной записи, переменную или выражение в скобках,
// а также другие математические функции
atom
   : scientific
   | variable
   | LPAREN expression RPAREN
   | sqrt
   | derivative
   | exponentialFunction
   | logarithm
   | naturalLogarithm
   | sinus
   | cosine
   | tangent
   | cotangent
   ;

// Число в экспоненциальной записи
scientific
   : SCIENTIFIC_NUMBER
   ;

// Переменная
variable
   : VARIABLE
   ;

// Операция отношения (равно, больше или меньше)
relop
   : EQ
   | GT
   | LT
   ;

// Квадратный корень: ключевое слово sqrt, открывающая скобка, атом со знаком, закрывающая скобка
sqrt
   : SQRT LPAREN signedAtom RPAREN;

// Производная: открывающая скобка, выражение, закрывающая скобка, штрих
derivative
   : LPAREN expression RPAREN PRIME;

// Экспонента: ключевое слово exp, открывающая скобка, атом со знаком, закрывающая скобка
exponentialFunction : EXP LPAREN signedAtom RPAREN;

// Логарифм: ключевое слово log, открывающая скобка, атом со знаком (основание), запятая, атом со знаком (число), закрывающая скобка
logarithm : LOG LPAREN signedAtom COMMA signedAtom RPAREN;

// Натуральный логарифм: ключевое слово ln, открывающая скобка, атом со знаком, закрывающая скобка
naturalLogarithm : LN LPAREN signedAtom RPAREN;

// Синус: ключевое слово sin, открывающая скобка, атом со знаком, закрывающая скобка
sinus : SIN LPAREN signedAtom RPAREN;

// Косинус: ключевое слово cos, открывающая скобка, атом со знаком, закрывающая скобка
cosine : COS LPAREN signedAtom RPAREN;

// Тангенс: ключевое слово tan, открывающая скобка, атом со знаком, закрывающая скобка
tangent : TAN LPAREN signedAtom RPAREN;

// Котангенс: ключевое слово ctan, открывающая скобка, атом со знаком, закрывающая скобка
cotangent : CTAN LPAREN signedAtom RPAREN;

// Правила лексера
// Ключевое слово для синуса
SIN : 'sin';

// Ключевое слово для косинуса
COS : 'cos';

// Ключевое слово для тангенса
TAN : 'tan';

// Ключевое слово для котангенса
CTAN : 'ctan';

// Ключевое слово для логрифма
LOG : 'log';

// Ключевое слово для натурального логрифма
LN : 'ln';

/// Ключевое слово exp для экспоненциальной функции
EXP : 'exp';

// Квадратный корень
SQRT
    : 'sqrt';

// Переменная. Состоит из одного валидного символа начала и нуля или более валидных символов
VARIABLE
   : VALID_ID_START VALID_ID_CHAR*
   ;

// Валидный символ начала. Один строчной или прописной символ или символ подчёркивания
fragment VALID_ID_START
   : ('a' .. 'z') | ('A' .. 'Z') | '_'
   ;

// Валидный символ. Это может быть либо валидный символ начала, либо цифра от 0 до 9
fragment VALID_ID_CHAR
   : VALID_ID_START | ('0' .. '9')
   ;

// Научная запись числа: номер, затем буква e, затем знак (+ или -), которого может не быть, если чтепень положительная. Ну и далее показатель степени. В научной записи числа может присутствовать только само число, а всё остальное - отсутствовать.
SCIENTIFIC_NUMBER
   : NUMBER (E SIGN? NUMBER)?
   ;

// Число. Состоит из 1 или более цифр от 0 до 9 и необязательной части, состоящей из точки и одной или более цифр от 0 до 9.
// Целая часть берёт свой знак из правила signedAtom
fragment NUMBER
   : ('0' .. '9') + ('.' ('0' .. '9') +)?
   ;

// Прописная или строчная буква e для научной записи.
fragment E
   : 'E' | 'e'
   ;

// Знак (плюс или минус), зачем-то взятый в скобки (TODO: Зачем? TODO: Почему нет ссылки на правила PLUS и MINUS)?
fragment SIGN
   : ('+' | '-')
   ;

// Открывающая скобка
LPAREN
   : '('
   ;

// Закрывающая скобка
RPAREN
   : ')'
   ;

// Плюс
PLUS
   : '+'
   ;

// Минус
MINUS
   : '-'
   ;

// Умножить
TIMES
   : '*'
   ;

// Разделить
DIV
   : '/'
   ;

// Больше
GT
   : '>'
   ;

// Меньше
LT
   : '<'
   ;

// Равно
EQ
   : '='
   ;

// Точка TODO: Возможно, следует добавить поддержку запятой
POINT
   : '.'
   ;

// Степень
POW
   : '^'
   ;

// Штрих
PRIME
   : '\''
   ;

// Запятая
COMMA
   : ','
   ;

// Пробел и другие знаки, которые нужно пропускать
WS
   : [ \r\n\t] + -> skip
   ;