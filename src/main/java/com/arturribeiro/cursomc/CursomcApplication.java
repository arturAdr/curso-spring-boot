package com.arturribeiro.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.arturribeiro.cursomc.domain.Address;
import com.arturribeiro.cursomc.domain.CardPayment;
import com.arturribeiro.cursomc.domain.Category;
import com.arturribeiro.cursomc.domain.City;
import com.arturribeiro.cursomc.domain.Client;
import com.arturribeiro.cursomc.domain.Payment;
import com.arturribeiro.cursomc.domain.PaymentWithBillet;
import com.arturribeiro.cursomc.domain.Product;
import com.arturribeiro.cursomc.domain.Order;
import com.arturribeiro.cursomc.domain.OrderItem;
import com.arturribeiro.cursomc.domain.State;
import com.arturribeiro.cursomc.domain.enums.CustomerType;
import com.arturribeiro.cursomc.domain.enums.OrderStatus;
import com.arturribeiro.cursomc.repositories.AddressRepository;
import com.arturribeiro.cursomc.repositories.CategoryRepository;
import com.arturribeiro.cursomc.repositories.CityRepository;
import com.arturribeiro.cursomc.repositories.ClientRepository;
import com.arturribeiro.cursomc.repositories.OrderItemRepository;
import com.arturribeiro.cursomc.repositories.PaymentRepository;
import com.arturribeiro.cursomc.repositories.ProductRepository;
import com.arturribeiro.cursomc.repositories.OrderRepository;
import com.arturribeiro.cursomc.repositories.StateRepository;


@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired OrderItemRepository orderItemRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Category cat1 = new Category(null, "Informática");
		Category cat2 = new Category(null, "Escritório");
		
		Product prod1 = new Product(null, "Computador", 2000.00);
		Product prod2 = new Product(null, "Impressora", 800.00);
		Product prod3 = new Product(null, "Mouse", 80.00);

		cat1.getProducts().addAll(Arrays.asList(prod1, prod2, prod3));
		cat2.getProducts().addAll(Arrays.asList(prod2));
		
		prod1.getCategories().addAll(Arrays.asList(cat1));
		prod2.getCategories().addAll(Arrays.asList(cat1, cat2));
		prod3.getCategories().addAll(Arrays.asList(cat1));
		
		State state1 = new State(null, "São Paulo");
		State state2 = new State(null, "Minas Gerais");
		
		City city1 = new City(null, "São Paulo", state1);
		City city2 = new City(null, "Campinas", state1);
		City city3 = new City(null, "Urberlandia", state2);
		City city4 = new City(null, "Belo Horizonte", state2);
		
		state1.getCities().addAll(Arrays.asList(city1, city2));
		state2.getCities().addAll(Arrays.asList(city3, city4));
		
		Client cli1 = new Client(null, "Artur Ribeiro", "artur.ribeiro@gmail.com.br", "43602763897", CustomerType.PF);
		
		cli1.getPhones().add("11949545729");;
		
		Address address1 = new Address(null, "av nova cantareiea", "1484", "ap 102", "tucuruvi", "02330001", cli1, city1);
		Address address2 = new Address(null, "av nova cantareiea", "1484", "ap 102", "tucuruvi", "02330001", cli1, city1);

		cli1.getAdresses().addAll(Arrays.asList(address1, address2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Order order1 = new Order(null, sdf.parse("30/09/2021 10:32"), cli1, address2);
		Order order2 = new Order(null, sdf.parse("01/09/2021 10:32"), cli1, address2);

		Payment pay1 = new CardPayment(null, OrderStatus.PAID, order1, 6);
		order1.setPayment(pay1);
	
		Payment pay2 = new PaymentWithBillet(null, OrderStatus.PENDING, order2, sdf.parse("20/10/2017 00:00"), null);
		order2.setPayment(pay2);
		
		cli1.getOrders().addAll(Arrays.asList(order1, order2));
		
		OrderItem odi1 = new OrderItem(order1, prod1, 0.00, 1, 2000.00);
		OrderItem odi2 = new OrderItem(order1, prod3, 0.00, 2, 80.00);
		OrderItem odi3 = new OrderItem(order2, prod2, 100.00, 1, 800.00);

		order1.getItens().addAll(Arrays.asList(odi1, odi2));
		order2.getItens().addAll(Arrays.asList(odi3));
		
		prod1.getItens().addAll(Arrays.asList(odi1));
		prod3.getItens().addAll(Arrays.asList(odi2));
		prod2.getItens().addAll(Arrays.asList(odi3));

		categoryRepository.saveAll(Arrays.asList(cat1, cat2));
		productRepository.saveAll(Arrays.asList(prod1, prod2, prod3));		
		stateRepository.saveAll(Arrays.asList(state1, state2));
		cityRepository.saveAll(Arrays.asList(city1, city2, city3, city4));
		clientRepository.saveAll(Arrays.asList(cli1));
		addressRepository.saveAll(Arrays.asList(address1, address2));
		orderRepository.saveAll(Arrays.asList(order1, order2));
		paymentRepository.saveAll(Arrays.asList(pay1, pay2));
		orderItemRepository.saveAll(Arrays.asList(odi1, odi2, odi3));
	}

}
