package kr.co.kmarket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.kmarket.entity.ProductEntity;

public interface ProductRepo extends JpaRepository<ProductEntity, String>{
	
	List<ProductEntity> findByProdNoContaining(int keyWord);
	
}
