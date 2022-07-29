package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {
  
  DatabaseAccessor db = new DatabaseAccessorObject();

  public static void main(String[] args) {
    FilmQueryApp app = new FilmQueryApp();
//		app.test();
    app.launch();
  }

  private void test() throws SQLException {
    Film film = db.findFilmById(1);
    System.out.println(film);
  }

  private void launch() {
    Scanner sc = new Scanner(System.in);
    
    startUserInterface(sc);
    boolean switchTrigger=true;
	while(switchTrigger) {
	int userInput= menu(sc);
	switch(userInput) {
	case 1:
		//Search by id:
			int userId=getID(sc);
		try {
			Film idFilm=db.findFilmById(userId);
			if(idFilm!=null) {
			System.out.println(idFilm);
			
			List<Film> listFilm= new ArrayList<>();
			listFilm.clear();
			listFilm.add(idFilm);
			
			subMenu(sc, listFilm);
			}else {
				System.out.println("That id does not go to one of our films");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		break;
	case 2:
		//search by keyword
			String userKeyword=getKeyword(sc);
		try {
			List<Film> keywordFilm= db.findFilmByKeyword(userKeyword);
			if(!keywordFilm.isEmpty()) {
				System.out.println(keywordFilm);
				subMenu(sc, keywordFilm);
				}else {
					System.out.println("We do not have a film that uses that keyword");
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		break;
	case 3:
		System.out.println("Goodbye");
		switchTrigger=false;
		break;
	}
	}
    
    sc.close();
  }

  private void startUserInterface(Scanner input) {
    
  }
  private int menu(Scanner sc) {
		
		boolean inputTrigger=true;
		int userInput=0;
		while(inputTrigger) {
		System.out.println("-----------------------------------------");
		System.out.println("How would you like to search for a movie?");
		System.out.println("1) Look up a film by its id");
		System.out.println("2) Look up a film by a search keyword.");
		System.out.println("3) Exit");
		System.out.println("-----------------------------------------");
	
		try {
			userInput= sc.nextInt();
			sc.nextLine();
			if(userInput>=1 && userInput<=3) {
				inputTrigger=false;
			}else {
				System.out.println("That was not a readable response");
			}
		}catch(Exception e) {
			sc.nextLine();
			System.out.println("That was not a readable response. Please type in a number between 1-3");
		}
		
		}
		
		return userInput;
	}
	private int getID(Scanner sc) {
		boolean inputTrigger=true;
		int userInput=0;
		while(inputTrigger) {
		System.out.println("Please give me the id number that you would like.");
		try {
			userInput= sc.nextInt();
				inputTrigger=false;
			
		}catch(Exception e) {
			System.out.println("That was not a readable response. Please try again.");
		}
		
		}
		return userInput;
	}
	
	private String getKeyword(Scanner sc) {
		System.out.println("What keyword would you like to search?");
		String userInput= sc.nextLine();
		String keyword= "%" + userInput+ "%";
		return keyword;
	}
	
	public void subMenu(Scanner sc, List<Film> film) {
		boolean inputTrigger=true;
		int userInput=0;
		while(inputTrigger) {
		System.out.println("-----------------------------------------");
		System.out.println("Would you like to:");
		System.out.println("1) Return to menu");
		System.out.println("2) View all details");
		System.out.println("-----------------------------------------");
	
		try {
			userInput= sc.nextInt();
			sc.nextLine();
			if(userInput>=1 && userInput<=3) {
				inputTrigger=false;
			}else {
				System.out.println("That was not a readable response");
			}
		}catch(Exception e) {
			sc.nextLine();
			System.out.println("That was not a readable response. Please type in either 1 or 2");
		}
		
		}
		
		switch(userInput) {
		case 1:
			break;
		case 2:
			for (Film film2 : film) {
				System.out.println(film2.toString2());
			}
			break;
		}
	}

}
