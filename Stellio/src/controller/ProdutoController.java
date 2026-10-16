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
import model.Produto;


public class ProdutoController {

	private Database database;

	public ProdutoController() {
		database = new Database();
	}


	public boolean Adicionar(Produto produto) {
		try {
			String sql = """
				insert into produtos (codigoBarras, descricao, categoria, precoCusto, precoVenda, quantidade, estoqueMinimo, idFornecedor)
				values (?, ?, ?, ?, ?, ?, ?, ?)
			""";

			Connection con = database.conectar();
			PreparedStatement stmt = con.prepareStatement(sql);
			
			if (produto.getCodigoBarras().isBlank()) {
				stmt.setNull(1, java.sql.Types.VARCHAR);
				
			} else {
				stmt.setString(1, produto.getCodigoBarras());
				
			}

			stmt.setString(2, produto.getDescricao());
			stmt.setString(3, produto.getCategoria());
			stmt.setDouble(4, produto.getPrecoCusto());
			stmt.setDouble(5, produto.getPrecoVenda());
			stmt.setInt(6, produto.getQuantidade());
			stmt.setInt(7, produto.getEstoqueMinimo());
			stmt.setInt(8, produto.getIdFornecedor());

			stmt.executeUpdate();

			stmt.close();
			con.close();
			
			return true;

		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public Produto buscar(String nome) {
		try {
			String sql = """
				select idProduto, codigoBarras, descricao, categoria, precoCusto, precoVenda, quantidade, estoqueMinimo, idFornecedor
				from produtos
				where descricao like ?
			""";

			Produto produto = null;

			Connection con = database.conectar();
			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setString(1, "%" + nome + "%");


			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				produto = new Produto();
				produto.setIdProduto(rs.getInt("idProduto"));
				produto.setCodigoBarras(rs.getString("codigoBarras"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setCategoria(rs.getString("categoria"));
				produto.setPrecoCusto(rs.getDouble("precoCusto"));
				produto.setPrecoVenda(rs.getDouble("precoVenda"));
				produto.setQuantidade(rs.getInt("quantidade"));
				produto.setEstoqueMinimo(rs.getInt("estoqueMinimo"));
				produto.setIdFornecedor(rs.getInt("idFornecedor"));
			}

			rs.close();
			stmt.close();
			con.close();

			return produto;

		} catch (Exception e) {
			System.out.println(e);
	
			
			return null;
		}
	}


		public Produto buscarCodigoBarras(String codigoBarras) {
			try {
				String sql = """
					select idProduto, codigoBarras, descricao, categoria, precoCusto, precoVenda, quantidade, estoqueMinimo, idFornecedor
					from produtos
					where codigoBarras like ?
				""";

				Produto produto = null;

				Connection con = database.conectar();
				PreparedStatement stmt = con.prepareStatement(sql);

				stmt.setString(1,  codigoBarras);

				ResultSet rs = stmt.executeQuery();

				if (rs.next()) {
					produto = new Produto();
					produto.setIdProduto(rs.getInt("idProduto"));
					produto.setCodigoBarras(rs.getString("codigoBarras"));
					produto.setDescricao(rs.getString("descricao"));
					produto.setCategoria(rs.getString("categoria"));
					produto.setPrecoCusto(rs.getDouble("precoCusto"));
					produto.setPrecoVenda(rs.getDouble("precoVenda"));
					produto.setQuantidade(rs.getInt("quantidade"));
					produto.setEstoqueMinimo(rs.getInt("estoqueMinimo"));
					produto.setIdFornecedor(rs.getInt("idFornecedor"));
				}

				rs.close();
				stmt.close();
				con.close();

				return produto;

			} catch (Exception e) {
				System.out.println(e);
		
				
				return null;
			}
		}

		public int fornecedorVinculado(int idFornecedor) {
		    try {
		        String sql = "select count(*) from produtos where idFornecedor = ?";

		        Connection con = database.conectar();
		        PreparedStatement stmt = con.prepareStatement(sql);
		        stmt.setInt(1, idFornecedor);

		        ResultSet rs = stmt.executeQuery();

		        int total = 0;
		        if (rs.next()) {
		            total = rs.getInt(1);
		        }

		        rs.close();
		        stmt.close();
		        con.close();

		        return total;

		    } catch (Exception e) {
		        System.out.println(e);
		        return 0;
		    }
		}
		
		public int dashProdutos() {
			try {
				String sql = """
						select count(*) as total from produtos;
						""";
				
				Connection con = database.conectar();
				PreparedStatement stmt = con.prepareStatement(sql);
				
				ResultSet rs = stmt.executeQuery();
				
				int total = 1;
				
				if (rs.next()) {
					total = rs.getInt(total);
				} 
				
				rs.close();
				stmt.close();
				con.close();

				return total;
				
			} catch (Exception e) {
				System.out.println(e);
				return 0;
			}
		}
		
		public int dashEstoqueBaixo() {
			try {
				String sql = """
						select count(*) as total
						from produtos
						where quantidade <= estoqueMinimo and quantidade != 0;
						""";
				
				Connection con = database.conectar();
				PreparedStatement stmt = con.prepareStatement(sql);
				
				ResultSet rs = stmt.executeQuery();
				
				int total = 1;
				
				if (rs.next()) {
					total = rs.getInt(total);
				} 
				
				rs.close();
				stmt.close();
				con.close();

				return total;
				
			} catch (Exception e) {
				System.out.println(e);
				return 0;
			}
		}
		
		public int dashSemEstoque() {
			try {
				String sql = """
						select count(*) as total
						from produtos
						where quantidade = 0
						""";
				
				Connection con = database.conectar();
				PreparedStatement stmt = con.prepareStatement(sql);
				
				ResultSet rs = stmt.executeQuery();
				
				int total = 1;
				
				if (rs.next()) {
					total = rs.getInt(total);
				} 
				
				rs.close();
				stmt.close();
				con.close();

				return total;
				
			} catch (Exception e) {
				System.out.println(e);
				return 0;
			}
		}
	
	
		public ArrayList<String> buscarPorTrecho(String trecho) {
		    ArrayList<String> nomes = new ArrayList<>();

		    try {
		        String sql = """
		            select descricao from produtos where descricao like ? order by descricao limit 10
		        """;

		        Connection con = database.conectar();
		        PreparedStatement stmt = con.prepareStatement(sql);
		        stmt.setString(1, "%" + trecho + "%");

		        ResultSet rs = stmt.executeQuery();

		        while (rs.next()) {
		            nomes.add(rs.getString("descricao"));
		        }

		        rs.close();
		        stmt.close();
		        con.close();

		    } catch (Exception e) {
		        System.out.println(e);
		    }

		    return nomes;
		}

			public boolean codigoBarrasExiste(String codigoBarras) {
			    try {
			        String sql = """
			            select codigoBarras from produtos where codigoBarras = ?
			            """;

			        Connection con = database.conectar();
			        PreparedStatement stmt = con.prepareStatement(sql);

			        stmt.setString(1, codigoBarras);

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
	

	public void Editar(Produto produto) {
		try {
			String sql = """
				update produtos
				set codigoBarras = ?, descricao = ?, categoria = ?, precoCusto = ?, precoVenda = ?, quantidade = ?, estoqueMinimo = ?, idFornecedor = ?
				where idProduto = ?
			""";

			Connection con = database.conectar();
			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setString(1, produto.getCodigoBarras());
			stmt.setString(2, produto.getDescricao());
			stmt.setString(3, produto.getCategoria());
			stmt.setDouble(4, produto.getPrecoCusto());
			stmt.setDouble(5, produto.getPrecoVenda());
			stmt.setInt(6, produto.getQuantidade());
			stmt.setInt(7, produto.getEstoqueMinimo());
			stmt.setInt(8, produto.getIdFornecedor());
			stmt.setInt(9, produto.getIdProduto());

			stmt.executeUpdate();

			stmt.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}


	public boolean Excluir(int idProduto) {
	    try {
	        String sql = """
	            delete from produtos
	            where idProduto = ?
	        """;

	        Connection con = database.conectar();
	        PreparedStatement stmt = con.prepareStatement(sql);

	        stmt.setInt(1, idProduto);

	        int linhasAfetadas = stmt.executeUpdate();
	        
	        stmt.close();
	        con.close();

	        return linhasAfetadas > 0;


	    } catch (Exception e) {
	        System.out.println(e);
	        return false;
	    }
	}

	public void gerarRelatorioProdutos() {
	    try {

	        String sql = """
	                select p.codigoBarras, p.descricao, p.categoria, f.nome as fornecedor, p.precoCusto, p.precoVenda, p.quantidade, p.estoqueMinimo
	                from produtos p
	                inner join fornecedores f on p.idFornecedor = f.idFornecedor
	                order by descricao
	                """;

	        Connection con = database.conectar();
	        PreparedStatement stmt = con.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery();

	        Document documento = new Document(PageSize.A4.rotate());
	        String caminho = "relatorio_produtos.pdf";
	        PdfWriter.getInstance(documento, new FileOutputStream(caminho));
	        documento.open();

	        Font fonteTitulo = new Font(Font.HELVETICA, 16, Font.BOLD);
	        Paragraph titulo = new Paragraph("RELATÓRIO DE CONTROLE DE ESTOQUE", fonteTitulo);
	        titulo.setAlignment(Element.ALIGN_CENTER);
	        documento.add(titulo);

	        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	        String dataHora = LocalDateTime.now().format(formato);
	        Paragraph data = new Paragraph("Data de emissão: " + dataHora);
	        data.setAlignment(Element.ALIGN_CENTER);
	        documento.add(data);

	        documento.add(new Paragraph(" "));

	        PdfPTable tabela = new PdfPTable(8);
	        tabela.setWidths(new float[] { 3.0f, 3.5f, 2.3f, 3.0f, 1.3f, 1.3f, 1.7f, 1.3f});
	        tabela.setWidthPercentage(100);

	        Font fonteCabecalho = new Font(Font.HELVETICA, 10, Font.BOLD, java.awt.Color.WHITE);
	        String[] colunas = { "Código de barras", "Descrição", "Categoria", "Fornecedor", "Custo (R$)", "Venda (R$)", "Qtd. Estoque", "Estoque Min." };
	        for (String nomeColuna : colunas) {
	            PdfPCell celulaCabecalho = new PdfPCell(new Paragraph(nomeColuna, fonteCabecalho));
	            celulaCabecalho.setBackgroundColor(java.awt.Color.DARK_GRAY);
	            celulaCabecalho.setHorizontalAlignment(Element.ALIGN_CENTER);
	            celulaCabecalho.setVerticalAlignment(Element.ALIGN_MIDDLE);
	            celulaCabecalho.setPadding(5f);
	            tabela.addCell(celulaCabecalho);
	        }

	        java.awt.Color corZerado = new java.awt.Color(220, 53, 69);
	        java.awt.Color corRepor = new java.awt.Color(255, 193, 7);

	        Font fonteNormal = new Font(Font.HELVETICA, 10, Font.NORMAL);
	        Font fonteAlertaBranca = new Font(Font.HELVETICA, 10, Font.BOLD, java.awt.Color.WHITE);
	        Font fonteAlertaEscura = new Font(Font.HELVETICA, 10, Font.BOLD, java.awt.Color.BLACK);

	        java.util.function.Function<String, PdfPCell> celulaCentralizada = texto -> {
	            PdfPCell celula = new PdfPCell(new Paragraph(texto, fonteNormal));
	            celula.setHorizontalAlignment(Element.ALIGN_CENTER);
	            celula.setVerticalAlignment(Element.ALIGN_MIDDLE);
	            celula.setPadding(5f);
	            return celula;
	        };

	        while (rs.next()) {
	            int quantidade = rs.getInt("quantidade");
	            int estoqueMinimo = rs.getInt("estoqueMinimo");

	            tabela.addCell(celulaCentralizada.apply(rs.getString("codigoBarras")));
	            tabela.addCell(celulaCentralizada.apply(rs.getString("descricao")));
	            tabela.addCell(celulaCentralizada.apply(rs.getString("categoria")));
	            tabela.addCell(celulaCentralizada.apply(rs.getString("fornecedor")));
	            tabela.addCell(celulaCentralizada.apply(String.format("%.2f", rs.getDouble("precoCusto"))));
	            tabela.addCell(celulaCentralizada.apply(String.format("%.2f", rs.getDouble("precoVenda"))));

	            PdfPCell cellQtde;
	            if (quantidade == 0) {
	                cellQtde = new PdfPCell(new Paragraph(quantidade + " ( Zerado ) ", fonteAlertaBranca));
	                cellQtde.setBackgroundColor(corZerado);

	            } else if (quantidade <= estoqueMinimo) {
	                cellQtde = new PdfPCell(new Paragraph(quantidade + " ( Repor ) ", fonteAlertaEscura));
	                cellQtde.setBackgroundColor(corRepor);

	            } else {
	                cellQtde = new PdfPCell(new Paragraph(String.valueOf(quantidade), fonteNormal));
	            }

	            cellQtde.setHorizontalAlignment(Element.ALIGN_CENTER);
	            cellQtde.setVerticalAlignment(Element.ALIGN_MIDDLE);
	            cellQtde.setPadding(5f);
	            tabela.addCell(cellQtde);

	            tabela.addCell(celulaCentralizada.apply(String.valueOf(estoqueMinimo)));
	        }

	        documento.add(tabela);
	        documento.close();

	        rs.close();
	        stmt.close();
	        con.close();

	        File arquivo = new File(caminho);
	        Desktop.getDesktop().open(arquivo);

	    } catch (Exception e) {
	        System.out.println(e);
	    }
	}


}