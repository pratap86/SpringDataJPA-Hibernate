package com.pratap.springdata;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.pratap.springdata.entities.EmployeeEntity;
import com.pratap.springdata.entities.ProductEntity;
import com.pratap.springdata.repos.EmployeeRepository;
import com.pratap.springdata.repos.ProductRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
class SpringDataJpaApplicationTests {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	private ProductEntity product;
	
	private EmployeeEntity employee;
	
	@BeforeEach
	void setup() {
		product = new ProductEntity();
		product.setName("iPhone");
		product.setDescription("Awesome");
		product.setPrice(2340.80);
		
		employee = new EmployeeEntity();
		employee.setFirstName("Pratap");
		employee.setLastName("Narayan");
	}
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void testCreateProduct() {
		ProductEntity savedEntity = productRepository.save(product);
		assertThat(savedEntity.getName(), equalTo(product.getName()));
	}
	
	@Test
	void testReadProduct() {
		ProductEntity productEntity = productRepository.findById(101).orElse(new ProductEntity());
		assertThat(productEntity.getDescription(), equalTo("Awesome"));
	}
	
	@Test
	void testProductUpdate() {
		ProductEntity productEntity = productRepository.findById(101).orElse(new ProductEntity());
		productEntity.setDescription("Very awesome product");
		ProductEntity updatedEntity = productRepository.save(productEntity);
		assertThat(updatedEntity.getDescription(), equalTo("Very awesome product"));
	}
	
	@Test
	void testDeleteProduct() {
		if(productRepository.existsById(101)) {
			productRepository.deleteById(101);
			ProductEntity productEntity = productRepository.findById(101).orElse(null);
			assertThat(productEntity, nullValue());
		} else {
			assertEquals(1, " : ID not present..");
		}
		
	}
	
	@Test
	void testCreateEmployee() {
		EmployeeEntity savedEmployee = employeeRepository.save(employee);
		assertThat(savedEmployee.getFirstName(), equalTo(employee.getFirstName()));
	}
	
	@Test
	void testProductFindByName() {
		List<ProductEntity> products = productRepository.findByName("product1");
		assertThat(products, hasSize(2));
	}
	
	@Test
	void testProductFindByNameAndDescription() {
		List<ProductEntity> products = productRepository.findByNameAndDescription("product4", "Awesome product");
		assertThat(products.get(0).getPrice(), equalTo(2600.0));
	}
	
	@Test
	void testProductFindByPriceGreaterThan() {
		List<ProductEntity> products = productRepository.findByPriceGreaterThan(2300.0);
		assertThat(products, hasSize(3));
	}
	
	@Test
	void testProductFindByDescriptionContains() {
		List<ProductEntity> products = productRepository.findByDescriptionContains("product");
		assertThat(products, hasSize(3));
	}

	@Test
	void testProductFindByPriceBetween() {
		List<ProductEntity> products = productRepository.findByPriceBetween(2351.0, 2600.0);
		assertThat(products, hasSize(2));
	}
	@Test
	void testProductFindByDescriptionLike() {
		List<ProductEntity> products = productRepository.findByDescriptionLike("Very%");
		assertThat(products, hasSize(1));
	}
	@Test
	void testProductFindByIdIn() {
		List<ProductEntity> products = productRepository.findByIdIn(Arrays.asList(101, 103, 104));
		assertThat(products, hasSize(3));
	}

}