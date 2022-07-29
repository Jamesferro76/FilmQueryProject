package com.skilldistillery.filmquery.entities;

import java.util.Objects;

public class Actor {
	private int Id;
	private String firstName;
	private String lastName;

	public Actor(int actorId, String firstName, String lastName) {
		super();
		this.Id = actorId;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Actor() {
		super();
	}

	public int getId() {
		return Id;
	}

	public void setId(int actorId) {
		this.Id = actorId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Id, firstName, lastName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Actor other = (Actor) obj;
		return Id == other.Id && Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName);
	}

	@Override
	public String toString() {
		return "Actor [Id=" + Id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	
	

}
