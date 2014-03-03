package game;
import java.io.*;

public class Location { // Global
	  int Id;  //array stores all of the location number ids, 0-15 
	  String name;
	  String description;
	  String item; // will call on anther class, Item in the future.
	 public Location(int Id){
		  this.Id = Id;
	}
	  public void locationName (String locationName){
	  this.name = locationName;
	  }
	  public void locationDescription (String locationDescription){
		  this.description = locationDescription;
	  }
	  public void locationItem (String locationItem){
		  this.item = locationItem;
	  }
	//
	  
	  public static void main(String[] args) {
		  // KennelTown is where the player starts 
		  Location KennelTown = new Location(0);
		  KennelTown.Id = 0;
		  KennelTown.name = "KennelTown";
		  KennelTown.description = "You are in a peaceful suburb outside of Catnip City";
		  KennelTown.item = "Old Backpack";
	 
		  System.out.println(KennelTown.Id);
		  System.out.println(KennelTown.name);
		  System.out.println(KennelTown.description);
		  System.out.println(KennelTown.item);
		  
		  // Magic Shop is where the player can buy 1 of 3 items for the game, for a knock off purse
		  Location magicShop = new Location(1);
		 magicShop.Id = 1;
		 magicShop.name = "Magic Shop";
		 magicShop.description = "a concert shop that sell various items";
		 magicShop.item = "duct tape handle sword, magic mr turtle, bent battle ax";
	 
		  System.out.println(magicShop.Id);
		  System.out.println(magicShop.name);
		  System.out.println(magicShop.description);
		  System.out.println(magicShop.item);
		  
		  // Icy Cave is a cold cave right out side of kennel town
		  Location icyCave = new Location(2);
		  icyCave.Id = 2;
		  icyCave.name = "Icy Cave";
		  icyCave.description = "A cave created by a glacier, it is covered in ice";
		  icyCave.item = "ice jewerly";
	 
		  System.out.println(icyCave.Id);
		  System.out.println(icyCave.name);
		  System.out.println(icyCave.description);
		  System.out.println(icyCave.item);
		  
		  // Cat Nip City is a busy city were everyone works
		  Location catNipCity = new Location(3);
		  catNipCity.Id = 3;
		  catNipCity.name = "Catnip City ";
		  catNipCity.description = "A busy city were everybody goes to work";
		  catNipCity.item = "Bus Ticket";
	 
		  System.out.println(catNipCity.Id);
		  System.out.println(catNipCity.name);
		  System.out.println(catNipCity.description);
		  System.out.println(catNipCity.item);
		  
		  //unpleasent medaows is a polluted wasteland
		  Location unpleasentMeadows = new Location(4);
		  unpleasentMeadows.Id = 4;
		  unpleasentMeadows.name = "Unpleasent Meadows";
		  unpleasentMeadows.description = "Bady polluted by the animals in Catnip City, it is now a barien wasteland";
		  unpleasentMeadows.item = "Three Headed Fish";
	 
		  System.out.println(unpleasentMeadows.Id);
		  System.out.println(unpleasentMeadows.name);
		  System.out.println(unpleasentMeadows.description);
		  System.out.println(unpleasentMeadows.item);
		  
		  //Space Ship Dealer is a place that sells space ships for daring adventures 
		  Location spaceShipDealer = new Location(5);
		  spaceShipDealer.Id = 5;
		  spaceShipDealer.name = " Whitie's Space Ship Dealer";
		  spaceShipDealer.description = "A space ship dealer that is know for its annoying radio ads";
		  spaceShipDealer.item = "Used Space Ship";
	 
		  System.out.println(spaceShipDealer.Id);
		  System.out.println(spaceShipDealer.name);
		  System.out.println(spaceShipDealer.description);
		  System.out.println(spaceShipDealer.item);
		
		  // vet of horrors is an haunted vetenarians office
		  Location vetOfHorrors = new Location(6);
		  vetOfHorrors.Id = 6;
		  vetOfHorrors.name = "Vet of Horror";
		  vetOfHorrors.description = "An antcent vetneraians office that was used before our anscestors rosed up";
		  vetOfHorrors.item = "Dr. Tortua's Mircale Drug";
	 
		  System.out.println(vetOfHorrors.Id);
		  System.out.println(vetOfHorrors.name);
		  System.out.println(vetOfHorrors.description);
		  System.out.println(vetOfHorrors.item);
		  
		  // uncanny valley is were creepy creatures live
		  Location uncannyValley = new Location(7);
		  uncannyValley.Id = 7;
		  uncannyValley.name = "Uncanny Valley";
		  uncannyValley.description = "Located right out side of Catnip CIty, it is a place where strang animals live";
		  uncannyValley.item = "Book on Phicoclody";
	 
		  System.out.println(uncannyValley.Id);
		  System.out.println(uncannyValley.name);
		  System.out.println(uncannyValley.description);
		  System.out.println(uncannyValley.item);
		  
		  //dragon meat factory is were dragons are rasied for their meat
		  Location dragonMeatFactory = new Location(8);
		  dragonMeatFactory.Id = 8;
		  dragonMeatFactory.name = "Draco's Dragon Meat Factory";
		  dragonMeatFactory.description = "A feedlot and factory that rasies dragons for their meat";
		  dragonMeatFactory.item = "box of dragon broth";
	 
		  System.out.println(dragonMeatFactory.Id);
		  System.out.println(dragonMeatFactory.name);
		  System.out.println(dragonMeatFactory.description);
		  System.out.println(dragonMeatFactory.item);
		 
		  //cheesey toruest aera is where cheap suvinures are sold
		  Location cheesyTourestAera = new Location(9);
		  cheesyTourestAera.Id = 9;
		  cheesyTourestAera.name = "Cheesy Tourest Aera";
		  cheesyTourestAera.description = "A part of Catnip CIty that selles cheap kick nacks and ripoffs to tourest";
		  cheesyTourestAera.item = "knock off purse";
	 
		  System.out.println(cheesyTourestAera.Id);
		  System.out.println(cheesyTourestAera.name);
		  System.out.println(cheesyTourestAera.description);
		  System.out.println(cheesyTourestAera.item);
		  
		  // monturous mountains is were fursome monsters live
		  Location montorousMountains = new Location(10);
		  montorousMountains.Id = 10;
		  montorousMountains.name = "Monturus Montains";
		  montorousMountains.description = "A mountain range famous for its various monsters";
		  montorousMountains.item = "Monster Tooth";
	 
		  System.out.println(montorousMountains.Id);
		  System.out.println(montorousMountains.name);
		  System.out.println(montorousMountains.description);
		  System.out.println(montorousMountains.item);
		  
		  //rusty dugeon- what text adture wouldnt have a dugoen
		  Location rustyDungeon = new Location(11);
		  rustyDungeon.Id = 11;
		  rustyDungeon.name = "Rusty Dungeon";
		  rustyDungeon.description = "A musty rusty dungeon, there has to be a key in there some where";
		  rustyDungeon.item = "rusty musty key";
	 
		  System.out.println(rustyDungeon.Id);
		  System.out.println(rustyDungeon.name);
		  System.out.println(rustyDungeon.description);
		  System.out.println(rustyDungeon.item);
		  
		  //abandent delivment , a consturtction site
		  Location abandonedDeleviment = new Location(12);
		  abandonedDeleviment.Id = 12;
		  abandonedDeleviment.name = "Abandoned Deleviment";
		  abandonedDeleviment.description = "An abandened consturction site that was closed for fincincal reasons";
		  abandonedDeleviment.item = "bulldozer";
	 
		  System.out.println(abandonedDeleviment.Id);
		  System.out.println(abandonedDeleviment.name);
		  System.out.println(abandonedDeleviment.description);
		  System.out.println(abandonedDeleviment.item);
		 
		  // outer outer space- the universe out side of our universe
		  Location outerOuterSpace = new Location(13);
		  outerOuterSpace.Id = 13;
		  outerOuterSpace.name = "Outer Outer Space";
		  outerOuterSpace.description = "the universe just out side of our universe. Not much is known about it";
		  outerOuterSpace.item = "mysturous portial";
	 
		  System.out.println(outerOuterSpace.Id);
		  System.out.println(outerOuterSpace.name);
		  System.out.println(outerOuterSpace.description);
		  System.out.println(outerOuterSpace.item);
		  
		  //crased space ship- an crash site of an space ship
		  Location crashedSpaceShip = new Location(14);
		  crashedSpaceShip.Id = 14;
		  crashedSpaceShip.name = "Crashed Space Ship";
		  crashedSpaceShip.description = "AN space ship that crashed into a teleophone pole. It is specatlated that the pilot was drunk";
		  crashedSpaceShip.item = "dented space ship engine";
	 
		  System.out.println(crashedSpaceShip.Id);
		  System.out.println(crashedSpaceShip.name);
		  System.out.println(crashedSpaceShip.description);
		  System.out.println(crashedSpaceShip.item);
		  
		  // not at evil blimp- where the boos lives
		  Location notAtAllEvilBlimp = new Location(15);
		  notAtAllEvilBlimp.Id = 15;
		  notAtAllEvilBlimp.name = "Not At All Evil Blimp";
		  notAtAllEvilBlimp.description = "a blimp that has been flying over Catnip City. It is were various criminals live";
		  notAtAllEvilBlimp.item = "Don't Push Me butteon";
	 
		  System.out.println(notAtAllEvilBlimp.Id);
		  System.out.println(notAtAllEvilBlimp.name);
		  System.out.println(notAtAllEvilBlimp.description);
		  System.out.println(notAtAllEvilBlimp.item);
		  
		  
		  
		  
		  
	
		  
		          }

}
