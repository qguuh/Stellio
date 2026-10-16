package utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class ValidadorCPF extends PlainDocument {


	private static final long serialVersionUID = 1L;

	public static boolean validar(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11) {
            return false;
        }

        if (cpf.chars().distinct().count() == 1) {
            return false;
        }

        try {
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                int digito = Character.getNumericValue(cpf.charAt(i));
                soma += digito * (10 - i);
            }
            int resto = soma % 11;
            int primeiroDigito = (resto < 2) ? 0 : (11 - resto);

            soma = 0;
            for (int i = 0; i < 10; i++) {
                int digito = Character.getNumericValue(cpf.charAt(i));
                soma += digito * (11 - i);
            }
            resto = soma % 11;
            int segundoDigito = (resto < 2) ? 0 : (11 - resto);

            int digitoInformado1 = Character.getNumericValue(cpf.charAt(9));
            int digitoInformado2 = Character.getNumericValue(cpf.charAt(10));

            return primeiroDigito == digitoInformado1 && segundoDigito == digitoInformado2;

        } catch (Exception e) {
            return false;
        }
    }
    
    private static final int MAX_DIGITOS = 11;

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str == null) {
            return;
        }

        String textoAtual = getText(0, getLength());
        String textoNovoBruto = textoAtual.substring(0, offs) + str + textoAtual.substring(offs);

        String digitos = extrairDigitos(textoNovoBruto);
        if (digitos.length() > MAX_DIGITOS) {
            digitos = digitos.substring(0, MAX_DIGITOS);
        }

        String formatado = formatar(digitos);

        super.remove(0, getLength());
        super.insertString(0, formatado, a);
    }

    @Override
    public void remove(int offs, int len) throws BadLocationException {
        String textoAtual = getText(0, getLength());
        String textoNovoBruto = textoAtual.substring(0, offs) + textoAtual.substring(offs + len);

        String digitos = extrairDigitos(textoNovoBruto);
        String formatado = formatar(digitos);

        super.remove(0, getLength());
        super.insertString(0, formatado, null);
    }

    private String extrairDigitos(String texto) {
        StringBuilder digitos = new StringBuilder();
        for (char c : texto.toCharArray()) {
            if (Character.isDigit(c)) {
                digitos.append(c);
            }
        }
        return digitos.toString();
    }

    private String formatar(String digitos) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digitos.length(); i++) {
            if (i == 3 || i == 6) {
                sb.append('.');
            }
            if (i == 9) {
                sb.append('-');
            }
            sb.append(digitos.charAt(i));
        }
        return sb.toString();
    }
}