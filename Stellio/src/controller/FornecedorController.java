package controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import database.Database;

import model.Fornecedor;

public class FornecedorController {

	private Database database;


	public FornecedorController() {
		database = new Database();

	} 


	public void Adicionar(Fornecedor fornecedor) {
	    try {
	        String sql = """
	            insert into fornecedores (cnpj, nome, fone, email, site)
	            values (?, ?, ?, ?, ?)
	        """;

	        Connection con = database.conectar();
	        PreparedStatement stmt = con.prepareStatement(sql);

	        stmt.setString(1, fornecedor.getCnpj());
	        stmt.setString(2, fornecedor.getNome());
	        stmt.setString(3, fornecedor.getFone());
	        stmt.setString(4, fornecedor.getEmail());
	        stmt.setString(5, fornecedor.getSite());

	        stmt.executeUpdate();

	        stmt.close();
	        con.close();

	    } catch (Exception e) {
	        System.out.println(e);
	    }
	} 
	
	
	public Fornecedor buscar(String nome) {
		try {
			String sql = """
				select idFornecedor, cnpj, nome, fone, email, site
				from fornecedores
				where nome like ?
			""";

			Fornecedor fornecedor = null;
			
			Connection con = database.conectar();
			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setString(1, "%" + nome + "%");
			
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				fornecedor = new Fornecedor();
				fornecedor.setIdFornecedor(rs.getInt("idFornecedor"));
				fornecedor.setCnpj(rs.getString("cnpj"));
				fornecedor.setNome(rs.getString("nome"));
				fornecedor.setFone(rs.getString("fone"));
				fornecedor.setEmail(rs.getString("email"));
				fornecedor.setSite(rs.getString("site"));
			}

			rs.close();
			stmt.close();
			con.close();
			
			return fornecedor;
			
			} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	public Fornecedor buscarCNPJ(String cnpj) {
		try {
			String sql = """
				select idFornecedor, cnpj, nome, fone, email, site
				from fornecedores
				where cnpj like ?
			""";
			Fornecedor fornecedor = null;

			Connection con = database.conectar();
			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setString(1, cnpj);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				fornecedor = new Fornecedor();
				fornecedor.setIdFornecedor(rs.getInt("idFornecedor"));
				fornecedor.setCnpj(rs.getString("cnpj"));
				fornecedor.setNome(rs.getString("nome"));
				fornecedor.setFone(rs.getString("fone"));
				fornecedor.setEmail(rs.getString("email"));
				fornecedor.setSite(rs.getString("site"));
			}
			
			rs.close();
			stmt.close();
			con.close();
			
			return fornecedor;
			
			} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	

	public void Editar(Fornecedor fornecedor) throws SQLException {
		String sql = """	
				update fornecedores
					set cnpj = ?, nome = ?, fone = ?, email = ?, site = ?
					where idFornecedor = ?
				""";

		Connection con = database.conectar();
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, fornecedor.getCnpj());
		stmt.setString(2, fornecedor.getNome());
		stmt.setString(3, fornecedor.getFone());
		stmt.setString(4, fornecedor.getEmail());
		stmt.setString(5, fornecedor.getSite());
		stmt.setInt(6, fornecedor.getIdFornecedor());
		stmt.executeUpdate();
		
