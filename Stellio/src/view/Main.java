package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import com.formdev.flatlaf.FlatLightLaf;

import controller.ClienteController;

import controller.FornecedorController;
import controller.ProdutoController;

import database.Database;
import model.Cliente;

import model.Fornecedor;
import model.Produto;
import utils.Validador;
import utils.ValidadorCEP;
import utils.ValidadorCNPJ;
import utils.ValidadorCPF;
import utils.ValidadorFone;


public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblData;

	Database db = new Database();
	
	private JLabel lblStatus;
	private JLabel lblMySQL;
	private JLabel lblDatabase;
	private JTextField txtEmail;
	private JTextField txtFone;
	private JTextField txtNome;
	private JTextField txtSite;
	private JTextField txtID;
	
	private JLabel lblNome;
	private JLabel lblFone;
	private JLabel lblEmail;
	private JLabel lblSite;
	private JLabel lblCPF;
	
	private JTextField txtCEP;
    private JTextField txtEndereco;
    private JTextField txtBairro;
    private JTextField txtCidade;
    private JComboBox<String> cbEstado;
    private JComboBox<String> cbFornecedor;
    private JComboBox<String> cbComplemento;
    
    private JLabel lblCEP;
    private JLabel lblEndereco;
    private JLabel lblBairro;
    private JLabel lblCidade;
    
    private JButton btnCEP;
    private JButton btnSearch;
    private JButton btnSearchAlt;
    private JButton btnVendas;
    
    private JLabel lblEstoque;
    private JLabel lblEstoqueMinimo;
    private JLabel lblPrecoVenda;
    private JLabel lblPrecoCusto;
    private JLabel lblBarCode;
    private JLabel lblCategoria;
    private JLabel lblFornecedor;

	private FornecedorController controller;
	private ClienteController clienteController;
	private ProdutoController produtoController;
	
	private Cliente cliente;
	private Produto produto;
	
	private String modo = "";
	private String modoInicial = "";
	
	private String ultimoNomeBuscado = "";
	private String ultimoBarCodeBuscado = "";
	private String ultimoCPFBuscado = "";
	private String ultimoCNPJBuscado = "";
	private String ultimoCEPBuscado = "";
	
	private Boolean card = false;
	private Boolean cardGrande = false;
	
	private Boolean sobre = false;
	
	private Boolean mudar = false;
	
	private boolean buscouPorCPF = false;
	private boolean buscouPorCNPJ = false;
	private boolean buscouPorBarCode = false;
	
    private JLabel lblNomeBotao;
    private Timer timerAnimacaoLabel;
    
    private JLabel lblNomeBotaoInferior;	
    private JLabel lblNomeBotaoBusca;
    
    
    private Fornecedor fornecedor;
    private JTextField txtCPF;
    private JTextField txtBarCode;
    private JTextField txtCategoria;
    private JTextField txtIDExtra;
    private JTextField txtPrecoCusto;
    private JTextField txtPrecoVenda;
    private JTextField txtEstoque;
    private JTextField txtEstoqueMinimo;

    private JPopupMenu popupSugestoesProduto;
    private JList<String> listaSugestoesProduto;
    private DefaultListModel<String> modeloSugestoesProduto;
    private JScrollPane scrollSugestoesProduto;
    private boolean ignorarEventoSugestao = false;
    private JTextField txtCNPJ;
    
    private ArrayList<String> nomesSugeridos = new ArrayList<>();
    private JLabel lblDashQuantidade;
    private JLabel lblDashRepor;
    private JLabel lblDashZerado;
    private JTextField txtNumero;
    
 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		System.setProperty("sun.java2d.uiScale", "1.0");

		FlatLightLaf.setup();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setTitle("Stellio");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/img/dress.png")));
		
		controller = new FornecedorController();
		clienteController = new ClienteController();
		produtoController = new ProdutoController();

		fornecedor = new Fornecedor();

		cliente = new Cliente();

		produto = new Produto();

		setResizable(false);
		
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        int resposta = JOptionPane.showConfirmDialog(
		                null,
		                "Deseja realmente sair do sistema?",
		                "Confirmar saída",
		                JOptionPane.YES_NO_OPTION
		        );

		        if (resposta == JOptionPane.YES_OPTION) {
		            System.exit(0);
		        }
		    }
		});
		
		
		setBounds(100, 100, 845, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		


		JPanel panelCadastro = new JPanel();
		panelCadastro.setBackground(new Color(210, 210, 210));

		panelCadastro.setBounds(64, 119, 705, 400);
		contentPane.add(panelCadastro);
		panelCadastro.setLayout(null);
		panelCadastro.setVisible(false);
		
		
		JPanel panelListaConteudo = new JPanel();
		panelListaConteudo.setLayout(null);
		panelListaConteudo.setBackground(new Color(210, 210, 210));



		lblNome = new JLabel("Nome:");
		lblNome.setHorizontalAlignment(SwingConstants.CENTER);
		lblNome.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNome.setBounds(83, 21, 115, 32);
		panelCadastro.add(lblNome);

		lblFone = new JLabel("Fone:");
		lblFone.setHorizontalAlignment(SwingConstants.CENTER);
		lblFone.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblFone.setBounds(83, 63, 115, 32);
		panelCadastro.add(lblFone);

		lblEmail = new JLabel("E-mail:");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblEmail.setBounds(83, 105, 115, 32);
		panelCadastro.add(lblEmail);
		
		lblSite = new JLabel("Site:");
		lblSite.setHorizontalAlignment(SwingConstants.CENTER);
		lblSite.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSite.setBounds(83, 147, 115, 32);
		panelCadastro.add(lblSite);
		
		txtCPF = new JTextField();
		txtCPF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtCPF.setColumns(10);
		txtCPF.setBounds(198, 147, 342, 32);
		panelCadastro.add(txtCPF);
		txtCPF.setDocument(new Validador(14));
		
		txtCPF.setDocument(new ValidadorCPF());
		
		lblCPF = new JLabel("CPF:");
		lblCPF.setHorizontalAlignment(SwingConstants.CENTER);
		lblCPF.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblCPF.setBounds(83, 147, 115, 32);
		panelCadastro.add(lblCPF);
		
		txtBarCode = new JTextField();
		txtBarCode.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtBarCode.setColumns(10);
		txtBarCode.setBounds(198, 21, 342, 32);
		panelCadastro.add(txtBarCode);
		txtBarCode.setDocument(new Validador(20));
		txtBarCode.setDocument(new Validador(20, "inteiro"));
		
		txtBarCode.addFocusListener(new FocusAdapter() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        SwingUtilities.invokeLater(() -> txtBarCode.selectAll());
		    }
		});
		
		lblBarCode = new JLabel("Código de Barras:");
		lblBarCode.setHorizontalAlignment(SwingConstants.CENTER);
		lblBarCode.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblBarCode.setBounds(10, 21, 188, 32);
		panelCadastro.add(lblBarCode);
		
		lblFornecedor = new JLabel("Fornecedor:");
		lblFornecedor.setHorizontalAlignment(SwingConstants.CENTER);
		lblFornecedor.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblFornecedor.setBounds(10, 147, 188, 32);
		panelCadastro.add(lblFornecedor);
		
		cbFornecedor = new JComboBox<>();
		cbFornecedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String selecionado = (String) cbFornecedor.getSelectedItem();

				if (selecionado != null && !selecionado.equals("Selecione")) {
				
					String id = selecionado.split(" - ")[0];
					
					
					txtIDExtra.setText(id);
					
				} else {

					txtIDExtra.setText("");
					
				}
				
			}
		});
		cbFornecedor.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cbFornecedor.setBounds(198, 147, 343, 32);
		panelCadastro.add(cbFornecedor);
		
		lblCategoria = new JLabel("Catégoria:");
		lblCategoria.setHorizontalAlignment(SwingConstants.CENTER);
		lblCategoria.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblCategoria.setBounds(10, 105, 188, 32);
		panelCadastro.add(lblCategoria);
		
		txtCategoria = new JTextField();
		txtCategoria.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtCategoria.setColumns(10);
		txtCategoria.setBounds(198, 105, 342, 32);
		panelCadastro.add(txtCategoria);
		
		txtIDExtra = new JTextField();
		txtIDExtra.setHorizontalAlignment(SwingConstants.CENTER);
		txtIDExtra.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txtIDExtra.setColumns(10);
		txtIDExtra.setBorder(null);
		txtIDExtra.setBackground(new Color(210, 210, 210));
		txtIDExtra.setBounds(0, 343, 87, 32);
		panelCadastro.add(txtIDExtra);
		
		
		JPanel panelTabela = new JPanel();
		panelTabela.setBackground(new Color(210, 210, 210));
		panelTabela.setBounds(64, 72, 705, 524);
		contentPane.add(panelTabela);
		panelTabela.setLayout(null);
		
		
		JButton btnAdicionar = new JButton("");
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		btnAdicionar.setIcon(new ImageIcon(Main.class.getResource("/img/add.png")));
		btnAdicionar.setBorderPainted(false);
		btnAdicionar.setBackground(new Color(210, 210, 210));
		btnAdicionar.setBounds(312, 462, 64, 64);
		panelTabela.add(btnAdicionar);
		
		
		
		
		JButton btnSearchSite = new JButton("");
		btnSearchSite.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSearchSite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			    if (txtSite.getText().isBlank()) {
			        JOptionPane.showMessageDialog(null, "Digite o endereço do site.");
			        txtSite.requestFocus();
			        return;
			    }
				
				String site = txtSite.getText();
				
				if (!site.endsWith(".com") && !site.endsWith(".com.br") && !site.endsWith(".net") && !site.endsWith(".org") && !site.endsWith(".br")) {
					    JOptionPane.showMessageDialog(null, "Site não encontrado");
					    txtSite.requestFocus();
					    return;
					}
					
				
				if (site.startsWith("http://") || site.startsWith("https://")) {
					link(site);
				} else {
					link("http://" + site);
				}

			}
		});
		btnSearchSite.setIcon(new ImageIcon(Main.class.getResource("/img/loupe.png")));
		btnSearchSite.setBorderPainted(false);
		btnSearchSite.setBackground(new Color(210, 210, 210));
		btnSearchSite.setBounds(540, 147, 25, 32);
		panelCadastro.add(btnSearchSite);

		txtFone = new JTextField();
		txtFone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				
				txtEmail.requestFocus();
				}
			}
		});
		
		
		txtFone.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtFone.setColumns(10);
		txtFone.setBounds(198, 63, 342, 32);
		panelCadastro.add(txtFone);
		txtFone.setDocument(new Validador(20));
		
		ValidadorFone.aplicar(txtFone);

		txtCNPJ = new JTextField();
		txtCNPJ.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtCNPJ.setColumns(10);
		txtCNPJ.setBounds(198, 63, 342, 32);
		panelCadastro.add(txtCNPJ);
		ValidadorCNPJ.aplicar(txtCNPJ);
		
		JLabel lblCNPJ = new JLabel("CNPJ:");
		lblCNPJ.setHorizontalAlignment(SwingConstants.CENTER);
		lblCNPJ.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblCNPJ.setBounds(83, 63, 115, 32);
		panelCadastro.add(lblCNPJ);

		txtID = new JTextField();
		txtID.setBorder(null);
		txtID.setBackground(new Color(210, 210, 210));
		txtID.setHorizontalAlignment(SwingConstants.CENTER);
		txtID.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txtID.setColumns(10);
		txtID.setBounds(0, 374, 87, 32);
		panelCadastro.add(txtID);
		
			btnSearch = new JButton("");
			btnSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				
		
			JButton btnRelatorio = new JButton("");
			btnRelatorio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnRelatorio.setBackground(new Color(210, 210, 210));
			
				btnRelatorio.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
								
						
						
						if (modoInicial.equals("clientes")) {
							
							clienteController.gerarRelatorioClientes();
							
						} else if (modoInicial.equals("fornecedores")) {
							
							controller.gerarRelatorioFornecedores();
							
						} else if (modoInicial.equals("produtos")) {
							
							produtoController.gerarRelatorioProdutos();
						
						}
								
					}
				}); 
				
				
			btnRelatorio.setIcon(new ImageIcon(Main.class.getResource("/img/file.png")));
			btnRelatorio.setBounds(312, 462, 64, 64);
			panelTabela.add(btnRelatorio);			




		JButton btnEditar = new JButton("");
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setBackground(new Color(210, 210, 210));
		btnEditar.setIcon(new ImageIcon(Main.class.getResource("/img/draw.png")));
		btnEditar.setBounds(207, 462, 64, 64);
		panelTabela.add(btnEditar);

		JButton btnExcluir = new JButton("");
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setBackground(new Color(210, 210, 210));

		btnExcluir.setIcon(new ImageIcon(Main.class.getResource("/img/trash.png")));
		btnExcluir.setBounds(414, 462, 64, 64);
		panelTabela.add(btnExcluir);



		btnCEP = new JButton("");
		btnCEP.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        lblCEP = new JLabel("CEP:");
        lblCEP.setHorizontalAlignment(SwingConstants.CENTER);
        lblCEP.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblCEP.setBounds(83, 190, 115, 32);
        panelCadastro.add(lblCEP);

        txtCEP = new JTextField();
        txtCEP.setFont(new Font("Tahoma", Font.PLAIN, 20));
        txtCEP.setColumns(10);
        txtCEP.setBounds(198, 190, 342, 32);
        panelCadastro.add(txtCEP);
        txtCEP.setDocument(new Validador(9));
        
        ValidadorCEP.aplicarMascara(txtCEP);

                String cep = txtCEP.getText().replaceAll("[^0-9]", "");
                if (cep.length() == 8) {
                    buscarCepEPreencherTela(cep);
                }

                if (!txtCEP.getText().isBlank()) {

                    txtCEP.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {

                            if(e.getKeyCode() == KeyEvent.VK_ENTER) {

                            txtEndereco.requestFocus();
                            }
                        }
                    });

                } else {
                    btnCEP.doClick();
                }

        btnCEP.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String cep = txtCEP.getText().replaceAll("[^0-9]", "");

                if (cep.length() == 8) {

                    ValidadorCEP servico = new ValidadorCEP();

                    try {
                        Cliente cliente = servico.buscarCep(cep);

                        txtEndereco.setText(cliente.getEndereco());
                        txtBairro.setText(cliente.getBairro());
                        txtCidade.setText(cliente.getCidade());

                        cbEstado.setSelectedItem(cliente.getEstado());
                        
                        ultimoCEPBuscado = txtCEP.getText().trim();

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,
                            "Erro ao buscar CEP: " + ex.getMessage(),
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(null,
                        "Por favor, digite um CEP válido com 8 números.",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE);
                    }

            }
        });
        btnCEP.setIcon(new ImageIcon(Main.class.getResource("/img/loupe.png")));
        btnCEP.setBorderPainted(false);
        btnCEP.setBackground(new Color(210, 210, 210));
        btnCEP.setBounds(540, 190, 25, 32);
        panelCadastro.add(btnCEP);
		
		txtEndereco = new JTextField();
		txtEndereco.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtEndereco.setColumns(10);
		txtEndereco.setBounds(198, 230, 268, 32);
		panelCadastro.add(txtEndereco);
		
		txtNumero = new JTextField("Nº");
		txtNumero.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtNumero.setColumns(10);
		txtNumero.setBounds(465, 230, 75, 32);
		panelCadastro.add(txtNumero);
		
		txtNumero.setDocument(new Validador(6));
		
		txtNumero.addFocusListener(new FocusAdapter() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        SwingUtilities.invokeLater(() -> txtNumero.selectAll());
		    }
		});
		
		
		lblEndereco = new JLabel("Endereço:");
		lblEndereco.setHorizontalAlignment(SwingConstants.CENTER);
		lblEndereco.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblEndereco.setBounds(64, 230, 134, 32);
		panelCadastro.add(lblEndereco);
		
		txtBairro = new JTextField();
		txtBairro.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtBairro.setColumns(10);
		txtBairro.setBounds(198, 310, 342, 32);
		panelCadastro.add(txtBairro);
		
		lblBairro = new JLabel("Bairro:");
		lblBairro.setHorizontalAlignment(SwingConstants.CENTER);
		lblBairro.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblBairro.setBounds(83, 310, 115, 32);
		panelCadastro.add(lblBairro);
		
		txtCidade = new JTextField();
		txtCidade.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtCidade.setColumns(10);
		txtCidade.setBounds(198, 350, 273, 32);
		panelCadastro.add(txtCidade);
		
		cbEstado = new JComboBox<>();
		cbEstado.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cbEstado.setModel(new DefaultComboBoxModel<>(
				new String[] { "UF", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA",
						"PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
		cbEstado.setBounds(465, 350, 75, 32);
		panelCadastro.add(cbEstado);
		
		lblCidade = new JLabel("Cidade:");
		lblCidade.setHorizontalAlignment(SwingConstants.CENTER);
		lblCidade.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblCidade.setBounds(83, 350, 115, 32);
		panelCadastro.add(lblCidade);
		
		txtPrecoCusto = new JTextField();
		txtPrecoCusto.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtPrecoCusto.setColumns(10);
		txtPrecoCusto.setBounds(198, 216, 342, 32);
		panelCadastro.add(txtPrecoCusto);
		txtPrecoCusto.setDocument(new Validador(12));
		
		txtPrecoCusto.setDocument(new Validador(10, "decimal"));
		
		txtPrecoVenda = new JTextField();
		txtPrecoVenda.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtPrecoVenda.setColumns(10);
		txtPrecoVenda.setBounds(198, 256, 342, 32);
		panelCadastro.add(txtPrecoVenda);
		txtPrecoVenda.setDocument(new Validador(12));
		
		txtPrecoVenda.setDocument(new Validador(10, "decimal"));
		
		txtEstoque = new JTextField();
		txtEstoque.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtEstoque.setColumns(10);
		txtEstoque.setBounds(198, 298, 342, 32);
		panelCadastro.add(txtEstoque);
		
		((AbstractDocument) txtEstoque.getDocument()).setDocumentFilter(new DocumentFilter() {
		    private final int MAX_DIGITOS = 8;

		    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
		        String atual = fb.getDocument().getText(0, fb.getDocument().getLength());
		        String novoTexto = atual.substring(0, offset) + text + atual.substring(offset + length);
		        String digitos = novoTexto.replaceAll("[^0-9]", "");

		        if (digitos.length() > MAX_DIGITOS) {
		            digitos = digitos.substring(0, MAX_DIGITOS);
		        }

		        String formatado = digitos.isEmpty() ? "" : new java.text.DecimalFormat("#,###").format(Long.parseLong(digitos)).replace(",", ".");
		        super.replace(fb, 0, fb.getDocument().getLength(), "", attrs);
		        super.replace(fb, 0, 0, formatado, attrs);
		    }
		});
		
		txtEstoqueMinimo = new JTextField();
		txtEstoqueMinimo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtEstoqueMinimo.setColumns(10);
		txtEstoqueMinimo.setBounds(198, 340, 342, 32);
		panelCadastro.add(txtEstoqueMinimo);
		
		((AbstractDocument) txtEstoqueMinimo.getDocument()).setDocumentFilter(new DocumentFilter() {
		    private final int MAX_DIGITOS = 8;

		    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
		        String atual = fb.getDocument().getText(0, fb.getDocument().getLength());
		        String novoTexto = atual.substring(0, offset) + text + atual.substring(offset + length);
		        String digitos = novoTexto.replaceAll("[^0-9]", "");

		        if (digitos.length() > MAX_DIGITOS) {
		            digitos = digitos.substring(0, MAX_DIGITOS);
		        }

		        String formatado = digitos.isEmpty() ? "" : new java.text.DecimalFormat("#,###").format(Long.parseLong(digitos)).replace(",", ".");
		        super.replace(fb, 0, fb.getDocument().getLength(), "", attrs);
		        super.replace(fb, 0, 0, formatado, attrs);
		    }
		});
		
		lblEstoqueMinimo = new JLabel("Estoque mínimo:");
		lblEstoqueMinimo.setHorizontalAlignment(SwingConstants.CENTER);
		lblEstoqueMinimo.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblEstoqueMinimo.setBounds(10, 340, 188, 32);
		panelCadastro.add(lblEstoqueMinimo);
		
		lblEstoque = new JLabel("Estoque:");
		lblEstoque.setHorizontalAlignment(SwingConstants.CENTER);
		lblEstoque.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblEstoque.setBounds(10, 298, 188, 32);
		panelCadastro.add(lblEstoque);
		
		lblPrecoVenda = new JLabel("Preço de Venda:");
		lblPrecoVenda.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrecoVenda.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblPrecoVenda.setBounds(10, 255, 188, 32);
		panelCadastro.add(lblPrecoVenda);
		
		lblPrecoCusto = new JLabel("Preço de Custo:");
		lblPrecoCusto.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrecoCusto.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblPrecoCusto.setBounds(10, 216, 188, 32);
		panelCadastro.add(lblPrecoCusto);
		
		JLabel lblDivisor = new JLabel("_________________________________________________________________________________________");
		lblDivisor.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 10));
		lblDivisor.setHorizontalAlignment(SwingConstants.CENTER);
		lblDivisor.setBounds(10, 178, 685, 28);
		panelCadastro.add(lblDivisor);
		
		JButton btnMudar = new JButton("Teste");
		btnMudar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnMudar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			if (modoInicial.equals("fornecedores")) {
				
				if (mudar) {
		            
		            mudar = false;
		            
		            txtNome.setVisible(true);
		            btnSearch.setVisible(true);
		            lblNome.setVisible(true);
		            
		            lblCNPJ.setVisible(false);
		            txtCNPJ.setVisible(false);
		            btnSearch.setVisible(true);
		            btnSearchAlt.setVisible(false);
		            
		            btnMudar.setText("Buscar pelo CNPJ");
		            
		            txtNome.requestFocus();
		            
				} else {
		            mudar = true;
		        
		            lblCNPJ.setVisible(true);
		            txtCNPJ.setVisible(true);
		            btnSearch.setVisible(false);
		            btnSearchAlt.setVisible(true);
		            
		            txtNome.setVisible(false);
		            lblNome.setVisible(false);
		            
		            btnMudar.setText("Buscar pelo Nome");
		            
		            txtCNPJ.requestFocus();
				
				}
				
			
			} else if (modoInicial.equals("clientes")) {
					
					if (mudar) {
			            
			            mudar = false;
			            
			            txtNome.setVisible(true);
			            btnSearch.setVisible(true);
			            lblNome.setVisible(true);
			            
			            lblCPF.setVisible(false);
			            txtCPF.setVisible(false);
			            btnSearch.setVisible(true);
			            btnSearchAlt.setVisible(false);
			            
			            btnMudar.setText("Buscar pelo CPF");
			            
			            txtNome.requestFocus();
			            
					} else {
			            mudar = true;
			        
			            lblCPF.setVisible(true);
			            txtCPF.setVisible(true);
			            btnSearch.setVisible(false);
			            btnSearchAlt.setVisible(true);
			            
			            txtNome.setVisible(false);
			            lblNome.setVisible(false);
			            
			            btnMudar.setText("Buscar pelo nome");
			            
			            txtCPF.requestFocus();
					
					}
				
				
			} else if (modoInicial.equals("produtos")) {
            	
				
				if (mudar) {
		            
		            mudar = false;
		            
		            txtNome.setVisible(true);
		            btnSearch.setVisible(true);
		            lblNome.setVisible(true);
		            
		            lblBarCode.setVisible(false);
		            txtBarCode.setVisible(false);
		            btnSearch.setVisible(true);
		            btnSearchAlt.setVisible(false);
		            
		            btnMudar.setText("Buscar pelo código de barras");
		            
		            txtNome.requestFocus();
		            
				} else {
		            mudar = true;
		        
		            lblBarCode.setVisible(true);
		            txtBarCode.setVisible(true);
		            btnSearch.setVisible(false);
		            btnSearchAlt.setVisible(true);
		            
		            txtNome.setVisible(false);
		            lblNome.setVisible(false);
		            
		            btnMudar.setText("Buscar pelo Nome");
		            
		            txtBarCode.requestFocus();
		            
				}
		            
				} else if (modoInicial.equals("vendas")) {
	            	
					
					if (mudar) {
			            
			            mudar = false;
			            
			            txtNome.setVisible(true);
			            btnSearch.setVisible(true);
			            lblNome.setVisible(true);
			            
			            lblBarCode.setVisible(false);
			            txtBarCode.setVisible(false);
			            btnSearch.setVisible(true);
			            btnSearchAlt.setVisible(false);
			            
			            btnMudar.setText("Buscar pelo código de barras");
			            
			            
			            txtNome.requestFocus();
			            
					} else {
			            mudar = true;
			        
			            lblBarCode.setVisible(true);
			            txtBarCode.setVisible(true);
			            btnSearch.setVisible(false);
			            btnSearchAlt.setVisible(true);
			            
			            txtNome.setVisible(false);
			            lblNome.setVisible(false);
			            
			            btnMudar.setText("Buscar pelo Nome");
			            
			            
			            txtBarCode.requestFocus();
				
				} 
				
				
				
			}}
		});
		
		
		btnMudar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnMudar.setBorderPainted(false);
		btnMudar.setBackground(new Color(210, 210, 210));
		btnMudar.setBounds(198, 0, 342, 41);
		panelCadastro.add(btnMudar);
		
		JLabel lblComplemento = new JLabel("Complemento");
		lblComplemento.setHorizontalAlignment(SwingConstants.CENTER);
		lblComplemento.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblComplemento.setBounds(20, 270, 178, 32);
		panelCadastro.add(lblComplemento);
		
		cbComplemento = new JComboBox<>();
		cbComplemento.setBounds(198, 270, 342, 32);
		cbComplemento.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cbComplemento.setModel(new DefaultComboBoxModel<>(
				new String[] {"Selecione", "Casa", "Arpatamento"}));
		cbEstado.setBounds(465, 350, 75, 32);
		panelCadastro.add(cbComplemento);
		
		
		modeloSugestoesProduto = new DefaultListModel<>();
		listaSugestoesProduto = new JList<>(modeloSugestoesProduto);
		listaSugestoesProduto.setFont(new Font("Tahoma", Font.PLAIN, 20));
		listaSugestoesProduto.setFixedCellHeight(32);
		listaSugestoesProduto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		popupSugestoesProduto = new JPopupMenu();
		popupSugestoesProduto.setFocusable(false);
		popupSugestoesProduto.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		scrollSugestoesProduto = new JScrollPane(listaSugestoesProduto);
		scrollSugestoesProduto.setBorder(null);
		scrollSugestoesProduto.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollSugestoesProduto.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		popupSugestoesProduto.add(scrollSugestoesProduto);

		listaSugestoesProduto.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        selecionarSugestao(listaSugestoesProduto.getSelectedIndex());
		    }
		});	


		JLabel lblTabela = new JLabel("");
		lblTabela.setIconTextGap(15);
		lblTabela.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblTabela.setHorizontalAlignment(SwingConstants.CENTER);
		lblTabela.setBounds(0, 0, 705, 46);
		panelTabela.add(lblTabela);

		JPanel panelSobre = new JPanel();
		panelSobre.setBackground(new Color(210, 210, 210));
		panelSobre.setBounds(64, 72, 705, 524);
		contentPane.add(panelSobre);
		panelSobre.setLayout(null);

		JLabel lblJava = new JLabel("");
		lblJava.setBounds(631, 10, 64, 64);
		lblJava.setIcon(new ImageIcon(Main.class.getResource("/img/java.png")));
		panelSobre.add(lblJava);

		JLabel lblGithub = new JLabel("");
		lblGithub.setIcon(new ImageIcon(Main.class.getResource("/img/github.png")));
		lblGithub.setBounds(10, 483, 32, 32);
		panelSobre.add(lblGithub);

		JLabel lblGitLink = new JLabel("github.com/qguuh");
		lblGitLink.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblGitLink.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				link("https://github.com/qguuh");
			}
		});
		lblGitLink.setForeground(new Color(0, 51, 255));
		lblGitLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblGitLink.setBounds(50, 487, 126, 23);
		panelSobre.add(lblGitLink);

		JLabel lblNome_1 = new JLabel("Stellio");
		lblNome_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNome_1.setFont(new Font("Vladimir Script", Font.BOLD, 72));
		lblNome_1.setBounds(149, 190, 409, 64);
		panelSobre.add(lblNome_1);

		JLabel lblDescricao = new JLabel("Plataforma de Gestão de Cadastros");
		lblDescricao.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescricao.setFont(new Font("NSimSun", Font.BOLD, 20));
		lblDescricao.setBounds(149, 264, 409, 33);
		panelSobre.add(lblDescricao);

		JLabel lblAuthor = new JLabel("Feito por: Gustavo");
		lblAuthor.setHorizontalAlignment(SwingConstants.CENTER);
		lblAuthor.setFont(new Font("NSimSun", Font.BOLD, 20));
		lblAuthor.setBounds(149, 307, 409, 23);
		panelSobre.add(lblAuthor);

		JLabel lblVersao = new JLabel("Versão: 1.0");
		lblVersao.setHorizontalAlignment(SwingConstants.CENTER);
		lblVersao.setFont(new Font("NSimSun", Font.BOLD, 20));
		lblVersao.setBounds(149, 342, 409, 23);
		panelSobre.add(lblVersao);
		panelSobre.setVisible(false);

		JPanel panelInferior = new JPanel();
		panelInferior.setBackground(new Color(210, 210, 210));
		panelInferior.setBounds(0, 606, 832, 107);
		contentPane.add(panelInferior);
		panelInferior.setLayout(null);

		JButton btnFornecedores = new JButton("Fornecedores");
		btnFornecedores.setBounds(270, 11, 64, 64);
		panelInferior.add(btnFornecedores);
		btnFornecedores.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnFornecedores.setForeground(new Color(0, 0, 0));
		btnFornecedores.setIcon(new ImageIcon(Main.class.getResource("/img/team.png")));

		JButton btnProdutos = new JButton("Produtos");
		btnProdutos.setBounds(478, 11, 64, 64);
		panelInferior.add(btnProdutos);
		btnProdutos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnProdutos.setForeground(new Color(0, 0, 0));

		JButton btnHome = new JButton("Home");
		btnHome.setBounds(375, 11, 64, 64);
		panelInferior.add(btnHome);
		btnHome.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnHome.setForeground(new Color(0, 0, 0));
		
		btnFornecedores.setBorderPainted(false);
		btnProdutos.setBorderPainted(false);
		btnHome.setBorderPainted(false);


		btnFornecedores.setIconTextGap(25);
		btnFornecedores.setHorizontalAlignment(SwingConstants.LEFT);
		btnFornecedores.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnFornecedores.setBackground(new Color(210, 210, 210));

		btnProdutos.setBackground(new Color(210, 210, 210));
		btnProdutos.setHorizontalAlignment(SwingConstants.LEFT);
		btnProdutos.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnProdutos.setIcon(new ImageIcon(Main.class.getResource("/img/box.png")));
		btnProdutos.setIconTextGap(25);

		btnHome.setBackground(new Color(210, 210, 210));
		btnHome.setHorizontalAlignment(SwingConstants.LEFT);
		btnHome.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnHome.setIcon(new ImageIcon(Main.class.getResource("/img/home.png")));
		btnHome.setIconTextGap(25);

		lblDatabase = new JLabel("");
		lblDatabase.setBounds(330, 81, 31, 26);
		panelInferior.add(lblDatabase);
		lblDatabase.setIcon(new ImageIcon(Main.class.getResource("/img/database.png")));
		lblDatabase.setFont(new Font("Tahoma", Font.PLAIN, 11));

		lblMySQL = new JLabel("");
		lblMySQL.setHorizontalAlignment(SwingConstants.CENTER);
		lblMySQL.setBounds(361, 79, 104, 26);
		panelInferior.add(lblMySQL);
		lblMySQL.setFont(new Font("Tahoma", Font.PLAIN, 11));

		lblStatus = new JLabel("•");
		lblStatus.setBounds(467, 80, 25, 26);
		panelInferior.add(lblStatus);
		lblStatus.setForeground(new Color(0, 174, 0));
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JButton btnClientes = new JButton("");
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClientes.setBackground(new Color(210, 210, 210));

		btnClientes.setBorderPainted(false);
		btnClientes.setIcon(new ImageIcon(Main.class.getResource("/img/profile.png")));

		btnClientes.setBounds(170, 11, 64, 64);
		panelInferior.add(btnClientes);

		JLabel lblDashboard = new JLabel("Dashboard");
		lblDashboard.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDashboard.setBounds(49, 21, 110, 49);
		contentPane.add(lblDashboard);

		JLabel lblDashboardLogo = new JLabel("");
		lblDashboardLogo.setIcon(new ImageIcon(Main.class.getResource("/img/speedometer.png")));
		lblDashboardLogo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDashboardLogo.setBounds(10, 21, 44, 49);
		contentPane.add(lblDashboardLogo);

		JLabel lbCalendário = new JLabel("");
		lbCalendário.setIcon(new ImageIcon(Main.class.getResource("/img/calendar.png")));
		lbCalendário.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbCalendário.setBounds(678, 21, 32, 49);
		contentPane.add(lbCalendário);

		lblData = new JLabel("");
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblData.setBounds(720, 30, 101, 31);
		contentPane.add(lblData);

		JPanel panelLateral1 = new JPanel();
		panelLateral1.setLayout(null);
		panelLateral1.setBackground(new Color(210, 210, 210));
		panelLateral1.setBounds(0, 345, 17, 251);
		contentPane.add(panelLateral1);

		JPanel panelLateral2 = new JPanel();
		panelLateral2.setLayout(null);
		panelLateral2.setBackground(new Color(210, 210, 210));
		panelLateral2.setBounds(0, 70, 17, 251);
		contentPane.add(panelLateral2);

		JPanel panelCard1 = new JPanel();
		panelCard1.setVisible(false);
		panelCard1.setBackground(new Color(210, 210, 210));
		panelCard1.setBounds(59, 70, 230, 251);
		contentPane.add(panelCard1);
		panelCard1.setLayout(null);
		
		JLabel lblImagem = new JLabel("");
		lblImagem.setHorizontalAlignment(SwingConstants.CENTER);
		lblImagem.setIcon(new ImageIcon(Main.class.getResource("/img/quantidade.png")));
		lblImagem.setBounds(10, 11, 64, 64);
		panelCard1.add(lblImagem);
		
		JLabel lblProdutos = new JLabel("Produtos");
		lblProdutos.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblProdutos.setBackground(new Color(255, 255, 255));
		lblProdutos.setBounds(84, 11, 136, 64);
		panelCard1.add(lblProdutos);
		
		JLabel lblTotal = new JLabel("Total de produtos cadastrados");
		lblTotal.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTotal.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotal.setBounds(0, 208, 229, 32);
		panelCard1.add(lblTotal);
		
		lblDashQuantidade = new JLabel("0");
		lblDashQuantidade.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblDashQuantidade.setHorizontalAlignment(SwingConstants.CENTER);
		lblDashQuantidade.setBounds(20, 86, 200, 111);
		panelCard1.add(lblDashQuantidade);

		JPanel panelCard2 = new JPanel();
		panelCard2.setBackground(new Color(210, 210, 210));
		panelCard2.setBounds(299, 70, 230, 251);
		contentPane.add(panelCard2);
		panelCard2.setLayout(null);
		
		JLabel lblBaixoEstoque = new JLabel("Produtos com o estoque abaixo do mínimo");
		lblBaixoEstoque.setHorizontalAlignment(SwingConstants.CENTER);
		lblBaixoEstoque.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBaixoEstoque.setBounds(0, 208, 229, 32);
		panelCard2.add(lblBaixoEstoque);
		
		lblDashRepor = new JLabel("0");
		lblDashRepor.setHorizontalAlignment(SwingConstants.CENTER);
		lblDashRepor.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblDashRepor.setBounds(20, 86, 200, 111);
		panelCard2.add(lblDashRepor);
		
		JLabel lblEstoqueBaixo = new JLabel("Estoque baixo");
		lblEstoqueBaixo.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblEstoqueBaixo.setBackground(Color.WHITE);
		lblEstoqueBaixo.setBounds(84, 11, 136, 64);
		panelCard2.add(lblEstoqueBaixo);
		
		JLabel lblImagem2 = new JLabel("");
		lblImagem2.setIcon(new ImageIcon(Main.class.getResource("/img/alerta.png")));
		lblImagem2.setHorizontalAlignment(SwingConstants.CENTER);
		lblImagem2.setBounds(10, 11, 64, 64);
		panelCard2.add(lblImagem2);

		JPanel panelCard3 = new JPanel();
		panelCard3.setBackground(new Color(210, 210, 210));
		panelCard3.setBounds(539, 70, 230, 251);
		contentPane.add(panelCard3);
		panelCard3.setLayout(null);
		
		JLabel lblZerado = new JLabel("Produtos sem estoque");
		lblZerado.setHorizontalAlignment(SwingConstants.CENTER);
		lblZerado.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblZerado.setBounds(0, 208, 229, 32);
		panelCard3.add(lblZerado);
		
		lblDashZerado = new JLabel("0");
		lblDashZerado.setHorizontalAlignment(SwingConstants.CENTER);
		lblDashZerado.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblDashZerado.setBounds(20, 86, 200, 111);
		panelCard3.add(lblDashZerado);
		
		JLabel lblSemEstoque = new JLabel("Sem estoque");
		lblSemEstoque.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSemEstoque.setBackground(Color.WHITE);
		lblSemEstoque.setBounds(84, 11, 136, 64);
		panelCard3.add(lblSemEstoque);
		
		JLabel lblImagem3 = new JLabel("");
		lblImagem3.setIcon(new ImageIcon(Main.class.getResource("/img/zerado.png")));
		lblImagem3.setHorizontalAlignment(SwingConstants.CENTER);
		lblImagem3.setBounds(10, 11, 64, 64);
		panelCard3.add(lblImagem3);
		panelCard3.setVisible(false);

		JPanel panelCard4 = new JPanel();
		panelCard4.setLayout(null);
		panelCard4.setBackground(new Color(210, 210, 210));
		panelCard4.setBounds(59, 345, 230, 251);
		contentPane.add(panelCard4);
		panelCard4.setVisible(false);
		panelCard4.setVisible(false);

		JPanel panelCard5 = new JPanel();
		panelCard5.setBackground(new Color(210, 210, 210));
		panelCard5.setBounds(299, 345, 230, 251);
		contentPane.add(panelCard5);
		panelCard5.setLayout(null);
		panelCard5.setVisible(false);

		JPanel panelCard6 = new JPanel();
		panelCard6.setBackground(new Color(210, 210, 210));
		panelCard6.setBounds(539, 345, 230, 251);
		contentPane.add(panelCard6);
		panelCard6.setLayout(null);
		panelCard2.setVisible(false);
		panelCard6.setVisible(false);

		JPanel panelLateralGrande = new JPanel();
		panelLateralGrande.setLayout(null);
		panelLateralGrande.setBackground(new Color(210, 210, 210));
		panelLateralGrande.setBounds(815, 70, 17, 526);
		contentPane.add(panelLateralGrande);

		JPanel panelMeioGrande = new JPanel();
		panelMeioGrande.setLayout(null);
		panelMeioGrande.setBackground(new Color(210, 210, 210));
		panelMeioGrande.setBounds(64, 72, 705, 525);
		contentPane.add(panelMeioGrande);
		panelMeioGrande.setVisible(false);

		JButton btnCard = new JButton("");
		btnCard.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCard.setIcon(new ImageIcon(Main.class.getResource("/img/right-arrow.png")));
		btnCard.setBounds(22, 308, 32, 49);
		contentPane.add(btnCard);

		JButton btnCardGrande = new JButton("");
		btnCardGrande.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCardGrande.setIcon(new ImageIcon(Main.class.getResource("/img/left-arrow.png")));

		btnCard.setBorderPainted(false);
		btnCard.setContentAreaFilled(false);

		btnCardGrande.setBorderPainted(false);
		btnCardGrande.setContentAreaFilled(false);

		btnCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				atualizarDashboard();
				
				sobre = false;
				
		        try {
		            if (card) {
		                card = false;
		                btnCard.setIcon(new ImageIcon(Main.class.getResource("/img/right-arrow.png")));
		                

						panelCard1.setVisible(false);
						panelCard2.setVisible(false);
						panelCard3.setVisible(false);
						panelCard4.setVisible(false);
						panelCard5.setVisible(false);
						panelCard6.setVisible(false);
						panelMeioGrande.setVisible(false);
						panelLateralGrande.setVisible(true);

						panelLateral2.setVisible(false);
						
						

						panelLateral1.setVisible(true);
						
						panelLateral1.setBounds(0, 205, 17, 251);

						panelTabela.setVisible(false);
						panelSobre.setVisible(false);
						panelCadastro.setVisible(false);

		            } else {
		            	cardGrande = false;
		                btnCardGrande.setIcon(new ImageIcon(Main.class.getResource("/img/left-arrow.png")));
		                
		                card = true;
		                btnCard.setIcon(new ImageIcon(Main.class.getResource("/img/left-arrow.png")));

						panelLateral2.setVisible(false);
						panelLateral1.setVisible(false);

						panelCard1.setVisible(true);
						panelCard2.setVisible(true);
						panelCard3.setVisible(true);
						
						
						
						panelCard4.setVisible(false);
						panelCard5.setVisible(false);
						panelCard6.setVisible(false);
						
						panelCard1.setBounds(59, 205, 230, 251);
						panelCard2.setBounds(299, 205, 230, 251);
						panelCard3.setBounds(539, 205, 230, 251);

						panelMeioGrande.setVisible(false);
						panelLateralGrande.setVisible(true);

						panelTabela.setVisible(false);
						panelSobre.setVisible(false);
						panelCadastro.setVisible(false);
		                
						btnClientes.setBounds(270, 11, 64, 64);
						btnFornecedores.setBounds(375, 11, 64, 64);
						btnProdutos.setBounds(478, 11, 64, 64);
						btnHome.setVisible(false);
						btnFornecedores.setVisible(true);
						btnClientes.setVisible(true);
						btnProdutos.setVisible(true);
						
		            }

		        } catch (Exception ex) {
		            System.out.println(ex);
		        }
			}
		});


		btnCardGrande.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				sobre = false;
				
		        try {
		            if (cardGrande) {
		                FlatLightLaf.setup();
		                cardGrande = false;
		                btnCardGrande.setIcon(new ImageIcon(Main.class.getResource("/img/left-arrow.png")));
		                
		                
						panelCard1.setVisible(false);
						panelCard2.setVisible(false);
						panelCard3.setVisible(false);
						panelCard4.setVisible(false);
						panelCard5.setVisible(false);
						panelCard6.setVisible(false);
						panelMeioGrande.setVisible(false);
						panelLateralGrande.setVisible(false);

						panelLateral2.setVisible(false);
						
						panelLateral1.setVisible(true);
						
						panelLateral1.setBounds(0, 205, 17, 251);

						panelTabela.setVisible(false);
						panelSobre.setVisible(false);
						panelCadastro.setVisible(false);

		            } else {
		            	card = false;
		                btnCard.setIcon(new ImageIcon(Main.class.getResource("/img/right-arrow.png")));
		                
		                cardGrande = true;
		                btnCardGrande.setIcon(new ImageIcon(Main.class.getResource("/img/right-arrow.png")));

						panelMeioGrande.setVisible(true);
						panelLateralGrande.setVisible(false);

						panelCard1.setVisible(false);
						panelCard2.setVisible(false);
						panelCard3.setVisible(false);
						panelCard4.setVisible(false);
						panelCard5.setVisible(false);
						panelCard6.setVisible(false);

						panelTabela.setVisible(false);
						panelSobre.setVisible(false);
						panelCadastro.setVisible(false);

						btnCard.setVisible(true);


						panelLateral2.setVisible(false);
						
						panelLateral1.setVisible(true);
						
						panelLateral1.setBounds(0, 205, 17, 251);
		                
		                JOptionPane.showMessageDialog(null, "em andamento");
		                btnHome.doClick();
		            }

		        } catch (Exception ex) {
		            System.out.println(ex);
		        }

			}
		});
		btnCardGrande.setBounds(777, 308, 32, 49);
		contentPane.add(btnCardGrande);

			
			

		
		
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				mudar = false;
				modo = "buscar";
				ultimoNomeBuscado = "";
				ultimoCPFBuscado = "";
				
				modoInicial = "clientes";
				limparCampos();

				
				panelTabela.setVisible(true);
				
				cardGrande = false;
				btnCardGrande.setIcon(new ImageIcon(Main.class.getResource("/img/left-arrow.png")));
				card = false;
				btnCard.setIcon(new ImageIcon(Main.class.getResource("/img/right-arrow.png")));
				
				sobre = false;
				

				
				if (modoInicial.equals("clientes")) {
				    lblTabela.setText("Clientes");
				    lblTabela.setIconTextGap(15);
				    lblTabela.setFont(new Font("Tahoma", Font.PLAIN, 30));
				    lblTabela.setIcon(new ImageIcon(Main.class.getResource("/img/profile.png")));
				    lblTabela.setHorizontalAlignment(SwingConstants.CENTER);
				    lblTabela.setBounds(0, 0, 705, 46);
				    
					SwingUtilities.invokeLater(new Runnable() {
					    public void run() {
					    	
					    	SwingUtilities.invokeLater(new Runnable() {
							    public void run() {
							    	
							    	panelCadastro.setVisible(true);
							    	

									lblFone.setVisible(false);
									lblEmail.setVisible(false);
									lblSite.setVisible(false);
									lblCPF.setVisible(false);
									
									lblBarCode.setVisible(false);
									lblCategoria.setVisible(false);
									lblFornecedor.setVisible(false);
									lblComplemento.setVisible(false);
									
									txtBarCode.setVisible(false);
									txtCategoria.setVisible(false);
									cbFornecedor.setVisible(false);
									
									lblPrecoCusto.setVisible(false);
									txtPrecoCusto.setVisible(false);
									lblPrecoVenda.setVisible(false);
									txtPrecoVenda.setVisible(false);
									lblEstoque.setVisible(false);
									txtEstoque.setVisible(false);
									lblEstoqueMinimo.setVisible(false);
									txtEstoqueMinimo.setVisible(false);
									cbComplemento.setVisible(false);
									txtNumero.setVisible(false);
									
									lblCNPJ.setVisible(false);
						            txtCNPJ.setVisible(false);
									
									lblDivisor.setVisible(false);
									
									
									txtCEP.setVisible(false);
					                txtEndereco.setVisible(false);
					                txtBairro.setVisible(false);
					                txtCidade.setVisible(false);
					                cbEstado.setVisible(false);

					                lblCEP.setVisible(false);
					                lblEndereco.setVisible(false);
					                lblBairro.setVisible(false);
					                lblCidade.setVisible(false);
									
									btnCEP.setVisible(false);
									btnSearchSite.setVisible(false);
									txtID.setVisible(false);
									txtFone.setVisible(false);
									txtEmail.setVisible(false);
									txtSite.setVisible(false);
									txtCPF.setVisible(false);
									
									
									lblNome.setVisible(true);
									lblNome.setBounds(78, 210, 115, 32);
									lblNome.setText("Nome: ");
									txtNome.setVisible(true);
									txtNome.setBounds(200, 210, 342, 32);
									
									btnSearchAlt.setVisible(false);
									btnSearch.setVisible(true);
									btnSearch.setBounds(542, 210, 25, 32);
									btnSearchAlt.setBounds(542, 210, 25, 32);
									
									lblCPF.setBounds(78, 210, 115, 32);
									txtCPF.setBounds(200, 210, 342, 32);
									
									panelSobre.setVisible(false);
									

									btnMudar.setVisible(true);
									btnMudar.setBounds(200, 242, 342, 32);
									btnMudar.setText("Buscar pelo CPF");

									btnAdicionar.setVisible(false);
									btnRelatorio.setVisible(true);
									btnEditar.setVisible(false);
									btnExcluir.setVisible(false);
									
									txtNome.requestFocus();
									
									
							    }
							});
					    }
					});
				    

				}
				
				panelCard1.setVisible(false);
				panelCard2.setVisible(false);
				panelCard3.setVisible(false);
				panelCard4.setVisible(false);
				panelCard5.setVisible(false);
				panelCard6.setVisible(false);
				panelMeioGrande.setVisible(false);


				panelLateral2.setVisible(false);
				
				
				panelLateral1.setVisible(true);
				panelLateralGrande.setVisible(true);
				
				
				panelLateral1.setBounds(0, 205, 17, 251);
				

				panelSobre.setVisible(false);

				
				btnFornecedores.setBounds(270, 11, 64, 64);
				btnHome.setBounds(375, 11, 64, 64);
				btnProdutos.setBounds(478, 11, 64, 64);
				btnHome.setVisible(true);
				btnFornecedores.setVisible(true);
				btnClientes.setVisible(false);
				btnProdutos.setVisible(true);

				
			}
		});

		btnFornecedores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				mudar = false;
				modo = "buscar"; 
				ultimoNomeBuscado = "";
				ultimoCNPJBuscado = "";
				
				modoInicial = "fornecedores";
				limparCampos();
				
				panelTabela.setVisible(true);
				cardGrande = false;
				btnCardGrande.setIcon(new ImageIcon(Main.class.getResource("/img/left-arrow.png")));
				card = false;
				btnCard.setIcon(new ImageIcon(Main.class.getResource("/img/right-arrow.png")));
				
				sobre = false;
				

				
				if (modoInicial.equals("fornecedores")) {
				    lblTabela.setText("Fornecedores");
				    lblTabela.setIconTextGap(15);
				    lblTabela.setFont(new Font("Tahoma", Font.PLAIN, 30));
				    lblTabela.setIcon(new ImageIcon(Main.class.getResource("/img/team.png")));
				    lblTabela.setHorizontalAlignment(SwingConstants.CENTER);
				    lblTabela.setBounds(0, 0, 705, 46);

					SwingUtilities.invokeLater(new Runnable() {
					    public void run() {
					    	
					    	panelCadastro.setVisible(true);
					    	

							lblFone.setVisible(false);
							lblEmail.setVisible(false);
							lblSite.setVisible(false);
							lblCPF.setVisible(false);
							
							lblCNPJ.setVisible(false);
							txtCNPJ.setVisible(false);
							
							lblBarCode.setVisible(false);
							lblCategoria.setVisible(false);
							lblFornecedor.setVisible(false);
							
							txtBarCode.setVisible(false);
							txtCategoria.setVisible(false);
							cbFornecedor.setVisible(false);
							
							lblPrecoCusto.setVisible(false);
							txtPrecoCusto.setVisible(false);
							lblPrecoVenda.setVisible(false);
							txtPrecoVenda.setVisible(false);
							lblEstoque.setVisible(false);
							txtEstoque.setVisible(false);
							lblEstoqueMinimo.setVisible(false);
							txtEstoqueMinimo.setVisible(false);
							
							lblDivisor.setVisible(false);
							
							txtCEP.setVisible(false);
			                txtEndereco.setVisible(false);
			                txtBairro.setVisible(false);
			                txtCidade.setVisible(false);
			                cbEstado.setVisible(false);

			                lblCEP.setVisible(false);
			                lblEndereco.setVisible(false);
			                lblBairro.setVisible(false);
			                lblCidade.setVisible(false);
			                lblComplemento.setVisible(false);

							btnCEP.setVisible(false);
							btnSearchSite.setVisible(false);
							txtID.setVisible(false);
							txtFone.setVisible(false);
							txtEmail.setVisible(false);
							txtSite.setVisible(false);
							txtCPF.setVisible(false);
							cbComplemento.setVisible(false);
							txtNumero.setVisible(false);
							
							
							lblNome.setVisible(true);
							lblNome.setBounds(65, 210, 130, 32);
	                        lblNome.setText("Fornecedor: ");
							txtNome.setVisible(true);
							txtNome.setBounds(200, 210, 342, 32);
							
							btnSearchAlt.setVisible(false);
							btnSearch.setVisible(true);
							btnSearch.setBounds(542, 210, 25, 32);
							btnSearchAlt.setBounds(542, 210, 25, 32);
							
							lblCNPJ.setBounds(65, 210, 130, 32);
							txtCNPJ.setBounds(200, 210, 342, 32);

							panelSobre.setVisible(true);
							
							btnMudar.setVisible(true);
							btnMudar.setBounds(200, 242, 342, 32);
							btnMudar.setText("Buscar pelo CNPJ");
							

							btnAdicionar.setVisible(false);
							btnRelatorio.setVisible(true);
							btnEditar.setVisible(false);
							btnExcluir.setVisible(false);
							
							txtNome.requestFocus();
							
							
					    }
					});
				    
				    
				}
				
				

				panelCard1.setVisible(false);
				panelCard2.setVisible(false);
				panelCard3.setVisible(false);
				panelCard4.setVisible(false);
				panelCard5.setVisible(false);
				panelCard6.setVisible(false);
				panelMeioGrande.setVisible(false);
				panelLateralGrande.setVisible(true);

				panelLateral2.setVisible(false);
				panelLateral1.setVisible(true);
				
				panelLateral1.setBounds(0, 205, 17, 251);
				

				panelSobre.setVisible(false);
				
				btnClientes.setBounds(270, 11, 64, 64);
				btnHome.setBounds(375, 11, 64, 64);
				btnProdutos.setBounds(478, 11, 64, 64);
				btnFornecedores.setVisible(false);
				btnHome.setVisible(true);
				btnClientes.setVisible(true);
				btnProdutos.setVisible(true);
				
				

				
			}
		});

		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cardGrande = false;
				btnCardGrande.setIcon(new ImageIcon(Main.class.getResource("/img/left-arrow.png")));
				card = false;
				btnCard.setIcon(new ImageIcon(Main.class.getResource("/img/right-arrow.png")));
				
				if (modoInicial.equals("fornecedores") && txtCNPJ.getText().isBlank()) {
		    	    JOptionPane.showMessageDialog(null, "Preencha o CNPJ.");
		    	    SwingUtilities.invokeLater(new Runnable() {
		    	        public void run() {
		    	        	
		    	            txtCNPJ.requestFocus();
		    	        }
		    	    });
		    	    return;
				
				} else if (txtNome.getText().isBlank()) {
		    	    JOptionPane.showMessageDialog(null, "Preencha o nome.");
		    	    SwingUtilities.invokeLater(new Runnable() {
		    	        public void run() {
		    	        	
		    	            txtNome.requestFocus();
		    	        }
		    	    });
		    	    return;
		    	    
				} else if (modoInicial.equals("fornecedores")  && txtFone.getText().isBlank()) {
		    	    JOptionPane.showMessageDialog(null, "Preencha o telefone.");
		    	    SwingUtilities.invokeLater(new Runnable() {
		    	        public void run() {
		    	        	
		    	            txtFone.requestFocus();
		    	        }
		    	    });
		    	    return;

		    	} else if (modoInicial.equals("clientes")  && txtFone.getText().isBlank()) {
		    	    JOptionPane.showMessageDialog(null, "Preencha o telefone.");
		    	    SwingUtilities.invokeLater(new Runnable() {
		    	        public void run() {
		    	        	
		    	            txtFone.requestFocus();
		    	        }
		    	    });
		    	    return;
		    	    

		    	} else if (modoInicial.equals("clientes") && txtCPF.getText().isBlank()) {
		    	    JOptionPane.showMessageDialog(null, "Preencha o CPF do cliente.");
		    	    
		    	    SwingUtilities.invokeLater(new Runnable() {
		    	        public void run() {	

		    	        	
		    	            txtCPF.requestFocus();
		    	        }
		    	    });
		    	    return;
		    	    
		    	} else if (modoInicial.equals("clientes") && clienteController.cpfExiste(txtCPF.getText())) {
		    		
		    		JOptionPane.showMessageDialog(null, "CPF já cadastrado");
		    		
		    	    SwingUtilities.invokeLater(new Runnable() {
		    	        public void run() {
		    	        	
		    	            txtCPF.requestFocus();
		    	        }
		    	    });
		    		
		    	} else if (modoInicial.equals("clientes") && !ValidadorCPF.validar(txtCPF.getText())) {
		    	    JOptionPane.showMessageDialog(null, "CPF inválido.");
		    	    
		    	    SwingUtilities.invokeLater(new Runnable() {
		    	        public void run() {
		    	        	

		    	            txtCPF.requestFocus();
		    	        }
		    	    });
		    	    return;
		    	    
		    	   
		    	} else if (modoInicial.equals("clientes") && txtCEP.getText().isBlank()) {
		    	    JOptionPane.showMessageDialog(null, "Preencha o CEP do cliente.");
		    	    
		    	    SwingUtilities.invokeLater(new Runnable() {
		    	        public void run() {	
		    	        
		    	        	
		    	            txtCEP.requestFocus();
		    	        }
		    	    });
		    	    return;
		    	    
		    	} else if (modoInicial.equals("clientes") && txtEndereco.getText().isBlank()) {
		    	        JOptionPane.showMessageDialog(null, "Preencha o endereço.");

		    	       

		    	        SwingUtilities.invokeLater(new Runnable() {
		    	            public void run() {
		    	                txtEndereco.requestFocus();
		    	                txtEndereco.setCaretPosition(txtEndereco.getText().length());
		    	            }
		    	        });
		    	        return;
		    	        
		    	} else if (modoInicial.equals("clientes") && (txtNumero.getText().isBlank() || txtNumero.getText().equals("Nº"))) {
		    	    JOptionPane.showMessageDialog(null, "Coloque o Número da sua casa.");
		    	    
		    	    SwingUtilities.invokeLater(new Runnable() {
		    	        public void run() {
		    	            txtNumero.requestFocus();
		    	        }
		    	    });
		    	    
		    	    return;
		    	
		    	        
		    	} else if (modoInicial.equals("clientes") && (cbComplemento.getSelectedItem() == null || cbComplemento.getSelectedItem().toString().equals("Selecione"))) {
		    	    JOptionPane.showMessageDialog(null, "Selecione um complemento.");

	    	        SwingUtilities.invokeLater(new Runnable() {
	    	            public void run() {
	    	                cbComplemento.requestFocus();
	    	            }
	    	        });
		    	    return;
		    	    
		    	    
		    	} else if (modoInicial.equals("clientes") && txtBairro.getText().isBlank()) {
		    	    JOptionPane.showMessageDialog(null, "Preencha o Bairro do cliente.");
		    	    
	    	      

	    	        SwingUtilities.invokeLater(new Runnable() {
	    	            public void run() {
	    	                txtBairro.requestFocus();
	    	            }
	    	        });
		    	    return;
		    	    
		    	} else if (modoInicial.equals("clientes") && txtCidade.getText().isBlank()) {
		    	    JOptionPane.showMessageDialog(null, "Preencha a Cidade do cliente.");
		    	    
	    	        

	    	        SwingUtilities.invokeLater(new Runnable() {
	    	            public void run() {
	    	                txtCidade.requestFocus();
	    	            }
	    	        });
		    	    return;
		    	    
		    	} else if (modoInicial.equals("clientes") && (cbEstado.getSelectedItem() == null || cbEstado.getSelectedItem().toString().equals("UF"))) {
		    	    JOptionPane.showMessageDialog(null, "Selecione o Estado.");
;

	    	        SwingUtilities.invokeLater(new Runnable() {
	    	            public void run() {
	    	                cbEstado.requestFocus();
	    	            }
	    	        });
		    	    return;
	    	        
		    		
		    	    
		    	} else if (clienteController.buscar(txtNome.getText()) != null) {
		    	    modoInicial.equals("clientes");
		    	    JOptionPane.showMessageDialog(null, "Cliente já cadastrado");
			    		SwingUtilities.invokeLater(new Runnable() {
			    	        public void run() {
			    	        	

			    	        	
			    	        	limparCampos();
		    	        }
		    	    });
		    	    return;
		    	    
		    	    
		    	    
		    		} else if (modoInicial.equals("produtos") && produtoController.codigoBarrasExiste(txtBarCode.getText())) {
		    		
		    		JOptionPane.showMessageDialog(null, "Código de barras já cadastrado");
		    		
		    	    SwingUtilities.invokeLater(new Runnable() {
		    	        public void run() {
		    	        	
		    	            txtBarCode.requestFocus();
		    	        }
		    	    });
		    	    
		    	    
		    		
		    	} else if (modoInicial.equals("produtos") && txtCategoria.getText().isBlank()) {
		    	    JOptionPane.showMessageDialog(null, "Preencha a Catégoria do produto.");
		    	    


	    	        SwingUtilities.invokeLater(new Runnable() {
	    	            public void run() {
	    	                txtCategoria.requestFocus();
	    	            }
	    	        });
		    	    return;
		    	    
		    	    
		    	} else if (modoInicial.equals("produtos") && (cbFornecedor.getSelectedItem() == null || cbFornecedor.getSelectedItem().toString().equals("Selecione"))) {
		    	    JOptionPane.showMessageDialog(null, "Selecione um Fornecedor.");

	    	        SwingUtilities.invokeLater(new Runnable() {
	    	            public void run() {
	    	                cbFornecedor.requestFocus();
	    	            }
	    	        });
		    	    return;
		    	    
		    	    
		    	} else if (modoInicial.equals("produtos") && txtPrecoCusto.getText().isBlank()) {
		    	    JOptionPane.showMessageDialog(null, "Preencha o Custo do produto.");
		    	    

	    	        SwingUtilities.invokeLater(new Runnable() {
	    	            public void run() {
	    	                txtPrecoCusto.requestFocus();
	    	            }
	    	        });
		    	    return;
		    	    
		    	} else if (modoInicial.equals("produtos") && txtPrecoVenda.getText().isBlank()) {
		    	    JOptionPane.showMessageDialog(null, "Preencha o preço de venda do produto.");
		    	    

	    	        SwingUtilities.invokeLater(new Runnable() {
	    	            public void run() {
	    	                txtPrecoVenda.requestFocus();
	    	            }
	    	        });
		    	    return;
		    	    
		    	} else if (modoInicial.equals("produtos") && txtEstoque.getText().isBlank()) {
		    	    JOptionPane.showMessageDialog(null, "Preencha o Estoque atual do produto.");
		    	    
	    	        SwingUtilities.invokeLater(new Runnable() {
	    	            public void run() {
	    	                txtEstoque.requestFocus();
	    	            }
	    	        });
		    	    return;
		    	    
		    	} else if (modoInicial.equals("produtos") && txtEstoqueMinimo.getText().isBlank()) {
		    	    JOptionPane.showMessageDialog(null, "Preencha o Estoque mínimo do produto.");
		    	    


	    	        SwingUtilities.invokeLater(new Runnable() {
	    	            public void run() {
	    	                txtEstoqueMinimo.requestFocus();
	    	            }
	    	        });
		    	    return;
		    	
		    	
		    	} else if (produtoController.buscar(txtNome.getText()) != null) {
		    	    JOptionPane.showMessageDialog(null, "Produto já cadastrado");
			    		SwingUtilities.invokeLater(new Runnable() {
			    	        public void run() {
			    	        	
			    	        	
			    	        	limparCampos();
		    	        }
		    	    });
		    	    return;
		    	    
		    	    
		    	} else if (modoInicial.equals("fornecedores") 
		    	        && !txtSite.getText().isBlank() 
		    	        && !txtSite.getText().endsWith(".com") 
		    	        && !txtSite.getText().endsWith(".com.br")
		    	        && !txtSite.getText().endsWith(".net")
		    	        && !txtSite.getText().endsWith(".org")
		    	        && !txtSite.getText().endsWith(".br")) {

		    	    JOptionPane.showMessageDialog(null, "Site inválido Coloque um domínio válido.");
		    	    txtSite.requestFocus();
		    	    return;
		    	    
		    	    
		    	} else if (controller.buscar(txtNome.getText()) != null) {
		    		modoInicial.equals("fornecedores");
			    		JOptionPane.showMessageDialog(null, "Fornecedor já cadastrado");
			    		SwingUtilities.invokeLater(new Runnable() {
			    	        public void run() {
			    	        	
			    	        	ultimoNomeBuscado = "";
			    	        	limparCampos();
			    	        	
			    	        	
		    	        }
		    	    });
		    	    return;
		    	
		    	    
		    	} else {
		    		

		            try {
		                if (modoInicial.equals("fornecedores")) {
		                	fornecedor.setCnpj(txtCNPJ.getText());
		                    fornecedor.setNome(txtNome.getText());
		                    fornecedor.setFone(txtFone.getText());
		                    fornecedor.setEmail(txtEmail.getText());
		                    fornecedor.setSite(txtSite.getText());
		                    
		                    ultimoNomeBuscado = txtNome.getText().trim();

		                    controller.Adicionar(fornecedor);

		                    JOptionPane.showMessageDialog(null, "Fornecedor adicionado com sucesso.");
		                    
		                    btnFornecedores.doClick();
		                    modoInicial = "fornecedores";
		                    ultimoNomeBuscado = "";

		                } else if (modoInicial.equals("clientes")) {
		                    cliente.setNome(txtNome.getText());
		                    cliente.setFone(txtFone.getText());
		                    cliente.setEmail(txtEmail.getText());
		                    cliente.setCPF(txtCPF.getText());
		                    cliente.setCEP(txtCEP.getText());
		                    cliente.setEndereco(txtEndereco.getText());
		                    cliente.setNumero(txtNumero.getText());
		                    cliente.setComplemento(cbComplemento.getSelectedItem().toString());
		                    cliente.setBairro(txtBairro.getText());
		                    cliente.setCidade(txtCidade.getText());
		                    cliente.setEstado(cbEstado.getSelectedItem().toString());
		                    
		                    ultimoNomeBuscado = txtNome.getText().trim();

		                    clienteController.Adicionar(cliente);

		                    JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso.");
		                    
		                    
		                    btnClientes.doClick();
		                    modoInicial = "clientes";
		                    ultimoNomeBuscado = "";
		                    
		                } else if (modoInicial.equals("produtos")) {
		                	
		                	produto.setCodigoBarras(txtBarCode.getText());
		                	produto.setDescricao(txtNome.getText());
		                	produto.setCategoria(txtCategoria.getText());
		                	produto.setPrecoCusto(Double.parseDouble(txtPrecoCusto.getText().replace(",", ".")));
		                	produto.setPrecoVenda(Double.parseDouble(txtPrecoVenda.getText().replace(",", ".")));
		                	produto.setQuantidade(Integer.parseInt(txtEstoque.getText().replace(".", "")));
		                	produto.setEstoqueMinimo(Integer.parseInt(txtEstoqueMinimo.getText().replace(".", "")));
		                	produto.setIdFornecedor(Integer.parseInt(txtIDExtra.getText()));
		                	
		                    ultimoNomeBuscado = txtBarCode.getText().trim();

		                    boolean sucesso = produtoController.Adicionar(produto);
		                    
		                    if (sucesso == true) {
								
		                    	JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso.");
			                    
			                    btnProdutos.doClick();
			                    modoInicial = "produtos";
			                    ultimoNomeBuscado = "";
		                    	
							} else {

								JOptionPane.showMessageDialog(null, "Não foi possível cadastrar o produto.");
								
							}

		                
		                } else {
		                    JOptionPane.showMessageDialog(null, "Erro: modo de cadastro não identificado.");
		                    return;         
		                }

		                txtNome.requestFocus();
		                limparCampos();

		            } catch (Exception e2) {
		                System.out.println(e2);
		                JOptionPane.showMessageDialog(null, "Erro ao salvar.");
		            }
		        }
				
				

			}
		});
		
		
		
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cardGrande = false;
				btnCardGrande.setIcon(new ImageIcon(Main.class.getResource("/img/left-arrow.png")));
				card = false;
				btnCard.setIcon(new ImageIcon(Main.class.getResource("/img/right-arrow.png")));
				
				
				if (modoInicial.equals("fornecedores") && txtCNPJ.getText().isBlank()) {
		    	    JOptionPane.showMessageDialog(null, "Preencha o CNPJ.");
		    	    SwingUtilities.invokeLater(new Runnable() {
		    	        public void run() {
		    	        	
		    	            txtCNPJ.requestFocus();
		    	        }
		    	    });
		    	    return;
				
				 
				} else if (txtNome.getText().isBlank()) {
		            JOptionPane.showMessageDialog(null, "Preencha o nome.");
		            txtNome.requestFocus();
		            
		            
		        } else if (modoInicial.equals("fornecedores") && txtFone.getText().isBlank()) {
		    	    JOptionPane.showMessageDialog(null, "Preencha o telefone.");
		    	    SwingUtilities.invokeLater(new Runnable() {
		    	        public void run() {
		    	        	
		    	            txtFone.requestFocus();
		    	        }
		    	    });
		    	    return;

		    	} else if (modoInicial.equals("clientes") && txtFone.getText().isBlank()) {
		    	    JOptionPane.showMessageDialog(null, "Preencha o telefone.");
		    	    SwingUtilities.invokeLater(new Runnable() {
		    	        public void run() {
		    	        	
		    	            txtFone.requestFocus();
		    	        }
		    	    });
		    	    return;
		            
		        } else if (modoInicial.equals("clientes") && !ValidadorCPF.validar(txtCPF.getText())) {
		            JOptionPane.showMessageDialog(null, "CPF inválido.");
		            txtCPF.requestFocus();
		            return;

			    	   
			    	} else if (modoInicial.equals("clientes") && txtCEP.getText().isBlank()) {
			    	    JOptionPane.showMessageDialog(null, "Preencha o CEP do cliente.");
			    	    
			    	    SwingUtilities.invokeLater(new Runnable() {
			    	        public void run() {	
			    	            txtCEP.requestFocus();
			    	        }
			    	    });
			    	    return;
			    	    
			    	} else if (modoInicial.equals("clientes") && txtEndereco.getText().isBlank()) {
			    	        JOptionPane.showMessageDialog(null, "Preencha o endereço.");


			    	        SwingUtilities.invokeLater(new Runnable() {
			    	            public void run() {
			    	                txtEndereco.requestFocus();
			    	            }
			    	        });
			    	        return;
			    	    
			    	    
			    	} else if (modoInicial.equals("clientes") && txtBairro.getText().isBlank()) {
			    	    JOptionPane.showMessageDialog(null, "Preencha o Bairro do cliente.");
			    	    


		    	        SwingUtilities.invokeLater(new Runnable() {
		    	            public void run() {
		    	                txtBairro.requestFocus();
		    	            }
		    	        });
			    	    return;
			    	    
			    	} else if (modoInicial.equals("clientes") && txtCidade.getText().isBlank()) {
			    	    JOptionPane.showMessageDialog(null, "Preencha a Cidade do cliente.");
			    	    

		    	        SwingUtilities.invokeLater(new Runnable() {
		    	            public void run() {
		    	                txtCidade.requestFocus();
		    	            }
		    	        });
			    	    return;
			    	    
			    	} else if (modoInicial.equals("clientes") && (cbEstado.getSelectedItem() == null || cbEstado.getSelectedItem().toString().equals("UF"))) {
			    	    JOptionPane.showMessageDialog(null, "Selecione o Estado.");

		    	        SwingUtilities.invokeLater(new Runnable() {
		    	            public void run() {
		    	                cbEstado.requestFocus();
		    	            }
		    	        });
			    	    return;
			    	    
			    	    
		            
		    	} else if (modoInicial.equals("fornecedores") 
		    	        && !txtSite.getText().isBlank() 
		    	        && !txtSite.getText().endsWith(".com") 
		    	        && !txtSite.getText().endsWith(".com.br")
		    	        && !txtSite.getText().endsWith(".net")
		    	        && !txtSite.getText().endsWith(".org")
		    	        && !txtSite.getText().endsWith(".br")) {

		    	    JOptionPane.showMessageDialog(null, "Site inválido, Coloque um domínio válido.");
		    	    txtSite.requestFocus();
		    	    return;
		    	    
		    	    
		    	} else if (modoInicial.equals("produtos") && txtBarCode.getText().isBlank()) {
		    	    JOptionPane.showMessageDialog(null, "Preencha o Código de Barras do produto.");


	    	        SwingUtilities.invokeLater(new Runnable() {
	    	            public void run() {
	    	                txtBarCode.requestFocus();
	    	            }
	    	        });
		    	    return;
		    	    
		    	    
		    		
		    	} else if (modoInicial.equals("produtos") && txtCategoria.getText().isBlank()) {
		    	    JOptionPane.showMessageDialog(null, "Preencha a Catégoria do produto.");
		    	    


	    	        SwingUtilities.invokeLater(new Runnable() {
	    	            public void run() {
	    	                txtCategoria.requestFocus();
	    	            }
	    	        });
		    	    return;
		    	    
		    	    
		    	} else if (modoInicial.equals("produtos") && (cbFornecedor.getSelectedItem() == null || cbFornecedor.getSelectedItem().toString().equals("Selecione"))) {
		    	    JOptionPane.showMessageDialog(null, "Selecione um Fornecedor.");

	    	        SwingUtilities.invokeLater(new Runnable() {
	    	            public void run() {
	    	                cbFornecedor.requestFocus();
	    	            }
	    	        });
		    	    return;
		    	    
		    	} else if (modoInicial.equals("produtos") && txtPrecoCusto.getText().isBlank()) {
		    	    JOptionPane.showMessageDialog(null, "Preencha o Custo do produto.");
		    	    

	    	        SwingUtilities.invokeLater(new Runnable() {
	    	            public void run() {
	    	                txtPrecoCusto.requestFocus();
	    	            }
	    	        });
		    	    return;
		    	    
		    	} else if (modoInicial.equals("produtos") && txtPrecoVenda.getText().isBlank()) {
		    	    JOptionPane.showMessageDialog(null, "Preencha o preço de venda do produto.");
		    	    

	    	        SwingUtilities.invokeLater(new Runnable() {
	    	            public void run() {
	    	                txtPrecoVenda.requestFocus();
	    	            }
	    	        });
		    	    return;
		    	    
		    	} else if (modoInicial.equals("produtos") && txtEstoque.getText().isBlank()) {
		    	    JOptionPane.showMessageDialog(null, "Preencha o Estoque atual do produto.");
		    	    
	    	        SwingUtilities.invokeLater(new Runnable() {
	    	            public void run() {
	    	                txtEstoque.requestFocus();
	    	            }
	    	        });
		    	    return;
		    	    
		    	} else if (modoInicial.equals("produtos") && txtEstoqueMinimo.getText().isBlank()) {
		    	    JOptionPane.showMessageDialog(null, "Preencha o Estoque mínimo do produto.");
		    	    


	    	        SwingUtilities.invokeLater(new Runnable() {
	    	            public void run() {
	    	                txtEstoqueMinimo.requestFocus();
	    	            }
	    	        });
		    	    return;
		    	    
		            
		            
		        } else {

		            try {
		                if (modoInicial.equals("fornecedores")) {
		                	fornecedor.setCnpj(txtCNPJ.getText());
		                    fornecedor.setNome(txtNome.getText());
		                    fornecedor.setFone(txtFone.getText());
		                    fornecedor.setEmail(txtEmail.getText());
		                    fornecedor.setSite(txtSite.getText());
		                    fornecedor.setIdFornecedor(Integer.parseInt(txtID.getText()));

		                    controller.Editar(fornecedor);

		                    JOptionPane.showMessageDialog(null, "Fornecedor editado com sucesso.");
		                    
		                    btnFornecedores.doClick();

		                } else if (modoInicial.equals("clientes")) {
		                	 cliente = new Cliente();
		                    cliente.setNome(txtNome.getText());
		                    cliente.setFone(txtFone.getText());
		                    cliente.setEmail(txtEmail.getText());
		                    cliente.setCPF(txtCPF.getText());
		                    cliente.setCEP(txtCEP.getText());
		                    cliente.setEndereco(txtEndereco.getText());
		                    cliente.setNumero(txtNumero.getText());
		                    cliente.setComplemento(cbComplemento.getSelectedItem().toString());
		                    cliente.setBairro(txtBairro.getText());
		                    cliente.setCidade(txtCidade.getText());
		                    cliente.setEstado(cbEstado.getSelectedItem().toString());
		                    cliente.setIdCliente(Integer.parseInt(txtID.getText()));

		                    clienteController.Editar(cliente);

		                    JOptionPane.showMessageDialog(null, "Cliente editado com sucesso.");
		                    btnClientes.doClick();
		                    
		                    
		                } else if (modoInicial.equals("produtos")) {
		                	
		                	produto.setCodigoBarras(txtBarCode.getText());
		                	produto.setDescricao(txtNome.getText());
		                	produto.setCategoria(txtCategoria.getText());
		                	produto.setPrecoCusto(Double.parseDouble(txtPrecoCusto.getText().replace(",", ".")));
		                	produto.setPrecoVenda(Double.parseDouble(txtPrecoVenda.getText().replace(",", ".")));
		                	produto.setQuantidade(Integer.parseInt(txtEstoque.getText().replace(".", "")));
		                	produto.setEstoqueMinimo(Integer.parseInt(txtEstoqueMinimo.getText().replace(".", "")));
		                	produto.setIdFornecedor(Integer.parseInt(txtIDExtra.getText()));
		                	
		                	produtoController.Editar(produto);

		                    JOptionPane.showMessageDialog(null, "Produto editado com sucesso.");
		                    
		                    btnProdutos.doClick();
		                	
		                }

		                txtNome.requestFocus();
		                limparCampos();

		            } catch (Exception e2) {
		                System.out.println(e2);
		                JOptionPane.showMessageDialog(null, "Erro ao editar.");
		            }
		        }

			}
		});

		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cardGrande = false;
				btnCardGrande.setIcon(new ImageIcon(Main.class.getResource("/img/left-arrow.png")));
				card = false;
				btnCard.setIcon(new ImageIcon(Main.class.getResource("/img/right-arrow.png")));
				
				modo = "excluir";
				
				
		        if (txtID.getText().isBlank()) {
		            JOptionPane.showMessageDialog(null, "Preencha o ID.");
		            txtNome.requestFocus();
		        } else {

		            String nomeExibicao = txtNome.getText();

		            int resposta = JOptionPane.showConfirmDialog(
		                    null,
		                    "Tem certeza que deseja excluir \"" + nomeExibicao + "\"?",
		                    "Confirmar exclusão",
		                    JOptionPane.YES_NO_OPTION,
		                    JOptionPane.WARNING_MESSAGE
		            );

		            if (resposta == JOptionPane.YES_OPTION) {

		                try {
		                    int id = Integer.parseInt(txtID.getText());
		                    boolean sucesso = false;

		                    if (modoInicial.equals("fornecedores")) {
		                    	
		                    	int qtdProdutos = produtoController.fornecedorVinculado(Integer.parseInt(txtID.getText()));

		                        if (qtdProdutos > 0) {
		                            JOptionPane.showMessageDialog(null,
		                                    "Não é possível excluir este fornecedor.\n"
		                                    + "Ele está vinculado a " + qtdProdutos + (qtdProdutos == 1 ? " produto." : " produtos."),
		                                    "Fornecedor vinculado",
		                                    JOptionPane.ERROR_MESSAGE);
		                            return;
		                        }
		                        sucesso = controller.Excluir(id);

		                        if (sucesso) {
		                            JOptionPane.showMessageDialog(null, "Fornecedor excluído com sucesso.");
		                            
		                            btnFornecedores.doClick();
		                            
		                        } else {
		                            JOptionPane.showMessageDialog(null, "Não foi possível excluir. Fornecedor não encontrado.");
		                        }

		                    } else if (modoInicial.equals("clientes")) {
		                        sucesso = clienteController.Excluir(id);

		                        if (sucesso) {
		                            JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso.");
		                            
		                            btnClientes.doClick();
		                            
		                        } else {
		                            JOptionPane.showMessageDialog(null, "Não foi possível excluir. Cliente não encontrado.");
		                        }
		                        
		                        
		                        
		                    } else if (modoInicial.equals("produtos")) {
		                        sucesso = produtoController.Excluir(id);

		                        if (sucesso) {
		                            JOptionPane.showMessageDialog(null, "Produto excluído com sucesso.");
		                            
		                            btnProdutos.doClick();
		                            
		                        } else {
		                            JOptionPane.showMessageDialog(null, "Não foi possível excluir. Produto não encontrado.");
		                        }
		                    }
		                    

		                    if (sucesso) {
		                        limparCampos();
		                    }

		                } catch (NumberFormatException ex) {
		                    JOptionPane.showMessageDialog(null, "ID inválido.");
		                }
		            }
		        }
				


			}
		});
		btnSearch.addActionListener(new ActionListener() {
			

				public void actionPerformed(ActionEvent e) {
				
				modo = "buscar";
				btnSearch.setVisible(true);
				
				        
				       
				      
			if (modoInicial.equals("fornecedores") && !mudar && txtNome.getText().isBlank()) {
			    JOptionPane.showMessageDialog(null, "Informe o fornecedor.");
			    txtNome.requestFocus();

			} else if (modoInicial.equals("fornecedores") && mudar && txtCNPJ.getText().isBlank()) {
			    JOptionPane.showMessageDialog(null, "Informe o CNPJ.");
			    txtCNPJ.requestFocus();
					
				} else if (modoInicial.equals("clientes") && !mudar && txtNome.getText().isBlank()) {
				    JOptionPane.showMessageDialog(null, "Informe o nome.");
				    txtNome.requestFocus();

				} else if (modoInicial.equals("clientes") && mudar && txtCPF.getText().isBlank()) {
				    JOptionPane.showMessageDialog(null, "Informe o CPF.");
				    txtCPF.requestFocus();
					
				} else if (modoInicial.equals("produtos") && !mudar && txtNome.getText().isBlank()) {
				    JOptionPane.showMessageDialog(null, "Informe o produto.");
				    txtNome.requestFocus();

				} else if (modoInicial.equals("produtos") && mudar && txtBarCode.getText().isBlank()) {
				    JOptionPane.showMessageDialog(null, "Informe o produto.");
				    txtBarCode.requestFocus();

        } else {

            try {
                String nome = txtNome.getText();

                if (modoInicial.equals("fornecedores")) {
                	
                	if (!mudar) {
                		fornecedor = controller.buscar(nome);
                	} else {
                	    fornecedor = controller.buscarCNPJ(txtCNPJ.getText());
                	}

                    

                    if (fornecedor != null) {
                        txtID.setText(String.valueOf(fornecedor.getIdFornecedor()));
                        txtCNPJ.setText(fornecedor.getCnpj());
                        
                        ignorarEventoSugestao = true;
                        txtNome.setText(fornecedor.getNome());
                        ignorarEventoSugestao = false;
                        
                        txtFone.setText(fornecedor.getFone());
                        txtEmail.setText(fornecedor.getEmail());
                        txtSite.setText(fornecedor.getSite());

                        ultimoNomeBuscado = txtNome.getText().trim();
                        ultimoCNPJBuscado = txtCNPJ.getText().trim();
                        
                        txtCNPJ.setVisible(true);
                        txtNome.setVisible(true);
                        txtFone.setVisible(true);
                        txtEmail.setVisible(true);
                        txtSite.setVisible(true);
                        
                        lblCNPJ.setVisible(true);
                        lblNome.setVisible(true);
                        lblNome.setVisible(true);
                        lblFone.setVisible(true);
                        lblEmail.setVisible(true);
                        lblSite.setVisible(true);
                        btnSearchSite.setVisible(true);
                        
                        lblDivisor.setVisible(true);
                        lblDivisor.setBounds(10, 178, 685, 28);
                        
                        btnMudar.setVisible(false);
                        
                        SwingUtilities.invokeLater(new Runnable() {
    		    	        public void run() {
    		    	        	
    		    	        	btnSearch.setVisible(true);
    		    	        	btnSearchAlt.setVisible(true);
    		    	        	btnSearch.setBounds(540, 63, 25, 32);
    		    	        	btnSearchAlt.setBounds(540, 21, 25, 32);
    		    	        	
    		    	        	if (!buscouPorCNPJ) {
    		                	    txtNome.requestFocus();
    		                	    
    		                	} else {
    		                	    txtCNPJ.requestFocus();
    		                	    
    		                	}
    		    	        	
    	    	        }
    	    	    });
                        
                        
                        txtCNPJ.setBounds(198, 21, 342, 32);
                        lblCNPJ.setBounds(83, 21, 115, 32);

                        txtNome.setBounds(198, 63, 342, 32);
                        lblNome.setBounds(65, 63, 130, 32);
                        lblNome.setText("Fornecedor: ");
                        btnSearch.setBounds(540, 63, 25, 32);
                        
                        
                        lblFone.setBounds(83, 105, 115, 32);
                        txtFone.setBounds(198, 105, 342, 32);
                        
                        lblEmail.setBounds(83, 147, 115, 32);
                        txtEmail.setBounds(198, 147, 342, 32);
                        
                        txtSite.setBounds(198, 190, 342, 32);
                        lblSite.setBounds(83, 190, 115, 32);
                        btnSearchSite.setBounds(540, 190, 25, 32);
                        
                        
                        lblDivisor.setBounds(10, 220, 685, 28);


                        
                        btnEditar.setVisible(true);
                        btnRelatorio.setVisible(true);
                        btnExcluir.setVisible(true);
                        
                        mudar = false;

                    } else {
                    	String textoMensagem = buscouPorCNPJ
                    		    ? "CNPJ não cadastrado, deseja cadastrar um novo cliente?"
                    		    : "Fornecedor não cadastrado, deseja cadastrar?";

                    		int resposta = JOptionPane.showConfirmDialog(null, textoMensagem, "Mensagem", JOptionPane.YES_NO_OPTION);

        				if (resposta == 0) {
        					
        					modo = "adicionar";
        					limparCamposAlt();
        					
        					if (buscouPorCNPJ == true) {
        					    txtNome.setText(null);
        					} else if (buscouPorCNPJ == false){
        					    txtCNPJ.setText(null);
        					}
        				
        				btnAdicionar.setVisible(true);

        				btnRelatorio.setVisible(false);
        				btnEditar.setVisible(false);
        				btnExcluir.setVisible(false);
        				
        				btnCEP.setVisible(false);
        				
        			
        				
        				if (modo.equals("adicionar") && modoInicial.equals("fornecedores")) {
        					
        				 ultimoNomeBuscado = txtNome.getText().trim();
        				 ultimoCNPJBuscado = txtNome.getText().trim();
        				
        				 

        				lblNome.setVisible(true);
        				lblFone.setVisible(true);
        				lblEmail.setVisible(true);
        				lblSite.setVisible(true);
        				lblCNPJ.setVisible(true);
        				lblCPF.setVisible(true);
        				lblCPF.setVisible(false);
        				
        				btnSearchSite.setVisible(true);
        				txtNome.setVisible(true);
        				txtFone.setVisible(true);
        				txtEmail.setVisible(true);
        				txtSite.setVisible(true);
        				txtCNPJ.setVisible(true);
        				txtCPF.setVisible(false);
        				
        				btnCEP.setVisible(false);
        				btnMudar.setVisible(false);
        				btnSearch.setVisible(false);
        				btnSearchAlt.setVisible(false);
        				
        				lblDivisor.setVisible(true);
        				
        				txtCNPJ.setBounds(198, 21, 342, 32);
                        lblCNPJ.setBounds(83, 21, 115, 32);

                        txtNome.setBounds(198, 63, 342, 32);
                        lblNome.setBounds(65, 63, 130, 32);
                        btnSearch.setBounds(540, 63, 25, 32);
                        
                        
                        lblFone.setBounds(83, 105, 115, 32);
                        txtFone.setBounds(198, 105, 342, 32);
                        
                        lblEmail.setBounds(83, 147, 115, 32);
                        txtEmail.setBounds(198, 147, 342, 32);
                        
                        txtSite.setBounds(198, 190, 342, 32);
                        lblSite.setBounds(83, 190, 115, 32);
                        btnSearchSite.setBounds(540, 190, 25, 32);
                        
                        lblDivisor.setBounds(10, 220, 685, 28);
        				
        				
                        txtID.setEditable(false);
                        txtNome.setEditable(true);
                        txtFone.setEditable(true);
                        txtEmail.setEditable(true);
                        txtSite.setEditable(true);
                        
        				
                        
                        if (fornecedor == null) {
                            fornecedor = new Fornecedor();

                            if (mudar) {

                                String nomeDigitado = txtNome.getText();
                                fornecedor.setNome(nomeDigitado);
                                txtNome.setText(nomeDigitado);

                                SwingUtilities.invokeLater(new Runnable() {
                                    public void run() {
                                        txtCNPJ.requestFocus();
                                    }
                                });

                            } else {


                                String cnpjDigitado = txtCNPJ.getText();
                                fornecedor.setCnpj(cnpjDigitado);
                                txtCNPJ.setText(cnpjDigitado);

                                SwingUtilities.invokeLater(new Runnable() {
                                    public void run() {
                                        txtCNPJ.requestFocus();
                                    }
                                });

                            }

                            return;
                        }
        					}
        				} else if (resposta == 1){

        					if (buscouPorCNPJ) {
        			            ultimoCNPJBuscado = txtCNPJ.getText().trim();
        			        } else {
        			            ultimoNomeBuscado = txtNome.getText().trim();
        			        }
        					
        					SwingUtilities.invokeLater(new Runnable() {
        			            public void run() {
        			                if (buscouPorCNPJ) {
        			                    txtNome.requestFocus(); 
        			                } else {
        			                    txtFone.requestFocus(); 
        			                }
        			            }
        			        });
        					
        				}
                    }
                    
                } else if (modoInicial.equals("clientes")) {
                	
                	if (!mudar) {
                		cliente = clienteController.buscar(nome);
                	} else {
                	    cliente = clienteController.buscarCPF(txtCPF.getText());
                	}

                    

                    if (cliente != null) {
                        txtID.setText(String.valueOf(cliente.getIdCliente()));
                        
                        ignorarEventoSugestao = true;
                        txtNome.setText(cliente.getNome());
                        ignorarEventoSugestao = false;
                        
                        txtFone.setText(cliente.getFone());
                        txtEmail.setText(cliente.getEmail());
                        txtCPF.setText(cliente.getCPF());
                        txtCEP.setText(cliente.getCEP());
                        txtEndereco.setText(cliente.getEndereco());
                        txtNumero.setText(cliente.getNumero());
                        cbComplemento.setSelectedItem(cliente.getComplemento());
                        txtBairro.setText(cliente.getBairro());
                        txtCidade.setText(cliente.getCidade());
                        cbEstado.setSelectedItem(cliente.getEstado());
                        
                        ultimoNomeBuscado = txtNome.getText().trim();
                        ultimoCEPBuscado = txtCEP.getText().trim();
                        ultimoCPFBuscado = txtCPF.getText().trim();
                        

                        txtNome.setVisible(true);
                        txtFone.setVisible(true);
                        txtEmail.setVisible(true);
                        txtCPF.setVisible(true);
                        txtCEP.setVisible(true);
                        txtEndereco.setVisible(true);
                        txtBairro .setVisible(true);
                        txtCidade.setVisible(true);
                        cbEstado.setVisible(true);
                        cbComplemento.setVisible(true);
                        txtNumero.setVisible(true);
                        

                        lblNome.setVisible(true);
                        lblFone.setVisible(true);
                        lblEmail.setVisible(true);
                        lblCPF.setVisible(true);
                        lblCEP.setVisible(true);
                        lblEndereco.setVisible(true);
                        lblBairro.setVisible(true);
                        lblCidade.setVisible(true);
                        btnSearchSite.setVisible(false);
                        btnMudar.setVisible(false);
						lblComplemento.setVisible(true);
                        
                        lblDivisor.setVisible(false);
                        lblDivisor.setBounds(10, 178, 685, 28);
                        
                        txtFone.setBounds(198, 63, 342, 32);
                        lblFone.setBounds(83, 63, 115, 32);
                        
                        lblEmail.setBounds(83, 105, 115, 32);
                        txtEmail.setBounds(198, 105, 342, 32);
                        
                        SwingUtilities.invokeLater(new Runnable() {
    		    	        public void run() {

    		    	        	btnSearch.setVisible(true);
    		    	        	btnSearchAlt.setVisible(true);
    		    	        	btnSearchAlt.setBounds(540, 147, 25, 32);
    		    	        	btnSearch.setBounds(540, 21, 25, 32);
    		    	        	
    		    	        	if (!buscouPorCPF) {
    		                	    txtNome.requestFocus();
    		                	    
    		                	} else {
    		                	    txtCPF.requestFocus();
    		                	    
    		                	}
    		    	        	
    	    	        }
    	    	    });

                        txtNome.setBounds(198, 21, 342, 32);
                        lblNome.setBounds(83, 21, 115, 32);
                        lblNome.setText("Nome: ");
                        btnSearch.setBounds(540, 21, 25, 32);

                        txtCPF.setBounds(198, 147, 342, 32);
                        lblCPF.setBounds(83, 147, 115, 32);
                        
                        btnEditar.setVisible(true);
                        btnRelatorio.setVisible(true);
                        btnExcluir.setVisible(true);
                        
                        btnCEP.setVisible(true);
                        
                        mudar = false;

                    } else {
                    	String textoMensagem = buscouPorCPF
                    		    ? "CPF não cadastrado, deseja cadastrar um novo fornecedor?"
                    		    : "Nome de cliente não cadastrado, deseja cadastrar?";

                    		int resposta = JOptionPane.showConfirmDialog(null, textoMensagem, "Mensagem", JOptionPane.YES_NO_OPTION);
        				if (resposta == 0) {
        					

        					
        					modo = "adicionar";
        					ultimoCEPBuscado = "";
        					limparCamposAlt();
        					
        					if (buscouPorCPF == true) {
        					    txtNome.setText(null);
        					} else if (buscouPorCPF == false){
        					    txtCPF.setText(null);
        					}
        					

            				btnAdicionar.setVisible(true);
            				
            				btnRelatorio.setVisible(false);
            				btnEditar.setVisible(false);
            				btnExcluir.setVisible(false);
        					
        					if (modo.equals("adicionar") && modoInicial.equals("clientes")) {
        						
        						ultimoNomeBuscado = txtNome.getText().trim();
        						ultimoCPFBuscado = txtCPF.getText().trim();
        						ultimoCEPBuscado = "";
            					
            					
                				lblSite.setVisible(false);
                				txtSite.setVisible(false);
                				btnSearchSite.setVisible(false);
                				btnMudar.setVisible(false);
                				
                				lblFone.setVisible(true);
                				lblEmail.setVisible(true);
                				lblCPF.setVisible(true);
    							lblComplemento.setVisible(true);
                				
                				txtNome.setVisible(true);
                				txtFone.setVisible(true);
                				txtEmail.setVisible(true);
                				txtCPF.setVisible(true);
                				cbComplemento.setVisible(true);
                				cbComplemento.setSelectedIndex(0);
                				txtNumero.setVisible(true);     
                				txtNumero.setText("Nº");

                				txtNome.setBounds(198, 21, 342, 32);
                				lblNome.setBounds(83, 21, 115, 32);
                				
                				txtCPF.setBounds(198, 147, 342, 32);
                                lblCPF.setBounds(83, 147, 115, 32);
                                
                                txtFone.setBounds(198, 63, 342, 32);
                                lblFone.setBounds(83, 63, 115, 32);
                                
                                lblEmail.setBounds(83, 105, 115, 32);
                                txtEmail.setBounds(198, 105, 342, 32);
                                
                				btnSearch.setVisible(false);
                				btnSearchAlt.setVisible(false);
                				
                				 txtNome.setVisible(true);
                                 txtFone.setVisible(true);
                                 txtEmail.setVisible(true);
                                 txtCPF.setVisible(true);
                                 txtCEP.setVisible(true);
                                 txtEndereco.setVisible(true);
                                 txtBairro .setVisible(true);
                                 txtCidade.setVisible(true);
                                 cbEstado.setVisible(true);
                                 

                                 lblNome.setVisible(true);
                                 lblFone.setVisible(true);
                                 lblEmail.setVisible(true);
                                 lblCPF.setVisible(true);
                                 lblCEP.setVisible(true);
                                 lblEndereco.setVisible(true);
                                 lblBairro.setVisible(true);
                                 lblCidade.setVisible(true);
                                 btnSearchSite.setVisible(false);
                                 btnMudar.setVisible(false);
                                 
                                 btnCEP.setVisible(true);
                                 
                                 lblDivisor.setVisible(false);
                                 lblDivisor.setBounds(10, 178, 685, 28);
                				


                				btnAdicionar.setVisible(true);
                				
                				
                				}

                				panelTabela.setVisible(true);
                				panelCadastro.setVisible(true);
                				txtNome.requestFocus();
                				
                				if (cliente == null) {
                				    cliente = new Cliente();

                				    if (mudar) {

                				        String cpfDigitado = txtCPF.getText();
                				        cliente.setCPF(cpfDigitado);
                				        txtCPF.setText(cpfDigitado);

                				        SwingUtilities.invokeLater(new Runnable() {
                				            public void run() {
                				                txtNome.requestFocus();
                				            }
                				        });

                				    } else {

                				        String nomeDigitado = txtNome.getText();
                				        cliente.setNome(nomeDigitado);
                				        txtNome.setText(nomeDigitado);

                				        SwingUtilities.invokeLater(new Runnable() {
                				            public void run() {
                				                txtFone.requestFocus();
                				            }
                				        });

                				    }

                				    return;
                				}
            					
            					
                				
        				} else if (resposta == 1){
        					
        					if (buscouPorCPF) {
        			            ultimoCPFBuscado = txtCPF.getText().trim();
        			        } else {
        			            ultimoNomeBuscado = txtNome.getText().trim();
        			        }
        					
        					SwingUtilities.invokeLater(new Runnable() {
        			            public void run() {
        			                if (buscouPorCPF) {
        			                    txtCEP.requestFocus(); 
        			                } else {
        			                    txtFone.requestFocus(); 
        			                }
        			            }
        			        });
        					
        				}
                    }
                    


                } else if (modoInicial.equals("produtos")) {


                	nome = txtNome.getText().trim();

                	if (!mudar) {
                	    produto = produtoController.buscar(nome);
                	} else {
                	    produto = produtoController.buscarCodigoBarras(txtBarCode.getText());
                	}
					

                    if (produto != null) {
                    	txtID.setText(String.valueOf(produto.getIdProduto()));
                        txtBarCode.setText(produto.getCodigoBarras());
                        
                        
                        ignorarEventoSugestao = true;
                        txtNome.setText(produto.getDescricao());
                        ignorarEventoSugestao = false;
                        
                        
                        txtCategoria.setText(produto.getCategoria());
                        txtIDExtra.setText(String.valueOf(produto.getIdFornecedor()));
                        txtPrecoCusto.setText(String.format("%.2f", produto.getPrecoCusto()));
                        txtPrecoVenda.setText(String.format("%.2f", produto.getPrecoVenda()));
                        txtEstoque.setText(String.valueOf(produto.getQuantidade()));
                        txtEstoqueMinimo.setText(String.valueOf(produto.getEstoqueMinimo()));

                        ultimoBarCodeBuscado = txtBarCode.getText().trim();
                        ultimoNomeBuscado = txtNome.getText().trim();
                        
                        int idFornecedorProduto = produto.getIdFornecedor();

    					for (int i = 0; i < cbFornecedor.getItemCount(); i++) {
    					    String item = cbFornecedor.getItemAt(i);

    					    if (item.startsWith(idFornecedorProduto + " - ")) {
    					        cbFornecedor.setSelectedItem(item);
    					        break;
    					    }
    					}
    					
                        
                	
					lblBarCode.setVisible(true);
					lblNome.setVisible(true);
					lblCategoria.setVisible(true);
					lblFornecedor.setVisible(true);
					lblPrecoVenda.setVisible(true);
					lblPrecoCusto.setVisible(true);
					lblEstoque.setVisible(true);
					lblEstoqueMinimo.setVisible(true);
					
					txtBarCode.setVisible(true);
					txtNome.setVisible(true);
					txtCategoria.setVisible(true);
					cbFornecedor.setVisible(true);
					txtPrecoVenda.setVisible(true);
					txtPrecoCusto.setVisible(true);
					txtEstoque.setVisible(true);
					txtEstoqueMinimo.setVisible(true);
					
					lblDivisor.setVisible(true);
					lblDivisor.setBounds(10, 178, 685, 28);

					
					txtSite.setVisible(false);
					btnMudar.setVisible(false);
					
                    SwingUtilities.invokeLater(new Runnable() {
		    	        public void run() {

		    	        	btnSearch.setVisible(true);
		    	        	btnSearchAlt.setVisible(true);
		    	        	btnSearch.setBounds(540, 63, 25, 32);
		    	        	btnSearchAlt.setBounds(540, 21, 25, 32);
		    	        	
		    	        	if (!buscouPorBarCode) {
		                	    txtNome.requestFocus();
		                	    
		                	} else {
		                	    txtCNPJ.requestFocus();
		                	    
		                	}
		    	        	
	    	        }
	    	    });

                    txtNome.setBounds(198, 63, 342, 32);
                    lblNome.setBounds(10, 63, 188, 32);
                    lblNome.setText("Produto: ");
                    
                   
                    lblBarCode.setBounds(10, 21, 188, 32);
                    txtBarCode.setBounds(198, 21, 342, 32);
                    
                    
                    btnEditar.setVisible(true);
                    btnRelatorio.setVisible(true);
                    btnExcluir.setVisible(true);
                    
                    btnCEP.setVisible(false);
                    
                    mudar = false;

					
                    } else {
                    	String textoMensagem = buscouPorBarCode
                    		    ? "Código de barras não cadastrado, deseja cadastrar um novo produto?"
                    		    : "Produto não cadastrado, deseja cadastrar?";

                    		int resposta = JOptionPane.showConfirmDialog(null, textoMensagem, "Mensagem", JOptionPane.YES_NO_OPTION);
        				if (resposta == 0) {
        					
        					modo = "adicionar";
        					limparCamposAlt();
        					
        					if (buscouPorBarCode == true) {
        					    txtNome.setText(null);
        					} else if (buscouPorBarCode == false) {
        					    txtBarCode.setText(null);
        					}

            				btnAdicionar.setVisible(false);
            				

            				btnRelatorio.setVisible(false);
            				btnEditar.setVisible(false);
            				btnExcluir.setVisible(false);
        					
        					if (modo.equals("adicionar") && modoInicial.equals("produtos")) {
        						
        						ultimoNomeBuscado = txtNome.getText().trim();
        						ultimoBarCodeBuscado = txtBarCode.getText().trim();
            					carregarFornecedores();
            					
                				lblSite.setVisible(false);
                				txtSite.setVisible(false);
                				btnSearchSite.setVisible(false);
                				btnCEP.setVisible(false);
                				btnMudar.setVisible(false);
                				
                				txtFone.setVisible(false);
                				
            					lblBarCode.setVisible(true);
            					lblNome.setVisible(true);
            					lblCategoria.setVisible(true);
            					lblFornecedor.setVisible(true);
            					lblPrecoVenda.setVisible(true);
            					lblPrecoCusto.setVisible(true);
            					lblEstoque.setVisible(true);
            					lblEstoqueMinimo.setVisible(true);
            					
            					txtBarCode.setVisible(true);
            					txtNome.setVisible(true);
            					txtCategoria.setVisible(true);
            					cbFornecedor.setVisible(true);
            					txtPrecoVenda.setVisible(true);
            					txtPrecoCusto.setVisible(true);
            					txtEstoque.setVisible(true);
            					txtEstoqueMinimo.setVisible(true);
            					
            					lblDivisor.setVisible(true);
            					lblDivisor.setBounds(10, 178, 685, 28);

            					 txtNome.setBounds(198, 63, 342, 32);
            	                 lblNome.setBounds(10, 63, 188, 32);
            	                 lblNome.setText("Produto: ");
            	                    
            	                 lblBarCode.setText("Código de Barras: ");
            	                 lblBarCode.setBounds(10, 21, 188, 32);
            	                 txtBarCode.setBounds(198, 21, 342, 32);
            	                 btnSearch.setBounds(540, 21, 25, 32);
                				
                				
                				btnSearch.setVisible(false);
                				btnSearchAlt.setVisible(false);
                				btnMudar.setVisible(false);
                				

                                txtID.setEditable(false);
                                txtNome.setEditable(true);
                                txtCategoria.setEditable(true);
                                cbFornecedor.setEditable(true);
                				


                				btnAdicionar.setVisible(true);
                				
                				
                				}

                				panelTabela.setVisible(true);
                				panelCadastro.setVisible(true);
                				txtBarCode.requestFocus();
                				
                				if (produto == null) {
                				    produto = new Produto();

                				    if (mudar) {
                				    	
                				        String codigoDigitado = txtBarCode.getText();
                				        produto.setCodigoBarras(codigoDigitado);
                				        txtBarCode.setText(codigoDigitado);

                				        SwingUtilities.invokeLater(new Runnable() {
                				            public void run() {

                				            	if (buscouPorBarCode == true) {
                	        					    txtNome.requestFocus();
                	        					} else if (buscouPorBarCode == false) {
                	        					    txtBarCode.requestFocus();
                	        					}
                				            	
                				            }
                				        });
                				        
                				    } else {
                				        
                				        String nomeDigitado = txtNome.getText();
                				        produto.setDescricao(nomeDigitado);
                				        txtNome.setText(nomeDigitado);

                				        SwingUtilities.invokeLater(new Runnable() {
                				            public void run() {
                				                txtBarCode.requestFocus();
                				            }
                				        });

                				    	
                				    }

                				    return;
                				}
                				
        				} else if (resposta == 1){
        					
        					if (buscouPorBarCode) {
        			            ultimoBarCodeBuscado = txtBarCode.getText().trim();
        			        } else {
        			            ultimoNomeBuscado = txtNome.getText().trim();
        			        }
        					
        					SwingUtilities.invokeLater(new Runnable() {
        			            public void run() {
        			                if (buscouPorBarCode) {
        			                    txtNome.requestFocus(); 
        			                } else {
        			                    txtCategoria.requestFocus(); 
        			                }
        			            }
        			        });
        				}
                    }
                    
                } else if (modoInicial.equals("vendas")) {

                    fornecedor = controller.buscar(nome);

                    if (fornecedor != null) {
                        txtID.setText(String.valueOf(fornecedor.getIdFornecedor()));
                        txtNome.setText(fornecedor.getNome());
                        txtFone.setText(fornecedor.getFone());
                        txtEmail.setText(fornecedor.getEmail());
                        txtSite.setText(fornecedor.getSite());

                        ultimoNomeBuscado = txtNome.getText().trim();
                        

                        txtNome.setVisible(true);
                        txtFone.setVisible(true);
                        txtEmail.setVisible(true);
                        txtSite.setVisible(true);

                        lblNome.setVisible(true);
                        lblFone.setVisible(true);
                        lblEmail.setVisible(true);
                        lblSite.setVisible(true);
                        btnSearchSite.setVisible(true);
                        
                        lblDivisor.setVisible(true);
                        
                        SwingUtilities.invokeLater(new Runnable() {
			    	        public void run() {
			    	        	txtNome.requestFocus();
		    	        }
		    	    });

                        txtNome.setBounds(198, 21, 342, 32);
                        lblNome.setBounds(65, 21, 130, 32);
                        lblNome.setText("Vendas: ");
                        btnSearch.setBounds(540, 21, 25, 32);


                        
                        btnEditar.setVisible(true);
                        btnRelatorio.setVisible(true);
                        btnExcluir.setVisible(true);

                    } else {
        				int resposta = JOptionPane.showConfirmDialog(null, "Nenhuma venda feita, deseja fazer?",
        						"Mensagem", JOptionPane.YES_NO_OPTION);
        				if (resposta == 0) {
        					
        					modo = "adicionar";
        					limparCamposAlt();
        				
        				btnAdicionar.setVisible(true);
        				
        				
        				btnRelatorio.setVisible(false);
        				btnEditar.setVisible(false);
        				btnExcluir.setVisible(false);
        				
        				btnCEP.setVisible(false);
        				
        			
        				
        				if (modo.equals("adicionar") && modoInicial.equals("vendas")) {
        					
        				 ultimoNomeBuscado = txtNome.getText().trim();

        				lblFone.setVisible(true);
        				lblEmail.setVisible(true);
        				lblSite.setVisible(true);
        				lblCPF.setVisible(false);
        				btnMudar.setVisible(false);
        				
        				btnSearchSite.setVisible(true);
        				txtNome.setVisible(true);
        				txtFone.setVisible(true);
        				txtEmail.setVisible(true);
        				txtSite.setVisible(true);
        				txtCPF.setVisible(false);
        				
        				btnCEP.setVisible(false);
        				
        				lblDivisor.setVisible(true);

        				txtNome.setBounds(198, 21, 342, 32);
        				lblNome.setBounds(83, 21, 115, 32);
        				btnSearch.setBounds(540, 21, 25, 32);
        				


                        txtID.setEditable(false);
                        txtNome.setEditable(true);
                        txtFone.setEditable(true);
                        txtEmail.setEditable(true);
                        txtSite.setEditable(true);
                        
        				
                        
                      String nomeDigitado = txtNome.getText();
        				
    					txtNome.setText(nomeDigitado);
    					
    					if (fornecedor == null) {
    				        fornecedor = new Fornecedor();
    				        fornecedor.setNome(nomeDigitado);
    				        txtNome.setText(nomeDigitado);
    				        
    				        SwingUtilities.invokeLater(new Runnable() {
    			    	        public void run() {
    			    	        	txtFone.requestFocus();
    		    	        }
    		    	    });
    		    	    return;
    							}
        					}
        				}
                    }
                    
                
            	} else {
                    panelCadastro.requestFocus();
                    JOptionPane.showInternalMessageDialog(null, "Modo inválido.");
                    txtNome.requestFocus();
                    limparCampos();
                    mudar = false;
                }


            } catch (Exception e2) {
                System.out.println(e2);
            }
        }
    }
});
		
		
		btnSearchAlt = new JButton("");
		btnSearchAlt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (!modoInicial.equals("produtos"))
				mudar = true;
			    buscouPorCPF = modoInicial.equals("clientes");
			    btnSearch.doClick();
				
			}
		});
		btnSearchAlt.setIcon(new ImageIcon(Main.class.getResource("/img/loupe.png")));
		btnSearchAlt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSearchAlt.setBorderPainted(false);
		btnSearchAlt.setBackground(new Color(210, 210, 210));
		btnSearchAlt.setBounds(540, 21, 25, 32);
		panelCadastro.add(btnSearchAlt);
		
		

		btnProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				sobre = false;
				mudar = false;
				ultimoNomeBuscado = "";
				ultimoBarCodeBuscado = "";
				
				modoInicial = "produtos";
				modo = "buscarAlt";
				limparCampos();

				carregarFornecedores();


				
				panelTabela.setVisible(true);
				
				cardGrande = false;
				btnCardGrande.setIcon(new ImageIcon(Main.class.getResource("/img/left-arrow.png")));
				card = false;
				btnCard.setIcon(new ImageIcon(Main.class.getResource("/img/right-arrow.png")));
				

				

				
				if (modoInicial.equals("produtos")) {
				    lblTabela.setText("Produtos");
				    lblTabela.setIconTextGap(15);
				    lblTabela.setFont(new Font("Tahoma", Font.PLAIN, 30));
				    lblTabela.setIcon(new ImageIcon(Main.class.getResource("/img/box.png")));
				    lblTabela.setHorizontalAlignment(SwingConstants.CENTER);
				    lblTabela.setBounds(0, 0, 705, 46);
				    
					SwingUtilities.invokeLater(new Runnable() {
					    public void run() {
					    	
					    	

					    	panelCadastro.setVisible(true);

							lblFone.setVisible(false);
							lblEmail.setVisible(false);
							lblSite.setVisible(false);
							lblCPF.setVisible(false);
							
							txtIDExtra.setVisible(false);
							txtID.setVisible(false);
							
							
							lblNome.setVisible(false);
							lblCategoria.setVisible(false);
							lblFornecedor.setVisible(false);
							
							txtNome.setVisible(false);
							txtCategoria.setVisible(false);
							cbFornecedor.setVisible(false);
							txtBarCode.setVisible(false);
							lblBarCode.setVisible(false);
							lblPrecoCusto.setVisible(false);
							txtPrecoCusto.setVisible(false);
							lblPrecoVenda.setVisible(false);
							txtPrecoVenda.setVisible(false);
							lblEstoque.setVisible(false);
							txtEstoque.setVisible(false);
							lblEstoqueMinimo.setVisible(false);
							txtEstoqueMinimo.setVisible(false);
							cbComplemento.setVisible(false);
							lblComplemento.setVisible(false);
							
							lblCNPJ.setVisible(false);
				            txtCNPJ.setVisible(false);
							
							lblDivisor.setVisible(false);
							
							
							txtCEP.setVisible(false);
			                txtEndereco.setVisible(false);
			                txtBairro.setVisible(false);
			                txtCidade.setVisible(false);
			                cbEstado.setVisible(false);

			                lblCEP.setVisible(false);
			                lblEndereco.setVisible(false);
			                lblBairro.setVisible(false);
			                lblCidade.setVisible(false);

							btnCEP.setVisible(false);
							btnSearchSite.setVisible(false);
							txtID.setVisible(false);
							txtFone.setVisible(false);
							txtEmail.setVisible(false);
							txtSite.setVisible(false);
							txtCPF.setVisible(false);
							txtNumero.setVisible(false);
							
							
							lblNome.setVisible(true);
							lblNome.setBounds(10, 210, 188, 32);
							lblNome.setText("Produto: ");
							txtNome.setVisible(true);
							txtNome.setBounds(200, 210, 342, 32);
							
							btnSearchAlt.setVisible(false);
							btnSearch.setVisible(true);
							btnSearch.setBounds(542, 210, 25, 32);
							
							
							lblBarCode.setBounds(10, 210, 188, 32);
							txtBarCode.setBounds(200, 210, 342, 32);
							btnSearch.setBounds(542, 210, 25, 32);
							btnSearchAlt.setBounds(542, 210, 25, 32);
							
							btnMudar.setVisible(true);
							btnMudar.setBounds(200, 242, 342, 32);
							btnMudar.setText("Buscar pelo código de barras");
							
							panelSobre.setVisible(false);
							

							

							btnAdicionar.setVisible(false);
							btnRelatorio.setVisible(true);
							btnEditar.setVisible(false);
							btnExcluir.setVisible(false);
							
							txtNome.requestFocus();
							
							btnClientes.setBounds(270, 11, 64, 64);
							btnHome.setBounds(375, 11, 64, 64);
							btnFornecedores.setBounds(478, 11, 64, 64);
							btnHome.setVisible(true);
							btnFornecedores.setVisible(true);
							btnClientes.setVisible(true);
							btnProdutos.setVisible(false);
					    	
					    }
					});
				    
				}
				
				


				panelCard1.setVisible(false);
				panelCard2.setVisible(false);
				panelCard3.setVisible(false);
				panelCard4.setVisible(false);
				panelCard5.setVisible(false);
				panelCard6.setVisible(false);
				panelMeioGrande.setVisible(false);
				panelLateralGrande.setVisible(true);

				panelLateral2.setVisible(false);
				panelLateral1.setVisible(true);


				
				panelLateral1.setBounds(0, 205, 17, 251);
				
				panelSobre.setVisible(false);


			}
		});
		
		
		btnVendas = new JButton("");
		btnVendas.setSize(64, 64);
		btnVendas.setLocation(588, 11);
		btnVendas.setIcon(new ImageIcon(Main.class.getResource("/img/shopping-bag.png")));
		btnVendas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnVendas.setBorderPainted(false);
		btnVendas.setBackground(new Color(210, 210, 210));
		btnVendas.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	
		    	mudar = false;
		    	sobre = false;
				modoInicial = "vendas";
				modo = "buscar";
				limparCampos();
				
				

				
				panelTabela.setVisible(true);
				
				cardGrande = false;
				btnCardGrande.setIcon(new ImageIcon(Main.class.getResource("/img/left-arrow.png")));
				card = false;
				btnCard.setIcon(new ImageIcon(Main.class.getResource("/img/right-arrow.png")));
				

				

				
				if (modoInicial.equals("vendas")) {
				    lblTabela.setText("Vendas");
				    lblTabela.setIconTextGap(15);
				    lblTabela.setFont(new Font("Tahoma", Font.PLAIN, 30));
				    lblTabela.setIcon(new ImageIcon(Main.class.getResource("/img/shopping-bag.png")));
				    lblTabela.setHorizontalAlignment(SwingConstants.CENTER);
				    lblTabela.setBounds(0, 0, 705, 46);
				    
					SwingUtilities.invokeLater(new Runnable() {
					    public void run() {
					    	
					    	

					    	panelCadastro.setVisible(true);

					    	txtCNPJ.setVisible(false);
					    	lblCNPJ.setVisible(false);
							lblFone.setVisible(false);
							lblEmail.setVisible(false);
							lblSite.setVisible(false);
							lblCPF.setVisible(false);
							
							txtIDExtra.setVisible(false);
							txtID.setVisible(false);
							
							
							lblBarCode.setVisible(false);
							lblCategoria.setVisible(false);
							lblFornecedor.setVisible(false);
							
							txtBarCode.setVisible(false);
							txtCategoria.setVisible(false);
							cbFornecedor.setVisible(false);
							
							lblPrecoCusto.setVisible(false);
							txtPrecoCusto.setVisible(false);
							lblPrecoVenda.setVisible(false);
							txtPrecoVenda.setVisible(false);
							lblEstoque.setVisible(false);
							txtEstoque.setVisible(false);
							lblEstoqueMinimo.setVisible(false);
							txtEstoqueMinimo.setVisible(false);
							cbComplemento.setVisible(false);
							lblComplemento.setVisible(false);
							
							
							lblDivisor.setVisible(false);
							
							
							txtCEP.setVisible(false);
			                txtEndereco.setVisible(false);
			                txtBairro.setVisible(false);
			                txtCidade.setVisible(false);
			                cbEstado.setVisible(false);
			                
			                lblCEP.setVisible(false);
			                lblEndereco.setVisible(false);
			                lblBairro.setVisible(false);
			                lblCidade.setVisible(false);

							btnCEP.setVisible(false);
							btnSearchSite.setVisible(false);
							txtID.setVisible(false);
							txtFone.setVisible(false);
							txtEmail.setVisible(false);
							txtSite.setVisible(false);
							txtCPF.setVisible(false);
							txtNumero.setVisible(false);
							
							
							lblNome.setVisible(true);
							lblNome.setBounds(88, 210, 120, 32);
		                    lblNome.setText("Vendas: ");
							txtNome.setVisible(true);
							txtNome.setBounds(200, 210, 342, 32);
							
							btnSearchAlt.setVisible(false);
							btnSearch.setVisible(true);
							btnSearch.setBounds(542, 210, 25, 32);
							btnSearchAlt.setBounds(542, 210, 25, 32);

							panelSobre.setVisible(false);
							
							btnMudar.setVisible(true);
							btnMudar.setBounds(200, 242, 342, 32);
							btnMudar.setText("Buscar pelo código de barras");
							

							btnAdicionar.setVisible(false);
							btnRelatorio.setVisible(true);
							btnEditar.setVisible(false);
							btnExcluir.setVisible(false);
							
							txtNome.requestFocus();
					    	
					    }
					});
				    
				}
				
				JOptionPane.showMessageDialog(null, "Em andamento");
				btnHome.doClick();
				
				

				panelCard1.setVisible(false);
				panelCard2.setVisible(false);
				panelCard3.setVisible(false);
				panelCard4.setVisible(false);
				panelCard5.setVisible(false);
				panelCard6.setVisible(false);
				panelMeioGrande.setVisible(false);
				panelLateralGrande.setVisible(true);


				panelLateral2.setVisible(true);
				panelLateral1.setVisible(true);
				panelLateral2.setVisible(true);
				panelLateral1.setVisible(true);

				panelSobre.setVisible(false);


			}
		});
		
		panelInferior.add(btnVendas);

		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cardGrande = false;
				btnCardGrande.setIcon(new ImageIcon(Main.class.getResource("/img/left-arrow.png")));
				card = false;
				btnCard.setIcon(new ImageIcon(Main.class.getResource("/img/right-arrow.png")));

				sobre = false;

				panelCard1.setVisible(false);
				panelCard2.setVisible(false);
				panelCard3.setVisible(false);
				panelCard4.setVisible(false);
				panelCard5.setVisible(false);
				panelCard6.setVisible(false);
				panelMeioGrande.setVisible(false);
				panelLateralGrande.setVisible(true);

				panelLateral2.setVisible(false);
				panelLateral1.setVisible(true);

				panelTabela.setVisible(false);
				panelSobre.setVisible(false);
				panelCadastro.setVisible(false);
				
				
				btnClientes.setBounds(270, 11, 64, 64);
				btnFornecedores.setBounds(375, 11, 64, 64);
				btnProdutos.setBounds(478, 11, 64, 64);
				btnHome.setVisible(false);
				btnFornecedores.setVisible(true);
				btnClientes.setVisible(true);
				btnProdutos.setVisible(true);


			}
		});

		
		
		btnSearch.setIcon(new ImageIcon(Main.class.getResource("/img/loupe.png")));
		btnSearch.setBorderPainted(false);
		btnSearch.setBackground(new Color(210, 210, 210));
		btnSearch.setBounds(540, 63, 25, 32);
		panelCadastro.add(btnSearch);
		
		


		txtEmail = new JTextField();

		
		txtEmail.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyPressed(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		            
		            if (modoInicial.equals("clientes")) {
		                txtCPF.requestFocus();
		                
		            } else if (modoInicial.equals("fornecedores")) {
		                txtSite.requestFocus();
		            }
		        }
		    }
		});
		
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtEmail.setColumns(10);
		txtEmail.setBounds(198, 105, 342, 32);
		panelCadastro.add(txtEmail);
		txtEmail.setDocument(new Validador(50));
		
		
		txtCNPJ.addKeyListener(new KeyAdapter() {
		    public void keyPressed(KeyEvent e) {

		        String cnpjAtual = txtCNPJ.getText().trim();

		        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		        	
		        if (modoInicial.equals("fornecedores") && modo.equals("adicionar")) {

		               txtNome.requestFocus();

		        } else if (modoInicial.equals("fornecedores") && cnpjAtual.equals(ultimoCNPJBuscado)) {
		                txtNome.requestFocus();

		            } else {
		            	mudar = true; 
		            	buscouPorCNPJ = true;
		                btnSearch.doClick();
		            }
		        }
		    }
		});
		
		
		
		txtNome = new JTextField();
		
		txtNome.setDocument(new Validador(50));
		
		txtNome.getDocument().addDocumentListener(new DocumentListener() {
		    public void insertUpdate(DocumentEvent e) { atualizarSugestoes(); }
		    public void removeUpdate(DocumentEvent e) { atualizarSugestoes(); }
		    public void changedUpdate(DocumentEvent e) { atualizarSugestoes(); }
		});
		
		txtNome.addKeyListener(new KeyAdapter() {
		    	@Override
		    public void keyPressed(KeyEvent e) {
		        

		        	if (popupSugestoesProduto.isVisible()) {

		                int indiceAtual = listaSugestoesProduto.getSelectedIndex();

		                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
		                    int proximo = Math.min(indiceAtual + 1, modeloSugestoesProduto.size() - 1);
		                    listaSugestoesProduto.setSelectedIndex(proximo);
		                    listaSugestoesProduto.ensureIndexIsVisible(proximo);
		                    return;

		                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
		                    int anterior = Math.max(indiceAtual - 1, 0);
		                    listaSugestoesProduto.setSelectedIndex(anterior);
		                    listaSugestoesProduto.ensureIndexIsVisible(anterior);
		                    return;

		                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		                    selecionarSugestao(listaSugestoesProduto.getSelectedIndex());
		                    return;

		                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
		                    popupSugestoesProduto.setVisible(false);
		                    return;
		                }
		            }
		        	
		        	 String nomeAtual = txtNome.getText().trim();
		        	 
		        	 if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		        	 
		        	 if (modoInicial.equals("fornecedores") & nomeAtual.equals(ultimoNomeBuscado)) {
		                 txtFone.requestFocus();
		                 
		        	 } else if (modoInicial.equals("fornecedores") && modo.equals("adicionar")) {

			               txtFone.requestFocus();
		             
		        	 } else if (modoInicial.equals("clientes") && nomeAtual.equals(ultimoNomeBuscado)) {
		                 txtFone.requestFocus();
		                 
		        	 } else if (modoInicial.equals("clientes") && modo.equals("adicionar")) {
			               txtFone.requestFocus();
			               
		        	 } else if (modoInicial.equals("produtos") && modo.equals("adicionar")) {
			               txtCategoria.requestFocus();
		                 
		             } else if (modoInicial.equals("produtos") && nomeAtual.equals(ultimoNomeBuscado)) {
		                 txtCategoria.requestFocus();
		                 
		             } else {
		            	 mudar = false; 
		            	 buscouPorCPF = false;
		            	 buscouPorCNPJ = false;
		            	 buscouPorBarCode = false;
		                 btnSearch.doClick();
		             }
	                
		        	 
	             
		        }
		    }
		});
		
		
		txtNome.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtNome.setColumns(10);
		txtNome.setBounds(198, 21, 342, 32);
		panelCadastro.add(txtNome);
		
		
		
		
		
		txtSite = new JTextField();
		txtSite.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				
				if(modo.equals("adicionar") & modoInicial.equals("fornecedores") & e.getKeyCode() == KeyEvent.VK_ENTER) {
					
				btnAdicionar.doClick();
				txtNome.requestFocus();
				
					} else if(modo.equals("buscar") && modoInicial.equals("fornecedores") && e.getKeyCode() == KeyEvent.VK_ENTER) {
						
						if (e.getKeyCode() == KeyEvent.VK_ENTER && modo.equals("buscar")) {
	                        
	                        Object[] opcoes = {"Editar", "Excluir", "Cancelar"};
	                        
	                        int escolha = JOptionPane.showOptionDialog(
	                                null,
	                                "Escolha uma das opções abaixo",
	                                "",
	                                JOptionPane.YES_NO_CANCEL_OPTION,
	                                JOptionPane.QUESTION_MESSAGE,
	                                null,
	                                opcoes,
	                                opcoes[2]
	                        );

	                        if (escolha == 0) {

	                            
	                            if (modoInicial.equals("fornecedores")) {
	                            	
	                            	btnEditar.doClick();
	                            	
	                            }

	                        } else if (escolha == 1) {
	                            
	                            if (modoInicial.equals("fornecedores")) {
	                            	
	                            	btnExcluir.doClick();
	                            }
	                        }
	                        
	                    }
					}
				
				}
		});
		
		
		txtSite.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtSite.setColumns(10);
		txtSite.setBounds(198, 147, 342, 32);
		panelCadastro.add(txtSite);
		txtSite.setDocument(new Validador(50));
		
		
		txtCPF.addKeyListener(new KeyAdapter() {
		    public void keyPressed(KeyEvent e) {

		    	String CPFAtual = txtCPF.getText().trim();

		        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		            
		            if (modoInicial.equals("clientes") && modo.equals("adicionar")) {
		                txtCEP.requestFocus();

		            } else if (CPFAtual.equals(ultimoCPFBuscado)) {
		                txtCEP.requestFocus();

		            } else {
		                mudar = true;  
		                buscouPorCPF = true;
		                btnSearch.doClick();
		            }
		        }
		    }
		});
		
		 
		
		txtCEP.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyPressed(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		            
		            String cepAtual = txtCEP.getText().trim();
		            
		            if (cepAtual.equals(ultimoCEPBuscado)) {
		            	
		                txtEndereco.requestFocus();
		                
		            } else {

		                btnCEP.doClick();
		            }
		        }
		    }
		});
		
		txtEndereco.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				
				txtNumero.requestFocus();
				}
			}
		});
		
		txtNumero.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				
				cbComplemento.requestFocus();
				}
			}
		});
		
		cbComplemento.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				
				txtBairro.requestFocus();
				}
			}
		});
		
		txtBairro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				
				txtCidade.requestFocus();
				}
			}
		});
		
		
		txtCidade.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(modo.equals("adicionar") & modoInicial.equals("clientes") & e.getKeyCode() == KeyEvent.VK_ENTER) {
					
				btnAdicionar.doClick();
				
				txtNome.requestFocus();
				
					} else if(modo.equals("buscar") & modoInicial.equals("clientes") & e.getKeyCode() == KeyEvent.VK_ENTER) {
						
			                	
			                    if (e.getKeyCode() == KeyEvent.VK_ENTER && modo.equals("buscar")) {
			                        
			                        Object[] opcoes = {"Editar", "Excluir", "Cancelar"};
			                        
			                        int escolha = JOptionPane.showOptionDialog(
			                                null,
			                                "Escolha uma das opções abaixo",
			                                "",
			                                JOptionPane.YES_NO_CANCEL_OPTION,
			                                JOptionPane.QUESTION_MESSAGE,
			                                null,
			                                opcoes,
			                                opcoes[2]
			                        );

			                        if (escolha == 0) {

			                            
			                            if (modoInicial.equals("clientes")) {
			                            	
			                            	btnEditar.doClick();
			                            	
			                            }

			                        } else if (escolha == 1) {
			                            
			                            if (modoInicial.equals("clientes")) {
			                            	
			                            	btnExcluir.doClick();
			                            }
			                        }
			                        
			                    }
							}

				}
			});
		
		txtBarCode.addKeyListener(new KeyAdapter() {
		    public void keyPressed(KeyEvent e) {

		        String codigoAtual = txtBarCode.getText().trim();

		        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		        	
		        	if (modoInicial.equals("produtos") && modo.equals("adicionar")) {
			               txtNome.requestFocus();

		        	} else if (codigoAtual.equals(ultimoBarCodeBuscado)) {
		                txtNome.requestFocus();
		                
		            } else {
		            	mudar = true;  
		                buscouPorBarCode = true;
		                btnSearch.doClick();
		            }
		        }
		    }
		});
		
		txtCategoria.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(modo.equals("adicionar") & modoInicial.equals("produtos") & e.getKeyCode() == KeyEvent.VK_ENTER) {
					
				cbFornecedor.requestFocus();
				
					} else if(modo.equals("buscar") & modoInicial.equals("produtos") & e.getKeyCode() == KeyEvent.VK_ENTER) {
						
						cbFornecedor.requestFocus();

					}
				}
			});
		
		cbFornecedor.getInputMap(JComponent.WHEN_FOCUSED).put(
			    KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterPressed");

			cbFornecedor.getActionMap().put("enterPressed", new AbstractAction() {
			    public void actionPerformed(ActionEvent e) {

			        if (modo.equals("adicionar") && modoInicial.equals("produtos")) {
			            txtPrecoCusto.requestFocus();

			        } else if (modo.equals("buscar") && modoInicial.equals("produtos")) {
			            txtPrecoCusto.requestFocus();
			        }
			    }
			});
		
		txtPrecoCusto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(modo.equals("adicionar") & modoInicial.equals("produtos") & e.getKeyCode() == KeyEvent.VK_ENTER) {
					
				txtPrecoVenda.requestFocus();
				
					} else if(modo.equals("buscar") & modoInicial.equals("produtos") & e.getKeyCode() == KeyEvent.VK_ENTER) {
						
						txtPrecoVenda.requestFocus();

					}
				}
			});
		
		txtPrecoVenda.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(modo.equals("adicionar") & modoInicial.equals("produtos") & e.getKeyCode() == KeyEvent.VK_ENTER) {
					
				txtEstoque.requestFocus();
				
					} else if(modo.equals("buscar") & modoInicial.equals("produtos") & e.getKeyCode() == KeyEvent.VK_ENTER) {
						
						txtEstoque.requestFocus();

					}
				}
			});
		
		txtEstoque.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(modo.equals("adicionar") & modoInicial.equals("produtos") & e.getKeyCode() == KeyEvent.VK_ENTER) {
					
				txtEstoqueMinimo.requestFocus();
				
					} else if(modo.equals("buscar") & modoInicial.equals("produtos") & e.getKeyCode() == KeyEvent.VK_ENTER) {
						
						txtEstoqueMinimo.requestFocus();

					}
				}
			});
		
		txtEstoqueMinimo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(modo.equals("adicionar") & modoInicial.equals("produtos") & e.getKeyCode() == KeyEvent.VK_ENTER) {
					
				btnAdicionar.doClick();
				
				} else if(modo.equals("buscar") & modoInicial.equals("produtos") & e.getKeyCode() == KeyEvent.VK_ENTER) {
					
                	
                    if (e.getKeyCode() == KeyEvent.VK_ENTER && modo.equals("buscar")) {
                        
                        Object[] opcoes = {"Editar", "Excluir", "Cancelar"};
                        
                        int escolha = JOptionPane.showOptionDialog(
                                null,
                                "Escolha uma das opções abaixo",
                                "",
                                JOptionPane.YES_NO_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                opcoes,
                                opcoes[2]
                        );

                        if (escolha == 0) {

                            
                            if (modoInicial.equals("produtos")) {
                            	
                            	btnEditar.doClick();
                            	
                            }

                        } else if (escolha == 1) {

                            
                            if (modoInicial.equals("produtos")) {
                            	
                            	btnExcluir.doClick();
                            }
                        }
                        
                    }
				}
				}
			});
		
		JButton btnSobre = new JButton("");
		btnSobre.setBackground(new Color(240, 240, 240));
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cardGrande = false;
				btnCardGrande.setIcon(new ImageIcon(Main.class.getResource("/img/left-arrow.png")));
				card = false;
				btnCard.setIcon(new ImageIcon(Main.class.getResource("/img/right-arrow.png")));
				
				
				if (sobre) {
		            panelSobre.setVisible(false);
		            sobre = false;
		            
				} else {
		            sobre = true;
		        
		            panelCard1.setVisible(false);
		            panelCard2.setVisible(false);
		            panelCard3.setVisible(false);
		            panelCard4.setVisible(false);
		            panelCard5.setVisible(false);
					panelCard6.setVisible(false);
					panelMeioGrande.setVisible(false);
					panelLateralGrande.setVisible(true);

					panelLateral2.setVisible(false);

					panelLateral1.setVisible(true);
					
					panelLateral1.setBounds(0, 205, 17, 251);

					panelTabela.setVisible(false);
					panelCadastro.setVisible(false);

					panelSobre.setVisible(true);
				
				}
				
				
			}
		});
		btnSobre.setIcon(new ImageIcon(Main.class.getResource("/img/info (24px).png")));
		btnSobre.setBounds(750, 2, 32, 32);
		btnSobre.setBorderPainted(false);
		contentPane.add(btnSobre);
		
		JButton btnSair = new JButton("");
		btnSair.setBackground(new Color(240, 240, 240));
		btnSair.setBorderPainted(false);
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente sair do sistema",
						"Confirmar saída", JOptionPane.YES_NO_OPTION);

				if (resposta == 0) {
					System.exit(0);
				}
				
			}
		});
		btnSair.setIcon(new ImageIcon(Main.class.getResource("/img/power.png")));
		btnSair.setBounds(780, 1, 32, 32);
		contentPane.add(btnSair);
		
		JButton btnIDK = new JButton("");
		btnIDK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog(null, "Em andamento.");
				
			}
		});
		btnIDK.setIcon(new ImageIcon(Main.class.getResource("/img/question.png")));
		btnIDK.setBorderPainted(false);
		btnIDK.setBackground(new Color(240, 240, 240));
		btnIDK.setBounds(720, 2, 32, 32);
		btnIDK.setBorderPainted(false);
		contentPane.add(btnIDK);	

		setLocationRelativeTo(null);

		atualizarData();
		
		Timer data = new Timer(100, new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	
		    	atualizarData();
		    }
		});
		data.start();

		panelTabela.setVisible(false);

		btnRelatorio.setBorderPainted(false);
		btnExcluir.setBorderPainted(false);
		btnEditar.setBorderPainted(false);
		
		lblMySQL.setText("Conectando...");
        lblStatus.setBounds(460, 78, 25, 26);
        lblDatabase.setBounds(340, 79, 31, 26);
        lblStatus.setForeground(new Color(80, 80, 80));

		Timer timerStatusBanco = new Timer(5000, new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	if (db.testarConexao()) {
		            lblMySQL.setText("MySQL Conectado");
		            lblStatus.setForeground(new Color(0, 174, 0));
		            lblStatus.setBounds(460, 78, 25, 26);
		            lblDatabase.setBounds(339, 79, 31, 26);
		        } else {
		            lblMySQL.setText("MySQL Desconectado");
		            lblStatus.setBounds(468, 78, 25, 26);
		            lblDatabase.setBounds(330, 79, 31, 26);
		            lblStatus.setForeground(new Color(204, 0, 0));
		        }
		    }
		});
		timerStatusBanco.start();
		
		btnIDK.setVisible(false);
		btnHome.doClick();
		btnVendas.setVisible(false);;
		
		btnCard.doClick();

		
		lblNomeBotaoBusca = new JLabel("");
		lblNomeBotaoBusca.setOpaque(true);
		lblNomeBotaoBusca.setBackground(new Color(50, 50, 50));
		lblNomeBotaoBusca.setForeground(Color.WHITE);
		lblNomeBotaoBusca.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNomeBotaoBusca.setHorizontalAlignment(SwingConstants.CENTER);
		lblNomeBotaoBusca.setVisible(false);
		panelCadastro.add(lblNomeBotaoBusca);
		panelCadastro.setComponentZOrder(lblNomeBotaoBusca, 0);
		
		lblNomeBotaoInferior = new JLabel("");
		lblNomeBotaoInferior.setOpaque(true);
		lblNomeBotaoInferior.setBackground(new Color(50, 50, 50));
		lblNomeBotaoInferior.setForeground(Color.WHITE);
		lblNomeBotaoInferior.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNomeBotaoInferior.setHorizontalAlignment(SwingConstants.CENTER);
		lblNomeBotaoInferior.setVisible(false);
		panelInferior.add(lblNomeBotaoInferior);
		panelInferior.setComponentZOrder(lblNomeBotaoInferior, 0);
		
		lblNomeBotao = new JLabel("");
		lblNomeBotao.setOpaque(true);
		lblNomeBotao.setBackground(new Color(50, 50, 50));
		lblNomeBotao.setForeground(Color.WHITE);
		lblNomeBotao.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNomeBotao.setHorizontalAlignment(SwingConstants.CENTER);
		lblNomeBotao.setVisible(false);
		panelTabela.add(lblNomeBotao);
		panelTabela.setComponentZOrder(lblNomeBotao, 0);
				
						JLabel lblVestido = new JLabel("");
						lblVestido.setIcon(new ImageIcon(Main.class.getResource("/img/dress 128px.png")));
						lblVestido.setBounds(217, 296, 119, 143);
						contentPane.add(lblVestido);
						
								JLabel lblStellio = new JLabel("Stellio");
								lblStellio.setHorizontalAlignment(SwingConstants.CENTER);
								lblStellio.setFont(new Font("Vladimir Script", Font.BOLD, 85));
								lblStellio.setBounds(328, 296, 249, 107);
								contentPane.add(lblStellio);
								
										JLabel lblRodape = new JLabel("Alfaiataria contemporânea");
										lblRodape.setFont(new Font("NSimSun", Font.PLAIN, 20));
										lblRodape.setHorizontalAlignment(SwingConstants.CENTER);
										lblRodape.setBounds(308, 390, 300, 26);
										contentPane.add(lblRodape);

		
		
		btnEditar.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	
		    	if (modoInicial.equals("clientes")) {
		    		
		        animarEntrada(lblNomeBotao, btnEditar, "Editar cliente", "cima", 110);
		        
		    } else if (modoInicial.equals("fornecedores")) {
		    		
		        animarEntrada(lblNomeBotao, btnEditar, "Editar fornecedor", "cima", 140);
		        
		    } else if (modoInicial.equals("produtos")) {
	    		animarEntrada(lblNomeBotao, btnEditar, "Editar produto", "cima", 110);
	    		
	    	} else if (modoInicial.equals("vendas")) {
	    		animarEntrada(lblNomeBotao, btnEditar, "Editar venda", "cima", 110);
	    	}
	    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		        lblNomeBotao.setVisible(false);
		    }
		});

		btnExcluir.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	
		    	if (modoInicial.equals("clientes")) {
		    		
		        animarEntrada(lblNomeBotao, btnExcluir, "Excluir cliente", "cima", 110);
		        
		    } if (modoInicial.equals("fornecedores")) {
		    	
		        animarEntrada(lblNomeBotao, btnExcluir, "Excluir fornecedor", "cima", 140);
		        
		    } else if (modoInicial.equals("produtos")) {
	    		animarEntrada(lblNomeBotao, btnExcluir, "Excluir produto", "cima", 110);
	    		
	    	} else if (modoInicial.equals("vendas")) {
	    		animarEntrada(lblNomeBotao, btnExcluir, "Excluir venda", "cima", 110);
	    	}
	    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		        lblNomeBotao.setVisible(false);
		    }
		});
		
		btnAdicionar.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	
		    if (modoInicial.equals("clientes")) {
		    		
		        animarEntrada(lblNomeBotao, btnAdicionar, "Adicionar cliente", "cima", 120);
		        
		    } else if (modoInicial.equals("fornecedores")) {
		    	
		    	animarEntrada(lblNomeBotao, btnAdicionar, "Adicionar fornecedor", "cima", 150);
		    	
		    } else if (modoInicial.equals("produtos")) {
	    		animarEntrada(lblNomeBotao, btnAdicionar, "Adicionar produto", "cima", 120);
	    		
	    	} else if (modoInicial.equals("vendas")) {
	    		animarEntrada(lblNomeBotao, btnAdicionar, "Adicionar venda", "cima", 120);
	    	}
		    
		    
	    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		        lblNomeBotao.setVisible(false);
		    }
		});

		btnRelatorio.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	
		    	if (modoInicial.equals("clientes")) {
		        animarEntrada(lblNomeBotao, btnRelatorio, "Relatório dos clientes", "cima", 150);
		        
		    } else if (modoInicial.equals("fornecedores")) {
		    	
		    	animarEntrada(lblNomeBotao, btnRelatorio, "Relatório dos fornecedores", "cima", 180);
		    	
		    } else if (modoInicial.equals("produtos")) {
		    	
		    	animarEntrada(lblNomeBotao, btnRelatorio, "Relatório dos produtos", "cima", 180);
		    	
		    } else if (modoInicial.equals("vendas")) {
		    	
		    	animarEntrada(lblNomeBotao, btnRelatorio, "Relatório das vendas", "cima", 150);
		    	
		    }
		    	
		    	
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		        lblNomeBotao.setVisible(false);
		    }
		});
		
		btnSearch.addMouseListener(new MouseAdapter() {
		    public void mouseEntered(MouseEvent e) {
		    	
		    	if (modoInicial.equals("clientes")) {
		        animarEntrada(lblNomeBotaoBusca, btnSearch, "Buscar cliente", "esquerda", 120);
		        
		    	} else if (modoInicial.equals("fornecedores")) {
		    		animarEntrada(lblNomeBotaoBusca, btnSearch, "Buscar fornecedor", "esquerda", 140);
		    		
		    	} else if (modoInicial.equals("produtos")) {
		    		animarEntrada(lblNomeBotaoBusca, btnSearch, "Buscar produto", "esquerda", 120);
		    	}
		    }
		   
		    public void mouseExited(MouseEvent e) {
		        lblNomeBotaoBusca.setVisible(false);
		    }
		});
		
		btnSearchAlt.addMouseListener(new MouseAdapter() {
		    public void mouseEntered(MouseEvent e) {
		    	
		    	if (modoInicial.equals("clientes")) {
		        animarEntrada(lblNomeBotaoBusca, btnSearchAlt, "Buscar pelo CPF", "esquerda", 140);
		        
		    	} else if (modoInicial.equals("fornecedores")) {
		    		animarEntrada(lblNomeBotaoBusca, btnSearchAlt, "Buscar pelo CNPJ", "esquerda", 150);
		    		
		    	} else if (modoInicial.equals("produtos")) {
		    		animarEntrada(lblNomeBotaoBusca, btnSearchAlt, "Buscar pelo Código de barras", "esquerda", 200);
		    	}
		    }
		   
		    public void mouseExited(MouseEvent e) {
		        lblNomeBotaoBusca.setVisible(false);
		    }
		});
		
		btnCEP.addMouseListener(new MouseAdapter() {
		    public void mouseEntered(MouseEvent e) {
		    	
		        animarEntrada(lblNomeBotaoBusca, btnCEP, "Buscar CEP", "esquerda", 120);
		    }
		    public void mouseExited(MouseEvent e) {
		        lblNomeBotaoBusca.setVisible(false);
		    }
		});

		btnSearchSite.addMouseListener(new MouseAdapter() {
		    public void mouseEntered(MouseEvent e) {
		        animarEntrada(lblNomeBotaoBusca, btnSearchSite, "Acessar Site", "esquerda", 120);
		    }
		    public void mouseExited(MouseEvent e) {
		        lblNomeBotaoBusca.setVisible(false);
		    }
		});
		
		btnClientes.addMouseListener(new MouseAdapter() {
		    public void mouseEntered(MouseEvent e) {
		        animarEntrada(lblNomeBotaoInferior, btnClientes, "Clientes", "baixo", 80);
		    }
		    public void mouseExited(MouseEvent e) {
		        lblNomeBotaoInferior.setVisible(false);
		    }
		});
		
		btnFornecedores.addMouseListener(new MouseAdapter() {
		    public void mouseEntered(MouseEvent e) {
		        animarEntrada(lblNomeBotaoInferior, btnFornecedores, "Fornecedores", "baixo", 110);
		    }
		    public void mouseExited(MouseEvent e) {
		        lblNomeBotaoInferior.setVisible(false);
		    }
		});
		
		btnProdutos.addMouseListener(new MouseAdapter() {
		    public void mouseEntered(MouseEvent e) {
		        animarEntrada(lblNomeBotaoInferior, btnProdutos, "Produtos", "baixo", 80);
		    }
		    public void mouseExited(MouseEvent e) {
		        lblNomeBotaoInferior.setVisible(false);
		    }
		});
		
		btnHome.addMouseListener(new MouseAdapter() {
		    public void mouseEntered(MouseEvent e) {
		        animarEntrada(lblNomeBotaoInferior, btnHome, "Início", "baixo", 80);
		    }
		    public void mouseExited(MouseEvent e) {
		        lblNomeBotaoInferior.setVisible(false);
		    }
		});
		btnVendas.addMouseListener(new MouseAdapter() {
		    public void mouseEntered(MouseEvent e) {
		        animarEntrada(lblNomeBotaoInferior, btnVendas, "Vendas", "baixo", 80);
		    }
		    public void mouseExited(MouseEvent e) {
		        lblNomeBotaoInferior.setVisible(false);
		    }
		});
		
		
		
		
	} 
	
	private void atualizarDashboard() {
		int totalProdutos = produtoController.dashProdutos();
		int estoqueMinimo = produtoController.dashEstoqueBaixo();
		int semEstoque = produtoController.dashSemEstoque();
		
		lblDashQuantidade.setText(String.valueOf(totalProdutos));
		lblDashRepor.setText(String.valueOf(estoqueMinimo));
		lblDashZerado.setText(String.valueOf(semEstoque));
		
	}

	
	private void animarEntrada(JLabel label, JButton botao, String texto, String posicao, int larguraFinal) {
	    
	    if (timerAnimacaoLabel != null && timerAnimacaoLabel.isRunning()) {
	        timerAnimacaoLabel.stop();
	    }
	    
	    label.setText(texto);
	    label.setVisible(true);

	    int alturaLabel = 25;
	    int x, y;

	    switch (posicao) {
	        case "esquerda":
	            x = botao.getX() - 5;
	            y = botao.getY() + (botao.getHeight() / 2) - (alturaLabel / 2);
	            break;
	        case "direita":
	            x = botao.getX() + botao.getWidth() + 5;
	            y = botao.getY() + (botao.getHeight() / 2) - (alturaLabel / 2);
	            break;
	        case "baixo":
	            x = botao.getX() + (botao.getWidth() / 2);
	            y = botao.getY() + botao.getHeight() + 5;
	            break;
	        case "cima":
	        default:
	            x = botao.getX() + (botao.getWidth() / 2);
	            y = botao.getY() - 30;
	            break;
	    }

	    label.setBounds(x, y, 0, alturaLabel);

	    timerAnimacaoLabel = new Timer(3, null);
	    timerAnimacaoLabel.addActionListener(new ActionListener() {
	        int larguraAtual = 0;

	        @Override
	        public void actionPerformed(ActionEvent e) {
	            larguraAtual += 6;
	            if (larguraAtual >= larguraFinal) {
	                larguraAtual = larguraFinal;
	                timerAnimacaoLabel.stop();
	            }

	            int xFinal;
	            if (posicao.equals("esquerda")) {
	                xFinal = x - larguraAtual;
	            } else if (posicao.equals("cima") || posicao.equals("baixo")) {
	                xFinal = x - (larguraAtual / 2);
	            } else {
	                xFinal = x;
	            }

	            label.setBounds(xFinal, y, larguraAtual, alturaLabel);
	        }
	    });
	    timerAnimacaoLabel.start();
	}
	
	
	void limparCampos() {
		txtID.setText(null);
		txtCNPJ.setText(null);
		txtNome.setText(null);
		txtFone.setText(null);
		txtEmail.setText(null);
		txtSite.setText(null);
		txtCPF.setText(null);
		txtCEP.setText(null);
		txtEndereco.setText(null);
		txtNumero.setText(null);
		cbComplemento.setToolTipText(null);
		txtBairro.setText(null);
		txtCidade.setText(null);
		cbEstado.setToolTipText(null);
		txtBarCode.setText(null);
		txtCategoria.setText(null);
		txtPrecoCusto.setText(null);
		txtPrecoVenda.setText(null);
		txtEstoque.setText(null);
		txtEstoqueMinimo.setText(null);


		txtNome.requestFocus();
		
	}	
		
		void limparCamposAlt() {
			txtID.setText(null);
			txtFone.setText(null);
			txtEmail.setText(null);
			txtSite.setText(null);
			txtCEP.setText(null);
			txtEndereco.setText(null);
			txtNumero.setText(null);
			cbComplemento.setToolTipText(null);
			txtBairro.setText(null);
			txtCidade.setText(null);
			cbEstado.setToolTipText(null);
			txtCategoria.setText(null);
			txtPrecoCusto.setText(null);
			txtPrecoVenda.setText(null);
			txtEstoque.setText(null);
			txtEstoqueMinimo.setText(null);
			
			txtFone.requestFocus();

	}
		
		
		private void atualizarSugestoes() {
			
			if (ignorarEventoSugestao) {
		        return;
		    }

		    if (!modoInicial.equals("clientes") && !modoInicial.equals("fornecedores") && !modoInicial.equals("produtos")) {
		        popupSugestoesProduto.setVisible(false);
		        return;
		    }


		    String texto = txtNome.getText().trim();

		    if (ignorarEventoSugestao) {
		        return;
		    }

		    if (!modoInicial.equals("clientes") && !modoInicial.equals("fornecedores") && !modoInicial.equals("produtos")) {
		        popupSugestoesProduto.setVisible(false);
		        return;
		    }

		    if (texto.isBlank()) {
		        popupSugestoesProduto.setVisible(false);
		        return;
		    }

		    if (modoInicial.equals("clientes")) {
		        nomesSugeridos = clienteController.buscarPorTrecho(texto);
		    } else if (modoInicial.equals("fornecedores")) {
		        nomesSugeridos = controller.buscarPorTrecho(texto);
		    } else {
		        nomesSugeridos = produtoController.buscarPorTrecho(texto);
		    }

		    modeloSugestoesProduto.clear();

		    if (nomesSugeridos.isEmpty()) {
		        popupSugestoesProduto.setVisible(false);
		        return;
		    }

		    for (String nome : nomesSugeridos) {
		        modeloSugestoesProduto.addElement(nome);
		    }

		    listaSugestoesProduto.setSelectedIndex(0);

		    int maxItensVisiveis = 6;
		    int itensVisiveis = Math.min(modeloSugestoesProduto.size(), maxItensVisiveis);
		    int alturaPorItem = listaSugestoesProduto.getFixedCellHeight();
		    int alturaTotal = itensVisiveis * alturaPorItem;

		    scrollSugestoesProduto.setPreferredSize(new Dimension(txtNome.getWidth(), alturaTotal));

		    if (!popupSugestoesProduto.isVisible()) {
		        popupSugestoesProduto.show(txtNome, 0, txtNome.getHeight());
		    } else {
		        popupSugestoesProduto.pack();
		    }
		}

		private void selecionarSugestao(int indice) {

		    if (indice < 0 || indice >= nomesSugeridos.size()) {
		        return;
		    }

		    popupSugestoesProduto.setVisible(false);

		    String nomeSelecionado = nomesSugeridos.get(indice);

		    ignorarEventoSugestao = true;
		    txtNome.setText(nomeSelecionado);
		    ignorarEventoSugestao = false;

		    btnSearch.doClick();
		}

	private void link(String url) {
		Desktop desktop = Desktop.getDesktop();
		try {
			URI uri = new URI(url);
			desktop.browse(uri);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	
	private void buscarCepEPreencherTela(String cep) {
	    ValidadorCEP servico = new ValidadorCEP();

	    try {
	        Cliente cliente = servico.buscarCep(cep);
	        
	        txtEndereco.setText(cliente.getEndereco());
	        txtBairro.setText(cliente.getBairro());
	        txtCidade.setText(cliente.getCidade());
	        
	        cbEstado.setSelectedItem(cliente.getEstado());
	        
	    } catch (Exception ex) {
	        JOptionPane.showMessageDialog(this, 
	            "CEP não encontrado ou erro na busca: " + ex.getMessage(), 
	            "Aviso", 
	            JOptionPane.WARNING_MESSAGE);
	            
	        txtEndereco.setText("");
	        txtBairro.setText("");
	        txtCidade.setText("");
	        cbEstado.setSelectedIndex(0);
	    }
	}

	private void atualizarData() {
		LocalDate now = LocalDate.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		lblData.setText(now.format(format));
		
	}
	
	private void carregarFornecedores() {
		
		cbFornecedor.removeAllItems();
		cbFornecedor.addItem("Selecione");
		ArrayList<Fornecedor>  lista = controller.listaFornecedores();
		for (Fornecedor fornecedor : lista) {
			cbFornecedor.addItem(fornecedor.getIdFornecedor() + " - " + fornecedor.getNome());
			
		}
	
	}
}