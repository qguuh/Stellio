package utils;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

public class ValidadorFone extends DocumentFilter {

    private static final int MAX_DIGITOS = 11;
    private final JTextField campo;

    private ValidadorFone(JTextField campo) {
        this.campo = campo;
    }

    public static void aplicar(JTextField campo) {
        PlainDocument doc = new PlainDocument();
        doc.setDocumentFilter(new ValidadorFone(campo));
        campo.setDocument(doc);
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        replace(fb, offset, 0, string, attr);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text == null) {
            text = "";
        }

        String textoAtual = fb.getDocument().getText(0, fb.getDocument().getLength());
        String textoNovoBruto = textoAtual.substring(0, offset) + text + textoAtual.substring(offset + length);

        StringBuilder digitos = new StringBuilder();
        for (char c : textoNovoBruto.toCharArray()) {
            if (Character.isDigit(c)) {
                digitos.append(c);
            }
        }
        if (digitos.length() > MAX_DIGITOS) {
            digitos.setLength(MAX_DIGITOS);
        }

        int caretBruto = offset + text.length();
        int digitosAteCaret = 0;
        for (int i = 0; i < Math.min(caretBruto, textoNovoBruto.length()); i++) {
            if (Character.isDigit(textoNovoBruto.charAt(i))) {
                digitosAteCaret++;
            }
        }
        if (digitosAteCaret > digitos.length()) {
            digitosAteCaret = digitos.length();
        }

        String formatado = formatar(digitos.toString());

        fb.replace(0, fb.getDocument().getLength(), formatado, attrs);

        int novoCaret = calcularCaretFormatado(formatado, digitosAteCaret);
        final int caretFinal = Math.min(novoCaret, formatado.length());
        javax.swing.SwingUtilities.invokeLater(() -> campo.setCaretPosition(caretFinal));
    }

    private String formatar(String digitos) {
        int len = digitos.length();
        if (len == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('(');

        int dddLen = Math.min(2, len);
        sb.append(digitos, 0, dddLen);

        if (len <= 2) {
            return sb.toString();
        }

        sb.append(") ");

        String resto = digitos.substring(2);
        int primeiroBlocoLen = (len >= 11) ? 5 : 4;

        if (resto.length() <= primeiroBlocoLen) {
            sb.append(resto);
        } else {
            sb.append(resto, 0, primeiroBlocoLen);
            sb.append('-');
            sb.append(resto.substring(primeiroBlocoLen));
        }

        return sb.toString();
    }

    private int calcularCaretFormatado(String formatado, int digitosAlvo) {
        if (digitosAlvo <= 0) {
            return formatado.isEmpty() ? 0 : 1;
        }
        int contador = 0;
        for (int i = 0; i < formatado.length(); i++) {
            if (Character.isDigit(formatado.charAt(i))) {
                contador++;
            }
            if (contador == digitosAlvo) {
                return i + 1;
            }
        }
        return formatado.length();
    }
}