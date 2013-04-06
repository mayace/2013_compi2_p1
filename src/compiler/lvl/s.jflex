package compiler.lvl;
import java_cup.runtime.Symbol;
import java.util.LinkedList;

%%

%public
%class Scanner
%cup
%line
%column


%{
	public LinkedList<compiler.Error> errors=new LinkedList<>();
	private void error(String message) {
		
		Symbol sym=new Symbol(Sym.error, yyline, yycolumn, yytext());
		compiler.Error e=new compiler.Error(message,sym,compiler.Error.Type.LEXIC);
		errors.add(e);
	}
	
	private Symbol symbol(int type) {
		return new Symbol(type, yyline, yycolumn,yytext());
	}
	private Symbol symbol(int type, Object value) {
		return new Symbol(type, yyline, yycolumn, value);
	}
	
%}

NEWLINE	=	\n|\r|\r\n
SPACE	=	[ \t]|{NEWLINE}
DIGIT	=	[0-9]
INT		=	{DIGIT}+

BOOLEAN	=	"verdadero"|"falso"

%%



<YYINITIAL>
{
	{SPACE}			{}
	
	"<Importar>"		{return symbol(Sym.IMPORTAR_TAG1);}
	"</Importar>"		{return symbol(Sym.IMPORTAR_TAG2);}
	
	"<Espacios>"		{return symbol(Sym.ESPACIOS_TAG1);}
	"</Espacios>"	{return symbol(Sym.ESPACIOS_TAG2);}
	"<Espacio>"		{return symbol(Sym.ESPACIO_TAG1);}
	"</Espacio>"		{return symbol(Sym.ESPACIO_TAG2);}
	"<Extiende>"		{return symbol(Sym.EXTIENDE_TAG1);}
	"</Extiende>"	{return symbol(Sym.EXTIENDE_TAG2);}
	"<Nombre>"		{return symbol(Sym.NOMBRE_TAG1);}
	"</Nombre>"		{return symbol(Sym.NOMBRE_TAG2);}
	"<Pasable>"		{return symbol(Sym.PASABLE_TAG1);}
	"</Pasable>"		{return symbol(Sym.PASABLE_TAG2);}
	"<Especial>"		{return symbol(Sym.ESPECIAL_TAG1);}
	"</Especial>"		{return symbol(Sym.ESPECIAL_TAG2);}
	"<Imagen>"		{return symbol(Sym.IMAGEN_TAG1);}
	"</Imagen>"		{return symbol(Sym.IMAGEN_TAG2);}
	"<Inicio>"		{return symbol(Sym.INICIO_TAG1);}
	"</Inicio>"		{return symbol(Sym.INICIO_TAG2);}
	"<Enemigo>"		{return symbol(Sym.ENEMIGO_TAG1);}
	"</Enemigo>"	{return symbol(Sym.ENEMIGO_TAG2);}
	"<Fin>"			{return symbol(Sym.FIN_TAG1);}
	"</Fin>"			{return symbol(Sym.FIN_TAG2);}
	"<dano>"		{return symbol(Sym.DANIO_TAG1);}
	"</dano>"		{return symbol(Sym.DANIO_TAG2);}
	"<cura>"			{return symbol(Sym.CURA_TAG1);}
	"</cura>"		{return symbol(Sym.CURA_TAG2);}
	"<magia>"		{return symbol(Sym.MAGIA_TAG1);}
	"</magia>"		{return symbol(Sym.MAGIA_TAG2);}
	"<invencibilidad>"		{return symbol(Sym.INVENCIBLE_TAG1);}
	"</invencibilidad>"		{return symbol(Sym.INVENCIBLE_TAG2);}
	
	
	"<Nivel>"		{return symbol(Sym.NIVEL_TAG1);}
	"</Nivel>"		{return symbol(Sym.NIVEL_TAG2);}
	"<Tamano>"		{return symbol(Sym.TAMANIO_TAG1);}
	"</Tamano>"		{return symbol(Sym.TAMANIO_TAG2);}
	"<X>"			{return symbol(Sym.X_TAG1);}
	"</X>"			{return symbol(Sym.X_TAG2);}
	"<Y>"			{return symbol(Sym.Y_TAG1);}
	"</Y>"			{return symbol(Sym.Y_TAG2);}
	"<Estructura>"	{return symbol(Sym.ESTRUCTURA_TAG1);}
	"</Estructura>"	{return symbol(Sym.ESTRUCTURA_TAG2);}
	"<Casilla>"		{return symbol(Sym.CASILLA_TAG1);}
	"</Casilla>"		{return symbol(Sym.CASILLA_TAG2);}
	"<Tipo>"			{return symbol(Sym.TIPO_TAG1);}
	"</Tipo>"		{return symbol(Sym.TIPO_TAG2);}
	"<casilla_personaje>"		{return symbol(Sym.PERSONAJE_TAG1);}
	"</casilla_personaje>"		{return symbol(Sym.PERSONAJE_TAG2);}
	
	
	
	{BOOLEAN}		{return symbol(Sym.BOOLEAN);}
	{INT}			{return symbol(Sym.INT);}
	[^\<\>\r\n\t ]*		{return symbol(Sym.ANY);}
}

.|\n			{
				error("Illegal character.");
				}


<<EOF>>			{return symbol(Sym.EOF);}




