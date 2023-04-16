/*
 Author: Daniel Akselrod
 File Name: Main.java
 Project Name: ISU
 Creation Date: December 20, 2018
 Modified Date: December 21, 2018
 Description: This program is a game 
 that is based of star wars.
 The purpose of the game is 
 for the user to try to save 
 the galaxy from the hideous empire.
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import com.engine.core.*;
import com.engine.core.gfx.*;

public class Main extends AbstractGame
{
	//Required Basic Game Functional Data	
	private static String gameName = "Star Wars Infinity";

	private static int windowWidth = 640;	
	private static int windowHeight =800;
	private static int fps = 60;
	
	//Stores cordinates of messages
	static int [] messageLocationsX = new int [10];
	static int [] messageLocationsY = new int [10];
	static int [] helpMessageLocationsX = new int [10];
	static int [] helpMessageLocationsY = new int [10];
	
	//Stores text messages
	static String messages [] = new String [10];
	static String helpMessages [] = new String [10];

	
	//Stores boolean values
	static boolean reloading = false;
	static boolean tieStatus;
	static boolean move = false;
	static boolean darthVaderMovement;
	
	//Stores images
	static SpriteSheet healthKitImg;
	static SpriteSheet miniExplosionAni;
	static SpriteSheet spaceImg;
	static SpriteSheet shipImg;
	static SpriteSheet tieImg;
	static SpriteSheet meteorImg;
	static SpriteSheet titleText;
	static SpriteSheet [] bulletImg = new SpriteSheet [21];
	static SpriteSheet [] enemyBulletImg = new SpriteSheet [21];
	static SpriteSheet menuBackgroundImg;
	static SpriteSheet gameBackgroundImg1;
	static SpriteSheet gameBackgroundImg2;
	static SpriteSheet ammoImg;
	static SpriteSheet darthVaderTieImg;
	static SpriteSheet darthVaderBulletImg[] = new SpriteSheet [21]; 
	
	//Stores Numerical Values
	static int reloadStartTime;
	static int reloadEndTime;
	static int ammoInClip;
	static int score;
	static int stage;	
	static int healthPickupNumber = 1;
	static int framesPassedSinceReload;
	static int reloadTime;
	static int replenishedAmmo;
	static int framesPassedSinceWin;
	static int framesPassedSinceMovement;
	static int movementTime;
	static int introFramesPassed;
	static int introSecondsPassed;	
	static int framesPassed;
	static int secondsPassed;
	static int programPhase;	
	static int scrollSpeed = 5;
	static int darthVaderHealth = 10;
	static int totalReplacedBullets;
	static int health = 10;
	static int shotsFired;
	static int currentShot;	
	static int currentEnemyShot = 0;
	static int meteorNumber = 1;	
	static int tieNumber = 1;
	static int currentTie;
	static int userBulletSpeed = 8;
	static int userShipSpeed = 3;	
	static int enemyBulletSpeed = 6;
	static int enemyMovementSpeed = 2;	
	static int meteorMovementSpeed = 4;	
	static int movementNumber = 1;
	static int totalAmmo = 20;
	static int ammoNumber = 1;
	static int enemyFireRate = 150;	
	static int reloadSpeed = 5;
	static int framesPassedSinceDeath;
	static int darthVaderFireRate = 60;
	static int darthVaderShot;
	static int [] tieLocations = new int [100];
	
	//Stores SOundClips
	static SoundClip explosionSnd;
	static SoundClip shotSnd;
	static SoundClip inGameMusic;
	static SoundClip menuMusic;
	static SoundClip creditsMusic;
	static SoundClip lossMusic;
	
	//Stores Mouse Position
	private static Vector2F mousePos = new Vector2F(0f, 0f);
	
	//Stores Fonts
	Font statusFont = new Font("Star Jedi Rounded", Font.PLAIN, 20);
	Font bigFont = new Font("Star Jedi Rounded", Font.PLAIN, 50);
	Font smallFont = new Font("Star Jedi Rounded", Font.PLAIN, 30);
	Font tinyFont = new Font("Calibri", Font.BOLD, 15);

	public static void main(String[] args) 
	{
		GameContainer gameContainer = new GameContainer(new Main(), gameName, windowWidth, windowHeight, fps);
		gameContainer.Start();
	}
	@Override
	public void LoadContent(GameContainer gc)
	{
		//Imports and defines images
		shipImg = new SpriteSheet(LoadImage.FromFile("/images/sprites/shipImg.png"));
		tieImg = new SpriteSheet(LoadImage.FromFile("/images/sprites/tieImg.png"));
		darthVaderTieImg = new SpriteSheet(LoadImage.FromFile("/images/sprites/tieImg.png"));
		gameBackgroundImg1 = new SpriteSheet(LoadImage.FromFile("/images/backgrounds/backgroundImg.jpg"));
		gameBackgroundImg2 = new SpriteSheet(LoadImage.FromFile("/images/backgrounds/backgroundImg.jpg"));
		healthKitImg = new SpriteSheet(LoadImage.FromFile("/images/sprites/healthKit.png"));
		miniExplosionAni = new SpriteSheet(LoadImage.FromFile("/images/sprites/explosionAni.png"), 5, 5, 0, 23, 0);
		menuBackgroundImg = new SpriteSheet(LoadImage.FromFile("/images/backgrounds/menuBackgroundImg.jpg"));
		meteorImg = new SpriteSheet(LoadImage.FromFile("/images/sprites/meteorImg.png"));
		ammoImg = new SpriteSheet(LoadImage.FromFile("/images/sprites/ammoImg.png"));
		titleText = new SpriteSheet(LoadImage.FromFile("/images/sprites/titleImage.png"));
		spaceImg = new SpriteSheet(LoadImage.FromFile("/images/backgrounds/spaceImg.jpg"));
		meteorImg.destRec = new Rectangle(0,-50,50,50);		
		ammoImg.destRec = new Rectangle((int)(Math.round(Math.random() * 600 + 20)), -25, 50, 25);		
		shipImg.destRec = new Rectangle(280,620,80,160);
		tieImg.destRec = new Rectangle(0,0,0,0);
		gameBackgroundImg1.destRec = new Rectangle(0,0,640,800);
		gameBackgroundImg2.destRec = new Rectangle(0,-800,640,800);	
		miniExplosionAni.destRec = new Rectangle(0, 0, 0, 0);
		titleText.destRec = new Rectangle (50, 200, 540, 200);
		meteorImg.destRec.x = (int) (Math.round(Math.random() * 500 + 50));
		meteorImg.destRec.y = -50;
		tieImg.destRec = new Rectangle (tieLocations[0], 20, 0, 0);
		tieImg.SetTransparency(1);
		spaceImg.destRec = new Rectangle(0,0,640,800);
		darthVaderTieImg.destRec = new Rectangle((int)(Math.round(Math.random() * 500 + 50)), 20, 0, 0);
		menuBackgroundImg.destRec = new Rectangle(0,0,640,800);
		healthKitImg.destRec = new Rectangle((int)(Math.round(Math.random() * 500 + 50)), -30, 40, 30);
		
		for (int i = 1; i < bulletImg.length; i++)
		{
			bulletImg[i] = new SpriteSheet(LoadImage.FromFile("/images/sprites/bulletImg.png"));

			bulletImg[i].destRec = new Rectangle(0,0,0,0);
			
			darthVaderBulletImg[i] = new SpriteSheet(LoadImage.FromFile("/images/sprites/enemyBulletImg.png"));
			darthVaderBulletImg[i].destRec = new Rectangle(0,0,0,0);
		}	
		
		for (int i = 0; i < enemyBulletImg.length; i++)
		{
			enemyBulletImg[i] = new SpriteSheet(LoadImage.FromFile("/images/sprites/enemyBulletImg.png"));

			enemyBulletImg[i].destRec = new Rectangle(0,0,0,0);
		}
		
		
		for (int i = 0; i < tieLocations.length; i++)
		{
			tieLocations[i] = (int)(Math.round(Math.random() * 560 + 40));
		}
		
		for (int i = 1; i< bulletImg.length; i++)
		{
			bulletImg[i].destRec.y = 620;
		}
		
		
		//imports sounds
		menuMusic = new SoundClip("/sounds/music/menuMusic.mp3", false);
		inGameMusic = new SoundClip("/sounds/music/ingameMusic.mp3", false);
		creditsMusic = new SoundClip("/sounds/music/creditsMusic.mp3", false);
		lossMusic = new SoundClip("/sounds/music/lossMusic.mp3", false);
		explosionSnd = new SoundClip("/sounds/effects/explosionSnd.mp3", false);
		shotSnd = new SoundClip("/sounds/effects/shotSnd.mp3", false);
		
		//Create messages
		messages[0] = "Star Wars infinity";
		messages[1] = "Luke is on his way home from coruscant, where he has managed to";
		messages[2] = "escape in a old jedi star ship. However, the empire has placed";
		messages[3] = "a tracking beacon on his ship and have sent waves of tie fighters";
		messages[4] = "in an attempt to destroy him. Luke intercepted a transmition from ";
		messages[5] = "Lord Vader who said he will deal with skywalker personally";
		messages[6] = "Can you help luke navigate through space and evade the dark lord";
		messages[7] = "It is up to you. The faith of the universe, now lies in your hands.";
		messages[8] = "May the force be with you.";
		helpMessages[0] = "instructions";
		helpMessages[1] = "Move Luke's Ship Using The Left And Right Arrow Keys.";
		helpMessages[2] = "Shoot 3 Times A Second Holding The Space Bar";
		helpMessages[3] = "Avoid Astroids And Tie Bullets. They Will Destroy The Ship.";
		helpMessages[4] = "Collect Ammo Packs Of 20 Every 30 Seconds.";
		helpMessages[5] = "Collect health packs to increase health every 45 seconds";
		helpMessages[6] = "Destroy Meteors And Tie Fighters For Extra Points.";
		helpMessages[7] = "Reload Your Turret By Pressing The R Key. Reloading Takes 5 Seconds";
		helpMessages[8] = "To Win, Beat Darth Vader, The Boss At 3 Minutes.";
		
		for (int i = 0; i < messageLocationsX.length; i++)
		{
			messageLocationsY[i] = 800 + i * 40; 
		}
		
		for (int i = 0; i < helpMessageLocationsX.length; i++)
		{
			helpMessageLocationsY[i] = 800 + i * 40; 
		}
		
		programPhase = 0;
	}
	@Override
	public void Update(GameContainer gc, float deltaTime) 
	{
		//Plays game music
		PlayMusic();
		
		//Gets mouse Position
		mousePos.x = (int) Input.GetMousePos().x;
		mousePos.y = (int) Input.GetMousePos().y;
		
		//Main Menu
		if (programPhase == 0)
		{
			RestartGame();
			if (mousePos.x >= 220 && mousePos.x <= 420 && mousePos.y >= 500 && mousePos.y <= 600 && Input.IsMouseButtonReleased(Input.MOUSE_LEFT))
			{
				programPhase = 3;
			}
			
			if (Input.IsKeyReleased(KeyEvent.VK_H))
			{
				programPhase = 2;
			}
		}
		
		//In Game
		else if (programPhase == 1)
		{		
			Timer();
			UpdateSpecs();
			CheckHealth();
			UserControl();
			Shoot();
			Meteors();
			TieFighter();
			EnemyShooting();
			AmmoPickUp();
			HealthPickUp();
			CheckCollision();
			Reload();
			ScrollingScreen(gc);
		}
		
		//Help Page
		else if (programPhase == 2)
		{
			for (int i = 0; i < helpMessages.length; i++)
			{
				helpMessageLocationsY[i] -= 1;
			}
			
			if (helpMessageLocationsY[8] <= 0)
			{
				if (Input.IsMouseButtonReleased(Input.MOUSE_LEFT))
				{
					programPhase = 1;
				}
			}
		}
		
		//Background Story
		else if (programPhase == 3)
		{
			for (int i = 0; i < messages.length; i++)
			{
				messageLocationsY[i] -= 1;
			}
			
			if (messageLocationsY[8] <= 0)
			{
				if (Input.IsMouseButtonReleased(Input.MOUSE_LEFT))
				{
					programPhase = 1;
				}
			}
		}
		
		//Loss Page
		else if (programPhase == 4)
		{
			if (Input.IsKeyReleased(KeyEvent.VK_SPACE))
			{
				programPhase = 0;
			}
		}
		
		//Win Page
		else if (programPhase == 5)
		{
			if (Input.IsKeyReleased(KeyEvent.VK_SPACE))
			{
				programPhase = 0;
			}
		}
	}
	@Override
	public void Draw(GameContainer gc, Graphics2D gfx) 
	{
		//Main Menu
		if (programPhase == 0)
		{	
			Draw.Sprite(gfx, menuBackgroundImg);
			Draw.Sprite(gfx, titleText);
			Draw.Text(gfx, "Daniel Akselrod", 430, 780, statusFont, Helper.WHITE, 1);
			Draw.FillRect(gfx, 220, 500, 200, 100, Helper.WHITE, 1);
			Draw.Rect(gfx, 220, 500, 200, 100, 10, Helper.BLACK, 1);
			Draw.Text(gfx, "START", 250, 570, bigFont, Helper.BLACK, 1);
			
			Draw.Text(gfx, "Press H for instructions", 100, 700, smallFont, Helper.WHITE, 1);
		}
		
		//In Game
		else if (programPhase == 1)
		{
			Draw.Sprite(gfx, gameBackgroundImg1);
			Draw.Sprite(gfx, gameBackgroundImg2);
			Draw.Sprite(gfx, shipImg);
			Draw.Sprite(gfx, meteorImg);
			Draw.Sprite(gfx, tieImg);
			Draw.Sprite(gfx, ammoImg);
			Draw.Sprite(gfx, darthVaderTieImg);
			Draw.Sprite(gfx, healthKitImg);

			for (int i = 1; i <= 20; i++)
			{
				Draw.Sprite(gfx, bulletImg[i]);
			}
			
			for (int i = 1; i <= 20; i++)
			{
				Draw.Sprite(gfx, darthVaderBulletImg[i]);
			}
			
			for (int i = 1; i <= 20; i++)
			{
				Draw.Sprite(gfx, enemyBulletImg[i]);
			}
			
			Draw.Text(gfx, "Amo: " + String.valueOf(ammoInClip) + "/" + (totalAmmo), 10, 790, statusFont, Helper.WHITE, 1);
			Draw.Text(gfx, "Hitpoints: " + String.valueOf(health), 10, 760, statusFont, Helper.WHITE, 1);
			Draw.Text(gfx, "Score: " + String.valueOf(score), 10, 730, statusFont, Helper.WHITE, 1);


			if (reloading == true)
			{
				Draw.Text(gfx, "Reloading", 140, 790, statusFont, Helper.WHITE, 1);
			}
			
			if (miniExplosionAni.IsAnimating() == true)
			{
				System.out.println("Hi");
				Draw.Sprite(gfx, miniExplosionAni);
			}
		}
		
		//Help Page
		else if (programPhase == 2)
		{
			Draw.Sprite(gfx, spaceImg);
			
			for (int i = 1; i < 9; i++)
			{				
				Draw.Text(gfx, helpMessages[0], 100, helpMessageLocationsY[0], bigFont, Helper.YELLOW, 1);
				Draw.Text(gfx, helpMessages[i], 70, helpMessageLocationsY[i], tinyFont, Helper.YELLOW, 1);
			}
			
			if (helpMessageLocationsY[9] <= 0)
			{
				Draw.Text(gfx, "click anywhere", 100, 500, bigFont, Helper.YELLOW, 1);
				Draw.Text(gfx, "to begin your", 100, 570, bigFont, Helper.YELLOW, 1);
				Draw.Text(gfx, "adventure", 150, 640, bigFont, Helper.YELLOW, 1);

			}
		}
		
		//Story Page
		else if (programPhase == 3)
		{
			Draw.Sprite(gfx, spaceImg);
			
			for (int i = 1; i < 8; i++)
			{				
				Draw.Text(gfx, messages[0], 50, messageLocationsY[0], bigFont, Helper.YELLOW, 1);
				Draw.Text(gfx, messages[i], 70, messageLocationsY[i], tinyFont, Helper.YELLOW, 1);
				Draw.Text(gfx, messages[8], 220, messageLocationsY[8], tinyFont, Helper.YELLOW, 1);
			}
			
			if (messageLocationsY[8] <= 0)
			{
				Draw.Text(gfx, "click anywhere", 100, 500, bigFont, Helper.YELLOW, 1);
				Draw.Text(gfx, "to begin your", 100, 570, bigFont, Helper.YELLOW, 1);
				Draw.Text(gfx, "adventure", 150, 640, bigFont, Helper.YELLOW, 1);

			}
		}
		
		//Death Page
		else if (programPhase == 4)
		{
			Draw.Sprite(gfx, spaceImg);
			Draw.Text(gfx, "You have failed!", 50, 500, bigFont, Helper.WHITE, 1);
			Draw.Text(gfx, "The empire has won!", 25, 600, bigFont, Helper.WHITE, 1);	
			Draw.Text(gfx, "Press space to return to main menu", 150, 700, tinyFont, Helper.WHITE, 1);

		}
		
		//Win Page
		else if (programPhase == 5)
		{
			Draw.Sprite(gfx, spaceImg);
			Draw.Text(gfx, "You have succeded!", 20, 500, bigFont, Helper.WHITE, 1);
			Draw.Text(gfx, "The resistance", 125, 600, bigFont, Helper.WHITE, 1);
			Draw.Text(gfx, "Has Won!", 200, 660, bigFont, Helper.WHITE, 1);	
			Draw.Text(gfx, "Press space to return to main menu", 150, 700, tinyFont, Helper.WHITE, 1);
		}
	}	

	//Pre: N/A
	//Post: N/A
	//Description: The purpose of this is to get input controls from the user
	public static void UserControl()
	{	
		if ((Input.IsKeyDown(KeyEvent.VK_LEFT)) && (Input.IsKeyDown(KeyEvent.VK_RIGHT)))
		{		
		}
		
		else if ((Input.IsKeyDown(KeyEvent.VK_LEFT)) && (shipImg.destRec.x >= 20))
		{
			shipImg.destRec.x -= 3;
		}
		
		else if ((Input.IsKeyDown(KeyEvent.VK_RIGHT)) && (shipImg.destRec.x <= 540))
		{
			shipImg.destRec.x += 3;
		}
	}
	//Pre: N/A
	//Post: N/A
	//Description: Stores a time value in game
	public static void Timer()
	{
		framesPassed += 1;
		
		if (framesPassed % 60 == 0)
		{
			secondsPassed ++;
			score ++;
		}
	}
	//Pre: N/A
	//Post: N/A
	//Description: Lets user shoot.
	public static void Shoot()
	{	
		if ((Input.IsKeyDown(KeyEvent.VK_R) && (Input.IsKeyDown(KeyEvent.VK_SPACE))))
		{	
		}
		
		else if ((Input.IsKeyDown(KeyEvent.VK_SPACE)) && (framesPassed % 20 == 0) & reloading == false && currentShot < 20 && ammoInClip > 0)
		{
			shotSnd.Play();
			currentShot++;
			bulletImg[currentShot].destRec.setRect((int) (shipImg.destRec.x + 25), 620, 20, 30);;
		}
		
		for (int i = 1; i <= currentShot; i++)
		{
			bulletImg[i].destRec.y -= userBulletSpeed;
		}
	}	
	//Pre: N/A
	//Post: N/A
	//Description: Creates meteor obstacles
	public static void Meteors()
	{
		if (score >= (meteorNumber * 6))
		{		
			meteorImg.Rotate(1);
			
			meteorImg.destRec.y += meteorMovementSpeed;
			
			if (meteorImg.destRec.y >= 800)
			{
				meteorNumber += 1;
				meteorImg.destRec.setRect((int)(Math.round(Math.random() * 600 + 20)), -50, 50, 50);
				meteorImg.SetTransparency(1);
			}	
		}
	}	
	
	//Pre: N/A
	//Post: N/A		
	//Description: Checks to see if tthere is any collision
	public static void CheckCollision()
	{
		//Checks Bullet-Meteor Collision
		for (int i = 1; i <= 20; i++)
		{
			if ((bulletImg[i].destRec.x + bulletImg[i].destRec.getWidth() < meteorImg.destRec.x) || 
			(bulletImg[i].destRec.x > meteorImg.destRec.x + meteorImg.destRec.getWidth()) || 
			(bulletImg[i].destRec.y + bulletImg[i].destRec.getHeight() < meteorImg.destRec.y) || 
			(bulletImg[i].destRec.y > meteorImg.destRec.y + meteorImg.destRec.getHeight()))
			{				
			}
				
			else if (meteorImg.GetTransparency() == 1)
			{
				explosionSnd.Play();
				
				bulletImg[i].SetTransparency(0);
				miniExplosionAni.destRec.setRect(meteorImg.destRec.x + 25, meteorImg.destRec.y, 50, 50);
				
				miniExplosionAni.StartAnimation(1);
				meteorImg.SetTransparency(0);
				
				score += 20;
			}			
		}
		
		//Checks Enemy Bullet-Ship Collision
		for (int i = 1; i <= 20; i++)
		{
			if ((enemyBulletImg[i].destRec.x + enemyBulletImg[i].destRec.getWidth() < shipImg.destRec.x) || 
			(enemyBulletImg[i].destRec.x > shipImg.destRec.x + shipImg.destRec.getWidth()) || 
			(enemyBulletImg[i].destRec.y + enemyBulletImg[i].destRec.getHeight() < shipImg.destRec.y) || 
			(enemyBulletImg[i].destRec.y > shipImg.destRec.y + shipImg.destRec.getHeight()))
			{				
			}
				
			else if (enemyBulletImg[i].GetTransparency() == 1 && shipImg.GetTransparency() == 1)
			{
				explosionSnd.Play();

				enemyBulletImg[i].SetTransparency(0);
				miniExplosionAni.destRec.setRect(shipImg.destRec.x + 15, shipImg.destRec.y - 40, 50, 50);
				
				miniExplosionAni.StartAnimation(1);
				
				health -= 1;
			}			
		}	
		
		//Checks Bullet-Bullet Collision
		for (int i = 1; i <= 20; i++)
		{
			for (int j = 1; j <= 20; j++)
			{
			if ((enemyBulletImg[i].destRec.x + enemyBulletImg[i].destRec.getWidth() < bulletImg[j].destRec.x) || 
			(enemyBulletImg[i].destRec.x > bulletImg[j].destRec.x + bulletImg[j].destRec.getWidth()) || 
			(enemyBulletImg[i].destRec.y + enemyBulletImg[i].destRec.getHeight() < bulletImg[j].destRec.y) || 
			(enemyBulletImg[i].destRec.y > bulletImg[j].destRec.y + bulletImg[j].destRec.getHeight()))
			{				
			}
					
			else if (bulletImg[j].GetTransparency() == 1 && enemyBulletImg[i].GetTransparency() == 1)
			{
				explosionSnd.Play();
				enemyBulletImg[i].SetTransparency(0);
				miniExplosionAni.destRec.setRect(bulletImg[j].destRec.x + 10, bulletImg[j].destRec.y - 40, 100, 100);
				
				miniExplosionAni.StartAnimation(1);
				bulletImg[j].SetTransparency(0);
				}			
			}
		}		
		
		//Checks Bullet-Tie Collision
		for (int i = 1; i <= 20; i++)
		{
			if ((bulletImg[i].destRec.x + bulletImg[i].destRec.getWidth() < tieImg.destRec.x) || 
			(bulletImg[i].destRec.x > tieImg.destRec.x + tieImg.destRec.getWidth()) || 
			(bulletImg[i].destRec.y + bulletImg[i].destRec.getHeight() < tieImg.destRec.y) || 
			(bulletImg[i].destRec.y > tieImg.destRec.y + tieImg.destRec.getHeight()))
			{				
			}
				
			else if ((tieImg.GetTransparency() == 1) && bulletImg[i].GetTransparency() == 1)
			{
				explosionSnd.Play();
				bulletImg[i].SetTransparency(0);
				miniExplosionAni.destRec.setRect(tieImg.destRec.x + 25, tieImg.destRec.y, 50, 50);
				
				miniExplosionAni.StartAnimation(1);
				tieImg.SetTransparency(0);
				tieStatus = false;
				
				score += 20;
				tieNumber += 1;
			}			
		}
		
		//Checks Ship-Meteor Collision
		if (((shipImg.destRec.x + shipImg.destRec.getWidth() < meteorImg.destRec.x) || 
		(shipImg.destRec.x > meteorImg.destRec.x + meteorImg.destRec.getWidth()) || 
		(shipImg.destRec.y + shipImg.destRec.getHeight() < meteorImg.destRec.y) || 
		(shipImg.destRec.y > meteorImg.destRec.y + meteorImg.destRec.getHeight())))
		{				
		}
		
		else if (meteorImg.GetTransparency() == 1 && shipImg.GetTransparency() == 1)
		{
			meteorImg.SetTransparency(0);
			explosionSnd.Play();
			
			miniExplosionAni.destRec.setRect(shipImg.destRec.x + 15, shipImg.destRec.y, 50, 50);
			miniExplosionAni.StartAnimation(1);
			
			health -= 1;		
		}
		
		//Checks Ship-Ammo Collision
		for (int i = 1; i <= 20; i++)
		{
			if ((shipImg.destRec.x + shipImg.destRec.getWidth() < ammoImg.destRec.x) || 
			(shipImg.destRec.x > ammoImg.destRec.x + ammoImg.destRec.getWidth()) || 
			(shipImg.destRec.y + shipImg.destRec.getHeight() < ammoImg.destRec.y) || 
			(shipImg.destRec.y > ammoImg.destRec.y + ammoImg.destRec.getHeight()))
			{				
			}
				
			else if (ammoImg.GetTransparency() == 1 && shipImg.GetTransparency() == 1)
			{
				ammoImg.SetTransparency(0);
				totalAmmo += 20;
			}			
		}
		
		//Checks Bullet-Darth Vader Collsion
		for (int i = 1; i <= 20; i++)
		{
			if ((bulletImg[i].destRec.x + bulletImg[i].destRec.getWidth() < darthVaderTieImg.destRec.x) || 
			(bulletImg[i].destRec.x > darthVaderTieImg.destRec.x + darthVaderTieImg.destRec.getWidth()) || 
			(bulletImg[i].destRec.y + bulletImg[i].destRec.getHeight() < darthVaderTieImg.destRec.y) || 
			(bulletImg[i].destRec.y > darthVaderTieImg.destRec.y + darthVaderTieImg.destRec.getHeight()))
			{				
			}
				
			else if ((darthVaderTieImg.GetTransparency() == 1) && bulletImg[i].GetTransparency() == 1)
			{
				explosionSnd.Play();
				bulletImg[i].SetTransparency(0);
				miniExplosionAni.destRec.setRect(darthVaderTieImg.destRec.x, darthVaderTieImg.destRec.y, 100, 100);
				
				miniExplosionAni.StartAnimation(1);
				tieStatus = false;
				
				score += 20;
				darthVaderHealth -= 1;
			}			
		}
		
		//Checks Bullet-Bullet Collision
		for (int i = 1; i <= 20; i++)
		{
			for (int j = 1; j <= 20; j++)
			{
			if ((darthVaderBulletImg[i].destRec.x + darthVaderBulletImg[i].destRec.getWidth() < bulletImg[j].destRec.x) || 
			(darthVaderBulletImg[i].destRec.x > bulletImg[j].destRec.x + bulletImg[j].destRec.getWidth()) || 
			(darthVaderBulletImg[i].destRec.y + darthVaderBulletImg[i].destRec.getHeight() < bulletImg[j].destRec.y) || 
			(darthVaderBulletImg[i].destRec.y > bulletImg[j].destRec.y + bulletImg[j].destRec.getHeight()))
			{				
			}
					
			else if (bulletImg[j].GetTransparency() == 1 && darthVaderBulletImg[i].GetTransparency() == 1)
			{
				explosionSnd.Play();
				darthVaderBulletImg[i].SetTransparency(0);
				miniExplosionAni.destRec.setRect(bulletImg[j].destRec.x + 10, bulletImg[j].destRec.y - 40, 100, 100);
				
				miniExplosionAni.StartAnimation(1);
				bulletImg[j].SetTransparency(0);
				}			
			}
		}		
		
		//Checks Darth Vader Bullet-Ship Collision
		for (int i = 1; i <= 20; i++)
		{
			if ((darthVaderBulletImg[i].destRec.x + darthVaderBulletImg[i].destRec.getWidth() < shipImg.destRec.x) || 
			(darthVaderBulletImg[i].destRec.x > shipImg.destRec.x + shipImg.destRec.getWidth()) || 
			(darthVaderBulletImg[i].destRec.y + darthVaderBulletImg[i].destRec.getHeight() < shipImg.destRec.y) || 
			(darthVaderBulletImg[i].destRec.y > shipImg.destRec.y + shipImg.destRec.getHeight()))
			{				
			}
				
			else if (darthVaderBulletImg[i].GetTransparency() == 1 && shipImg.GetTransparency() == 1)
			{
				explosionSnd.Play();

				darthVaderBulletImg[i].SetTransparency(0);
				miniExplosionAni.destRec.setRect(shipImg.destRec.x + 40, shipImg.destRec.y, 25, 25);
				
				miniExplosionAni.StartAnimation(1);
				
				health -= 1;
			}			
		}	
		
		//Checks Ship-health Collisions
		for (int i = 1; i <= 20; i++)
		{
			if ((shipImg.destRec.x + shipImg.destRec.getWidth() < healthKitImg.destRec.x) || 
			(shipImg.destRec.x > healthKitImg.destRec.x + healthKitImg.destRec.getWidth()) || 
			(shipImg.destRec.y + shipImg.destRec.getHeight() < healthKitImg.destRec.y) || 
			(shipImg.destRec.y > healthKitImg.destRec.y + healthKitImg.destRec.getHeight()))
			{				
			}
				
			else if (healthKitImg.GetTransparency() == 1 && shipImg.GetTransparency() == 1)
			{
				healthKitImg.SetTransparency(0);
				health += 1;
			}			
		}
	}

	public static void ScrollingScreen(GameContainer gc)
	{
		gameBackgroundImg1.destRec.y += scrollSpeed;
		gameBackgroundImg2.destRec.y += scrollSpeed;
		
		if (gameBackgroundImg1.destRec.y >= gameBackgroundImg1.destRec.height)
		{
			gameBackgroundImg1.destRec.y = -gameBackgroundImg1.destRec.height;
		}
		
		else if (gameBackgroundImg2.destRec.y >= gameBackgroundImg2.destRec.height)
		{
			gameBackgroundImg2.destRec.y = -gameBackgroundImg2.destRec.height;
		}
	}

	//Pre: N/A
	//Post: N/A
	//Description: Lets user reload gun
	public static void Reload()
	{
		//System.out.println(totalAmmo);
		ammoInClip = 20-currentShot;

		if ((Input.IsKeyDown(KeyEvent.VK_R) && (Input.IsKeyDown(KeyEvent.VK_SPACE))))
		{	
		}
		
		else if (((currentShot == 20 &&  (bulletImg[20].destRec.getY() + 30 <= 0)) || (Input.IsKeyReleased(KeyEvent.VK_R))) && totalAmmo > 0)
		{
			reloading = true;
		}
		
		if (reloading == true)
		{
			if (reloadTime < reloadSpeed)
			{
				framesPassedSinceReload += 1;
				
				if (framesPassedSinceReload % 60 == 0)
				{
					reloadTime += 1;
				}
			}	
			
			if (reloadTime == reloadSpeed)
			{
				replenishedAmmo = currentShot;
				reloading = false;
				totalAmmo -= replenishedAmmo;
				currentShot = 0;                 
				reloadTime = 0;
				framesPassedSinceReload = 0;
				
				for (int i = 1; i <= 20; i++)
				{
					bulletImg[i].SetTransparency(1);
				}
			}
		}
	}

	//Pre: N/A
	//Post: N/A
	//Description: Creates enemey's
	public static void TieFighter()
	{
		if (framesPassed == tieNumber * 900)
		{
			tieStatus = true;
			tieImg.SetTransparency(1);
			tieImg.destRec.y = -60;
		}
		
		if (tieStatus == true)
		{
			tieImg.destRec.setSize(60, 60);
			tieImg.SetTransparency(1);
			if (tieImg.GetTransparency()  == 1)
			{				
				framesPassedSinceMovement += 1;
				if (framesPassedSinceMovement % 60 == 0)
				{
					movementTime += 1;
				}
				
				if (framesPassedSinceMovement % 240 == 0)
				{
					move = true;
				}
				
				if (tieImg.destRec.y < 20)
				{
					tieImg.destRec.y += 2;
				}
				
				if (move == true)
				{
					if (tieLocations[movementNumber] > tieImg.destRec.x)
					{
						tieImg.destRec.x += 2;

						if (tieLocations[movementNumber] <= tieImg.destRec.x)
						{
							System.out.println("check1");
							move = false;
							movementNumber ++;
						}
					}
					
					else if (tieLocations[movementNumber] < tieImg.destRec.x)
					{
						tieImg.destRec.x -= 2;
						if (tieLocations[movementNumber] >= tieImg.destRec.x)
						{
							System.out.println("check2");

							move = false;
							movementNumber ++;
						}

					}
				}
			}
			
			else if (tieStatus == false)
			{
				for (int i = 0; i < tieLocations.length; i++)
				{
					tieLocations[i] = (int)(Math.round(Math.random() * 560 + 40));
					System.out.println(tieLocations[i]);
				}
				tieImg.SetTransparency(0);
			}
		}
		
		else if (score < tieNumber * 15)
		{
		}
	}

	
	//Pre: N/A
	//Post: N/A		
	//Description: Creates enemy shots
	public static void EnemyShooting()
	{
		//System.out.println(currentEnemyShot);
		if (framesPassed % enemyFireRate == 0 && tieStatus == true)
		{
			System.out.println("Check");
			currentEnemyShot += 1;
			enemyBulletImg[currentEnemyShot].SetTransparency(1);
			enemyBulletImg[currentEnemyShot].destRec.setRect(tieImg.destRec.x + 20, 20, 20, 30);
		}
		
		enemyBulletImg[1].destRec.y += enemyBulletSpeed;
		enemyBulletImg[2].destRec.y += enemyBulletSpeed;

		
		if (tieStatus == false || enemyBulletImg[currentEnemyShot].destRec.y >= 800)
		{
			currentEnemyShot = 1;
			//System.out.println("Reloaded!");
		}
	}
	//Pre: N/A
	//Post: N/A
	//Description: Lets user retrive more ammo
	public static void AmmoPickUp()
	{
		if (secondsPassed >= (ammoNumber * 30))
		{					
			ammoImg.destRec.y += 2;
			
			if (ammoImg.destRec.y >= 800)
			{
				ammoNumber += 1;
				ammoImg.destRec.setRect((int)(Math.round(Math.random() * 560 + 20)), -25, 50, 25);
				ammoImg.SetTransparency(1);
			}	
		}
	}
	//Pre: N/A
	//Post: N/A
	//Description: Lets user retrieve more health
	public static void HealthPickUp()
	{
		if (secondsPassed  >= (healthPickupNumber * 45))
		{					
			healthKitImg.destRec.y += 2;
			
			if (healthKitImg.destRec.y >= 800)
			{
				healthPickupNumber += 1;
				healthKitImg.destRec.setRect((int)(Math.round(Math.random() * 560 + 20)), -30, 40, 30);
				healthKitImg.SetTransparency(1);
			}	
		}
	}
	
	//Pre: N/A
	//Post: N/A
	//Description: Controls in game music
	public static void PlayMusic()
	{
		if (programPhase == 0 || programPhase == 3)
		{
			menuMusic.Play();
		}
		
		else
		{
			menuMusic.Stop();
		}
		
		if (programPhase == 1)
		{
			inGameMusic.Play();
		}
		
		else
		{
			inGameMusic.Stop();
		}
		
		if (programPhase == 2 || programPhase == 5)
		{
			creditsMusic.Play();
		}
		
		else
		{
			creditsMusic.Stop();
		}
		
		if  (programPhase == 4)
		{
			lossMusic.Play();
		}
		
		else
		{
			lossMusic.Stop();
		}
	}
	
	//Pre: N/A
	//Post: N/A
	//Description: Makes game more difficult over time
	public static void UpdateSpecs()
	{
		if (secondsPassed == 45)
		{
			userBulletSpeed = 10;
			enemyBulletSpeed = 8;
			enemyMovementSpeed = 4;
			meteorMovementSpeed = 8;
			enemyFireRate = 120;
			reloadSpeed = 4;
		}
		
		if (secondsPassed == 90)
		{
			userBulletSpeed = 12;
			enemyBulletSpeed = 10;
			enemyMovementSpeed = 5;
			meteorMovementSpeed = 10;
			enemyFireRate = 100;
			reloadSpeed = 3;
		}
		
		if (secondsPassed == 135)
		{
			userBulletSpeed = 14;
			enemyBulletSpeed = 12;
			enemyMovementSpeed = 6;
			meteorMovementSpeed = 12;
			enemyFireRate = 100;
			reloadSpeed = 2;
		}
		
		if (secondsPassed >= 180)
		{
			
			userBulletSpeed = 16;
			enemyBulletSpeed = 14;
			enemyMovementSpeed = 7;
			meteorMovementSpeed = 12;
			enemyFireRate = 100;
			reloadSpeed = 2;
			DarthVader();
			DarthVaderShooting();
		}
		
		if (darthVaderTieImg.GetTransparency() == 0)
		{
			framesPassedSinceWin ++;
			
			if (framesPassedSinceWin == 1)
			{
				miniExplosionAni.destRec.setRect(darthVaderTieImg.destRec.x, darthVaderTieImg.destRec.y, 100, 100);
				explosionSnd.Play();
			}
			
			if (framesPassedSinceWin == 120)
			{
				programPhase = 5;
			}
		}
	}
	
	//Pre: N/A
	//Post: N/A
	//Description: Checks to see if user is still alive
	public static void CheckHealth()
	{
		if (health <= 0)
		{
			shipImg.SetTransparency(0);
			framesPassedSinceDeath ++; 
			
			miniExplosionAni.destRec.setRect(shipImg.destRec.x - 10, shipImg.destRec.y + 80, 100, 100);
			
			if (framesPassedSinceDeath == 1)
			{
				miniExplosionAni.StartAnimation(1);
				explosionSnd.Play();
			}
			
			if (framesPassedSinceDeath == 100)
			{
				programPhase = 4;
			}
		}
		
		
	}
	//Pre: N/A
	//Post: N/A
	//Description: Creates th boss obstacle
	public static void DarthVader()
	{
		darthVaderTieImg.destRec.height = 100;
		darthVaderTieImg.destRec.width = 100;

		if (darthVaderHealth <= 0)
		{
			darthVaderTieImg.SetTransparency(0);
		}
		if (darthVaderTieImg.GetTransparency() == 1)
		{
			if (framesPassed % 300 == 0)
			{
				darthVaderMovement = true;
			}	
			
			if (darthVaderMovement == true)
			{
				if (darthVaderTieImg.destRec.x < shipImg.destRec.x)
				{
					darthVaderTieImg.destRec.x += 2;
					
					if (darthVaderTieImg.destRec.x >= shipImg.destRec.x)
					{
						darthVaderMovement = false;
					}
					
				}
				
				else if (darthVaderTieImg.destRec.x > shipImg.destRec.x)
				{
					darthVaderTieImg.destRec.x -= 2;
					
					if (darthVaderTieImg.destRec.x <= shipImg.destRec.x)
					{
						darthVaderMovement = false;
					}
				}
			}
		}
	}
	
	//Pre: N/A
	//Post: N/A		
	//Description: Lets the boss shoot
	public static void DarthVaderShooting()
	{	
		if (darthVaderTieImg.GetTransparency() == 1 && framesPassed % darthVaderFireRate == 0 && darthVaderShot < 20)
		{
			darthVaderShot ++;
			darthVaderBulletImg[darthVaderShot].SetTransparency(1);
			darthVaderBulletImg[darthVaderShot].destRec.setRect(darthVaderTieImg.destRec.x + 40, 20, 20, 30);
			
			if (darthVaderShot == 20 && darthVaderBulletImg[darthVaderShot].destRec.y >=800)
			{
				darthVaderShot = 1;
			}
		}
		
		for (int i = 1; i <= darthVaderShot; i++)
		{
			darthVaderBulletImg[i].destRec.y += enemyBulletSpeed;
		}
	}

	public static void RestartGame()
	{
		gameBackgroundImg1.destRec = new Rectangle(0,0,640,800);
		gameBackgroundImg2.destRec = new Rectangle(0,-800,640,800);	
		miniExplosionAni.destRec = new Rectangle(0, 0, 0, 0);
		titleText.destRec = new Rectangle (50, 200, 540, 200);
		meteorImg.destRec.x = (int) (Math.round(Math.random() * 500 + 50));
		meteorImg.destRec.y = -50;
		tieImg.destRec = new Rectangle (tieLocations[0], 20, 0, 0);
		tieImg.SetTransparency(1);
		spaceImg.destRec = new Rectangle(0,0,640,800);
		darthVaderTieImg.destRec = new Rectangle((int)(Math.round(Math.random() * 500 + 50)), 20, 0, 0);
		menuBackgroundImg.destRec = new Rectangle(0,0,640,800);
		healthKitImg.destRec = new Rectangle((int)(Math.round(Math.random() * 500 + 50)), -30, 40, 30);
		
		for (int i = 1; i < bulletImg.length; i++)
		{
			bulletImg[i].destRec = new Rectangle(0,0,0,0);
			
			darthVaderBulletImg[i] = new SpriteSheet(LoadImage.FromFile("/images/sprites/enemyBulletImg.png"));
			darthVaderBulletImg[i].destRec = new Rectangle(0,0,0,0);
		}	
		
		for (int i = 0; i < enemyBulletImg.length; i++)
		{
			enemyBulletImg[i].destRec = new Rectangle(0,0,0,0);
		}
		
		
		for (int i = 0; i < tieLocations.length; i++)
		{
			tieLocations[i] = (int)(Math.round(Math.random() * 560 + 40));
		}
		
		for (int i = 1; i< bulletImg.length; i++)
		{
			bulletImg[i].destRec.y = 620;
		}
		
		
		for (int i = 0; i < messageLocationsX.length; i++)
		{
			messageLocationsY[i] = 800 + i * 40; 
		}
		
		for (int i = 0; i < helpMessageLocationsX.length; i++)
		{
			helpMessageLocationsY[i] = 800 + i * 40; 
		}
		
		 healthPickupNumber = 1;
		 framesPassedSinceReload = 0;
		 reloadTime= 0;
		 replenishedAmmo= 0;
		 framesPassedSinceWin= 0;
		 framesPassedSinceMovement= 0;
		 movementTime= 0;
		 introFramesPassed= 0;
		 introSecondsPassed= 0;	
		 framesPassed= 0;
		 secondsPassed= 0;
		 programPhase= 0;	
		 scrollSpeed = 5;
		 darthVaderHealth = 10;
		 totalReplacedBullets= 0;
		 health = 10;
		 shotsFired= 0;
		 score = 0;
		 currentShot= 0;	
		 currentEnemyShot = 0;
		 meteorNumber = 1;	
		 tieNumber = 1;
		 currentTie= 0;
		 userBulletSpeed = 8;
		 userShipSpeed = 3;	
		 enemyBulletSpeed = 6;
		 enemyMovementSpeed = 2;	
		 meteorMovementSpeed = 4;	
		 movementNumber = 1;
		 totalAmmo = 20;
		 ammoNumber = 1;
		 enemyFireRate = 150;	
		 reloadSpeed = 5;
		 framesPassedSinceDeath= 0;
		 darthVaderFireRate = 60;
		 darthVaderShot= 0;
		 
		 tieImg.SetTransparency(1);
		 shipImg.SetTransparency(1);
		 darthVaderTieImg.SetTransparency(1);
		 meteorImg.SetTransparency(1);

		 for (int i = 1; i <20; i++)
		 {
			bulletImg[i].SetTransparency(1);
			enemyBulletImg[i].SetTransparency(1);
			darthVaderBulletImg[i].SetTransparency(1);

		 }
		 
		programPhase = 0;
	}
}