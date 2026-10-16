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

import javax.swing.JOptionPane;

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
import model.Cliente;

public class ClienteController {
	
	private Database database;

	public ClienteController() {
		database = new Database();

	} 

	public void Adicionar(Cliente cliente) {
	    try {
	        String sql = """
	            insert into clientes (nome, fone, email, cpf, cep, endereco, numero, complemento, bairro, cidade, estado)
	        		values (?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)
	        """;

	        Connection con = database.conectar();
	        PreparedStatement stmt = con.prepareStatement(sql);
	        
	        stmt.setString(1, cliente.getNome());
	        stmt.setString(2, cliente.getFone());
	        stmt.setString(3, cliente.getEmail());
	        stmt.setString(4, cliente.getCPF());
	        stmt.setString(5, cliente.getCEP());
	        stmt.setString(6, cliente.getEndereco());
	        stmt.setString(7, cliente.getNumero());
	        stmt.setString(8, cliente.getComplemento());
	        stmt.setString(9, cliente.getBairro());
	        stmt.setString(10, cliente.getCidade());
	        stmt.setString(11, cliente.getEstado());

	        stmt.executeUpdate();

	        stmt.close();
	        con.close();

	    } catch (Exception e) {
	        System.out.println(e);
	    }
	} 
	
	public Cliente buscar(String nome) {
		try {
			String sql = """
				select idCliente, nome, fone, email, cpf, cep, endereco, numero, complemento, bairro, cidade, estado
				from clientes
				where nome like ?
			""";
			Cliente cliente = null;

			Connection con = database.conectar();
			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setString(1, "%" + nome + "%");

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				cliente = new Cliente();
				cliente.setIdCliente(rs.getInt("idCliente"));
				cliente.setNome(rs.getString("nome"));
				cliente.setFone(rs.getString("fone"));
				cliente.setEmail(rs.getString("email"));
				cliente.setCPF(rs.getString("CPF"));
				cliente.setCEP(rs.getString("CEP"));
				cliente.setEndereco(rs.getString("Endereco"));
				cliente.setNumero(rs.getString("Numero"));
				cliente.setComplemento(rs.getString("Complemento"));
				cliente.setBairro(rs.getString("Bairro"));
				cliente.setCidade(rs.getString("Cidade"));
				cliente.setEstado(rs.getString("Estado"));
				
			}

			rs.close();
			stmt.close();
			con.close();
			
			return cliente;
			
			} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
		
		public Cliente buscarCPF(String cpf) {
			try {
				String sql = """
					select idCliente, nome, fone, email, cpf, cep, endereco, numero, complemento, bairro, cidade, estado
					from clientes
					where cpf like ?
				""";
				Cliente cliente = null;
				Connection con = database.conectar();
				PreparedStatement stmt = con.prepareStatement(sql);
				stmt.setString(1, cpf);
				ResultSet rs = stmt.executeQuery();

				if (rs.next()) {
					cliente = new Cliente();
					cliente.setIdCliente(rs.getInt("idCliente"));
					cliente.setNome(rs.getString("nome"));
					cliente.setFone(rs.getString("fone"));
					cliente.setEmail(rs.getString("email"));
					cliente.setCPF(rs.getString("CPF"));
					cliente.setCEP(rs.getString("CEP"));
					cliente.setEndereco(rs.getString("Endereco"));
					cliente.setNumero(rs.getString("Numero"));
					cliente.setComplemento(rs.getString("Complemento"));
					cliente.setBairro(rs.getString("Bairro"));
					cliente.setCidade(rs.getString("Cidade"));
					cliente.setEstado(rs.getString("Estado"));
					
				}

				rs.close();
				stmt.close();
				con.close();
				
				return cliente;
				
				} catch (Exception e) {
				System.out.println(e);
				return null;
			}
		}

	public void Editar(Cliente cliente) throws SQLException {

		String sql = """	
				update clientes
					set nome = 	?, fone = ?, email = ?, cpf = ?, cep = ?, endereco = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, estado = ?
					where idCliente = ?
				""";

		Connection con = database.conectar();
		
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setString(1, cliente.getNome());
		stmt.setString(2, cliente.getFone());
		stmt.setString(3, cliente.getEmail());
		stmt.setString(4, cliente.getCPF());
		stmt.setString(5, cliente.getCEP());
		stmt.setString(6, cliente.getEndereco());
		stmt.setString(7, cliente.getNumero());
		stmt.setString(8, cliente.getComplemento());
	    stmt.setString(9, cliente.getBairro());
	    stmt.setString(10, cliente.getCidade());
	    stmt.setString(11, cliente.getEstado());
		stmt.setInt(12, cliente.getIdCliente());
		stmt.executeUpdate();
		
		stmt.close();
		con.close();
	} 

