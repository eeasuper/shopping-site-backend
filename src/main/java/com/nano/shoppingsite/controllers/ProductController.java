package com.nano.shoppingsite.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nano.shoppingsite.models.Product;
import com.nano.shoppingsite.services.ProductService;


@RestController
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@RequestMapping(method=RequestMethod.GET, value="/product/{id}", produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Product> getOneProduct(@PathVariable("id") Long productId){
		return ResponseEntity.ok().body(productService.getOneProduct(productId));
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/product/all", produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Iterable<Product>> getAllProducts(){
		return ResponseEntity.ok().body(productService.getAllProducts());
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/product/musical", produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<Product>> getMusicalProducts(){
		return ResponseEntity.ok().body(productService.getMusicalProducts());
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/product/flight", produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<Product>> getFlightProducts(){
		return ResponseEntity.ok().body(productService.getFlightProducts());
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/product/product", produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<Product>> getActivityProducts(){
		return ResponseEntity.ok().body(productService.getActivityProducts());
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/product/{id}/thumbnail", produces= MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody ResponseEntity<byte[]> getOneThumbnail(@PathVariable("id") Long productId) throws IOException{
		byte[] image = productService.getOneThumbnail(productId);
	    return ResponseEntity.ok().body(image);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/search/{query}", produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<Product>> getSearch(@PathVariable("query") String query) throws IOException{
		List<Product> videoList = productService.getSearch(query);
	    return ResponseEntity.ok().body(videoList);
	}
	
}
