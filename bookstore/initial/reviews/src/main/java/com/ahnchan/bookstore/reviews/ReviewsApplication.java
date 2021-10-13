package com.ahnchan.bookstore.reviews;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

//@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
public class ReviewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewsApplication.class, args);
	}

}

@RestController
class ReviewController {

	@Autowired
	private ReviewRepository repository;

	@Autowired
	private DiscoveryClient discoveryClient;
	
	@GetMapping("/products/reviews")
	public List<Review> getReviews() {
		return repository.findAll();
	}

	@GetMapping("/products/{id}/reviews")
	public List<Review> getReviewsByProductId(@PathVariable Integer id) {
		
		System.out.println("Product Id : "+ id);

		return repository.retrivefindById_product(id);
	}
}

interface ReviewRepository extends JpaRepository<Review, Integer> {
	@Query("select r from Review r where r.id_product = :id_product")
	List<Review> retrivefindById_product(@Param("id_product") Integer id_product);
}

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
class Review {
	@Id
	private Integer id;
	private Integer id_product;
	private String reviewer;
	private String text;
}