		stmt.close();
		con.close();
	} 

	public boolean Excluir(int idFornecedor) {
	    try {
	        String sql = """
	            delete from fornecedores
	            where idFornecedor = ?
	        """;

	        Connection con = database.conectar();
	        PreparedStatement stmt = con.prepareStatement(sql);

	        stmt.setInt(1, idFornecedor);

	        int linhasAfetadas = stmt.executeUpdate();
	        
	        stmt.close();
	        con.close();

	        return linhasAfetadas > 0;


	    } catch (Exception e) {
	        System.out.println(e);
	        return false;
	    }
	}
			
	public ArrayList<String> buscarPorTrecho(String trecho) {
	    ArrayList<String> nomes = new ArrayList<>();

	    try {
	        String sql = """
	            select nome from fornecedores where nome like ? order by nome limit 10
	        """;

	        Connection con = database.conectar();
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1, "%" + trecho + "%");

	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            nomes.add(rs.getString("nome"));
	        }

	        rs.close();
	        stmt.close();
	        con.close();

	    } catch (Exception e) {
	        System.out.println(e);
	    }

	    return nomes;
	}
	
	public ArrayList <Fornecedor> listaFornecedores () {
		
		ArrayList<Fornecedor> lista = new ArrayList<>();
		
		try {
			String sql = """
					select idFornecedor, nome
					from fornecedores
					order by nome
					""";
			
			Connection con = database.conectar();
			
			PreparedStatement stmt = con.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();		
			
			while (rs.next()) {
				
				Fornecedor fornecedor = new Fornecedor();
				
				fornecedor.setIdFornecedor(rs.getInt("idFornecedor"));
				fornecedor.setNome(rs.getString("Nome"));
				
				lista.add(fornecedor);
				
				
				
			}
		
			rs.close();
			stmt.close();
			con.close();
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		return lista;
	}
	

	public void gerarRelatorioFornecedores() {
	    try {
	        String sql = """
	                select cnpj, nome, fone, email, site
	                from fornecedores
	                order by nome
	                """;

	        Connection con = database.conectar();
	        PreparedStatement stmt = con.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery();

	        Document documento = new Document(PageSize.A4.rotate());
	        String caminho = "relatorio_fornecedores.pdf";
	        PdfWriter.getInstance(documento, new FileOutputStream(caminho));
	        documento.open();

	        Font fonteTitulo = new Font(Font.HELVETICA, 16, Font.BOLD);
	        Paragraph titulo = new Paragraph("RELATÓRIO DE FORNECEDORES", fonteTitulo);
	        titulo.setAlignment(Element.ALIGN_CENTER);
	        documento.add(titulo);

	        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	        String dataHora = LocalDateTime.now().format(formato);
	        Paragraph data = new Paragraph("Data de emissão: " + dataHora);
	        data.setAlignment(Element.ALIGN_CENTER);
	        documento.add(data);

	        documento.add(new Paragraph(" "));

	        PdfPTable tabela = new PdfPTable(5);
	        tabela.setWidths(new float[] { 2.0f, 2.5f, 2.8f, 3.5f, 3.0f });
	        tabela.setWidthPercentage(100);

	        Font fonteCabecalho = new Font(Font.HELVETICA, 10, Font.BOLD, java.awt.Color.WHITE);
	        String[] colunas = { "CNPJ", "Nome", "Telefone", "E-mail", "Site" };
	        for (String nomeColuna : colunas) {
	            PdfPCell celulaCabecalho = new PdfPCell(new Paragraph(nomeColuna, fonteCabecalho));
	            celulaCabecalho.setBackgroundColor(java.awt.Color.DARK_GRAY);
	            celulaCabecalho.setHorizontalAlignment(Element.ALIGN_CENTER);
	            celulaCabecalho.setVerticalAlignment(Element.ALIGN_MIDDLE);
	            celulaCabecalho.setPadding(5f);
	            tabela.addCell(celulaCabecalho);
	        }

	        Font fonteNormal = new Font(Font.HELVETICA, 10, Font.NORMAL);

	        java.util.function.Function<String, PdfPCell> celulaCentralizada = texto -> {
	            PdfPCell celula = new PdfPCell(new Paragraph(texto, fonteNormal));
	            celula.setHorizontalAlignment(Element.ALIGN_CENTER);
	            celula.setVerticalAlignment(Element.ALIGN_MIDDLE);
	            celula.setPadding(5f);
	            return celula;
	        };

	        int quantidade = 0;

	        while (rs.next()) {
	            tabela.addCell(celulaCentralizada.apply(rs.getString("cnpj")));
	            tabela.addCell(celulaCentralizada.apply(rs.getString("nome")));
	            tabela.addCell(celulaCentralizada.apply(rs.getString("fone")));
	            tabela.addCell(celulaCentralizada.apply(rs.getString("email")));
	            tabela.addCell(celulaCentralizada.apply(rs.getString("site")));

	            quantidade++;
	        }

	        documento.add(tabela);
	        documento.add(new Paragraph(" "));

	        Font fonteTotal = new Font(Font.HELVETICA, 10, Font.BOLD);
	        Paragraph total = new Paragraph("Total de Fornecedores: " + quantidade, fonteTotal);
	        total.setAlignment(Element.ALIGN_RIGHT);
	        documento.add(total);

	        documento.close();

	        con.close();
	        stmt.close();
	        rs.close();

	        File arquivo = new File(caminho);
	        Desktop.getDesktop().open(arquivo);

	    } catch (Exception e) {
	        System.out.println(e);
	    }
	}
	
}
