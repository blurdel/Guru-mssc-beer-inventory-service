package guru.sfg.beer.inventory.service.services;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import guru.sfg.beer.inventory.service.config.JmsConfig;
import guru.sfg.beer.inventory.service.domain.BeerInventory;
import guru.sfg.beer.inventory.service.repositories.BeerInventoryRepository;
import guru.sfg.common.events.NewInventoryEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class NewInventoryListener {

	private final BeerInventoryRepository beerInventoryRepo;
	
	
	@JmsListener(destination = JmsConfig.NEW_INVENTORY_QUE)
	public void listen(NewInventoryEvent event) {
		
		log.debug("Got Inventory: " + event.toString());
		
		beerInventoryRepo.save(BeerInventory.builder()
				.beerId(event.getBeerDto().getId())
				.upc(event.getBeerDto().getUpc())
				.quantityOnHand(event.getBeerDto().getQuantityOnHand())				
				.build());
	}
	
}
