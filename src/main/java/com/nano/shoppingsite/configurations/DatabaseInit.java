package com.nano.shoppingsite.configurations;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.nano.shoppingsite.models.Cart;
import com.nano.shoppingsite.models.CartItem;
import com.nano.shoppingsite.models.Product;
import com.nano.shoppingsite.models.SiteUser;
import com.nano.shoppingsite.repositories.CartItemRepository;
import com.nano.shoppingsite.repositories.CartRepository;
import com.nano.shoppingsite.repositories.ProductRepository;
import com.nano.shoppingsite.repositories.UserRepository;

@Configuration
public class DatabaseInit {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	CartRepository cartRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	CartItemRepository cartItemRepository;
	@Autowired
	BCryptPasswordEncoder passwordEncoder; 
	
	@Bean
	CommandLineRunner init() {
    	return (args) -> {
//    		productRepository.deleteAll();
//    		userRepository.deleteAll();
//    		cartRepository.deleteAll();
//    		cartItemRepository.deleteAll();
    		SiteUser user1 = new SiteUser("John", "johnny", passwordEncoder.encode("1234"),"john@email.com");
    		userRepository.save(user1);
    		Product siliconePlug = new Product(1,"Pillow Soft® Silicone Putty Ear Plugs",5000,"The ultimate in earplug comfort, Mack’s® silicone putty molds to the unique contours of any ear",
				new HashSet<>(Arrays.asList("Provides a better, more comfortable fit and seal than custom ear plugs", "Great for sleeping, swimming, studying, bathing, travel, loud events, flying discomfort, etc.", "Noise Reduction Rating (NRR) – 22 decibels"))
			);
    		productRepository.save(siliconePlug);
    		Product flightPlug = new Product(2,"Flightguard™ Airplane Pressure Relief Ear Plugs",14000,"THE COMFORTABLE EARPLUGS FOR FLYING!TM – earplugs that do not hurt your ears",
    				new HashSet<>(Arrays.asList("AERO FILTER regulates ear pressure to ease altitude changes", "Cabin and engine noise is reduced by 26 decibels"))
    			);
    		productRepository.save(flightPlug);
    		Product fidelityPlug = new Product(3,"Hear Plugs® High Fidelity Earplugs",15000,"Unique open-air filter reduces sound levels evenly so music and speech are clear and natural, not muffled",
    				new HashSet<>(Arrays.asList("Great for concerts, jam sessions, nightclubs, loud events, shop work and motorcycles", "Noise Reduction Rating (NRR) – 12 decibels"))
    			);
    		productRepository.save(fidelityPlug);
    		Product ammoPlug = new Product(4,"Earammo® Earplugs for Men",5000,"Step-down design results in one of our most comfortable ear plugs ever",
    				new HashSet<>(Arrays.asList("Fun, unique ammunition shape','Noise Reduction Rating (NRR) – 30 decibels", "Great for loud events, power tools, shooting sports, motor sports, travel, sleeping, etc."))
    			);
    		productRepository.save(ammoPlug);
    		Product dreamweaverPlug = new Product(5,"Dreamweaver™ Contoured Sleep Mask",15000,"The most comfortable sleep mask on the planet",
    				new HashSet<>(Arrays.asList("Contoured design prevents mask from pressing against eyelids and eyelashes while keeping the wearer comfortable inside a curtain of darkness", "Includes 1 pair of Mack’s® Soft Foam Earplugs and storage pouch"))
    			);
    		productRepository.save(dreamweaverPlug);
    		Product earDryer = new Product(6,"Macks® Ear Dryer",70000,"Dry your ears naturally with warm, soothing air",
    				new HashSet<>(Arrays.asList("Dries water from swimmers’ ears in about one minute", "Removes moisture buildup behind hearing aids, providing a more secure fit"))
    			);
    		productRepository.save(earDryer);
    		Product daddyPlug = new Product(7,"Maximum Protection Soft Foam Earplugs",5000,"Finally, a standard-sized foam earplug with a Maximum Noise Reduction Rating of 33 dB! (highest NRR available)",
    				new HashSet<>(Arrays.asList("The “Mack Daddy” of Earplugs™", "Great for loud concerts, motor sports, sleep, shooting sports, power tools, etc."))
    			);
    		productRepository.save(daddyPlug);
    		Product dualPlug = new Product(8,"Ear Seals® Dual Purpose Earplugs",5000,"Dual-purpose earplugs – reduce noise and seal out water",
    				new HashSet<>(Arrays.asList("Dual-durometer design provides a unique combination of super-soft seal rings for unmatched comfort and a stiffer inner core for ease of insertion", "Detachable cord allows user to conveniently hang the earplugs around neck in environments where noise comes and goes","Washable and reusable"))
    			);
    		productRepository.save(dualPlug);
    		Product vizPlug = new Product(9,"Hi Viz Shooters Earplugs Banded Foam",6000,"Banded hearing protection with ergonomically-shaped soft foam ear plugs for comfort and performance",
    				new HashSet<>(Arrays.asList("Great for tactical training, hunting, loud events, target practice, skeet and trap shooting, etc.", "Noise Reduction Rating (NRR) – 28 dB"))
    			);
    		productRepository.save(vizPlug);
    		Product thermaPlug = new Product(10,"ThermaFit™ Soft Foam Ear Plugs",17000,"These ear plugs start firmer to ease insertion then use body heat to conform to the unique contours of your ears",
    				new HashSet<>(Arrays.asList("Discreet and latex free", "Ultimate comfort and performance"))
    			);
    		productRepository.save(thermaPlug);
    		Product dreamgirlPlug = new Product(11,"Dreamgirl™ Contoured Sleep Mask",15000,"The most comfortable sleep mask on the planet",
    				new HashSet<>(Arrays.asList("Contoured design prevents mask from pressing against eyelids and eyelashes while keeping the wearer comfortable inside a curtain of darkness", "Includes 1 pair of Mack’s® Soft Foam Earplugs and storage pouch"))
    			);
    		productRepository.save(dreamgirlPlug);
    		Product snoozerPlug = new Product(12,"Snoozers® Silicone Putty Earplugs",7000,"The ultimate in earplug comfort, Mack’s® silicone putty ear plugs mold to the unique contours of any ear",
    				new HashSet<>(Arrays.asList("Great for snoring spouses, noisy neighbors, air travel, etc.", "No pressure, cover-only design offers unsurpassed comfort"))
    			);
    		productRepository.save(snoozerPlug);
    		Product blackoutPlug = new Product(13,"Blackout™ Soft Foam Earplugs",7000,"Rock out with Mack’s® Blackout® ear plugs!",
    				new HashSet<>(Arrays.asList("Fashionable, discreet color", "Fully skinned and tapered, providing unmatched user comfort and hygiene"))
    			);
    		productRepository.save(blackoutPlug);
    		Product snorePlug = new Product(14,"Snore Blockers™ Soft Foam Earplugs",7000,"Made with super soft foam, these ear plugs are perfect for extended wear in situations where extreme comfort is paramount",
    				new HashSet<>(Arrays.asList("Great for blocking out snoring spouses, roommates, fishing buddies, travel partners, etc.", "#1 Doctor Recommended Brand to get a good night’s sleep when sleeping with a snoring spouse"))
    			);
    		productRepository.save(snorePlug);
    		Product walletPlug = new Product(15,"Roll-Ups™ Wallet Earplugs",7000,"Never forget your ear plugs again!",
    				new HashSet<>(Arrays.asList("These comfortable, silky smooth, soft foam earplugs store flat in your wallet so they’re always handy and roll up in seconds for quick, easy use", "Great for loud concerts, sporting events, nightclubs, shooting sports, worksites, etc."))
    			);
    		productRepository.save(walletPlug);
    		Product acousticPlug = new Product(16,"Acoustic Foam Earplugs",7000,"These ear plugs’ innovative hollow-cut and grooved design provides clearer acoustics and allows for improved communication",
    				new HashSet<>(Arrays.asList("Noise Reduction Rating (NRR) – 20 decibels", "Great for concerts, jam sessions, nightclubs, shop work, etc."))
    			);
    		productRepository.save(acousticPlug);
    		Product dreamgirlSoftPlug = new Product(17,"Dreamgirl™ Soft Foam Ear Plugs",7000,"Soft foam ear plugs designed for women with small or sensitive ear canals",
    				new HashSet<>(Arrays.asList("Unique hollow and flared design maximizes comfort, especially during sleep", "Ultimate comfort for women: softer, smaller, silky smooth and contoured"))
    			);
    		productRepository.save(dreamgirlSoftPlug);
    		Cart cart1 = cartRepository.save(new Cart());
    		cart1.setUser(user1);
    		CartItem cartItem1 = cartItemRepository.save(new CartItem(siliconePlug,cart1,1,new Date().getTime()));
    		CartItem cartItem2 = cartItemRepository.save(new CartItem(acousticPlug,cart1,2,new Date().getTime()));
    		cart1.setCartItems(new HashSet<>(Arrays.asList(cartItem1,cartItem2)));
    		cartRepository.save(cart1);
    	};
    }
	
}
