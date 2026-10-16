package utils;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

public class ValidadorCNPJ extends DocumentFilter {

    private static final int MAX_DIGITOS = 14;
    private final JTextField campo;

    private ValidadorCNPJ(JTextField campo) {
        this.campo = campo;
    }

    public static void aplicar(JTextField campo) {
        PlainDocument doc = new PlainDocument();
        doc.setDocumentFilter(new ValidadorCNPJ(campo));
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
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digitos.length(); i++) {
            if (i == 2 || i == 5) {
                sb.append('.');
            }
            if (i == 8) {
                sb.append('/');
            }
            if (i == 12) {
                sb.append('-');
            }
            sb.append(digitos.charAt(i));
        }
        return sb.toString();
    }

    private int calcularCaretFormatado(String formatado, int digitosAlvo) {
        if (digitosAlvo <= 0) {
            return 0;
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