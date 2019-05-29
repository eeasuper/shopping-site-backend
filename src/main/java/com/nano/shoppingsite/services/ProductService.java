package com.nano.shoppingsite.services;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nano.shoppingsite.exceptions.ElementNotFoundException;
import com.nano.shoppingsite.models.Product;
import com.nano.shoppingsite.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;
	
	private final List<Long> musicalProducts = new ArrayList<>(Arrays.asList((long)3,(long)8,(long)9,(long)16));
	private final List<Long> flightProducts = new ArrayList<>(Arrays.asList((long)1,(long)2,(long)4,(long)5,(long)7,(long)11,(long)12,(long)14,(long)15));
	private final List<Long> activityProducts = new ArrayList<>(Arrays.asList((long)1,(long)6,(long)7,(long)8,(long)9));
	
	public Product getOneProduct(Long productId) {
		return productRepository.findById(productId).orElseThrow(()->new ElementNotFoundException("productId: "+productId.toString()));
	}
	
	public Iterable<Product> getAllProducts(){
		Iterable<Product> iterable= productRepository.findAll();
		return iterable;
	}
	
	public List<Product> getMusicalProducts(){
		List<Product> list = new ArrayList<>();
		musicalProducts.forEach((productId)->{
			list.add(productRepository.findById(productId).orElseThrow(()->new ElementNotFoundException("productId: "+productId.toString())));
		});
		return list;
	}
	
	public List<Product> getFlightProducts(){
		List<Product> list = new ArrayList<>();
		flightProducts.forEach((productId)->{
			list.add(productRepository.findById(productId).orElseThrow(()->new ElementNotFoundException("productId: "+productId.toString())));
		});
		return list;
	}
	
	public List<Product> getActivityProducts(){
		List<Product> list = new ArrayList<>();
		activityProducts.forEach((productId)->{
			list.add(productRepository.findById(productId).orElseThrow(()->new ElementNotFoundException("productId: "+productId.toString())));
		});
		return list;
	}
	
	public byte[] getOneThumbnail(Long productId) throws IOException{
		Product product = productRepository.findById(productId).orElseThrow(()->new ElementNotFoundException("productId: "+productId.toString()));
		String filename = "ear-"+product.getId() + ".jpg";
		String file = "thumbnail-dir/"+filename;
		byte[] fileContent = FileUtils.readFileToByteArray(new File(file));
		InputStream in = new ByteArrayInputStream(fileContent);
		return IOUtils.toByteArray(in);
	}

	public List<Product> getSearch(String query){
		return productRepository.findByNameContainingIgnoreCase(query);
	}
}
