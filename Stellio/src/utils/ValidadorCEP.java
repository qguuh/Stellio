package utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import model.Cliente;

public class ValidadorCEP {



    public Cliente buscarCep(String cep) throws Exception {
        cep = cep.replaceAll("[^0-9]", "");

        if (cep.length() != 8) {
            throw new IllegalArgumentException("CEP deve conter 8 dígitos.");
        }

        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();

        if (json.contains("\"erro\"")) {
            throw new Exception("CEP não encontrado.");
        }

        Cliente cliente = new Cliente();
        cliente.setEndereco(extrairValorJson(json, "logradouro"));
        cliente.setBairro(extrairValorJson(json, "bairro"));
        cliente.setCidade(extrairValorJson(json, "localidade"));
        cliente.setEstado(extrairValorJson(json, "uf"));

        if (cliente.getCidade() == null || cliente.getCidade().isBlank()) {
            throw new Exception("CEP não encontrado.");
        }

        return cliente;
    }

    private String extrairValorJson(String json, String chave) {
        String regex = "\"" + chave + "\":\\s*\"([^\"]+)\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    public static void aplicarMascara(JTextField campo) {
        PlainDocument doc = new PlainDocument();
        doc.setDocumentFilter(new MascaraCEPFilter(campo));
        campo.setDocument(doc);
    }

    private static class MascaraCEPFilter extends DocumentFilter {

        private static final int MAX_DIGITOS = 8;
        private final JTextField campo;

        private MascaraCEPFilter(JTextField campo) {
            this.campo = campo;
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
                if (i == 5) {
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
}