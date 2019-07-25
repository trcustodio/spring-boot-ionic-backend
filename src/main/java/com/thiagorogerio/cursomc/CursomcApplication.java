package com.thiagorogerio.cursomc;

import java.text.SimpleDateFormat;
/**
 * @author trcustodio
 */
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.thiagorogerio.cursomc.domain.Categoria;
import com.thiagorogerio.cursomc.domain.Cidade;
import com.thiagorogerio.cursomc.domain.Cliente;
import com.thiagorogerio.cursomc.domain.Endereco;
import com.thiagorogerio.cursomc.domain.Estado;
import com.thiagorogerio.cursomc.domain.Pagamento;
import com.thiagorogerio.cursomc.domain.PagamentoComBoleto;
import com.thiagorogerio.cursomc.domain.PagamentoComCartao;
import com.thiagorogerio.cursomc.domain.Pedido;
import com.thiagorogerio.cursomc.domain.Produto;
import com.thiagorogerio.cursomc.domain.enums.EstadoPagamento;
import com.thiagorogerio.cursomc.domain.enums.TipoCliente;
import com.thiagorogerio.cursomc.repositories.CategoriaRepository;
import com.thiagorogerio.cursomc.repositories.CidadeRepository;
import com.thiagorogerio.cursomc.repositories.ClienteRepository;
import com.thiagorogerio.cursomc.repositories.EnderecoRepository;
import com.thiagorogerio.cursomc.repositories.EstadoRepository;
import com.thiagorogerio.cursomc.repositories.PagamentoRepository;
import com.thiagorogerio.cursomc.repositories.PedidoRepository;
import com.thiagorogerio.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepositoty;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria categoria1 = new Categoria(null, "Informática");
		Categoria categoria2 = new Categoria(null, "Escritório");

		Produto produto1 = new Produto(null, "Computador", 2000.00);
		Produto produto2 = new Produto(null, "Impressora", 800.00);
		Produto produto3 = new Produto(null, "Mouse", 80.00);

		categoria1.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3));
		categoria2.getProdutos().addAll(Arrays.asList(produto2));

		produto1.getCategorias().addAll(Arrays.asList(categoria1));
		produto2.getCategorias().addAll(Arrays.asList(categoria1, categoria2));
		produto3.getCategorias().addAll(Arrays.asList(categoria1));

		categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2));
		produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3));

		Estado estado1 = new Estado(null, "Minas Gerais");
		Estado estado2 = new Estado(null, "São Paulo");

		Cidade cidade1 = new Cidade(null, "Uberlândia", estado1);
		Cidade cidade2 = new Cidade(null, "São Paulo", estado2);
		Cidade cidade3 = new Cidade(null, "Campinas", estado2);

		estado1.getCidades().addAll(Arrays.asList(cidade1));
		estado2.getCidades().addAll(Arrays.asList(cidade2, cidade3));

		estadoRepositoty.saveAll(Arrays.asList(estado1, estado2));
		cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3));

		Cliente cliente1 = new Cliente(null, "Marai da Silva", "maria@gmail.com", "36378912377",
				TipoCliente.PESSOA_FISICA);

		cliente1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));

		Endereco endereco1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cidade1,
				cliente1);
		Endereco endereco2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cidade2,
				cliente1);

		cliente1.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));

		clienteRepository.saveAll(Arrays.asList(cliente1));
		enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido pedido1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cliente1, endereco1);
		Pedido pedido2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cliente1, endereco2);

		Pagamento pagamento1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedido1, 6);
		pedido1.setPagamento(pagamento1);

		Pagamento pagamento2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pedido2,
				sdf.parse("20/10/2017 00:00"), null);
		pedido2.setPagamento(pagamento2);

		cliente1.getPedidos().addAll(Arrays.asList(pedido1, pedido2));
		
		pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
		pagamentoRepository.saveAll(Arrays.asList(pagamento1, pagamento2));

	}

}