		public boolean Excluir(int idCliente) {
		    try {
		        String sql = """
		            delete from clientes
		            where idCliente = ?
		        """;

		        Connection con = database.conectar();
		        PreparedStatement stmt = con.prepareStatement(sql);

		        stmt.setInt(1, idCliente);

		        int linhasAfetadas = stmt.executeUpdate();
		        
		        stmt.close();
		        con.close();

		        return linhasAfetadas > 0;


		    } catch (Exception e) {
		        System.out.println(e);
		        return false;
		    }
		}
		
		public boolean cpfExiste(String cpf) {
		    try {
		        String sql = """
		            select cpf from clientes where cpf = ?
		            """;

		        Connection con = database.conectar();
		        PreparedStatement stmt = con.prepareStatement(sql);

		        stmt.setString(1, cpf);

		        ResultSet rs = stmt.executeQuery();

		        boolean existe = rs.next();

		        rs.close();
		        stmt.close();
		        con.close();

		        return existe;

		    } catch (SQLException e) {
		        JOptionPane.showMessageDialog(null, "Erro ao verificar CPF no banco: " + e.getMessage());
		        return false;
		    }
		}
		
		public ArrayList<String> buscarPorTrecho(String trecho) {
		    ArrayList<String> nomes = new ArrayList<>();

		    try {
		        String sql = """
		            select nome from clientes where nome like ? order by nome limit 10
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

		public void gerarRelatorioClientes() {
		    try {
		        String sql = """
		                select nome, fone, email, cpf, cidade, estado
		                from clientes
		                order by nome
		                """;

		        Connection con = database.conectar();
		        PreparedStatement stmt = con.prepareStatement(sql);
		        ResultSet rs = stmt.executeQuery();

		        Document documento = new Document(PageSize.A4.rotate());
		        String caminho = "relatorio_clientes.pdf";
		        PdfWriter.getInstance(documento, new FileOutputStream(caminho));
		        documento.open();

		        Font fonteTitulo = new Font(Font.HELVETICA, 16, Font.BOLD);
		        Paragraph titulo = new Paragraph("RELATÓRIO DE CLIENTES", fonteTitulo);
		        titulo.setAlignment(Element.ALIGN_CENTER);
		        documento.add(titulo);

		        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		        String dataHora = LocalDateTime.now().format(formato);
		        Paragraph data = new Paragraph("Data de emissão: " + dataHora);
		        data.setAlignment(Element.ALIGN_CENTER);
		        documento.add(data);

		        documento.add(new Paragraph(" "));

		        PdfPTable tabela = new PdfPTable(6);
		        tabela.setWidths(new float[] { 2.5f, 2.3f, 4.0f, 2.3f, 1.7f , 1.0f});
		        tabela.setWidthPercentage(100);

		        Font fonteCabecalho = new Font(Font.HELVETICA, 10, Font.BOLD, java.awt.Color.WHITE);
		        String[] colunas = { "Nome", "Telefone", "E-mail", "CPF", "Cidade", "Estado" };
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
		            tabela.addCell(celulaCentralizada.apply(rs.getString("nome")));
		            tabela.addCell(celulaCentralizada.apply(rs.getString("fone")));
		            tabela.addCell(celulaCentralizada.apply(rs.getString("email")));
		            tabela.addCell(celulaCentralizada.apply(rs.getString("cpf")));
		            tabela.addCell(celulaCentralizada.apply(rs.getString("cidade")));
		            tabela.addCell(celulaCentralizada.apply(rs.getString("estado")));

		            quantidade++;
		        }

		        documento.add(tabela);
		        documento.add(new Paragraph(" "));

		        Font fonteTotal = new Font(Font.HELVETICA, 10, Font.BOLD);
		        Paragraph total = new Paragraph("Total de Clientes: " + quantidade, fonteTotal);
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
