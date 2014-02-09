
@@ -0,0 +1,476 @@
+<!DOCTYPE HTML>
+<!-- When the player reaches Billy Bob's, he must play a game of Hangman.  He can not go any further until he wins.  The game is controlled by the function hangman(). -->
+<!-- If the player loses, hangman() is called recurvisely until he wins.  The winning answer is sinso. -->
+   <html>
+     <head>
+ 
+      <title> Antagonist Carlos </title>
+ 
+            <script type = "text/javascript" src= "locations5v4.js"></SCRIPT>
+
+            <script>
+
+ var total = 0;
+		  		
+      function Location(id,name,description,item) {
+         this.id = id;
+         this.name = name;
+         this.description = description;
+         this.item = item;
+      this.toString=function() {
+        var retVal = "";
+        retVal = this.description;
+        return retVal;
+     }
+   }
+
+                var airport = new Location("airport","airport","You are stuck at an airport terminal.  Your flight is in 12 hours.", "Airplane Ticket");
+                var billybobs = new Location("billybobs","billybobs","You are at Billie Bob's Biker Bar and Bar-B-Q.", "Pistol");
+                var robs = new Location("robs","robs","You are standing next to Robs's Rent-A-Car.  There are several cars available, some with their windows open.", "Car Keys");
+                var street = new Location("street","street","You are 361 St.  It is a charming street, filled with shops and gift stores.", "Clothes");
+                var apartment = new Location("apartment","apartment","You are at your apartment building.", "");
+                var alley = new Location("alley","alley","You are in a back alley. You fish out a ratty town map from your storm grey suit pocket and look up Sinso Publishing.", "");
+                var bookstore = new Location("bookstore","bookstore","You are back at the bookstore.", "");
+                var subway = new Location("subway","subway","You have entered a subway station.", "");
+                var construction = new Location("construction","construction","You are wandering around an abandoned construction site.", "");
+                var sinso = new Location("sinso","sinso","You have arrived at Sinso Publishing.", "");
+
+                var arrayLocation = new Array();
+
+                arrayLocation[0] = apartment;
+                arrayLocation[1] = airport;
+                arrayLocation[2] = alley;
+                arrayLocation[3] = bookstore;
+                arrayLocation[4] = robs;
+                arrayLocation[5] = subway;
+                arrayLocation[6] = street;
+                arrayLocation[7] = construction;
+                arrayLocation[8] = sinso;
+                arrayLocation[9] = billybobs;
+
+                var westButton = new Array();
+                    westButton[0] = true;
+                    westButton[1] = false;
+                    westButton[2] = true;
+                    westButton[3] = false;
+                    westButton[4] = false;
+                    westButton[5] = true;
+                    westButton[6] = false;
+                    westButton[7] = true;
+                    westButton[8] = false;
+                    westButton[9] = false;
+
+                var eastButton = new Array();
+                    eastButton[0] = false;
+                    eastButton[1] = true;
+                    eastButton[2] = false;
+                    eastButton[3] = false;
+                    eastButton[4] = true;
+                    eastButton[5] = false;
+                    eastButton[6] = true;
+                    eastButton[7] = false;
+                    eastButton[8] = true;
+                    eastButton[9] = false;
+
+                var southButton = new Array();
+                    southButton[0] = false;
+                    southButton[1] = false;
+                    southButton[2] = false;
+                    southButton[3] = false;
+                    southButton[4] = true;
+                    southButton[5] = false;
+                    southButton[6] = false;
+                    southButton[7] = true;
+                    southButton[8] = true;
+                    southButton[9] = true;
+
+                var northButton = new Array();
+                    northButton[0] = true;
+                    northButton[1] = true;
+                    northButton[2] = true;
+                    northButton[3] = false;
+                    northButton[4] = false;
+                    northButton[5] = false;
+                    northButton[6] = false;
+                    northButton[7] = false;
+                    northButton[8] = true;
+                    northButton[9] = false;
+
+                var takeButton = new Array();
+                    takeButton[0] = true;
+                    takeButton[1] = false;
+                    takeButton[2] = true;
+                    takeButton[3] = true;
+                    takeButton[4] = false;
+                    takeButton[5] = true;
+                    takeButton[6] = false;
+                    takeButton[7] = true;
+                    takeButton[8] = true;
+                    takeButton[9] = false;                
+
+    function Item(id,name,description) {
+      this.id = id;
+      this.name = name;
+      this.description = description;
+      this.toString=function() {
+        var retVal = "";
+        retVal = this.description;
+        return retVal;
+     }
+  }
+
+    var ticket = new Item("ticket","Airplane Ticket","There is an airplane ticket here.");
+    var carkeys = new Item("carkeys","Car Keys","There are car keys on the ground");
+    var clothes = new Item("clothes","Clothes","There is a pile of clothes, just your size, lying here");
+    var pistol = new Item("pistol","Pistol","There is a pistol on the bar.");
+    var nothing = new Item ("","","");
+
+                var arrayItem = new Array();
+                arrayItem[0] = nothing;
+                arrayItem[1] = ticket;
+                arrayItem[2] = nothing;
+                arrayItem[3] = nothing;
+                arrayItem[4] = carkeys;
+                arrayItem[5] = nothing;
+                arrayItem[6] = clothes;
+                arrayItem[7] = nothing;
+                arrayItem[8] = nothing;
+                arrayItem[9] = pistol;
+
+              
+                var totalInventory = new Array();
+
+                var arrayMap = new Array();
+                arrayMap[0] = 'sinsomap0.gif';
+                arrayMap[1] = 'sinsomap1.gif';
+                arrayMap[2] = 'sinsomap2.gif';
+                arrayMap[3] = 'sinsomap3.gif';
+                arrayMap[4] = 'sinsomap4.gif';
+                arrayMap[5] = 'sinsomap5.gif';
+                arrayMap[6] = 'sinsomap6.gif';
+                arrayMap[7] = 'sinsomap7.gif';
+                arrayMap[8] = 'sinsomap8.gif';
+                arrayMap[9] = 'sinsomap9.gif';
+
+                var hangMap = new Array();
+                hangMap[0] = 'hangman1.gif';
+                hangMap[1] = 'hangman2.gif';
+                hangMap[2] = 'hangman3.gif';
+                hangMap[3] = 'hangman4.gif';
+                hangMap[4] = 'hangman5.gif';
+                hangMap[5] = 'hangman6.gif';
+                hangMap[6] = 'hangman7.gif';
+
+
+
+
+
+
+                var hasVistedNorth = false;
+		var hasVistedSouth = false;
+		var hasVistedEast = false;
+		var hasVistedWest = false;
+		var hasVistedBillyBobs = false;
+                var oldLocationIndex = 3;
+                var locationIndex = 3;
+                var inventoryCount = 0;
+
+          var moveMatrix = new Array(10);
+          moveMatrix[0] = new Array(4);
+          moveMatrix[1] = new Array(4);
+          moveMatrix[2] = new Array(4);
+          moveMatrix[3] = new Array(4);
+          moveMatrix[4] = new Array(4);
+          moveMatrix[5] = new Array(4);
+          moveMatrix[6] = new Array(4);
+          moveMatrix[7] = new Array(4);
+          moveMatrix[8] = new Array(4);
+          moveMatrix[9] = new Array(4);
+  
+          moveMatrix[0][0] = "0";
+          moveMatrix[0][1] = "1";
+          moveMatrix[0][2] = "0";
+          moveMatrix[0][3] = "3";
+
+          moveMatrix[1][0] = "0";
+          moveMatrix[1][1] = "1";
+          moveMatrix[1][2] = "1";
+          moveMatrix[1][3] = "4";
+
+          moveMatrix[2][0] = "2";
+          moveMatrix[2][1] = "3";
+          moveMatrix[2][2] = "2";
+          moveMatrix[2][3] = "5";
+
+          moveMatrix[3][0] = "2";
+          moveMatrix[3][1] = "4";
+          moveMatrix[3][2] = "0";
+          moveMatrix[3][3] = "6";
+
+          moveMatrix[4][0] = "3";
+          moveMatrix[4][1] = "4";
+          moveMatrix[4][2] = "1";
+          moveMatrix[4][3] = "4";
+
+          moveMatrix[5][0] = "5";
+          moveMatrix[5][1] = "6";
+          moveMatrix[5][2] = "2";
+          moveMatrix[5][3] = "7";
+
+          moveMatrix[6][0] = "5";
+          moveMatrix[6][1] = "6";
+          moveMatrix[6][2] = "3";
+          moveMatrix[6][3] = "9";
+
+          moveMatrix[7][0] = "7";
+          moveMatrix[7][1] = "9"; 
+          moveMatrix[7][2] = "5";
+          moveMatrix[7][3] = "7";
+
+          moveMatrix[8][0] = "9";
+          moveMatrix[8][1] = "8";
+          moveMatrix[8][2] = "8";
+          moveMatrix[8][3] = "8";
+
+          moveMatrix[9][0] = "7";
+          moveMatrix[9][1] = "8";
+          moveMatrix[9][2] = "6";
+          moveMatrix[9][3] = "9";       
+       
+             
+// function take() allows player to take items and add to inventory
+
+	function take() {
+          if (arrayLocation[locationIndex].item === "")
+            nothingtotakeMsg();
+        else {
+          takeMsg();
+          totalInventory[inventoryCount] = arrayItem[locationIndex].name;
+          arrayLocation[locationIndex].item = "";
+          arrayItem[locationIndex].description = "";
+          inventoryCount = inventoryCount + 1;
+          total = total + 10;
+          updateDisplay("You got 10 points for taking the " + arrayItem[locationIndex].name);
+          takeButton[locationIndex] = true;
+        }
+      }
+
+/*  Function move(direction) takes input from either the text box or the buttons.  Input will be either 1, 2, 3 or 4, depending on the direction chosen.  Function has 4 parts corresponding
+		to the four directions you can move in.  Each part then makes the move based on the input direction and updates the score.  */
+
+        function move(directionIndex) {
+              locationIndex = moveMatrix[locationIndex][directionIndex];
+              if (oldLocationIndex == locationIndex)
+                 ycngtwMsg();
+              oldLocationIndex = locationIndex;
+	      moveMsg(locationIndex);
+              var img = document.getElementById("map");
+              img.src = arrayMap[locationIndex];
+              edButton();
+
+              if (!hasVistedBillyBobs && locationIndex == 9) {
+                 hasVistedBillyBobs = true;
+                 hangman();
+              }
+              
+              if (!hasVistedSouth && directionIndex == 3) {
+                 total = total + 5;
+                 hasVistedSouth = true;
+	     } 
+              if (!hasVistedNorth && directionIndex == 2) {
+                 total = total + 5;
+                 hasVistedNorth = true;
+	     }
+              if (!hasVistedEast && directionIndex == 1) {
+                 total = total + 5;
+                 hasVistedEast = true;
+	     }
+              if (!hasVistedWest && directionIndex == 0) {
+                 total = total + 5;
+                 hasVistedWest = true;
+	     } 
+       }
+    function hangman() {
+                var guessSpace = new Array();
+                guessSpace[0] = "-";
+                guessSpace[1] = "-";
+                guessSpace[2] = "-";
+                guessSpace[3] = "-";
+                guessSpace[4] = "-";
+        hangFailCount = 0;
+        updateDisplay ("You have to win at Hangman before you can go on");
+        updateDisplay (guessSpace[0] + " " + guessSpace[1] + " " + guessSpace[2] + " " + guessSpace[3] + " " + guessSpace[4]);
+        while (hangFailCount < 6) {  
+           var img = document.getElementById("map");
+           img.src = hangMap[hangFailCount];
+           var hangGuess = prompt ("Please guess a lower case letter","");
+		switch (hangGuess){
+		case "s": {
+                guessSpace[0] = "s";
+                guessSpace[3] = "s";
+                }
+		break;
+		case "i": guessSpace[1] = "i";
+ 		break;
+		case "n": guessSpace[2] = "n";
+		break;
+		case "o": guessSpace[4] = "o";
+		break;
+                default: { 
+                hangFailCount = hangFailCount +1;
+                img.src = hangMap[hangFailCount];
+                         }
+		}  
+           updateDisplay (guessSpace[0] + " " + guessSpace[1] + " " + guessSpace[2] + " " + guessSpace[3] + " " + guessSpace[4]);
+           if (guessSpace[0] === "s" && guessSpace[1] === "i" && guessSpace[2] === "n" && guessSpace[3] === "s" && guessSpace[4] === "o") {
+              updateDisplay("YOU WIN!");
+              var img = document.getElementById("map");
+              img.src = arrayMap[locationIndex];
+          return;
+          }
+    }   
+   updateDisplay ("Hint:  It's the name of the publisher - all lower case.  Look on the map.");
+hangman()
+}
+		
+//  Function edButton() - enable disable button - enables or disables buttons depending on the locationIndex.
+
+	function edButton() {              
+			document.getElementById("wbutton").disabled = westButton[locationIndex];
+			document.getElementById("ebutton").disabled = eastButton[locationIndex];
+			document.getElementById("sbutton").disabled = southButton[locationIndex];
+			document.getElementById("nbutton").disabled = northButton[locationIndex];
+                        document.getElementById("tbutton").disabled = takeButton[locationIndex];
+	}
+
+// Function score() displays player score
+
+	function score(){
+		updateDisplay("Your score is  " + total);
+	}
+
+// Function inventoryBox() displays player inventory
+
+	function inventoryBox(){
+                counter = 1;
+                if (inventoryCount == 0)
+                  updateDisplay("You do not have any items");
+                else {
+                  while (counter <= inventoryCount){
+                  updateDisplay(totalInventory[counter - 1]);
+                  counter = counter + 1;
+                }
+                updateDisplay("You have the following items ");
+		}
+        }       
+
+// Function helpBox() displays the user help
+
+        function helpBox(){
+		updateDisplay ("The object of the game is to move around the map until you find Sinso Publishing." + "\n" + "You can either press a direction button or type E, e, W, w, N, n, S or s to move around." + "\n" + "Typing T or t will take an object (if available)." + "\n" + "Typing I or i will display your current inventory." + "\n" + "Typing H or h will bring up help information" + "\n" + "You can press the Score button to find your current score.");
+	}
+	
+//  Function updateDisplay(message) take one arguments and update the text box with that argument
+
+	function updateDisplay(message){
+		var ta = document.getElementById("game");
+		ta.value = message + "\n" + "\n" + ta.value;
+	}
+  
+// Function compass() takes user direction input for the four direction or take commands and clears input box after direction is input
+
+	function compass(){
+		var iDirection = document.getElementById("inputDirection");
+		userDirection = iDirection.value;
+		document.getElementById("inputDirection").value = "";
+		switch (userDirection){
+		case "W": move(0);
+		break;
+		case "w": move(0);
+ 		break;
+		case "E": move(1);
+		break;
+		case "e": move(1);
+		break;
+		case "N": move(2);
+		break;
+ 		case "n": move(2);
+		break;
+		case "S": move(3);
+		break;
+ 		case "s": move(3);
+		break;
+ 		case "T": take();
+		break;
+ 		case "t": take();
+		break;
+ 		case "I": inventoryBox();
+		break;
+ 		case "i": inventoryBox();
+		break;
+ 		case "H": helpBox();
+		break;
+ 		case "h": helpBox();
+		break;
+		default:updateDisplay("I don't understand that input.  Valid values are N, n, S, s, E, e, W, w, T, t, I and i.");
+		}
+ 	}
+
+// Function init() puts initial text in the textarea
+        function init() {
+                updateDisplay("You are in a bookstore. There, on a shelf, a cheap paper back thriller catches your attention.  You lift it up and see that is called \"Drug Lord Down\". Curious and slighty board, you open up the book to a random page, only to discover that your name, Carlos Lucus Forerunner, is in the book. You also realize that the book is through the police's view, and that you are the antagonist. Shocked, you read on, on to discover that he is, like you, a drug smuggler that belongs to the gang. The Pitbulls. He is also being chased by the Dark City Police and his wife was murdered by a rival gang member, just like you. You buy the book and look at the spine. The book was published by a company called Sinso and you realize that your only chance is to get to Sinso.");
+        }
+	</script>	
+	</head>
+	<body onload = "init()">
+
+	<h1>Antagonist Carlos</h1>
+
+<!-- Display map -->
+        <img id = map src = "sinsomap3.gif" width= "400" height = "400" >
+
+<!-- Set up buttons -->
+	<input	type = "button"
+		id = "wbutton"
+		value = "West"
+		onclick= "move(0);">
+	<input	type = "button"
+		id = "ebutton"
+		Value = "East"
+		onclick= "move(1);">
+	<input	type = "button"
+		id = "nbutton"
+		value = "North"
+		onclick= "move(2);">
+	<input 	type = "button"
+		id = "sbutton"
+		value = "South"
+		onclick = "move(3);">
+        <input 	type = "button"
+                id = "tbutton"
+		value = "Take"
+		onclick = "take();">
+        <input 	type = "button"
+		value = "Inventory"
+		onclick = "inventoryBox();">
+        <input 	type = "button"
+		value = "Score"
+		onclick = "score();">
+        <input	type = "button"
+		value = "Help"
+		onclick = "helpBox();">
+	
+       <br>
+
+<!-- Set up command input area and clear after receiving input -->
+	Command: <input type = "text" id= "inputDirection"><br>
+	<input type = "button" value = "Go" onclick = "compass() ;"><br>
+
+         <p>
+<!-- Set up textarea to give player information -->
+	<textarea align = "center" rows = "15" cols = "100" id = "game">  </textarea>
+	</p>
+ 	<br>
+	<a href = "mailto:Amelia.Beckham2@marist.edu">This is my mailto</a>
+ 
+	</body> 
